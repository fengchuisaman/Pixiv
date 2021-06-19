package cn.youngbear.service;

import cn.youngbear.pojo.Constant;
import cn.youngbear.pojo.PicPopularPermanent;
import cn.youngbear.utils.PixivLiteUtils.PixivLiteUtil;
import cn.youngbear.utils.util.RedisUtils;
import cn.youngbear.utils.util.Utils;
import com.alibaba.fastjson.JSON;
import org.htmlcleaner.XPatherException;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BaseService {
    private final HashMap<String,String> propertiesMap = new HashMap();
    private HashMap<String,String> dataMap = new HashMap();

    private WebService  webService = new WebService();
    private RedisService redisService= new RedisService();
    /**
     * 初始化，把配置文件信息 写入类
     */
    public void init() {
        Properties prop = new Properties();
        try {
            //读取属性文件config.properties
            prop.load(this.getClass().getResourceAsStream("/config.properties"));
            Iterator<String> it = prop.stringPropertyNames().iterator();
            Constant constant = new Constant();
            Class<?> clz = constant.getClass();
            while (it.hasNext()) {
                String key = it.next();
                propertiesMap.put(key, prop.getProperty(key));
                try {
                    Field modifiersField = clz.getDeclaredField(key);
                    modifiersField.setAccessible(true);
                    modifiersField.set(null,prop.getProperty(key));
                }catch (NoSuchFieldException e){System.out.println(e);}

            }
            Utils.setDownloadPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 选择功能
     * @return
     */
    public String chooseUrl() {
        Scanner sc = new Scanner(System.in);
        int choose;
        System.out.println("=============================");
        System.out.println("0、获取推荐信息");
        System.out.println("1、获取日榜信息");
        System.out.println("2、获取周榜信息");
        System.out.println("3、获取月榜信息");
        System.out.println("4、获取男生榜信息");
        System.out.println("5、获取女生榜信息");
//        System.out.println("6、输入Pid，图片Id下载");
        System.out.println("6、获取原创榜信息");
        System.out.println("7、输入关键字，抓取图片");
//        System.out.println("8、输入画师Id");
        System.out.println("8、根据你的收藏夹下载");
        System.out.println("9、恢复你的下载进度(需要有redis支持)");
//        System.out.println("9、r18");
//        System.out.println("10、r18g");
        System.out.println("============================");
        try {
            choose = sc.nextInt();
//            choose=3;
            if (choose == 0) {
                return "recommend";
            }
            if (choose == 1) {
                return "daily";
            }
            if (choose == 2) {
                return "week";
            }
            if (choose == 3) {
                return "month";
            }
            if (choose == 4) {
                return "Male";
            }
            if (choose == 5) {
                return "female";
            }
            if (choose == 6) {
                return "original";
            }
            if (choose == 7) {
                System.out.println("输入关键字");
                String keyWord = sc.next();
                dataMap.put("word",keyWord);
                return "word";
            }
            if (choose == 8) {
                Map<String, List<String>> picMap = webService.getPicMap(new ArrayList<>(webService.getFollowPerson().keySet()));
                chooseLove(picMap);
                return "love";
            }
            if (choose == 9) {
                resume();
                return "r18";
            }
            if (choose == 10) {
                return "r18g";
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请输入正确数字");
        } finally {
            sc.close();
        }
        return null;
    }


    /**
     * 如果选择了下载关注人，则会判断是否限制下载数（限制每个画师的下载数量）
     * 以后会考虑是不是优先考虑 创建日期
     * @throws XPatherException
     * @throws IOException
     * @throws InterruptedException
     */
    private void chooseLove(Map<String, List<String>> picMap) throws XPatherException {
        HashMap<String, String> headerMap = webService.getHeader(null);
        for (String authId : picMap.keySet()) {
            List<String> picList = picMap.get(authId);
            //如果下载图片 并且 downloadLimitForLove 设置了值，并且不为-1 那么限制下载图片数量
            if(Boolean.valueOf(Constant.isDownLoadPic)
                    &&Constant.downloadLimitForLove!=null && Integer.valueOf(Constant.downloadLimitForLove)!=-1){
                if(picList.size()>Integer.valueOf(Constant.downloadLimitForLove)){
                    picList = picList.subList(0,Integer.valueOf(Constant.downloadLimitForLove));
                }
            }
            for (String picId : picList) {
                Map<String, Object> returnMap = webService.getPicPojo(picId);
                boolean isDownLoadSuccess = false;
                if (Boolean.valueOf(Constant.isDownLoadPic)) {
                    isDownLoadSuccess =  Utils.downloadPic(returnMap, headerMap);
                }
                //如果开启了redis，那么将要删除redis里面的值，保证下次能够继续下载
                if(Boolean.valueOf(Constant.isDownLoadPic) && Boolean.valueOf(Constant.isSaveDataToRedis) && isDownLoadSuccess){
                    String authorId = ((PicPopularPermanent)returnMap.get("picPopularPermanent")).getAuthorId();
                    redisService.deletePic(authorId,picId);
                }
            }
        }
    }

    public void resume() throws XPatherException, InterruptedException, IOException {
        Jedis jedis = RedisUtils.getJedis();
        jedis.select(2);
        Long dbSize = jedis.dbSize();
        if(dbSize == 0){
            return ;
        }
        Map<String, List<String>> picMap = redisService.resumeAuthorPicId(false);
        chooseLove(picMap);
    }
}

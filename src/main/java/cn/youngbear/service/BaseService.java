package cn.youngbear.service;

import cn.youngbear.pojo.Constant;
import cn.youngbear.utils.PixivLiteUtils.PixivLiteUtil;
import cn.youngbear.utils.util.Utils;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BaseService {
    private PixivLiteUtil pixivLiteUtil = new PixivLiteUtil();
    private Utils utils = new Utils();
    private final HashMap<String,String> propertiesMap = new HashMap();
    private static int totalDownloadCount = 0;
    private SaveToFile saveToFile = new SaveToFile();
    private HashMap<String,String> dataMap = new HashMap();
    private HashMap<String,String> headerMap = new HashMap(8);

    /**
     * 初始化，把配置文件信息 写入类
     */
    public void init() {
        Properties prop = new Properties();
        try {
            //读取属性文件a.properties
            prop.load(this.getClass().getResourceAsStream("/config.properties"));
//            prop.load(new InputStreamReader(this.getClass().getResourceAsStream("/config.properties"), "UTF-8"));
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
//        System.out.println("9、r18");
//        System.out.println("10、r18g");
        System.out.println("============================");
        try {
            choose = sc.nextInt();
//            choose=3;
            if (choose == 0) {
                this.begin("recommend",dataMap);
                return "recommend";
            }
            if (choose == 1) {
                this.begin("daily",dataMap);
                return "daily";
            }
            if (choose == 2) {
                this.begin("week",dataMap);
                return "week";
            }
            if (choose == 3) {
                this.begin("month",dataMap);
                return "month";
            }
            if (choose == 4) {
                this.begin("Male",dataMap);
                return "Male";
            }
            if (choose == 5) {
                this.begin("female",dataMap);
                return "female";
            }
            if (choose == 6) {
                this.begin("original",dataMap);
                return "original";
            }
            if (choose == 7) {
                System.out.println("输入关键字");
                String keyWord = sc.next();
                dataMap.put("word",keyWord);
                this.begin("word",dataMap);
                return "word";
            }
            if (choose == 8) {
                this.begin("love",dataMap);
                return "love";
            }
            if (choose == 9) {
                this.begin("r18",dataMap);
                return "r18";
            }
            if (choose == 10) {
                this.begin("r18g",dataMap);
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
     *初始化URL
     * @param type
     * @param dataMap
     */
    public void begin(String type,Map<String,String> dataMap) {
        //构建请求头
        this.buildHeaderMap();
        String Url;
        switch (type) {
            case "daily":
                Url = Constant.baseUrl + "day";
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dataMap.put("path", "Day\\" + df.format(LocalDateTime.now()));
                getPicForBase(headerMap, Url, dataMap);
                break;
            case "week":
                Url = Constant.baseUrl + "week";
                dataMap.put("path", "Week\\" + LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-w")));
                getPicForBase(headerMap, Url, dataMap);
                break;
            case "month":
                Url = Constant.baseUrl + "month";
                dataMap.put("path", "Month\\" + LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-M")));
                getPicForBase(headerMap, Url, dataMap);
                break;
            case "Male":
                Url = Constant.baseUrl + "day_male";
                dataMap.put("path", "Male");
                getPicForBase(headerMap, Url, dataMap);
                break;
            case "female":
                Url = Constant.baseUrl + "day_female";
                dataMap.put("path", "Female");
                getPicForBase(headerMap, Url, dataMap);
                break;
            case "original":
                Url = Constant.baseUrl + "week_original";
                dataMap.put("path", "Orginal");
                getPicForBase(headerMap, Url, dataMap);
                break;
            case "r18":
                Url = Constant.baseUrl + Constant.timeSpan + "_r18";
                dataMap.put("path", "R18");
                getPicForBase(headerMap, Url, dataMap);
                break;
            case "r18g":
                Url = Constant.baseUrl + Constant.timeSpan + "_r18g";
                dataMap.put("path", "R18g");
                getPicForBase(headerMap, Url, dataMap);
                break;
            case "word":
                Url = Constant.wordUrl;
                dataMap.put("path", "Word\\" + dataMap.get("word"));
                getPicForBase(headerMap, Url, dataMap);
                break;
            case "authId":
                Url = Constant.authIdUrl;
                dataMap.put("path", "AuthId");
                getPicForBase(headerMap, Url, dataMap);
                break;
            case "love":
                dataMap.put("path", "Love");
                getLovePic(headerMap);
                break;
            case "recommend":
                Url = Constant.recommendUrl;
                dataMap.put("path", "Recommend");
                getPicForBase(headerMap, Url, dataMap);
                break;
        }
    }


    /**
     * 获取基本的操作（每日 每周 每月 推荐……）作品
     * @param headerMap
     * @param Url
     * @param dataMap
     */
    public void getPicForBase(Map<String, String> headerMap, String Url, Map<String, String> dataMap) {
        if (Url.contains("&word=") || dataMap.get("word") != null) {
            Url = Url + dataMap.get("word");
            if (propertiesMap.get("wordLimit") != null) {
                Url = Url + "%20" + propertiesMap.get("wordLimit") + "users%E5%85%A5%E3%82%8A";
            } else {
                Url = Url + "%205000users%E5%85%A5%E3%82%8A";
            }
        }
        List picList = new LinkedList();
        while (true) {
            String result = Utils.sendGet(Url, Boolean.valueOf(Constant.useProxy),headerMap,null);
            Map resultMap = JSON.parseObject(result, Map.class);
            List<Map> list = (List) resultMap.get("illusts");
            for (int i = 0; i < list.size(); i++) {
                if (totalDownloadCount++ <= Integer.valueOf(Constant.downloadLimit)) {
                    if (list != null) {
                        picList.add(list.get(i));
                    }
                }
            }
            if (totalDownloadCount >= Integer.valueOf(Constant.downloadLimit)) {
                break;
            }
            //如果没有下一页，直接退出
            if (resultMap.get("next_url") != null) {
                Url = (String) resultMap.get("next_url");
                Url = Url.replace("https://","http://").replace("app-api.pixiv.net","api.pixivlite.com:8091");
            } else {
                break;
            }
        }
        // 选择方式 下载还是存储
        if("true".equals(Constant.isDownLoadPic)){
            List<Map<String, String>> picNameUrlList = utils.chageToDownloadUrl(picList);
            saveToFile.baseDownloadPic(headerMap, picNameUrlList,dataMap.get("path"));
        }
        if("".equals(Constant.isSaveDataToMysql)){
//            saveToDB
        }
        System.out.println("完成");
    }


    /**
     * 获取关注人的作品
     * @param headerMap
     */
    public void getLovePic(Map<String, String> headerMap) {
        String authName ="";
        List picList = new LinkedList();
        List<String> authIdList = new ArrayList<>();
        String loveAuthUrl = Constant.loveAuthUrl+Constant.loginUserId;
        String result = Utils.sendGet(loveAuthUrl, Boolean.valueOf(Constant.useProxy),headerMap,null);
        Map resultMap = JSON.parseObject(result, Map.class);
        List<Map> list = (List) resultMap.get("user_previews");
        for (Map map : list) {
            String authId = String.valueOf(((Map) map.get("user")).get("id"));
            if (authId != null && !"".equals(authId)) {
                authIdList.add(authId);
            }
        }
//        for (String authId : authIdList) {
        for (int j=0 ;j<authIdList.size();j++) {
            picList = new LinkedList();
            String  authId = authIdList.get(j);
            String userPicUrl = Constant.userPicUrl+ authId;
            totalDownloadCount=0;
            while (true) {
                String picListresult = Utils.sendGet(userPicUrl, Boolean.valueOf(Constant.useProxy),headerMap,null);
                resultMap = JSON.parseObject(picListresult, Map.class);
                List<Map> authPicList = (List) resultMap.get("illusts");
                if(authPicList == null || authPicList.get(0)==null | authPicList.get(0).get("user") == null){
                    break;
                }
                authName = String.valueOf(((Map) authPicList.get(0).get("user")).get("name"));
                for (int i = 0; i < authPicList.size(); i++) {
                    if (totalDownloadCount++ <= Integer.valueOf(Constant.downloadLimit)) {
                        if (authPicList.get(i) != null) {
                            picList.add(authPicList.get(i));
                        }
                    }else{
                        break;
                    }

                }
                if (totalDownloadCount >= Integer.valueOf(Constant.downloadLimit)) {
                    System.out.println("作者"+authName+"数量达到"+totalDownloadCount+",停止爬取");
                    break;
                }
                //如果没有下一页，直接退出
                if (resultMap.get("next_url") != null) {
                    userPicUrl = (String) resultMap.get("next_url");
                    userPicUrl = userPicUrl.replace("https:","http:").replace("app-api.pixiv.net","api.pixivlite.com:8091");
                    //睡觉 防止调用被拌
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        System.out.println("线程睡觉 睡死了");
                    }
                } else {
                    break;
                }

            }
            // 选择方式 下载还是存储
            if("true".equals(Constant.isDownLoadPic)){
                List<Map<String, String>> picNameUrlList = utils.chageToDownloadUrl(picList);
                saveToFile.baseDownloadPic(headerMap, picNameUrlList,"love\\"+authName+"\\");
                System.out.println("下载文件完成，下载总数为"+picNameUrlList.size());
            }
            if("".equals(Constant.isSaveDataToMysql)){
//            saveToDB
            }


        }

    }

    /**
     * 设置请求头
     */
    public void buildHeaderMap(){
        headerMap.put("User-Agent",Constant.userAgent);
        headerMap.put("Accept","*/*");
        headerMap.put("Accept-Encoding","gzip, deflate, br");
        headerMap.put("Connection","keep-alive");
        headerMap.put("App-OS","android");
        headerMap.put("App-OS-Version","6.0.1");
        headerMap.put("App-Version","5.0.118");
        headerMap.put("x-client-hash",Constant.xClientHash);
        headerMap.put("x-client-time",Constant.xClientTime);
        headerMap.put("Authorization",Constant.token);
        headerMap.put("Referer","https://www.pixiv.net/");
    }
}

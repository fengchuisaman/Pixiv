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
    private HashMap<String,String> dataMap = new HashMap();
    private HashMap<String,String> headerMap = new HashMap(8);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 选择功能
     * @return
     */
//    public String chooseUrl() {
//        Scanner sc = new Scanner(System.in);
//        int choose;
//        System.out.println("=============================");
//        System.out.println("0、获取推荐信息");
//        System.out.println("1、获取日榜信息");
//        System.out.println("2、获取周榜信息");
//        System.out.println("3、获取月榜信息");
//        System.out.println("4、获取男生榜信息");
//        System.out.println("5、获取女生榜信息");
////        System.out.println("6、输入Pid，图片Id下载");
//        System.out.println("6、获取原创榜信息");
//        System.out.println("7、输入关键字，抓取图片");
////        System.out.println("8、输入画师Id");
//        System.out.println("8、根据你的收藏夹下载");
////        System.out.println("9、r18");
////        System.out.println("10、r18g");
//        System.out.println("============================");
//        try {
//            choose = sc.nextInt();
////            choose=3;
//            if (choose == 0) {
//                this.begin("recommend",dataMap);
//                return "recommend";
//            }
//            if (choose == 1) {
//                this.begin("daily",dataMap);
//                return "daily";
//            }
//            if (choose == 2) {
//                this.begin("week",dataMap);
//                return "week";
//            }
//            if (choose == 3) {
//                this.begin("month",dataMap);
//                return "month";
//            }
//            if (choose == 4) {
//                this.begin("Male",dataMap);
//                return "Male";
//            }
//            if (choose == 5) {
//                this.begin("female",dataMap);
//                return "female";
//            }
//            if (choose == 6) {
//                this.begin("original",dataMap);
//                return "original";
//            }
//            if (choose == 7) {
//                System.out.println("输入关键字");
//                String keyWord = sc.next();
//                dataMap.put("word",keyWord);
//                this.begin("word",dataMap);
//                return "word";
//            }
//            if (choose == 8) {
//                this.begin("love",dataMap);
//                return "love";
//            }
//            if (choose == 9) {
//                this.begin("r18",dataMap);
//                return "r18";
//            }
//            if (choose == 10) {
//                this.begin("r18g",dataMap);
//                return "r18g";
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("请输入正确数字");
//        } finally {
//            sc.close();
//        }
//        return null;
//    }

    /**
     *初始化URL
     * @param type
     * @param dataMap
     */
//    public void begin(String type,Map<String,String> dataMap) {
//        //构建请求头
//        this.buildHeaderMap();
//        String Url;
//        switch (type) {
//            case "daily":
//                Url = Constant.baseUrl + "day";
//                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                dataMap.put("path", "Day\\" + df.format(LocalDateTime.now()));
//                getPicForBase(headerMap, Url, dataMap);
//                break;
//            case "week":
//                Url = Constant.baseUrl + "week";
//                dataMap.put("path", "Week\\" + LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-w")));
//                getPicForBase(headerMap, Url, dataMap);
//                break;
//            case "month":
//                Url = Constant.baseUrl + "month";
//                dataMap.put("path", "Month\\" + LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-M")));
//                getPicForBase(headerMap, Url, dataMap);
//                break;
//            case "Male":
//                Url = Constant.baseUrl + "day_male";
//                dataMap.put("path", "Male");
//                getPicForBase(headerMap, Url, dataMap);
//                break;
//            case "female":
//                Url = Constant.baseUrl + "day_female";
//                dataMap.put("path", "Female");
//                getPicForBase(headerMap, Url, dataMap);
//                break;
//            case "original":
//                Url = Constant.baseUrl + "week_original";
//                dataMap.put("path", "Orginal");
//                getPicForBase(headerMap, Url, dataMap);
//                break;
//            case "r18":
//                Url = Constant.baseUrl + Constant.timeSpan + "_r18";
//                dataMap.put("path", "R18");
//                getPicForBase(headerMap, Url, dataMap);
//                break;
//            case "r18g":
//                Url = Constant.baseUrl + Constant.timeSpan + "_r18g";
//                dataMap.put("path", "R18g");
//                getPicForBase(headerMap, Url, dataMap);
//                break;
//            case "word":
//                Url = Constant.wordUrl;
//                dataMap.put("path", "Word\\" + dataMap.get("word"));
//                getPicForBase(headerMap, Url, dataMap);
//                break;
//            case "authId":
//                Url = Constant.authIdUrl;
//                dataMap.put("path", "AuthId");
//                getPicForBase(headerMap, Url, dataMap);
//                break;
//            case "love":
//                dataMap.put("path", "Love");
//                getLovePic(headerMap);
//                break;
//            case "recommend":
//                Url = Constant.recommendUrl;
//                dataMap.put("path", "Recommend");
//                getPicForBase(headerMap, Url, dataMap);
//                break;
//        }
//    }

}

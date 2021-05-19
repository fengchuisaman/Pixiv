package cn.youngbear;

import cn.youngbear.pojo.Author;
import cn.youngbear.pojo.PicPopularPermanent;
import cn.youngbear.service.BaseService;
import cn.youngbear.utils.PixivLiteUtils.PixivLiteUtil;
import cn.youngbear.utils.PixivWebUtils.WebService;
import cn.youngbear.utils.util.Utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.htmlcleaner.XPatherException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Pixiv {
    public static void main(String args[]) throws IOException, XPatherException, InterruptedException {

        HashMap headerMap = new HashMap();
        headerMap.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
        headerMap.put("Referer","https://www.pixiv.net/");

//        PixivLiteUtil pixivLiteUtil = new PixivLiteUtil();
        BaseService baseService = new BaseService();
        baseService.init();
//        pixivLiteUtil.pixivLogin();
//        // 功能分类
//        baseService.chooseUrl();
//        //登录
        new Utils().init();
        WebService webService = new WebService() ;
        HashMap<String,String> map =  new HashMap<>();
//        map.put("r18","OPEN");
//        map.put("r18g","OPEN");
        webService.changeR18(map);

    }

    public static void main1(String[] args) throws XPatherException, ParseException, IOException, InterruptedException {
        BaseService baseService = new BaseService();
        WebService  webService = new WebService();
        baseService.init();
        Set<String> FollowPersonSet = webService.getFollowPerson().keySet();
        Map<String, List<String>> picMap = webService.getPicMap(new ArrayList<>(FollowPersonSet));
        picMap.remove("8886419");
        picMap.remove("61015614");
        picMap.remove("15387072");
        for(String authId : picMap.keySet()){
            List<String> picList = picMap.get(authId);
            for(String picId:picList){
                Map<String, Object> returnMap = webService.getPicPojo(picId);
                PicPopularPermanent picPopularPermanent = (PicPopularPermanent) returnMap.get("picPopularPermanent");
                Author author = (Author) returnMap.get("author");
                String picdownloadUrl = picPopularPermanent.getPicUrl();
                String picName = picPopularPermanent.getPicName();
                String authName = author.getAuthorName();
                File file = new File("E:\\var\\",authName+"\\"+picName+".jpg");
                if(file.exists()){
                    System.out.println("E:\\var\\"+authName+"\\"+picName+".jpg"+"文件名已存在");
                }else{
                    Utils.downloadUseHttpClient(webService.getHeader(null),picdownloadUrl,"E:\\var\\",authName+"\\"+picName+".jpg");
                    Thread.sleep(10000);
                }

            }
        }
    }

}

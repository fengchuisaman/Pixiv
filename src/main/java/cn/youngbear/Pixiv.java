package cn.youngbear;

import cn.youngbear.pojo.Author;
import cn.youngbear.pojo.Constant;
import cn.youngbear.pojo.PicPopularPermanent;
import cn.youngbear.service.BaseService;
import cn.youngbear.service.RedisService;
import cn.youngbear.service.WebService;
import cn.youngbear.utils.util.Utils;
import org.htmlcleaner.XPatherException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Pixiv {
    public static void main(String args[]) throws IOException, XPatherException, InterruptedException {
        BaseService baseService = new BaseService();
        baseService.init();
        baseService.chooseUrl();


    }

    public static void main2(String[] args) throws XPatherException, ParseException, IOException, InterruptedException {
        BaseService baseService = new BaseService();
        WebService  webService = new WebService();
        baseService.init();
        Set<String> FollowPersonSet = webService.getFollowPerson().keySet();
        Map<String, List<String>> picMap = webService.getPicMap(new ArrayList<>(FollowPersonSet));
        for(String authId : picMap.keySet()){
            List<String> picList = picMap.get(authId);
            for(String picId:picList){
                Map<String, Object> returnMap = webService.getPicPojo(picId);
                PicPopularPermanent picPopularPermanent = (PicPopularPermanent) returnMap.get("picPopularPermanent");
                Author author = (Author) returnMap.get("author");
                String picdownloadUrl = picPopularPermanent.getPicUrl();
                String picName = picPopularPermanent.getPicName();
                String authName = author.getAuthorName();
                File file = new File(Constant.downloadPathForWindows,authName+"\\"+picName+".jpg");
                if(file.exists()){
                    System.out.println(Constant.downloadPathForWindows+authName+"\\"+picName+".jpg"+"文件名已存在");
                }else{
                    Utils.downloadUseHttpClient(webService.getHeader(null),picdownloadUrl,Constant.downloadPathForWindows,authName+"\\"+picName+".jpg");
                    Thread.sleep(10000);
                }

            }
        }
    }


}

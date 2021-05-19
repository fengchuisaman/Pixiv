package cn.youngbear.utils.PixivWebUtils;

import cn.youngbear.pojo.Author;
import cn.youngbear.pojo.Constant;
import cn.youngbear.pojo.PicPopularPermanent;
import cn.youngbear.pojo.Tag;
import cn.youngbear.service.BaseService;
import cn.youngbear.utils.util.Utils;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.beans.binding.StringBinding;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class WebService {
    //web登陆地址
    private String loginHtmlUrl = "https://accounts.pixiv.net/login?return_to=https%3A%2F%2Fwww.pixiv.net%2F&lang=zh&source=pc&view_type=page";
    //web登录接口地址
    private String loginApiUrl = "https://accounts.pixiv.net/api/login?lang=zh";
    //获取googleToken接口
    private String getGoogleTokenUrl = "https://www.google.com/recaptcha/enterprise/anchor?ar=1&k=6LfF1dcZAAAAAOHQX8v16MX5SktDwmQINVD_6mBF&co=aHR0cHM6Ly9hY2NvdW50cy5waXhpdi5uZXQ6NDQz&hl=zh-CN&v=dpzVjBAupwRfx3UzvXRnnAKb&size=invisible&cb=";
    //获取 关注的人的地址
    private String getFollowPersopnUrl="https://www.pixiv.net/ajax/user/${user_id}/following?offset=0&limit=100&rest=show&tag=&lang=zh";
    // 获取人的所有作品Id  地址
    private String getAllPicIdUrl ="https://www.pixiv.net/ajax/user/${auth_id}/profile/all?lang=zh";

    private String getImgDownUrl="https://www.pixiv.net/ajax/illust/${pic_id}/pages?lang=zh";
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();;

    /**
     * 登录接口暂未实现
     * @throws XPatherException
     */
    public void loginWeb() throws XPatherException {
        HashMap headerMap = new HashMap(6);
        headerMap.put("Host","accounts.pixiv.net");
        headerMap.put("Cookie",null);
        String resultHtml = Utils.sendGet(Constant.loginHtmlUrl,getHeader(headerMap),null);
//        System.out.println(resultHtml);
        TagNode tagNode= new HtmlCleaner().clean(resultHtml);
        String postKey = String.valueOf(tagNode.evaluateXPath("//*[@id=\"old-login\"]/form/input[1]/@value")[0]);
        String webInitConfig = String.valueOf(tagNode.evaluateXPath("//*[@id=\"init-config\"]/@value")[0]);
        System.out.println(webInitConfig);
        Map tempMap = gson.fromJson(webInitConfig,Map.class);
        System.out.println("=====>"+tempMap.get("pixivAccount.postKey"));


    }


    /**
     * 设置请求头，放入的map value值为
     * @param map
     * @return
     */
    public HashMap<String,String> getHeader(HashMap<String,String> map) {
        HashMap headerMap = new HashMap(6);
        headerMap.put("Referer","https://accounts.pixiv.net/");
        headerMap.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0");
        headerMap.put("Connection","keep-alive");
        headerMap.put("Host","www.pixiv.net");
        headerMap.put("Accept-Encoding","gzip, deflate, br");
        headerMap.put("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        headerMap.put("Cookie","__cfduid=d89dd630084c34622408feb9381fc37101618823442; p_ab_id=5; p_ab_id_2=8; p_ab_d_id=919237983; privacy_policy_agreement=2; first_visit_datetime_pc=2021-04-19+18%3A13%3A45; yuid_b=FwCRBwI; device_token=fabba25ff8813af8f6af8a76fef93ac2; c_type=23; a_type=0; b_type=1; _ga=GA1.2.134374166.1620954078; __utma=235335808.134374166.1620954078.1620978637.1621095448.2; __utmz=235335808.1621095448.2.2.utmcsr=accounts.pixiv.net|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmv=235335808.|3=plan=normal=1^5=gender=ma…iDoX~NzsShxkKo0~Y7FmV-7-dN~IkPuQ0u99v~jmeFczJY83~0jgdd7MBGR~V7AIQ6HoRs; __cf_bm=dd49ba241cc7c44625a1d27060fd1171b1acdb46-1621095425-1800-AUMw2YI81E6DrOnsbnbKJ8tw5ei0Ja+3HP0woBRlf+bYvnPGIyBSrnwJiG5pWrxe7FiJ6mcWxXdYBr+n1jz5LLEc6aKgDRJJYLeuXt+S5xEKUtBHZ0J/Gf2WqYh4NLEPeOxYrBAbUiqSHlRSR/lLWgnSd7WvUaAuzy5m9lotumZg5O3hBJNoTVf2tL9GrxEFwg==; PHPSESSID=42075219_b0nWu8e68UuBvoL42lx9jcIkN7jtmKAt; _gid=GA1.2.1544315153.1621095434; _gat=1; __utmb=235335808.2.10.1621095448; __utmc=235335808; __utmt=1; _gat_UA-1830249-3=1");
        if(map!=null&&!map.isEmpty()){
            for(String key :map.keySet()){
                if(map.get(key)==null){
                    headerMap.remove(key);
                }else{
                    headerMap.put(key,map.get(key));
                }
            }
        }
        return headerMap;
    }


    /**
     * 获取当前登录人的关注人列表 目前直获取
     */
    public Map<String,String> getFollowPerson(){
        HashMap returnMap = new HashMap();
        String getFollowPersopnUrl=Constant.getFollowPersopnUrl.replace("${user_id}","42075219");
        String resultStr = Utils.sendGet(getFollowPersopnUrl,getHeader(null),null);
        Map resultMap = JSON.parseObject(resultStr, Map.class);
        Map resultBodyMap = (Map) resultMap.get("body");
        List<Map<String,String>> followPersonList = null ;
        if(resultBodyMap!=null && resultBodyMap.size()>0){
            followPersonList = (List<Map<String, String>>) resultBodyMap.get("users");
        }
        for(Map map :followPersonList){
            returnMap.put(map.get("userId"),map.get("userName"));
//            System.out.println(map.get("userId")+"-------->"+map.get("userName"));
        }
        return  returnMap;
    }

    /**
     * 根据 作者Id获取作品List
     * @param authIdList List<作者Id>
     * @return Map<authId,List<picId>>
     */
    public Map<String,List<String>> getPicMap(List<String> authIdList){
        Map<String,List<String>> returnMap = new HashMap<>();
        for(String authId:authIdList){
            String resultStr = Utils.sendGet(Constant.getAllPicIdUrl.replace("${auth_id}",authId),getHeader(null),null);
//            Map resultMap = JSON.parseObject(resultStr, Map.class);
            Map resultMap = gson.fromJson(resultStr,Map.class);
            Map<String,String> illustsMap = (Map) ((Map) resultMap.get("body")).get("illusts");
            // 临时List，里面是一个画家的所有插画作品的Id
            ArrayList picIdList = new ArrayList();
            for (String key:illustsMap.keySet()){
                picIdList.add(key);
            }
            returnMap.put(authId,picIdList);
        }

        return returnMap;
    }


    /**
     * 根据PicId获取图片Url,因为可能会有多张图片，所以返回的Map<String,List>
     * @param picId
     * @return 返回Map
     */
    public Map<String,List<String>> getDownloadUrl(String picId){
        Map<String,List<String>> returnMap = new HashMap<>();
        ArrayList<String> smallPicUrlList = new ArrayList();
        ArrayList<String> originalPicUrlList = new ArrayList();
        String resultStr = Utils.sendGet(Constant.getImgDownUrl.replace("${pic_id}",picId),getHeader(null),null);
        Map resultMap = JSON.parseObject(resultStr, Map.class);
        List<Map<String,Map<String,String>>> picInfoList = (List) resultMap.get("body");
        for (int i=0;i<picInfoList.size();i++){
            smallPicUrlList.add(picInfoList.get(i).get("urls").get("small"));
            originalPicUrlList.add(picInfoList.get(i).get("urls").get("original"));
        }
        returnMap.put("smallPicUrlList",smallPicUrlList);
        returnMap.put("originalPicUrlList",originalPicUrlList);
        return returnMap;
    }

    public Map<String,Object> getPicPojo(String picId) throws XPatherException {
        PicPopularPermanent picPopularPermanent = null;
        Author author = null;
        Tag tag = null;
        Map<String, Object> returnMap = new HashMap<>(3);
        // 发送请求页面
        String resultHtml = Utils.sendGet(Constant.getImgPojoUrl.replace("${pic_id}", picId), getHeader(null), null);
        TagNode tagNode = new HtmlCleaner().clean(resultHtml);
        // 图片信息主要在 这里
        String metaPreloadDataStr = String.valueOf(tagNode.evaluateXPath("//*[@id=\"meta-preload-data\"]/@content")[0]);
        Map<String, Map> tempMap = gson.fromJson(metaPreloadDataStr, Map.class);
        // 临时数据，里面只有一个值，key值是图片Id,value是图片信息
        Map<String, Map> temp1Map = tempMap.get("illust");
        // 临时数据，里面只有一个值，key值是作者Id,value是作者信息
        Map<String, Map> temp2Map = tempMap.get("user");


        // 图片信息
        for (String key : temp1Map.keySet()) {
            Map<String, Object> picInfoMap = temp1Map.get(key);
            // 图片的各种url
            Map<String, String> urlsMap = (Map<String, String>) picInfoMap.get("urls");
            picPopularPermanent = new PicPopularPermanent();
            picPopularPermanent.setAuthorId(String.valueOf(picInfoMap.get("userId")))
                    .setPageCount(String.valueOf(picInfoMap.get("pageCount")))
                    .setPicId(Long.valueOf(String.valueOf(picInfoMap.get("illustId"))))
                    .setPicName(String.valueOf(picInfoMap.get("illustTitle")))
                    .setPicSize((int) Double.parseDouble(String.valueOf(picInfoMap.get("width"))) + "*" + (int) Double.parseDouble(String.valueOf(picInfoMap.get("height"))))
                    .setPicSmallUrl(String.valueOf(urlsMap.get("mini")))
                    .setPicUrl(String.valueOf(urlsMap.get("original")))
                    .setTotalBookmarks((int) Double.parseDouble(String.valueOf(picInfoMap.get("bookmarkCount"))))
                    .setTotalView((int) Double.parseDouble(String.valueOf(picInfoMap.get("viewCount"))))
                    .setTotalLike((int) Double.parseDouble(String.valueOf(picInfoMap.get("likeCount"))));
            try {
                Date createTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(String.valueOf(picInfoMap.get("createDate")));
                picPopularPermanent.setCreateTime(createTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //作者信息
        for (String key : temp2Map.keySet()) {
            Map<String, Object> authorInfoMap = temp2Map.get(key);
            // 图片的各种url
            Map<String, String> backgroundMap = (Map<String, String>) authorInfoMap.get("background");

            author = new Author();
            author.setAuthorId(String.valueOf(authorInfoMap.get("userId")))
                    .setAuthorName(String.valueOf(authorInfoMap.get("name")))
                    .setAuthorPic(String.valueOf(authorInfoMap.get("imageBig")))
                    .setIsFollowed(String.valueOf(authorInfoMap.get("isFollowed")));
            if(backgroundMap!=null && backgroundMap.get("url")!=null){
                author.setAuthor_background_pic(String.valueOf(backgroundMap.get("url")));
            }
        }

        for (String key : temp1Map.keySet()){
            Map<String, Object> picInfoMap = temp1Map.get(key);
            // 临时数据，里面是 图片标签信息
            String tagsStr = String.valueOf(((Map)picInfoMap.get("tags")).get("tags"));
//            List<Map<String, Object>> temp3List = gson.fromJson(tagsStr, List.class);

//            for (Map<String, Object> tagMap : temp3List) {
//                System.out.println(tagMap.get("tag"));
//                String translationStr = String.valueOf(tagMap.get("translation"));
//                Map translationMap = gson.fromJson(translationStr, Map.class);
//                System.out.println(String.valueOf(translationMap.get("en")).replace("-"," "));
//
//            }
    }
        returnMap.put("picPopularPermanent",picPopularPermanent);
        returnMap.put("author",author);
        returnMap.put("tag",tag);
        return  returnMap;
    }

    /**
     * 有问题
     * @param parameterMap
     * @throws XPatherException
     */
    public void changeR18(Map<String,String> parameterMap) throws XPatherException {
        String resultHtmlStr = Utils.sendGet(Constant.chageR18Url,getHeader(null),null);
        TagNode tagNode = new HtmlCleaner().clean(resultHtmlStr);
        //
        String mod = String.valueOf(tagNode.evaluateXPath("//*[@id=\"page-setting-user\"]/div/div[2]/div[2]/form/input[1]/@value")[0]);
        String tt = String.valueOf(tagNode.evaluateXPath("//*[@id=\"page-setting-user\"]/div/div[2]/div[2]/form/input[2]/@value")[0]);
        String userLanguage = String.valueOf(tagNode.evaluateXPath("//*[@id=\"page-setting-user\"]/div/div[2]/div[2]/form/table/tbody/tr[1]/td/select/option[4]/@value")[0]);
        String r18OpenStatus = String.valueOf(tagNode.evaluateXPath("//*[@id=\"page-setting-user\"]/div/div[2]/div[2]/form/table/tbody/tr[2]/td/dl/dd[1]/label[1]/input/@value")[0]);
        String r18CloseStatus = String.valueOf(tagNode.evaluateXPath("//*[@id=\"page-setting-user\"]/div/div[2]/div[2]/form/table/tbody/tr[2]/td/dl/dd[1]/label[2]/input/@value")[0]);
        String r18gOpenStatus = String.valueOf(tagNode.evaluateXPath("//*[@id=\"page-setting-user\"]/div/div[2]/div[2]/form/table/tbody/tr[2]/td/dl/dd[2]/label[1]/input/@value")[0]);
        String r18gCloseStatus = String.valueOf(tagNode.evaluateXPath("//*[@id=\"page-setting-user\"]/div/div[2]/div[2]/form/table/tbody/tr[2]/td/dl/dd[2]/label[2]/input/@value")[0]);
        String submitStr = String.valueOf(tagNode.evaluateXPath("//*[@id=\"page-setting-user\"]/div/div[2]/div[2]/form/div/input/@value")[0]);
        String r18Status = "OPEN".equals(parameterMap.get("r18"))?r18OpenStatus:r18CloseStatus;
        String r18gStatus = "OPEN".equals(parameterMap.get("r18g"))?r18gOpenStatus:r18gCloseStatus;

        List<BasicNameValuePair> paramsList = Arrays.asList(new BasicNameValuePair[]{
                new BasicNameValuePair("mod",mod),
                new BasicNameValuePair("tt",tt),
                new BasicNameValuePair("user_language",userLanguage),
                new BasicNameValuePair("r18",r18Status),
                new BasicNameValuePair("r18g",r18gStatus),
                new BasicNameValuePair("submit",submitStr),

        });
        new BasicNameValuePair("mod",mod);

        HashMap headerMap = new HashMap();
        headerMap.put("https://www.pixiv.net/setting_user.php","https://www.pixiv.net/setting_user.php");
        headerMap.put("Origin","https://www.pixiv.net");
        Utils.sendPost(Constant.chageR18Url,false,getHeader(headerMap),null,paramsList);

    }


}

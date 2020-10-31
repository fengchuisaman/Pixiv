package cn.youngbear.utils.PixivLiteUtils;

import cn.youngbear.utils.util.Constant;
import cn.youngbear.utils.util.Utils;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PixivLiteUtil {

    public Map<String,String> getClientHash(){
        HashMap resultMap = new HashMap(2);
        String currentTime  = clientTime();
        resultMap.put("currentTime",currentTime);
        resultMap.put("clientHash",clientHash(currentTime));
        return resultMap;
    }

    public static String clientTime() {
//        return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault())).format(new Date());
        return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())).format(new Date());
    }
    public static String clientHash(String paramString) {
        StringBuilder stringBuilder = new StringBuilder(paramString);
        stringBuilder.append("28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c");
        return EncryptUtils.encryptMD5ToString(stringBuilder.toString()).toLowerCase();
    }

    /**
     * pixiv 登录接口
     * @return
     * @throws IOException
     */
    public HashMap<String, String> pixivLogin() throws IOException {
        HashMap<String, String> headerMap = new HashMap(16);
        ArrayList<NameValuePair> paramsList = new ArrayList<>(8);
        Properties prop = new Properties();
        prop.load(new InputStreamReader(this.getClass().getResourceAsStream("/config.properties"), "UTF-8"));
        String userName = String.valueOf(Constant.loginUserMail);
        String passWord = String.valueOf(Constant.loginUserPassWord);
        String xClientTime = PixivLiteUtil.clientTime();
        String xClientHash = PixivLiteUtil.clientHash(xClientTime);
        String loginUrl = "http://api.pixivlite.com:8091/index.php/user/index/login";
        headerMap.put("User-Agent", "PixivAndroidApp/5.0.200 (Android 10; Redmi Note 8 Pro)");
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        headerMap.put("Accept-Language", "zh_CN");
        headerMap.put("Accept-Encoding", "gzip");
        headerMap.put("Connection", "Keep-Alive");

        paramsList.add(new BasicNameValuePair("agent", "PixivAndroidApp/5.0.200 (Android 10; Redmi Note 8 Pro)"));
        paramsList.add(new BasicNameValuePair("username", userName));
        paramsList.add(new BasicNameValuePair("password", passWord));
        paramsList.add(new BasicNameValuePair("device_token", getDeviceToken(userName)));
        paramsList.add(new BasicNameValuePair("x-client-time", xClientTime));
        paramsList.add(new BasicNameValuePair("x-client-hash", xClientHash));
        String result = Utils.sendPost(loginUrl, false, true, headerMap, null, paramsList);
        try{
            Map<String, Map> resultMap = JSONObject.parseObject(result, Map.class);
            String token = String.valueOf((resultMap.get("response")).get("access_token"));
            String loginUserId = String.valueOf(((Map) (resultMap.get("response")).get("user")).get("id"));
            headerMap.put("token","Bearer " + token);
            headerMap.put("loginUserId", loginUserId);
            Constant.loginUserId=loginUserId;
            Constant.userAgent="PixivAndroidApp/5.0.200 (Android 10; Redmi Note 8 Pro)";
            Constant.contentType="application/x-www-form-urlencoded";
            Constant.acceptLanguage="zh_CN";
            Constant.acceptEncoding="gzip";
            Constant.connection="Connection";
            Constant.deviceToken=getDeviceToken(userName);
            Constant.xClientTime=xClientTime;
            Constant.xClientHash=xClientHash;
            Constant.token="Bearer " + token;
        }catch (Exception e){
            System.out.println(result);
            e.printStackTrace();
        }
        return headerMap;
    }

    public String getDeviceToken(String email) {
        String loginUrl = "http://api.pixivlite.com:8091/index.php/user/index/getDeviceToken?email=" + email;
        ArrayList<NameValuePair> paramsList = new ArrayList<>(8);
        HashMap headerMap = new HashMap(16);
        headerMap.put("User-Agent", "PixivAndroidApp/5.0.200 (Android 10; Redmi Note 8 Pro)");
        headerMap.put("Host", "oauth.secure.pixiv.net");
        paramsList.add(new BasicNameValuePair("device_token", "pixiv"));
        String result = Utils.sendPost(loginUrl, false, true, headerMap, null, paramsList);
        Constant.deviceToken=result;
        return result;
    }

    /**
     * 把picList转化成下载地址List<Map<PicName,downloadUrl>>
     * @param picList
     */
    public List<Map<String,String>> chageToDownloadUrl(List<Map> picList) {
        List<Map<String,String>> picNameUrlList = new LinkedList();
        for (Map map : picList) {
            String url = String.valueOf(((Map) map.get("meta_single_page")).get("original_image_url"));
            if (url == null || "null".equals(url)) {
                url = String.valueOf(((Map) map.get("image_urls")).get("large"));
            }
            String fileName = map.get("title") + "_" + map.get("id") + ".jpg";
            if (!Utils.checkFileName(fileName)) {
                System.out.println("发现非法id:" + fileName);
                fileName = map.get("id") + ".jpg";
            }
            HashMap<String,String> picNameUrlMap = new HashMap<>(2);
            picNameUrlMap.put("picName",fileName);
            picNameUrlMap.put("picDownloadUrl",url);
            picNameUrlList.add(picNameUrlMap);
        }
        return picNameUrlList;
    }

}


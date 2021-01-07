package cn.youngbear.utils.PixivLiteUtils;

import cn.youngbear.pojo.Constant;
import cn.youngbear.utils.util.Utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 在反编译后pixivLite的com.bravedefault.pixivhelper.Constant下面有
 */
public class PixivLiteUtil {

    public Map<String,String> getClientHash(){
        HashMap resultMap = new HashMap(2);
        String currentTime  = clientTime();
        resultMap.put("currentTime",currentTime);
        resultMap.put("clientHash",clientHash(currentTime));
        return resultMap;
    }

    public static String clientTime() {
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
        String deviceToken = getDeviceToken(userName);
        String loginUrl = Constant.loginUrl;
        headerMap.put("User-Agent", Constant.userAgent);
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        headerMap.put("Accept-Language", "zh_CN");
        headerMap.put("Accept-Encoding", "gzip");
        headerMap.put("Connection", "Keep-Alive");

        paramsList.add(new BasicNameValuePair("agent", Constant.userAgent));
        paramsList.add(new BasicNameValuePair("username", userName));
        paramsList.add(new BasicNameValuePair("password", passWord));
        paramsList.add(new BasicNameValuePair("device_token", deviceToken));
        paramsList.add(new BasicNameValuePair("x-client-time", xClientTime));
        paramsList.add(new BasicNameValuePair("x-client-hash", xClientHash));
        String result = Utils.sendPost(loginUrl, true, headerMap, null, paramsList);
        try{
            Map<String, Map> resultMap = JSONObject.parseObject(result, Map.class);
            String token = String.valueOf((resultMap.get("response")).get("access_token"));
            String loginUserId = String.valueOf(((Map) (resultMap.get("response")).get("user")).get("id"));
            headerMap.put("token","Bearer " + token);
            headerMap.put("loginUserId", loginUserId);
            Constant.loginUserId=loginUserId;
            Constant.userAgent=Constant.userAgent;
            Constant.contentType="application/x-www-form-urlencoded";
            Constant.acceptLanguage="zh_CN";
            Constant.acceptEncoding="gzip";
            Constant.connection="Connection";
            Constant.deviceToken=deviceToken;
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
        String getDeviceUrl = Constant.getDeviceBaseUrl + email;
        ArrayList<NameValuePair> paramsList = new ArrayList<>(8);
        HashMap headerMap = new HashMap(16);
        headerMap.put("User-Agent", Constant.userAgent);
        headerMap.put("Host", Constant.host);
        paramsList.add(new BasicNameValuePair("device_token", Constant.deviceToken));
        String result = Utils.sendPost(getDeviceUrl,  true, headerMap, null, paramsList);
        Map<String, Map> resultMap = JSONObject.parseObject(result, Map.class);
        Constant.deviceToken= String.valueOf(resultMap.get("device_token"));
        return result;
    }

    /**
     * 刷新Token，获取最新Token
     *
     * @return
     */
    public static String flushToken(String clientId, String clientSecret) {
        String url = Constant.flushTokenUrl;
        HashMap<String, String> headMap = new HashMap(7);
        headMap.put("client_id", clientId);
        headMap.put("client_secret", clientSecret);
        headMap.put("refresh_token", Constant.token.replace("Bearer ", ""));
        headMap.put("device_token", Constant.deviceToken);
        headMap.put("grant_type", "refresh_token");
        headMap.put("get_secure_url", "true");
        headMap.put("include_policy", "true");
        String result = Utils.sendPost(url,true,headMap,null,null);
        Map resultMap = JSON.parseObject(result, Map.class);
        String token = String.valueOf(((Map) resultMap.get("response")).get("access_token"));
        if(token!=null && !"".equals(token)){
            return "Bearer " + token;
        }else{

            return "";
        }
    }



}


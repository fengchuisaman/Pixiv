package cn.youngbear.utils.util;

import cn.youngbear.pojo.Constant;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {


    public static String sendGet(String url,boolean isUserProxy, Map<String, String> headerMap, Map<String, String> paramsMap) {
        String returnValue = "";
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.createDefault();
            URIBuilder urlBuilder = new URIBuilder(url);
            if(paramsMap!=null && !paramsMap.isEmpty()){
                for (String key : paramsMap.keySet()) {
                    urlBuilder.setParameter(key, paramsMap.get(key));
                }
            }
            HttpGet httpGet = new HttpGet(urlBuilder.build());
            for (String key : headerMap.keySet()) {
                httpGet.setHeader(key, headerMap.get(key));
            }
            if(isUserProxy){
                HttpHost proxy = new HttpHost("127.0.0.1", 1080, "HTTP");
                response = httpClient.execute(proxy,httpGet);
            }else{
                response = httpClient.execute(httpGet);
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String respContent = EntityUtils.toString(entity, "UTF-8");
                returnValue = respContent;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if(response!=null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }

    public static String sendPost(String url, boolean isUserProxy,boolean isForm, Map<String, String> headerMap, Map<String, String> paramsMap, List<? extends NameValuePair> paramsList) {
        String returnValue = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpPost httppost = new HttpPost(url);
            for (String key : headerMap.keySet()) {
                httppost.setHeader(key, headerMap.get(key));
            }
            // body json提交
            if (!isForm) {
                StringEntity entity = new StringEntity(JSONObject.toJSONString(paramsMap),ContentType.APPLICATION_JSON);
                httppost.setEntity(entity);
            }
            //body 表单提交
            if (isForm) {
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsList, "UTF-8");
                httppost.setEntity(formEntity);
            }
            if(isUserProxy){
                HttpHost proxy = new HttpHost("127.0.0.1", 8888, "HTTPS");
                response = httpClient.execute(proxy,httppost);
            }else{
                response = httpClient.execute(httppost);
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                response.setHeader("Content-Type","application/json");
                HttpEntity entity = response.getEntity();
                String respContent = EntityUtils.toString(entity, "UTF-8");
                returnValue = respContent;
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            returnValue = "timeout";
        } catch (Exception e) {
            System.out.println("请求失败，请求错误码："+response.getStatusLine().getStatusCode()+"  错误内容："+response.getStatusLine());
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }

    public static void downloadFile(String urlPath, String path, String fileName) throws IOException {
        File file = new File(path + fileName);
        URL url = new URL(urlPath);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Referer", "https://www.pixiv.net");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
        long begin = 0;
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            if (file.exists()) {
                if (urlConnection.getContentLength() == file.length()) {
                    System.out.println("发现重复文件：   " + fileName);
                    return;
                } else {
                    System.out.println("发现重复文件：   " + fileName + "，且文件损坏，删除文件，重新下载");
                    file.delete();
                }
            }
            begin = System.currentTimeMillis();
            is = urlConnection.getInputStream();
            byte[] data = new byte[3 * 1024 * 1024];
            int lenth;
            fos = new FileOutputStream(file);
            while ((lenth = is.read(data)) != -1) {
                fos.write(data, 0, lenth);
            }
            fos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (is != null) {
                is.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("时间" + LocalDateTime.now() + "文件已下载：" + path + fileName + "  共耗时：" + (end - begin) / 1000.0 + "秒");
    }

    public static Date changStrToDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(dateStr, pos);
        return strtodate;
    }

    public static boolean checkFileName(String fileName) {
        //windows 非法文件名
        fileName = fileName.trim();
        String[] illegalChar = {"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};
        for (int i = 0; i < illegalChar.length; i++) {
            if (fileName.contains(illegalChar[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 刷新Token，获取最新Token
     *
     * @return
     */
    public static String flushToken(String clientId, String clientSecret) {
        String url = "https://oauth.secure.pixiv.net/auth/token";
        HashMap<String, String> map = new HashMap(7);
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("refresh_token", Constant.token.replace("Bearer ", ""));
        map.put("device_token", Constant.deviceToken);
        map.put("grant_type", "refresh_token");
        map.put("get_secure_url", "true");
        map.put("include_policy", "true");
//        String result = Utils.sendPost(url, map);
//        Map resultMap = JSON.parseObject(result, Map.class);
//        String token = String.valueOf(((Map) resultMap.get("response")).get("access_token"));
//        return "Bearer " + token;
        return null;
    }


    public static void downloadUseHttpClient(Map<String, String> headerMap,String urlPath, String path, String fileName) throws IOException {
        String filePath = path + fileName;
        String currOS = System.getProperty("os.name").toLowerCase();
        if(!currOS.contains("windows")){
            filePath.replace("\\","/");
        }
        File file = new File(filePath);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response ;
        long begin = 0;
        InputStream is = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(urlPath);

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            for(String headerKey: headerMap.keySet()){
                httpGet.addHeader(headerKey, headerMap.get(headerKey));
            }
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (file.exists()) {
                if (entity.getContentLength() == file.length()) {
                    System.out.println("发现重复文件：   " + fileName);
                    return;
                } else {
                    System.out.println("发现重复文件：   " + fileName + "，且文件损坏，删除文件，重新下载");
                    file.delete();
                }
            }
            is = entity.getContent();
            begin = System.currentTimeMillis();
            FileUtils.copyInputStreamToFile(is,file);
        } catch (Exception e) {
            System.out.println(urlPath);
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("时间" + LocalDateTime.now() + "文件已下载：" + path + fileName + "  共耗时：" + (end - begin) / 1000.0 + "秒");
    }

//    public Map<String, Set> changOriginalFieldToPojo(List fullFieldPicList) {
//        SaveData saveData = new SaveData();
//        Long tagId = saveData.selectMaxTagId();
//        Map<String, Set> resultMap = new HashMap();
//        Set resultPicList = new HashSet();
//        Set resultAuthorList = new HashSet();
//        Set<cn.youngbear.pojo.Tag> resultTagList = new HashSet();
//        Set resultPicTagList = new HashSet();
//        PicPopularPermanent pic = new PicPopularPermanent();
//        //从原始数据，一个一个取出数据
//        for (int i = 0; i < fullFieldPicList.size(); i++) {
//            Author author = new Author();
//            FullFieldPic tempFullField = JSONObject.toJavaObject((JSONObject) fullFieldPicList.get(i), FullFieldPic.class);
//            pic = new PicPopularPermanent();
//            pic.setPicId(Long.valueOf(tempFullField.getId()));
////            String dataStr = tempFullField.getCretate_date();
////            Date createDate = Utils.changStrToDate(dataStr);
////            pic.setCreateTime(createDate);
//            pic.setPicName(tempFullField.getTitle());
//            pic.setAuthorId(tempFullField.getUser().getId());
//            pic.setPageCount(tempFullField.getPage_count());
//            pic.setPicSize(tempFullField.getWidth() + "*" + tempFullField.getHeight());
//            pic.setPicSmallUrl(tempFullField.getImageUrls().getMedium());
//            pic.setTotalBookmarks(Integer.valueOf(tempFullField.getTotal_bookmarks()));
//            pic.setTotalView(Integer.valueOf(tempFullField.getTotal_view()));
//            if (!"null".equals(String.valueOf(tempFullField.getMeta_single_page().get("original_image_url")))) {
//                pic.setPicUrl(String.valueOf(tempFullField.getMeta_single_page().get("original_image_url")));
//            } else {
//                pic.setPicUrl(String.valueOf(tempFullField.getImageUrls().getLarge()));
//            }
//            pic.setTagId(null);
//            resultPicList.add(pic);
//            author.setAuthorId(tempFullField.getUser().getId());
//            author.setAuthorName(tempFullField.getUser().getName());
//            author.setAuthorPic(tempFullField.getUser().getProfile_image_urls().getMedium());
////            user.setAccount(tempFullField.getUser().getAccount());
////            user.setIs_followed(tempFullField.getUser().getIs_followed());
//            // 获取标签
//            resultAuthorList.add(author);
//            List<Tag> tagList = tempFullField.getTags();
//            List<cn.youngbear.pojo.Tag> tags = new ArrayList<cn.youngbear.pojo.Tag>(tagList.size());
//            cn.youngbear.pojo.Tag tag = null;
//            for (Tag tempTag : tagList) {
//                tag = new cn.youngbear.pojo.Tag();
//                tag.setTagId(tagId += 1);
//                tag.setTagName(tempTag.getTagName());
//                tags.add(tag);
//            }
//            resultTagList.addAll(tags);
//
//            //拼凑 pic_tag中间表
//            List<PicTag> pic_tags = new ArrayList(tags.size());
//            for (int temp = 0; temp < tags.size(); temp++) {
//                PicTag picTag = new PicTag();
//                picTag.setPicId(pic.getPicId());
//                picTag.setTagId(tags.get(temp).getTagId());
//                pic_tags.add(picTag);
//            }
//            resultPicTagList.addAll(pic_tags);
//        }
//        resultMap.put("resultAuthorList", resultAuthorList);
//        resultMap.put("resultPicList", resultPicList);
//        resultMap.put("resultTagList", resultTagList);
//        resultMap.put("resultPicTagList", resultPicTagList);
//        return resultMap;
//    }


}

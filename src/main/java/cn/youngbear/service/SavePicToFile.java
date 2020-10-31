package cn.youngbear.service;

import cn.youngbear.utils.util.Constant;
import cn.youngbear.utils.util.Utils;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SavePicToFile {
//    private SaveData saveData = new SaveData();
    private int totalDownloadCount = 0;
    private static long tagId = 1L;
    private Properties prop = new Properties();

    /**
     * 根据收藏夹下载图片
     *
     * @param headerMap
     * @param path
     * @param authIdList
     */
    public void downloadPicForLove(Map<String, String> headerMap, String path, List<String> authIdList ) {
        String dirPath = null;
        String authName = null;
        for (String authId : authIdList) {
            String picList = "https://app-api.pixiv.net/v1/user/illusts?filter=for_ios&type=illust&user_id=";
            String picListresult = Utils.sendGet(picList + authId, false,headerMap,null);
            Map tempMap = JSON.parseObject(picListresult, Map.class);
            List<Map> pics = (List<Map>) tempMap.get("illusts");
            String nextUrl = String.valueOf(tempMap.get("next_url"));
            authName = String.valueOf(((Map) pics.get(0).get("user")).get("name"));
            boolean flag = true;
            while (flag) {
                if (!"".equals(nextUrl) && !"null".equals(nextUrl)) {
                    Map nextMap = JSON.parseObject(Utils.sendGet(nextUrl, false,headerMap,null), Map.class);
                    List<Map> picsNext = (List<Map>) nextMap.get("illusts");
                    pics.addAll(picsNext);
                    nextUrl = String.valueOf(nextMap.get("next_url"));
                } else {
                    flag = false;
                }
            }
            dirPath = path + "\\" + authName + "\\";
            File file = new File(dirPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            for (Map map : pics) {
                String url = String.valueOf(((Map) map.get("meta_single_page")).get("original_image_url"));
                if (url == null || "null".equals(url)) {
                    url = String.valueOf(((Map) map.get("image_urls")).get("large"));
                }
                String fileName = map.get("title") + "_" + map.get("id") + ".jpg";
                if (!Utils.checkFileName(fileName)) {
                    System.out.println("发现非法id:" + fileName);
                    fileName = map.get("id") + ".jpg";
                }

                try {
                    Utils.downloadUseHttpClient(headerMap,url, dirPath, fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 根据Pic下载图片
     * @param headerMap
     */
    public void baseDownloadPic(Map<String, String> headerMap, List<Map> picList,String path) {
        byte[] b = (prop.getProperty("picPath")).getBytes();
        String dirPath = null;
        try {
            dirPath = new String(b,"UTF-8")+path+"\\";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
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
            try {
                Utils.downloadUseHttpClient(headerMap,url, dirPath, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据关键字获取
     * @param headerMap
     * @param Url
     * @param params
     */
    public void getPicByWord(Map<String, String> headerMap, String Url, Map<String, String> params) {
        if (Url.contains("&word=") || params.get("word") != null) {
            Url = Url + params.get("word");
            if (prop.getProperty("wordLimit") != null) {
                Url = Url + "%20" + prop.getProperty("wordLimit") + "users%E5%85%A5%E3%82%8A";
            } else {
                Url = Url + "%205000users%E5%85%A5%E3%82%8A";
            }
        }
        List picList = new LinkedList();
        while (true) {
            String result = Utils.sendGet(Url, false,headerMap,null);
            Map resultMap = JSON.parseObject(result, Map.class);
            List<Map> list = (List) resultMap.get("illusts");
            for (int i = 0; i < list.size(); i++) {
                if (totalDownloadCount++ <= Integer.valueOf(Constant.downloadLimit)) {
                    if(list!=null){
                        picList.add(list.get(i));
                    }
                }
            }
            if (totalDownloadCount++ >= Integer.valueOf(Constant.downloadLimit)) {
                break;
            }
            //如果没有下一页，直接退出
            if((String) resultMap.get("next_url")!=null ){
                Url = (String) resultMap.get("next_url");
            }else{
                break;
            }
        }
        this.baseDownloadPic(headerMap,picList,params.get("path"));
        System.out.println("完成");
    }
}


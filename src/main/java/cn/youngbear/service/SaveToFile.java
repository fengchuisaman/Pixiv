package cn.youngbear.service;

import cn.youngbear.pojo.Constant;
import cn.youngbear.utils.util.Utils;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class SaveToFile {

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
     *
     * @param secondarDirectory 传二级目录
     */
    public void baseDownloadPic(Map<String, String> headerMap, List<Map<String,String>> picList,String secondarDirectory) {
        String currOS = System.getProperty("os.name").toLowerCase();
        byte[] basePath = new byte[10];
        if(currOS.contains("windows")){
            basePath = (Constant.downloadPathForWindows).getBytes();
        }else{
            basePath = (Constant.downloadPathForLinux).getBytes();
        }


        String dirPath = null;
        for (Map<String,String> map : picList) {
            String picName = map.get("picName");
            String picDownloadUrl = map.get("picDownloadUrl");
            try {
                if(secondarDirectory!=null && !"".equals(secondarDirectory)){
                    dirPath = new String(basePath,"UTF-8")+secondarDirectory;
                }else{
                    dirPath = new String(basePath,"UTF-8")+"other"+"\\";
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                Utils.downloadUseHttpClient(headerMap,picDownloadUrl, dirPath, picName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}


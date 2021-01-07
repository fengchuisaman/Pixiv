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
     * 根据Pic下载图片
     * @param headerMap
     * @param picList List<Map<PicName,downloadUrl>>
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
                    dirPath = new String(basePath,"UTF-8")+secondarDirectory+"\\";
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


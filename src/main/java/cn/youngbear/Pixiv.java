package cn.youngbear;

import cn.youngbear.service.BaseService;
import cn.youngbear.utils.PixivLiteUtils.PixivLiteUtil;

import java.io.IOException;

public class Pixiv {

    public static void main(String args[]) throws IOException {
        PixivLiteUtil pixivLiteUtil = new PixivLiteUtil();
        BaseService baseService = new BaseService();
        baseService.init();
        pixivLiteUtil.pixivLogin();
        // 功能分类
        baseService.chooseUrl();
        //登录

    }


}

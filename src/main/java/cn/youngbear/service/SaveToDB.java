//package cn.youngbear.service;
//
//import cn.youngbear.originalField.FullFieldPic;
//import cn.youngbear.pojo.Author;
//import cn.youngbear.pojo.PicPopularPermanent;
//import cn.youngbear.pojo.PicTag;
//import cn.youngbear.pojo.Tag;
//import cn.youngbear.utils.util.Utils;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//
//import java.util.*;
//
//public class SaveToDB {
//    private int picAffectedNum = 0;
//    private String timSpan = "";
//    private static int totalDownloadCount = 0;
//    private static int downloadLimit = 140;
//    private static long tagId = 1L;
//    private Properties prop = new Properties();
//
//    /**
//     * 获取收藏夹
//     *
//     * @param headerMap
//     */
//    public void getLovePic(Map<String, String> headerMap, String Url, Map params) {
//        List<String> authIdList = new ArrayList<>();
//        String result = Utils.getSender(Url, headerMap);
//        Map resultMap = JSON.parseObject(result, Map.class);
//        List<Map> list = (List) resultMap.get("user_previews");
//        for (Map map : list) {
//            String authId = String.valueOf(((Map) map.get("user")).get("id"));
//            if (authId != null && !"".equals(authId)) {
//                authIdList.add(authId);
//            }
//        }
//
//        try {
//            String isDownLoadPicStr = prop.getProperty("isDownLoadPic");
//            String isSaveDataToMysqlStr = prop.getProperty("isSaveDataToMysql");
//            String picPathStr = new String(prop.getProperty("picPath").getBytes("ISO-8859-1"), "UTF-8")+params.get("path");
//            if (isDownLoadPicStr.equals("true")) {
//                downloadPicForLove(headerMap, picPathStr, authIdList);
//            }
//            if (isSaveDataToMysqlStr.equals("true")) {
//                savePicToMysqlForLove(headerMap, authIdList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    /**
//     * 根据收藏夹保存到数据库
//     *
//     * @param headerMap
//     * @param authIdList
//     * @return
//     */
//    public String savePicToMysqlForLove(Map<String, String> headerMap, List<String> authIdList) {
//        int count = 0;
//        String authName;
//        List pics = new LinkedList<>();
//        for (String authId : authIdList) {
//            String picList = "https:app-api.pixiv.net/v1/user/illusts?filter=for_ios&type=illust&user_id=";
//            String picListresult = Utils.sendGet(picList + authId, false,headerMap,null)
//            Map tempMap = JSON.parseObject(picListresult, Map.class);
//            pics.addAll((List) tempMap.get("illusts"));
//            String nextUrl = String.valueOf(tempMap.get("next_url"));
//            boolean flag = true;
//            while (flag) {
//                if (!"".equals(nextUrl) && !"null".equals(nextUrl)) {
//                    Map nextMap = JSON.parseObject(Utils.sendGet(picList + authId, false,headerMap,null), Map.class);
//                    List<Map> picsNext = (List<Map>) nextMap.get("illusts");
//                    pics.addAll(picsNext);
//                    nextUrl = String.valueOf(nextMap.get("next_url"));
//                } else {
//                    flag = false;
//                }
//            }
//        }
//        Map map = changOriginalFieldToPojo(pics);
//        Set<Author> authorSet = (Set<Author>) map.get("resultAuthorList");
//        Set<PicPopularPermanent> picSet = (Set<PicPopularPermanent>) map.get("resultPicList");
//        Set<PicTag> picTagSet = (Set<PicTag>) map.get("resultPicTagList");
//        Set<com.test.pojo.Tag> tagSet = (Set<com.test.pojo.Tag>) map.get("resultTagList");
//
//        for (PicPopularPermanent pic : picSet) {
//            PicPopularPermanent tempPic = saveData.selectPicByPicId(String.valueOf(pic.getPicId()));
//            if (tempPic == null) {
//                picAffectedNum = saveData.insertPicPopularPermanent(pic);
//                if (picAffectedNum >= 1) {
//                    totalDownloadCount++;
//                    if ((totalDownloadCount) == count) {
//                        break;
//                    }
//                }
//            }
//        }
//        for (Author author : authorSet) {
//            Author tempAuthor = saveData.selectAuthorByAuthorId(author.getAuthorId());
//            if (tempAuthor == null) {
//                saveData.insertAuthor(author);
//            }
//        }
//        for (com.test.pojo.Tag tag : tagSet) {
//            try {
//                if (saveData.selectTagIdByName(tag.getTagName()) == null) {
//                    saveData.insertTag(tag);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        for (PicTag picTag : picTagSet) {
//            try {
//                if (saveData.selectCountWithTag_Pic(picTag) == 0) {
//                    saveData.insertPicTags(new ArrayList<>(picTagSet));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * 根据榜单获取
//     * @param headerMap
//     * @param Url
//     * @param params
//     */
//    public void getPicByList(Map<String, String> headerMap, String Url, Map<String, String> params) {
//        if (Url.contains("&word=") || params.get("word") != null) {
//            Url = Url + params.get("word");
//            if (prop.getProperty("wordLimit") != null) {
//                Url = Url + "%20" + prop.getProperty("wordLimit") + "users%E5%85%A5%E3%82%8A";
//            } else {
//                Url = Url + "%205000users%E5%85%A5%E3%82%8A";
//            }
//        }
//        List picList = new LinkedList();
//        while (true) {
//            String result = Utils.getSender(Url, headerMap);
//            Map resultMap = JSON.parseObject(result, Map.class);
//            List<Map> list = (List) resultMap.get("illusts");
////            如果没有下一页，直接退出
//            if((String) resultMap.get("next_url")!=null){
//                Url = (String) resultMap.get("next_url");
//            }else{
//                break;
//            }
//            for (int i = 0; i < list.size(); i++) {
//                if (totalDownloadCount++ <= downloadLimit) {
//                    if(list!=null){
//                        picList.add(list.get(i));
//                    }
//                }
//            }
//            if (totalDownloadCount++ >= downloadLimit) {
//                break;
//            }
//        }
//        this.baseDownloadPic(headerMap,picList,params.get("path"));
//    }
//
//    public Map<String, Set> changOriginalFieldToPojo(List fullFieldPicList) {
//        Map<String, Set> resultMap = new HashMap();
//        Set resultPicList = new HashSet();
//        Set resultAuthorList = new HashSet();
//        Set<Tag> resultTagList = new HashSet();
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
//            List<cn.youngbear.originalField.Tag> tagList = tempFullField.getTags();
//            List<Tag> tags = new ArrayList<Tag>(tagList.size());
//            Tag tag ;
//            for (cn.youngbear.originalField.Tag tempTag : tagList) {
//                tag = new Tag();
//                tag.setTagName(tempTag.getName());
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
//
//    public void baseSaveDB(List<Map<String,String>> picList){
//
//    }
//
//}

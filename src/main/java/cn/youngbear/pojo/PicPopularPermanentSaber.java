package cn.youngbear.pojo;

public class PicPopularPermanentSaber {
    private String picId;

    private String picName;

    private String picSmallUrl;

    private String picSize;

    private Long tagId;

    private String authorId;

    private String pageCount;

    private String picUrl;

    private Integer totalView;

    private Integer totalBookmarks;

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId == null ? null : picId.trim();
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? null : picName.trim();
    }

    public String getPicSmallUrl() {
        return picSmallUrl;
    }

    public void setPicSmallUrl(String picSmallUrl) {
        this.picSmallUrl = picSmallUrl == null ? null : picSmallUrl.trim();
    }

    public String getPicSize() {
        return picSize;
    }

    public void setPicSize(String picSize) {
        this.picSize = picSize == null ? null : picSize.trim();
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount == null ? null : pageCount.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Integer getTotalView() {
        return totalView;
    }

    public void setTotalView(Integer totalView) {
        this.totalView = totalView;
    }

    public Integer getTotalBookmarks() {
        return totalBookmarks;
    }

    public void setTotalBookmarks(Integer totalBookmarks) {
        this.totalBookmarks = totalBookmarks;
    }
}
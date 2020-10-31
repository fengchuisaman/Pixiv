package cn.youngbear.pojo;

import java.util.Date;
import java.util.Objects;

public class PicPopularPermanent {
    private Long picId;

    private String picName;

    private String picSmallUrl;

    private String picSize;

    private Long tagId;

    private String authorId;

    private String pageCount;

    private String picUrl;

    private Integer totalView;

    private Integer totalBookmarks;

    private Date createTime;

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId ;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PicPopularPermanent that = (PicPopularPermanent) o;
        return Objects.equals(picId, that.picId) &&
                Objects.equals(picName, that.picName) &&
                Objects.equals(picSmallUrl, that.picSmallUrl) &&
                Objects.equals(picSize, that.picSize) &&
                Objects.equals(tagId, that.tagId) &&
                Objects.equals(authorId, that.authorId) &&
                Objects.equals(pageCount, that.pageCount) &&
                Objects.equals(picUrl, that.picUrl) &&
                Objects.equals(totalView, that.totalView) &&
                Objects.equals(totalBookmarks, that.totalBookmarks) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(picId, picName, picSmallUrl, picSize, tagId, authorId, pageCount, picUrl, totalView, totalBookmarks, createTime);
    }
}
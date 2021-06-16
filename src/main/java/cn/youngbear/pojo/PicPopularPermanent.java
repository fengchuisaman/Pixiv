package cn.youngbear.pojo;

import java.util.Date;
import java.util.Objects;

public class PicPopularPermanent {
    private String picId;

    private String picName;

    private String picSmallUrl;

    private String picSize;

    private Long tagId;

    private String authorId;

    private String pageCount;

    private String picUrl;

    private Integer totalView;

    private Integer totalLike;

    private Integer totalBookmarks;

    private Date createTime;

    public String getPicId() {
        return picId;
    }

    public PicPopularPermanent setPicId(String picId) {
        this.picId = picId;
        return this;
    }

    public String getPicName() {
        return picName;
    }

    public PicPopularPermanent setPicName(String picName) {
        this.picName = picName;
        return this;
    }

    public String getPicSmallUrl() {
        return picSmallUrl;
    }

    public PicPopularPermanent setPicSmallUrl(String picSmallUrl) {
        this.picSmallUrl = picSmallUrl;
        return this;
    }

    public String getPicSize() {
        return picSize;
    }

    public PicPopularPermanent setPicSize(String picSize) {
        this.picSize = picSize;
        return this;
    }

    public Long getTagId() {
        return tagId;
    }

    public PicPopularPermanent setTagId(Long tagId) {
        this.tagId = tagId;
        return this;
    }

    public String getAuthorId() {
        return authorId;
    }

    public PicPopularPermanent setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getPageCount() {
        return pageCount;
    }

    public PicPopularPermanent setPageCount(String pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public PicPopularPermanent setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public Integer getTotalView() {
        return totalView;
    }

    public PicPopularPermanent setTotalView(Integer totalView) {
        this.totalView = totalView;
        return this;
    }

    public Integer getTotalBookmarks() {
        return totalBookmarks;
    }

    public PicPopularPermanent setTotalBookmarks(Integer totalBookmarks) {
        this.totalBookmarks = totalBookmarks;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PicPopularPermanent setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getTotalLike() {
        return totalLike;
    }

    public PicPopularPermanent setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
        return this;
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
                Objects.equals(totalLike, that.totalLike) &&
                Objects.equals(totalBookmarks, that.totalBookmarks) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(picId, picName, picSmallUrl, picSize, tagId, authorId, pageCount, picUrl, totalView, totalLike, totalBookmarks, createTime);
    }
    public PicPopularPermanent getPic(PicPopularPermanent picPopularPermanent){
        PicPopularPermanent newPicPopularPermanent = new PicPopularPermanent();
        newPicPopularPermanent.setCreateTime(picPopularPermanent.getCreateTime());
        newPicPopularPermanent.setTotalBookmarks(picPopularPermanent.getTotalBookmarks());
        newPicPopularPermanent.setTotalLike(picPopularPermanent.getTotalLike());
        newPicPopularPermanent.setTotalView(picPopularPermanent.getTotalView());
        newPicPopularPermanent.setPicSmallUrl(picPopularPermanent.getPicSmallUrl());
        newPicPopularPermanent.setPicUrl(picPopularPermanent.getPicUrl());
        newPicPopularPermanent.setPicSize(picPopularPermanent.getPicSize());
        newPicPopularPermanent.setPicName(picPopularPermanent.getPicName());
        newPicPopularPermanent.setAuthorId(picPopularPermanent.getAuthorId());
        newPicPopularPermanent.setPicId(picPopularPermanent.getPicId());
        newPicPopularPermanent.setTagId(picPopularPermanent.getTagId());
        newPicPopularPermanent.setPageCount(picPopularPermanent.getPageCount());
        return newPicPopularPermanent;
    }
}
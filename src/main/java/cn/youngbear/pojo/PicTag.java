package cn.youngbear.pojo;

import java.util.List;
import java.util.Objects;

public class PicTag {
    private String picId;

    private Long tagId;

    private List<Tag> tagList;

    public String getPicId() {
        return picId;
    }

    public PicTag setPicId(String picId) {
        this.picId = picId;
        return this;
    }

    public Long getTagId() {
        return tagId;
    }

    public PicTag setTagId(Long tagId) {
        this.tagId = tagId;
        return this;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public PicTag setTagList(List<Tag> tagList) {
        this.tagList = tagList;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PicTag picTag = (PicTag) o;
        return Objects.equals(picId, picTag.picId) &&
                Objects.equals(tagId, picTag.tagId) &&
                Objects.equals(tagList, picTag.tagList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(picId, tagId, tagList);
    }

    @Override
    public String toString() {
        return "PicTag{" +
                "picId='" + picId + '\'' +
                ", tagId=" + tagId +
                ", tagList=" + tagList +
                '}';
    }
}
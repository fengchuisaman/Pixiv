package cn.youngbear.pojo;

import java.util.Objects;

public class PicTag {
    private Long picId;

    private Long tagId;

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PicTag picTag = (PicTag) o;
        return Objects.equals(picId, picTag.picId) &&
                Objects.equals(tagId, picTag.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(picId, tagId);
    }
}
package cn.youngbear.pojo;

import java.util.Objects;

public class Tag {
    private Long tagId;

    private String tagName;

    public Tag() {
    }

    public Tag(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(tagId, tag.tagId) &&
                Objects.equals(tagName, tag.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, tagName);
    }
}
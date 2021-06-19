package cn.youngbear.pojo;

import java.util.Objects;

public class Tag {
    private Long tagId;

    private String tagName;

    private String tagNameZh;

    public Tag() {
    }

    public Tag(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public Long getTagId() {
        return tagId;
    }

    public Tag setTagId(Long tagId) {
        this.tagId = tagId;
        return this;
    }

    public String getTagName() {
        return tagName;
    }

    public Tag setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public String getTagNameZh() {
        return tagNameZh;
    }

    public Tag setTagNameZh(String tagNameZh) {
        this.tagNameZh = tagNameZh;
        return this;
    }
    public Tag getTag(Tag tag){
        Tag newTag = new Tag();
        newTag.setTagId(tag.getTagId());
        newTag.setTagName(tag.getTagName());
        newTag.setTagNameZh(tag.getTagNameZh());
        return newTag;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", tagNameZh='" + tagNameZh + '\'' +
                '}';
    }
}
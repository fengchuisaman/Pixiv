package cn.youngbear.pojo;

import java.util.Date;

public class Pic {
    private String picId;
    private String picName ;
    private String picSmallUrl;
    private String picSize;
    private String tagId;
    private Date createTime;
    private String authorId;

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicSmallUrl() {
        return picSmallUrl;
    }

    public void setPicSmallUrl(String picSmallUrl) {
        this.picSmallUrl = picSmallUrl;
    }

    public String getPicSize() {
        return picSize;
    }

    public void setPicSize(String picSize) {
        this.picSize = picSize;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Pic{" +
                "picId='" + picId + '\'' +
                ", picName='" + picName + '\'' +
                ", picSmallUrl='" + picSmallUrl + '\'' +
                ", picSize='" + picSize + '\'' +
                ", tagId='" + tagId + '\'' +
                ", authorId='" + authorId + '\'' +
                '}';
    }
}

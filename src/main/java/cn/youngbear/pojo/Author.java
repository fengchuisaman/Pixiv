package cn.youngbear.pojo;

import java.util.Objects;

public class Author {
    private String authorId;

    private String authorName;

    private String authorPic;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public String getAuthorPic() {
        return authorPic;
    }

    public void setAuthorPic(String authorPic) {
        this.authorPic = authorPic == null ? null : authorPic.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(authorId, author.authorId) &&
                Objects.equals(authorName, author.authorName) &&
                Objects.equals(authorPic, author.authorPic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, authorName, authorPic);
    }
}
package cn.youngbear.pojo;

import java.util.Objects;

public class Author {
    private String authorId;

    private String authorName;

    private String authorPic;

    private String author_background_pic;

    private String isFollowed;

    public String getAuthorId() {
        return authorId;
    }

    public Author setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Author setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getAuthorPic() {
        return authorPic;
    }

    public Author setAuthorPic(String authorPic) {
        this.authorPic = authorPic;
        return this;
    }

    public String getAuthor_background_pic() {
        return author_background_pic;
    }

    public Author setAuthor_background_pic(String author_background_pic) {
        this.author_background_pic = author_background_pic;
        return this;
    }

    public String getIsFollowed() {
        return isFollowed;
    }

    public Author setIsFollowed(String isFollowed) {
        this.isFollowed = isFollowed;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(authorId, author.authorId) &&
                Objects.equals(authorName, author.authorName) &&
                Objects.equals(authorPic, author.authorPic) &&
                Objects.equals(author_background_pic, author.author_background_pic) &&
                Objects.equals(isFollowed, author.isFollowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, authorName, authorPic, author_background_pic, isFollowed);
    }
}
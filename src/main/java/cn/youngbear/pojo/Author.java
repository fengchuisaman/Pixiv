package cn.youngbear.pojo;

import java.util.Objects;

public class Author {
    private String authorId;

    private String authorName;

    private String authorPic;

    private String authorBackgroundPic;

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

    public String getAuthorBackgroundPic() {
        return authorBackgroundPic;
    }

    public Author setAuthorBackgroundPic(String authorBackgroundPic) {
        this.authorBackgroundPic = authorBackgroundPic;
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
                Objects.equals(authorBackgroundPic, author.authorBackgroundPic) &&
                Objects.equals(isFollowed, author.isFollowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, authorName, authorPic, authorBackgroundPic, isFollowed);
    }

    public Author getAuthor(Author author){
        Author returnAuthor = new Author();
        returnAuthor.setAuthorId(author.getAuthorId());
        returnAuthor.setAuthorName(author.getAuthorName());
        returnAuthor.setAuthorPic(author.getAuthorPic());
        returnAuthor.setAuthorBackgroundPic(author.getAuthorBackgroundPic());
        returnAuthor.setAuthorId(author.getAuthorId());
        return  returnAuthor;
    }
}
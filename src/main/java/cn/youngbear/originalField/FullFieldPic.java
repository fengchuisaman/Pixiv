package cn.youngbear.originalField;

import java.util.List;
import java.util.Map;

/**
 *  原始全字段类 - 每日
 */
public class FullFieldPic {
    private String cretate_date;
    private String height;
    private String is_bookmarked;
    private String total_bookmarks;
    private String total_view;
    private String width;
    private String page_count;
    private List<Tag> tags;
    private String title;
    /**
     * 图片Id
     */
    private String id ;
    /**
     * 图片url，分 大、中、小三种
     */
    private Image_urls imageUrls;

    /**
     *  作者
     */
    private User user ;

    private Map<String,String> meta_single_page;


    public String getCretate_date() {
        return cretate_date;
    }

    public void setCretate_date(String cretate_date) {
        this.cretate_date = cretate_date;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Image_urls getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(Image_urls imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getPage_count() {
        return page_count;
    }

    public void setPage_count(String page_count) {
        this.page_count = page_count;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getIs_bookmarked() {
        return is_bookmarked;
    }

    public void setIs_bookmarked(String is_bookmarked) {
        this.is_bookmarked = is_bookmarked;
    }

    public String getTotal_bookmarks() {
        return total_bookmarks;
    }

    public void setTotal_bookmarks(String total_bookmarks) {
        this.total_bookmarks = total_bookmarks;
    }

    public String getTotal_view() {
        return total_view;
    }

    public void setTotal_view(String total_view) {
        this.total_view = total_view;
    }

    public Map<String, String> getMeta_single_page() {
        return meta_single_page;
    }

    public void setMeta_single_page(Map<String, String> meta_single_page) {
        this.meta_single_page = meta_single_page;
    }

    @Override
    public String toString() {
        return "FullFieldPic{" +
                "cretate_date='" + cretate_date + '\'' +
                ", height='" + height + '\'' +
                ", id='" + id + '\'' +
                ", imageUrls=" + imageUrls +
                ", page_count='" + page_count + '\'' +
                ", tags=" + tags +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", width='" + width + '\'' +
                '}';
    }
}

package cn.youngbear.originalField;

/**
 *  原始类-作者
 */
public class User {
    private String account;
    private String id;
    private String name;
    private String is_followed;
    private ProfileIamgeUrls profile_image_urls;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(String is_followed) {
        this.is_followed = is_followed;
    }

    public ProfileIamgeUrls getProfile_image_urls() {
        return profile_image_urls;
    }

    public void setProfile_image_urls(ProfileIamgeUrls profile_image_urls) {
        this.profile_image_urls = profile_image_urls;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", is_followed='" + is_followed + '\'' +
                ", profile_image_urls=" + profile_image_urls +
                '}';
    }
}

package cn.youngbear.originalField;

/**
 *  原始 图片类
 */
public class Image_urls {
    private String large;
    private String medium;
    private String square_medium;

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSquare_medium() {
        return square_medium;
    }

    public void setSquare_medium(String square_medium) {
        this.square_medium = square_medium;
    }

    @Override
    public String toString() {
        return "Image_urls{" +
                "large='" + large + '\'' +
                ", medium='" + medium + '\'' +
                ", square_medium='" + square_medium + '\'' +
                '}';
    }
}

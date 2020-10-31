package cn.youngbear.originalField;

/**
 *  原始标签类
 */
public class Tag {
    private String name;
    private String translated_name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslated_name() {
        return translated_name;
    }

    public void setTranslated_name(String translated_name) {
        this.translated_name = translated_name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", translated_name='" + translated_name + '\'' +
                '}';
    }
}

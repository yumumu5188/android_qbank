package pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class GridViewMessages implements Serializable{
    private int id;
    private String icon;
    private String name;
    public GridViewMessages() {
    }

    public GridViewMessages(int id, String icon, String name) {
        this.id = id;
        this.icon = icon;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package wsu.csc5991.trustcircle.vo;


import java.io.Serializable;

/**
 * Created by sasidhav on 7/15/16.
 */
public class Event implements Serializable {

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package wsu.csc5991.trustcircle.vo;

import java.util.List;

/**
 * Created by sasidhav on 7/15/16.
 */
public class Circle {

    private int id;
    private int pin;

    private String name;
    private Member primaryMember;
    private List<Member> members;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member getPrimaryMember() {
        return primaryMember;
    }

    public void setPrimaryMember(Member primaryMember) {
        this.primaryMember = primaryMember;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}

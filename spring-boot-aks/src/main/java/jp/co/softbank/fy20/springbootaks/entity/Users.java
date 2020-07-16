package jp.co.softbank.fy20.springbootaks.entity;

public class Users {
    private String id;
    private String name;
    private String password;
    private String userGroup;

    public Users(){}

    public Users(String id, String name,String password,String userGroup){
        this.id = id;
        this.name = name;
        this.password = password;
        this.userGroup = userGroup;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password=" + password +
                ", userGroup=" + userGroup +
                "}";
    }
}
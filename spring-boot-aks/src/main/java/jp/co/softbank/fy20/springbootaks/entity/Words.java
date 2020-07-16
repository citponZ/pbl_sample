package jp.co.softbank.fy20.springbootaks.entity;

import java.time.LocalDateTime;

public class Words{
    private Integer id;
    private String name;
    private String userID;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Words(){}

    public Words(String name,String userID,String content){
        this.name = name;
        this.userID = userID;
        this.content = content;
        //this.createdDate = createdDate;
        //this.updatedDate = updatedDate;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Words{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userID=" + userID +
                ", content=" + content +
                ", createdDate=" + createdDate  +
                ", updatedDate=" + updatedDate +
                "}";
    }
}


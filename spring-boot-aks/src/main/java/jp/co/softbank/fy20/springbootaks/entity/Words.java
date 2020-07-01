package jp.co.softbank.fy20.springbootaks.entity;

import java.time.LocalDateTime;

public class Words{
    private Integer id;
    private String name;
    private Integer userID;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Words(){}

    public Words(Integer id,String name,Integer userID,String content
    ,LocalDateTime createdDate,LocalDateTime updatedDate){
        this.id = id;
        this.name = name;
        this.userID = userID;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
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
}
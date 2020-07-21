package jp.co.softbank.fy20.springbootaks.entity;

import java.time.LocalDateTime;

public class DeleteRequest {
    private Integer id;
    private String userID;
    private String wordName;
    private String reason;
    private LocalDateTime requestDate;

    public DeleteRequest(){}

    public DeleteRequest(String userID,String wordName,String reason){
        this.wordName = wordName;
        this.userID = userID;
        this.reason = reason;
        //this.requestDate = requestDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "DeleteRequest{" +
                "ID='" + id + '\'' +
                "wordName='" + wordName + '\'' +
                ", userID=" + userID +
                ", content=" + reason +
                ", requestDate=" + requestDate  +
                "}";
    }
}
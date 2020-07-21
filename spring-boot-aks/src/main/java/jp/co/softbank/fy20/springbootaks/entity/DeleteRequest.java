package jp.co.softbank.fy20.springbootaks.entity;

import java.time.LocalDateTime;

public class DeleteRequest {
    private String userID;
    private Integer wordID;
    private String reason;
    private LocalDateTime requestDate;

    public DeleteRequest(){}

    public DeleteRequest(String userID,Integer wordID,String reason){
        this.wordID = wordID;
        this.userID = userID;
        this.reason = reason;
        //this.requestDate = requestDate;
    }
    
    public Integer getWordID() {
        return wordID;
    }

    public void setWordID(Integer wordID) {
        this.wordID = wordID;
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
                "wordID='" + wordID + '\'' +
                ", userID=" + userID +
                ", content=" + reason +
                ", requestDate=" + requestDate  +
                "}";
    }
}
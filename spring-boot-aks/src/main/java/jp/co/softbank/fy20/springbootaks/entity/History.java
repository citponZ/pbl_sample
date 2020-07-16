package jp.co.softbank.fy20.springbootaks.entity;

import java.time.LocalDateTime;

public class History {
    private Integer id;
    private String historyType;
    private String userID;
    private Integer wordID;
    private LocalDateTime historyDate;

    public History(){}

    public History(String historyType,String userID,Integer wordID){
        this.historyType = historyType;
        this.userID = userID;
        this.wordID = wordID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHistoryType() {
        return historyType;
    }

    public void setHistoryType(String historyType) {
        this.historyType = historyType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getWordID() {
        return wordID;
    }

    public void setWordID(Integer wordID) {
        this.wordID = wordID;
    }

    public LocalDateTime getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(LocalDateTime historyDate) {
        this.historyDate = historyDate;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", historyType='" + historyType + '\'' +
                ", userID=" + userID +
                ", wordID=" + wordID +
                ", historyDate＝" + historyDate  +
                "}";
    }
}
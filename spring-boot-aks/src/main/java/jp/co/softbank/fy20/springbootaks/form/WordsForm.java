package jp.co.softbank.fy20.springbootaks.form;

import jp.co.softbank.fy20.springbootaks.entity.Words;

public class WordsForm {
    private String name;
    private Integer userID;
    private String content;
    
    public Words convertToEntity(){
        return new Words(name, userID, content);
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
    
}
package jp.co.softbank.fy20.springbootaks.form;

import jp.co.softbank.fy20.springbootaks.entity.Words;
import javax.validation.constraints.NotBlank;

public class WordsForm {
    @NotBlank
    private String name;
    @NotBlank
    private String userID;
    @NotBlank
    private String content;
    
    public Words convertToEntity(){
        return new Words(name,userID, content);
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
    
}
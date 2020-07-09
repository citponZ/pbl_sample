package jp.co.softbank.fy20.springbootaks.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WordsListAbb {
    private Integer id;
    private String name;
    private String content;
    private LocalDateTime updatedDate;
    private List<String> abbName = new ArrayList<String>();

    public WordsListAbb(){}

    public WordsListAbb(WordsByAbb wordsByAbb){
        this.id = wordsByAbb.getId();
        this.name = wordsByAbb.getName();
        this.content = wordsByAbb.getContent();
        this.updatedDate = wordsByAbb.getUpdatedDate();
        if (wordsByAbb.getAbbName() != null){
            this.abbName.add(wordsByAbb.getAbbName());
        }
    }

    public void addAbbName(String abbName){
        if (abbName != null){
            this.abbName.add(abbName);
        }
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<String> getAbbName() {
        return abbName;
    }

    public void setAbbName(List<String> abbName) {
        this.abbName = abbName;
    }

    
    @Override
    public String toString() {
        return "Words{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content=" + content +
                ", updatedDate=" + updatedDate +
                ", abbName=" + abbName +
                "}";
    }
}
package jp.co.softbank.fy20.springbootaks.entity;
import java.time.LocalDateTime;


public class WordsByAbb {
    private Integer id;
    private String name;
    private String content;
    private LocalDateTime updatedDate;
    private String abbName;

    public WordsByAbb(){}

    
    public WordsByAbb(String name,String content){
        this.name = name;
        this.content = content;
        //this.createdDate = createdDate;
        this.updatedDate = null;
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

    public String getAbbName() {
        return abbName;
    }

    public void setAbbName(String abbName) {
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
package jp.co.softbank.fy20.springbootaks.form;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotBlank;

public class NameForm {
    @NotBlank
    private String name;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    @AssertFalse
    public boolean isContainsSlash() {
        if (name == null)
        {   
            return false;
        }
        return name.contains("%2F");
    }

}
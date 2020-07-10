package jp.co.softbank.fy20.springbootaks.form;

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

}
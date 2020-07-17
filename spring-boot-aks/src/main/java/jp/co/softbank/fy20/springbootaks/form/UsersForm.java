package jp.co.softbank.fy20.springbootaks.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.ObjectUtils;

import jp.co.softbank.fy20.springbootaks.entity.Users;

public class UsersForm {
    @NotBlank
    @Length(min = 1, max = 100)
    private String id;

    @NotBlank
    @Length(min = 1, max = 50)
    private String userName;

    @NotBlank
    @Length(min = 1, max = 100)
    private String password;

    @NotBlank
    @Length(min = 1, max = 100)
    private String confirmPassword;

    @NotBlank
    private String userGroup;

    public Users convertToEntity(){
        return new Users(id,userName,password, userGroup);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    @AssertTrue
    public boolean isSamePassword() {
        return ObjectUtils.nullSafeEquals(password, confirmPassword);
    }
}
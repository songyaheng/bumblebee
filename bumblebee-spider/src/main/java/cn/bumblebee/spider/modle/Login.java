package cn.bumblebee.spider.modle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songyaheng on 2017/12/21
 * @version 1.0
 */
public class Login {
    private List<UserPassWord> userPassWords = new ArrayList<>();
    private String loginButton;
    private String userNameField;
    private String passWordField;


    public Login(String userNameField, String passWordField, String loginButton) {
        this.loginButton = loginButton;
        this.passWordField = passWordField;
        this.userNameField = userNameField;
    }

    public void putUserNameAndPassWord(String userName, String passWord) {
        UserPassWord userPassWord = new UserPassWord();
        userPassWord.setUserName(userName);
        userPassWord.setPassWord(passWord);
        userPassWords.add(userPassWord);
    }

    public List<UserPassWord> getUserPassWords() {
        return userPassWords;
    }

    public String getLoginButton() {
        return loginButton;
    }

    public String getUserNameField() {
        return userNameField;
    }

    public String getPassWordField() {
        return passWordField;
    }
}

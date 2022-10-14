package com.example.login_example;

public class UserAccount {


    private String idToken; // 파이어베이스 Uid (고유 토큰정보)
    private String emailId; // 이메일
    private String password; // 비밀번호
    private String name;
    private String age;
    private String gender;

    public UserAccount(String idToken, String emailId, String password, String name, String age, String gender) {
        this.idToken = idToken;
        this.emailId = emailId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public UserAccount() {
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

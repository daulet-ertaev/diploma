package com.example.diploma;

public class UserHelpWhenRegister {
    String RegisterEmail, RegisterFullName, RegisterPassword, RegisterPhone;

    public UserHelpWhenRegister() {

    }

    public UserHelpWhenRegister(String registerEmail, String registerFullName, String registerPassword, String registerPhone) {
        RegisterEmail = registerEmail;
        RegisterFullName = registerFullName;
        RegisterPassword = registerPassword;
        RegisterPhone = registerPhone;
    }

    public String getRegisterFullName() {
        return RegisterFullName;
    }

    public void setRegisterFullName(String registerFullName) {
        RegisterFullName = registerFullName;
    }

    public String getRegisterEmail() {
        return RegisterEmail;
    }

    public void setRegisterEmail(String registerEmail) {
        RegisterEmail = registerEmail;
    }

    public String getRegisterPhone() {
        return RegisterPhone;
    }

    public void setRegisterPhone(String registerPhone) {
        RegisterPhone = registerPhone;
    }

    public String getRegisterPassword() {
        return RegisterPassword;
    }

    public void setRegisterPassword(String registerPassword) {
        RegisterPassword = registerPassword;
    }
}

package com.example.diploma;

public class UserHelpWhenRegister {
    String RegisterEmail, RegisterFullName, RegisterPhone, Gender;

    public UserHelpWhenRegister() {

    }

    public UserHelpWhenRegister(String registerEmail, String registerFullName, String registerPhone, String gender) {
        RegisterEmail = registerEmail;
        RegisterFullName = registerFullName;
        RegisterPhone = registerPhone;
        Gender = gender;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}

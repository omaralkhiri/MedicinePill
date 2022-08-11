package com.example.ministry_of_health;

public class Users {
    private String name;
    private String id_number;
    private String birthday;
    private String gender;
    private String place_of_birth;
    private String phone;
    private String password;
    private String job;
    private String imgidcard;

    public Users(String name, String id_number, String birthday, String gender, String phone,String pass,String job,String img ) {
        this.name = name;
        this.id_number = id_number;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        password=pass;
        this.job=job;
        imgidcard=img;
    }
    public Users(String name,String id_number,String phone,String gender){
        this.name=name;
        this.id_number=id_number;
        this.phone=phone;
        this.gender=gender;
    }


    public String getImgidcard() {
        return imgidcard;
    }

    public void setImgidcard(String imgidcard) {
        this.imgidcard = imgidcard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}

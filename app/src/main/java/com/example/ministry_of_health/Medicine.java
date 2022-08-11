package com.example.ministry_of_health;

public class Medicine {
    private int medicineid;
    private String img;
    private String namemedicine;
    private String takemedicine;
    private int quentitymedicine;
    private int quentityinstore;

    public Medicine(String img, String namemedicine, String takemedicine, int quentitymedicine) {
        this.img = img;
        this.namemedicine = namemedicine;
        this.takemedicine = takemedicine;
        this.quentitymedicine = quentitymedicine;
    }
    public Medicine(String img,String namemedicine,String takemedicine,int quentitymedicine,int quentityinstore){
        this.img=img;
        this.namemedicine=namemedicine;
        this.takemedicine=takemedicine;
        this.quentitymedicine=quentitymedicine;
        this.quentityinstore=quentityinstore;
    }
    public Medicine(String img,String namemedicine){
        this.img=img;
        this.namemedicine=namemedicine;
    }
    public Medicine(int medicineid,String img,String namemedicine){
        this.medicineid=medicineid;
        this.img=img;
        this.namemedicine=namemedicine;
    }

    public int getMedicineid() {
        return medicineid;
    }

    public void setMedicineid(int medicineid) {
        this.medicineid = medicineid;
    }

    public int getQuentityinstore() {
        return quentityinstore;
    }

    public void setQuentityinstore(int quentityinstore) {
        this.quentityinstore = quentityinstore;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNamemedicine() {
        return namemedicine;
    }

    public void setNamemedicine(String namemedicine) {
        this.namemedicine = namemedicine;
    }

    public String getTakemedicine() {
        return takemedicine;
    }

    public void setTakemedicine(String takemedicine) {
        this.takemedicine = takemedicine;
    }

    public int getQuentitymedicine() {
        return quentitymedicine;
    }

    public void setQuentitymedicine(int quentitymedicine) {
        this.quentitymedicine = quentitymedicine;
    }
}

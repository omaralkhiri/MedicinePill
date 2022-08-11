package com.example.ministry_of_health;

public class idinfo {
    private static String id;
    private static String iduser;
    private static String username;
    private static int medicineid;
    private static String ipaddress="192.168.1.71";
    private static String branch_id;
    private static String PathImg="http://192.168.1.71/imgmedicine/";
    private static String pathimag="http://192.168.1.71/image_idcard/";
    private static Double Latitude,Longitude;
    private static String neworder;

    public static String getNeworder() {
        return neworder;
    }

    public static void setNeworder(String neworder) {
        idinfo.neworder = neworder;
    }

    public static Double getLatitude() {
        return Latitude;
    }

    public static void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public static Double getLongitude() {
        return Longitude;
    }

    public static void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public static String getPathimag() {
        return pathimag;
    }

    public static void setPathimag(String pathimag) {
        idinfo.pathimag = pathimag;
    }

    public static String getPathImg() {
        return PathImg;
    }

    public static void setPathImg(String pathImg) {
        PathImg = pathImg;
    }

    public static String getBranch_id() {
        return branch_id;
    }

    public static void setBranch_id(String branch_id) {
        idinfo.branch_id = branch_id;
    }

    public static String getId(){
        return id;
    }

    public static void setId(String iduser){
        id=iduser;
    }

    public static String getIpaddress() {
        return ipaddress;
    }

    public static void setIpaddress(String ipaddress) {
        idinfo.ipaddress = ipaddress;
    }

    public static String getIduser() {
        return iduser;
    }

    public static void setIduser(String iduser) {
        idinfo.iduser = iduser;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String name) {
        idinfo.username = name;
    }

    public static int getMedicineid() {
        return medicineid;
    }

    public static void setMedicineid(int medicineid) {
        idinfo.medicineid = medicineid;
    }
}

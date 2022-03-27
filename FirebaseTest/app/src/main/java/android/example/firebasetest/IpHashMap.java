package android.example.firebasetest;

import java.util.HashMap;

public class IpHashMap {
    private String ip;
    private String ipAddress;
    private String cameraName;

    IpHashMap(String address) {
        this.ip = "IP";
        this.ipAddress = address;
        this.cameraName = "Unknown";
    }
    IpHashMap(String cameraName,String address) {
        this.ip = "IP";
        this.ipAddress = address;
        this.cameraName = cameraName;
    }

    String getIpAddress() {return this.ipAddress;}
    String getCameraName() {return this.cameraName;}
    String getIp() {return this.ip;}

    HashMap getMap() {
        HashMap<String,Object> map = new HashMap<>();
        map.put(getCameraName(),getIpAddress());
        return map;
    }


}

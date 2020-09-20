package com.eason.zookeeper.configcenter;

/**
 * @Auther: Eason
 * @Date: 2020/8/15 - 08 - 15 - 10:39
 * @Description: com.eason.zookeeper.configcenter
 * @version: 1.0
 */
public class ZKConfig {
    private String address;
    private int sessionTimeout;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}

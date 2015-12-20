package com.sdl.sdlarchivesmanager.bean;

/**
 * Created by majingyuan on 15/11/30.
 * 审核列表中的项目类
 */
public class BeanAudit {
    private String status;  //状态
    private String clientName;  //经销商名称
    private String clientAddress;  //经销商地址
    private String clientOwner; //经销商户主
    private String clientType;  //经销商类型
    private String clientLevel; //经销商层级

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddr) {
        this.clientAddress = clientAddr;
    }

    public String getClientOwner() {
        return clientOwner;
    }

    public void setClientOwner(String clientOwner) {
        this.clientOwner = clientOwner;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientLevel() {
        return clientLevel;
    }

    public void setClientLevel(String clientLevel) {
        this.clientLevel = clientLevel;
    }
}

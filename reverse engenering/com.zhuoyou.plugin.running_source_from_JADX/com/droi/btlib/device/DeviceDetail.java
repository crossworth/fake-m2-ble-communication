package com.droi.btlib.device;

public class DeviceDetail {
    private int alarmCmd;
    private int antiLostCmd;
    private int btType = 0;
    private int callRemindCmd;
    private int displaySettingCmd;
    private int disturbanceModeCmd;
    private int findBraceletCmd;
    private int firmwareCmd;
    private int hardwareCmd;
    private int heartCmd;
    private String name;
    private int pushRemindCmd;
    private int sedentaryRemindCmd;
    private int sleepCmd;
    private int smsRemindCmd;
    private int sportTargetCmd;
    private int updateCmd;
    private int wristLiftCmd;

    public int getFindBraceletCmd() {
        return this.findBraceletCmd;
    }

    public void setFindBraceletCmd(int findBraceletCmd) {
        this.findBraceletCmd = findBraceletCmd;
    }

    public int getDisturbanceModeCmd() {
        return this.disturbanceModeCmd;
    }

    public void setDisturbanceModeCmd(int disturbanceModeCmd) {
        this.disturbanceModeCmd = disturbanceModeCmd;
    }

    public int getSedentaryRemindCmd() {
        return this.sedentaryRemindCmd;
    }

    public void setSedentaryRemindCmd(int sedentaryRemindCmd) {
        this.sedentaryRemindCmd = sedentaryRemindCmd;
    }

    public int getDisplaySettingCmd() {
        return this.displaySettingCmd;
    }

    public void setDisplaySettingCmd(int displaySettingCmd) {
        this.displaySettingCmd = displaySettingCmd;
    }

    public int getWristLiftCmd() {
        return this.wristLiftCmd;
    }

    public void setWristLiftCmd(int wristLiftCmd) {
        this.wristLiftCmd = wristLiftCmd;
    }

    public int getSportTargetCmd() {
        return this.sportTargetCmd;
    }

    public void setSportTargetCmd(int sportTargetCmd) {
        this.sportTargetCmd = sportTargetCmd;
    }

    public int getUpdateCmd() {
        return this.updateCmd;
    }

    public void setUpdateCmd(int updateCmd) {
        this.updateCmd = updateCmd;
    }

    public int getHeartCmd() {
        return this.heartCmd;
    }

    public void setHeartCmd(int heartCmd) {
        this.heartCmd = heartCmd;
    }

    public int getSleepCmd() {
        return this.sleepCmd;
    }

    public void setSleepCmd(int sleepCmd) {
        this.sleepCmd = sleepCmd;
    }

    public int getAntiLostCmd() {
        return this.antiLostCmd;
    }

    public void setAntiLostCmd(int antiLostCmd) {
        this.antiLostCmd = antiLostCmd;
    }

    public int getAlarmCmd() {
        return this.alarmCmd;
    }

    public void setAlarmCmd(int alarmCmd) {
        this.alarmCmd = alarmCmd;
    }

    public int getFirmwareCmd() {
        return this.firmwareCmd;
    }

    public void setFirmwareCmd(int firmwareCmd) {
        this.firmwareCmd = firmwareCmd;
    }

    public int getHardwareCmd() {
        return this.hardwareCmd;
    }

    public void setHardwareCmd(int hardwareCmd) {
        this.hardwareCmd = hardwareCmd;
    }

    public int getCallRemindCmd() {
        return this.callRemindCmd;
    }

    public void setCallRemindCmd(int callRemindCmd) {
        this.callRemindCmd = callRemindCmd;
    }

    public int getSmsRemindCmd() {
        return this.smsRemindCmd;
    }

    public void setSmsRemindCmd(int smsRemindCmd) {
        this.smsRemindCmd = smsRemindCmd;
    }

    public int getPushRemindCmd() {
        return this.pushRemindCmd;
    }

    public void setPushRemindCmd(int pushRemindCmd) {
        this.pushRemindCmd = pushRemindCmd;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBtType() {
        return this.btType;
    }

    public void setBtType(int btType) {
        this.btType = btType;
    }
}

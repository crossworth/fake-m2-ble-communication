package com.zhuoyou.plugin.firmware;

public class Firmware {
    private String content;
    private String currentVer;
    private String datFileUrl;
    private String description;
    private String fileUrl;
    private String md5;
    private String name;
    private String title;

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getCurrentVer() {
        return this.currentVer;
    }

    public String getMd5() {
        return this.md5;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public String getDatFileUrl() {
        return this.datFileUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCurrentVer(String currentVer) {
        this.currentVer = currentVer;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setDatFileUrl(String datFileUrl) {
        this.datFileUrl = datFileUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

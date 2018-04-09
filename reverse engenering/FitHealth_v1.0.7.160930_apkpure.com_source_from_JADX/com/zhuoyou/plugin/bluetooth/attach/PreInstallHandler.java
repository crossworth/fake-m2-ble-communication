package com.zhuoyou.plugin.bluetooth.attach;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PreInstallHandler extends DefaultHandler {
    private static final String PLUGIN_ATTR_NICKNAME = "name";
    private static final String PLUGIN_ATTR_PACKAGENAME = "package_name";
    private static final String PLUGIN_ROOT = "plugin";
    private static final String PRODUCCT_ATTR_NAME = "name";
    private static final String PRODUCCT_ATTR_TYPE = "type";
    private static final String PRODUCCT_ROOT = "product";
    private StringBuilder builder;
    private String mNickName = null;
    private String mPackageName = null;
    private PreInstallBean mPreInstallBean = null;
    private List<PreInstallBean> mPreInstallBeans = null;

    public List<PreInstallBean> getRoot() {
        return this.mPreInstallBeans;
    }

    public void startDocument() throws SAXException {
        super.startDocument();
        this.mPreInstallBeans = new ArrayList();
        this.builder = new StringBuilder();
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equals(PRODUCCT_ROOT)) {
            this.mPreInstallBean = new PreInstallBean();
            this.mPreInstallBean.setName(attributes.getValue("name"));
            this.mPreInstallBean.setCategory(attributes.getValue("type"));
        } else if (localName.equals(PLUGIN_ROOT)) {
            this.mPackageName = attributes.getValue("package_name");
            this.mNickName = attributes.getValue("name");
        }
        this.builder.setLength(0);
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        this.builder.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equals(PLUGIN_ROOT)) {
            this.mPreInstallBean.addPlugName(this.mNickName);
            this.mPreInstallBean.addPlugPackageName(this.mPackageName);
        } else if (localName.equals(PRODUCCT_ROOT)) {
            this.mPreInstallBeans.add(this.mPreInstallBean);
        }
    }
}

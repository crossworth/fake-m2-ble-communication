package com.zhuoyou.plugin.bluetooth.product;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ProductHandler extends DefaultHandler {
    private static final String FATHER_ATTR_NAME = "name";
    private static final String FATHER_ATTR_TYPE = "type";
    private static final String FATHER_ROOT = "father";
    private static final String GRANDPA_ATTR_MODIFYTIME = "modify_time";
    private static final String GRANDPA_ROOT = "grandpa";
    private static final String SON_ATTR_PACKAGENAME = "package_name";
    private static final String SON_ROOT = "son";
    private StringBuilder builder;
    private Father mFather = null;
    private Grandpa mGrandPa = null;
    private Son mSon = null;

    public void startDocument() throws SAXException {
        super.startDocument();
        this.mGrandPa = new Grandpa();
        this.builder = new StringBuilder();
    }

    public Grandpa getRoot() {
        return this.mGrandPa;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equals(GRANDPA_ROOT)) {
            this.mGrandPa.setModifyTime(attributes.getValue(GRANDPA_ATTR_MODIFYTIME));
        } else if (localName.equals(FATHER_ROOT)) {
            this.mFather = new Father();
            this.mFather.setName(attributes.getValue("name"));
            this.mFather.setCategory(attributes.getValue("type"));
        } else if (localName.equals(SON_ROOT)) {
            this.mSon = new Son();
            this.mSon.setPackageName(attributes.getValue("package_name"));
            this.mSon.setProductName(this.mFather.getName());
        }
        this.builder.setLength(0);
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        this.builder.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equals(SON_ROOT)) {
            this.mFather.addSon(this.mSon);
        } else if (localName.equals(FATHER_ROOT)) {
            this.mGrandPa.addFather(this.mFather);
        }
    }
}

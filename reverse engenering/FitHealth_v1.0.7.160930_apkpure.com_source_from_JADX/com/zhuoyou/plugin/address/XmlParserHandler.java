package com.zhuoyou.plugin.address;

import com.amap.api.services.district.DistrictSearchQuery;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParserHandler extends DefaultHandler {
    CityModel cityModel = new CityModel();
    DistrictModel districtModel = new DistrictModel();
    private List<ProvinceModel> provinceList = new ArrayList();
    ProvinceModel provinceModel = new ProvinceModel();

    public List<ProvinceModel> getDataList() {
        return this.provinceList;
    }

    public void startDocument() throws SAXException {
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(DistrictSearchQuery.KEYWORDS_PROVINCE)) {
            this.provinceModel = new ProvinceModel();
            this.provinceModel.setName(attributes.getValue(0));
            this.provinceModel.setCityList(new ArrayList());
        } else if (qName.equals(DistrictSearchQuery.KEYWORDS_CITY)) {
            this.cityModel = new CityModel();
            this.cityModel.setName(attributes.getValue(0));
            this.cityModel.setDistrictList(new ArrayList());
        } else if (qName.equals(DistrictSearchQuery.KEYWORDS_DISTRICT)) {
            this.districtModel = new DistrictModel();
            this.districtModel.setName(attributes.getValue(0));
            this.districtModel.setZipcode(attributes.getValue(1));
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(DistrictSearchQuery.KEYWORDS_DISTRICT)) {
            this.cityModel.getDistrictList().add(this.districtModel);
        } else if (qName.equals(DistrictSearchQuery.KEYWORDS_CITY)) {
            this.provinceModel.getCityList().add(this.cityModel);
        } else if (qName.equals(DistrictSearchQuery.KEYWORDS_PROVINCE)) {
            this.provinceList.add(this.provinceModel);
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
    }
}

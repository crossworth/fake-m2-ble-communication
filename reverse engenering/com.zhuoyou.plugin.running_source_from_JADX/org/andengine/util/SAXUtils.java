package org.andengine.util;

import org.xml.sax.Attributes;

public final class SAXUtils {
    public static final String getAttribute(Attributes pAttributes, String pAttributeName, String pDefaultValue) {
        String value = pAttributes.getValue("", pAttributeName);
        return value != null ? value : pDefaultValue;
    }

    public static final String getAttributeOrThrow(Attributes pAttributes, String pAttributeName) {
        String value = pAttributes.getValue("", pAttributeName);
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException("No value found for attribute: '" + pAttributeName + "'");
    }

    public static final boolean getBooleanAttribute(Attributes pAttributes, String pAttributeName, boolean pDefaultValue) {
        String value = pAttributes.getValue("", pAttributeName);
        return value != null ? Boolean.parseBoolean(value) : pDefaultValue;
    }

    public static final boolean getBooleanAttributeOrThrow(Attributes pAttributes, String pAttributeName) {
        return Boolean.parseBoolean(getAttributeOrThrow(pAttributes, pAttributeName));
    }

    public static final byte getByteAttribute(Attributes pAttributes, String pAttributeName, byte pDefaultValue) {
        String value = pAttributes.getValue("", pAttributeName);
        return value != null ? Byte.parseByte(value) : pDefaultValue;
    }

    public static final byte getByteAttributeOrThrow(Attributes pAttributes, String pAttributeName) {
        return Byte.parseByte(getAttributeOrThrow(pAttributes, pAttributeName));
    }

    public static final short getShortAttribute(Attributes pAttributes, String pAttributeName, short pDefaultValue) {
        String value = pAttributes.getValue("", pAttributeName);
        return value != null ? Short.parseShort(value) : pDefaultValue;
    }

    public static final short getShortAttributeOrThrow(Attributes pAttributes, String pAttributeName) {
        return Short.parseShort(getAttributeOrThrow(pAttributes, pAttributeName));
    }

    public static final int getIntAttribute(Attributes pAttributes, String pAttributeName, int pDefaultValue) {
        String value = pAttributes.getValue("", pAttributeName);
        return value != null ? Integer.parseInt(value) : pDefaultValue;
    }

    public static final int getIntAttributeOrThrow(Attributes pAttributes, String pAttributeName) {
        return Integer.parseInt(getAttributeOrThrow(pAttributes, pAttributeName));
    }

    public static final long getLongAttribute(Attributes pAttributes, String pAttributeName, long pDefaultValue) {
        String value = pAttributes.getValue("", pAttributeName);
        return value != null ? Long.parseLong(value) : pDefaultValue;
    }

    public static final long getLongAttributeOrThrow(Attributes pAttributes, String pAttributeName) {
        return Long.parseLong(getAttributeOrThrow(pAttributes, pAttributeName));
    }

    public static final float getFloatAttribute(Attributes pAttributes, String pAttributeName, float pDefaultValue) {
        String value = pAttributes.getValue("", pAttributeName);
        return value != null ? Float.parseFloat(value) : pDefaultValue;
    }

    public static final float getFloatAttributeOrThrow(Attributes pAttributes, String pAttributeName) {
        return Float.parseFloat(getAttributeOrThrow(pAttributes, pAttributeName));
    }

    public static final double getDoubleAttribute(Attributes pAttributes, String pAttributeName, double pDefaultValue) {
        String value = pAttributes.getValue("", pAttributeName);
        return value != null ? Double.parseDouble(value) : pDefaultValue;
    }

    public static final double getDoubleAttributeOrThrow(Attributes pAttributes, String pAttributeName) {
        return Double.parseDouble(getAttributeOrThrow(pAttributes, pAttributeName));
    }

    public static final void appendAttribute(StringBuilder pStringBuilder, String pName, boolean pValue) {
        appendAttribute(pStringBuilder, pName, String.valueOf(pValue));
    }

    public static final void appendAttribute(StringBuilder pStringBuilder, String pName, byte pValue) {
        appendAttribute(pStringBuilder, pName, String.valueOf(pValue));
    }

    public static final void appendAttribute(StringBuilder pStringBuilder, String pName, short pValue) {
        appendAttribute(pStringBuilder, pName, String.valueOf(pValue));
    }

    public static final void appendAttribute(StringBuilder pStringBuilder, String pName, int pValue) {
        appendAttribute(pStringBuilder, pName, String.valueOf(pValue));
    }

    public static final void appendAttribute(StringBuilder pStringBuilder, String pName, long pValue) {
        appendAttribute(pStringBuilder, pName, String.valueOf(pValue));
    }

    public static final void appendAttribute(StringBuilder pStringBuilder, String pName, float pValue) {
        appendAttribute(pStringBuilder, pName, String.valueOf(pValue));
    }

    public static final void appendAttribute(StringBuilder pStringBuilder, String pName, double pValue) {
        appendAttribute(pStringBuilder, pName, String.valueOf(pValue));
    }

    public static final void appendAttribute(StringBuilder pStringBuilder, String pName, String pValue) {
        pStringBuilder.append(' ').append(pName).append('=').append('\"').append(pValue).append('\"');
    }
}

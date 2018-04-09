package com.zhuoyi.system.network.serializer;

import com.zhuoyi.system.network.util.DESUtil;
import com.zhuoyi.system.util.EncryptUtils;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MessageCodec {
    private byte[] m_key = EncryptUtils.getEncryptKey().getBytes();

    class TempField {
        public ByteField attrib;
        public Field fieldInfo;

        TempField() {
        }
    }

    class TempFieldComparer implements Comparator {
        TempFieldComparer() {
        }

        public int compare(Object o1, Object o2) {
            if (((TempField) o1).attrib.index() > ((TempField) o2).attrib.index()) {
                return 1;
            }
            return -1;
        }
    }

    class TempFieldResult {
        public int length;
        public Object value;

        TempFieldResult() {
        }
    }

    private native byte[] nativeEncrypt(byte[] bArr, int i);

    private native String nativeGetServerAddress(int i);

    private native byte[] nativedecrypt(byte[] bArr, int i);

    public byte[] serializeMessage(ZyCom_Message msg) throws Exception {
        try {
            msg.head.length = 28;
            if (msg.message == null) {
                throw new Exception("message body is null");
            }
            byte[] bodyMessageArray = serializeObject(msg.message);
            SignalCode attrib = AttributeUitl.getMessageAttribute(msg.message);
            if (attrib != null && attrib.encrypt()) {
                bodyMessageArray = DESUtil.encrypt(bodyMessageArray, this.m_key);
            }
            ZyCom_MessageHead zyCom_MessageHead = msg.head;
            zyCom_MessageHead.length += bodyMessageArray.length;
            byte[] headMessageArray = serializeObject(msg.head);
            byte[] wholeMessageArray = new byte[(headMessageArray.length + bodyMessageArray.length)];
            System.arraycopy(headMessageArray, 0, wholeMessageArray, 0, headMessageArray.length);
            System.arraycopy(bodyMessageArray, 0, wholeMessageArray, headMessageArray.length, bodyMessageArray.length);
            return wholeMessageArray;
        } catch (Exception e) {
            throw new Exception("serialize message " + msg.message.getClass() + " fail:" + e.getLocalizedMessage());
        }
    }

    public ZyCom_MessageHead deserializeHead(byte[] buff, int offset) throws Exception {
        try {
            if (offset + 28 <= buff.length) {
                return deserializeObject(buff, offset, ZyCom_MessageHead.class).value;
            }
            throw new Exception("head length error");
        } catch (Exception e) {
            throw new Exception("deserialize head fail:" + e.getLocalizedMessage());
        }
    }

    public Object deserializeBody(byte[] buff, int offset, int size, int messageCode) throws Exception {
        try {
            byte[] bodyMessageArray = new byte[size];
            System.arraycopy(buff, offset, bodyMessageArray, 0, size);
            Class cls = MessageRecognizer.getClassByCode(messageCode);
            if (cls == null) {
                throw new Exception("Message code " + messageCode + " not found");
            }
            SignalCode m = AttributeUitl.getMessageAttribute(cls);
            if (m != null && m.encrypt()) {
                bodyMessageArray = DESUtil.decrypt(bodyMessageArray, this.m_key);
            }
            return deserializeObject(bodyMessageArray, 0, cls).value;
        } catch (Exception e) {
            throw new Exception("deserialize body fail:" + e.getLocalizedMessage());
        }
    }

    public byte[] serializeObject(Object obj) throws Exception {
        List<TempField> fieldArray = getFieldList(obj.getClass());
        List<Byte> serializedList = new ArrayList();
        for (int i = 0; i < fieldArray.size(); i++) {
            TempField tempfield = (TempField) fieldArray.get(i);
            tempfield.fieldInfo.setAccessible(true);
            byte[] array;
            if (tempfield.fieldInfo.getType().isPrimitive() || isWrapClass(tempfield.fieldInfo.getType()) || tempfield.fieldInfo.getType() == String.class) {
                array = primitiveObject2Buffer(tempfield.fieldInfo.getType(), tempfield.fieldInfo.get(obj));
                addByteArray2List(serializedList, array, 0, array.length);
            } else if (tempfield.fieldInfo.getType().isArray()) {
                Object arrayObj = tempfield.fieldInfo.get(obj);
                arrayLen = Array.getLength(arrayObj);
                countBytes = new byte[4];
                ByteUtil.putInt(countBytes, arrayLen, 0);
                addByteArray2List(serializedList, countBytes, 0, countBytes.length);
                if (tempfield.fieldInfo.getType().getComponentType().isPrimitive() || isWrapClass(tempfield.fieldInfo.getType().getComponentType()) || tempfield.fieldInfo.getType().getComponentType() == String.class) {
                    for (l = 0; l < arrayLen; l++) {
                        array = primitiveObject2Buffer(tempfield.fieldInfo.getType().getComponentType(), Array.get(arrayObj, l));
                        addByteArray2List(serializedList, array, 0, array.length);
                    }
                } else {
                    throw new Exception(new StringBuilder(String.valueOf(tempfield.fieldInfo.getName())).append(" array type is not primitive or String").toString());
                }
            } else if (List.class.isAssignableFrom(tempfield.fieldInfo.getType())) {
                List<?> arrayObj2 = (List) tempfield.fieldInfo.get(obj);
                arrayLen = arrayObj2.size();
                countBytes = new byte[4];
                ByteUtil.putInt(countBytes, arrayLen, 0);
                addByteArray2List(serializedList, countBytes, 0, countBytes.length);
                Type fc = tempfield.fieldInfo.getGenericType();
                if (fc instanceof ParameterizedType) {
                    Class<?> genericClazz = ((ParameterizedType) fc).getActualTypeArguments()[0];
                    if (genericClazz.isPrimitive() || isWrapClass(genericClazz) || genericClazz == String.class) {
                        for (l = 0; l < arrayLen; l++) {
                            array = primitiveObject2Buffer(genericClazz, arrayObj2.get(l));
                            addByteArray2List(serializedList, array, 0, array.length);
                        }
                    } else {
                        for (l = 0; l < arrayLen; l++) {
                            array = serializeObject(arrayObj2.get(l));
                            addByteArray2List(serializedList, array, 0, array.length);
                        }
                    }
                }
            } else {
                byte[] buff = serializeObject(tempfield.fieldInfo.get(obj));
                addByteArray2List(serializedList, buff, 0, buff.length);
            }
        }
        return toByteArray(serializedList);
    }

    private byte[] primitiveObject2Buffer(Class<?> cls, Object obj) throws UnsupportedEncodingException {
        byte[] array = null;
        if (cls == Byte.TYPE || cls == Byte.class) {
            array = new byte[]{((Byte) obj).byteValue()};
        }
        if (cls == Character.TYPE || cls == Character.class) {
            return new byte[]{(byte) ((Character) obj).charValue()};
        } else if (cls == Short.TYPE || cls == Short.class) {
            array = new byte[2];
            ByteUtil.putShort(array, ((Short) obj).shortValue(), 0);
            return array;
        } else if (cls == Integer.TYPE || cls == Integer.class) {
            array = new byte[4];
            ByteUtil.putInt(array, ((Integer) obj).intValue(), 0);
            return array;
        } else if (cls == Long.TYPE || cls == Long.class) {
            array = new byte[8];
            ByteUtil.putLong(array, ((Long) obj).longValue(), 0);
            return array;
        } else if (cls == Float.TYPE || cls == Float.class) {
            array = new byte[4];
            ByteUtil.putFloat(array, ((Float) obj).floatValue(), 0);
            return array;
        } else if (cls == Double.TYPE || cls == Double.class) {
            array = new byte[8];
            ByteUtil.putDouble(array, ((Double) obj).doubleValue(), 0);
            return array;
        } else if (cls == Boolean.TYPE || cls == Boolean.class) {
            array = new byte[1];
            if (((Boolean) obj).booleanValue()) {
                array[0] = (byte) 1;
                return array;
            }
            array[0] = (byte) 0;
            return array;
        } else if (cls != String.class) {
            return array;
        } else {
            if (obj == null) {
                obj = "";
            }
            byte[] t = ((String) obj).getBytes("UTF-8");
            array = new byte[(t.length + 1)];
            System.arraycopy(t, 0, array, 0, t.length);
            array[t.length] = (byte) 0;
            return array;
        }
    }

    public TempFieldResult deserializeObject(byte[] array, int offset, Class<?> cls) throws Exception {
        TempFieldResult ret = new TempFieldResult();
        ret.value = cls.newInstance();
        List<TempField> fieldArray = getFieldList(cls);
        for (int i = 0; i < fieldArray.size(); i++) {
            TempField tempfield = (TempField) fieldArray.get(i);
            tempfield.fieldInfo.setAccessible(true);
            TempFieldResult result;
            if (tempfield.fieldInfo.getType().isPrimitive() || isWrapClass(tempfield.fieldInfo.getType()) || tempfield.fieldInfo.getType() == String.class) {
                result = buffer2PrimitiveObject(array, offset, tempfield.fieldInfo.getType());
                offset += result.length;
                tempfield.fieldInfo.set(ret.value, result.value);
            } else if (tempfield.fieldInfo.getType().isArray()) {
                arrayLen = ByteUtil.getInt(array, offset);
                offset += 4;
                if (tempfield.fieldInfo.getType().getComponentType().isPrimitive() || isWrapClass(tempfield.fieldInfo.getType().getComponentType()) || tempfield.fieldInfo.getType().getComponentType() == String.class) {
                    for (l = 0; l < arrayLen; l++) {
                        result = buffer2PrimitiveObject(array, offset, tempfield.fieldInfo.getType().getComponentType());
                        offset += result.length;
                        Array.set(tempfield.fieldInfo.get(ret.value), l, result.value);
                    }
                    System.out.print("\n");
                } else {
                    throw new Exception(new StringBuilder(String.valueOf(tempfield.fieldInfo.getName())).append(" array type is not primitive or String").toString());
                }
            } else if (List.class.isAssignableFrom(tempfield.fieldInfo.getType())) {
                arrayLen = ByteUtil.getInt(array, offset);
                offset += 4;
                Type fc = tempfield.fieldInfo.getGenericType();
                if (fc instanceof ParameterizedType) {
                    Class<?> genericClazz = ((ParameterizedType) fc).getActualTypeArguments()[0];
                    List objList = new ArrayList();
                    if (genericClazz.isPrimitive() || isWrapClass(genericClazz) || genericClazz == String.class) {
                        for (l = 0; l < arrayLen; l++) {
                            result = buffer2PrimitiveObject(array, offset, genericClazz);
                            offset += result.length;
                            objList.add(result.value);
                        }
                    } else {
                        for (l = 0; l < arrayLen; l++) {
                            result = deserializeObject(array, offset, genericClazz);
                            offset = result.length;
                            objList.add(result.value);
                        }
                    }
                    tempfield.fieldInfo.set(ret.value, objList);
                }
            } else {
                result = deserializeObject(array, offset, tempfield.fieldInfo.getType());
                offset = result.length;
                tempfield.fieldInfo.set(ret.value, result.value);
            }
        }
        ret.length = offset;
        return ret;
    }

    private TempFieldResult buffer2PrimitiveObject(byte[] valueBytes, int offset, Class<?> cls) throws Exception {
        TempFieldResult result = new TempFieldResult();
        if (cls == Byte.TYPE || cls == Byte.class) {
            result.length = 1;
            result.value = new Byte(valueBytes[offset]);
        }
        if (cls == Character.TYPE || cls == Character.class) {
            result.length = 1;
            result.value = Character.valueOf((char) valueBytes[offset]);
        } else if (cls == Short.TYPE || cls == Short.class) {
            result.length = 2;
            result.value = Short.valueOf(ByteUtil.getShort(valueBytes, offset));
        } else if (cls == Integer.TYPE || cls == Integer.class) {
            result.length = 4;
            result.value = Integer.valueOf(ByteUtil.getInt(valueBytes, offset));
        } else if (cls == Long.TYPE || cls == Long.class) {
            result.length = 8;
            result.value = Long.valueOf(ByteUtil.getLong(valueBytes, offset));
        } else if (cls == Float.TYPE || cls == Float.class) {
            result.length = 4;
            result.value = Float.valueOf(ByteUtil.getFloat(valueBytes, offset));
        } else if (cls == Double.TYPE || cls == Double.class) {
            result.length = 8;
            result.value = Double.valueOf(ByteUtil.getDouble(valueBytes, offset));
        } else if (cls == Boolean.TYPE || cls == Boolean.class) {
            result.length = 1;
            if (valueBytes[offset] == (byte) 1) {
                result.value = Boolean.valueOf(true);
            } else {
                result.value = Boolean.valueOf(false);
            }
        } else if (cls == String.class) {
            List<Byte> blist = new ArrayList();
            for (int l = offset; l < valueBytes.length; l++) {
                result.length++;
                if (valueBytes[l] == (byte) 0) {
                    break;
                }
                blist.add(Byte.valueOf(valueBytes[l]));
            }
            result.value = new String(toByteArray(blist), "UTF-8");
        }
        return result;
    }

    private List<TempField> getFieldList(Class<?> cls) {
        List<TempField> fieldArray = new ArrayList();
        ArrayList<Field> fields = getAllField(cls);
        for (int i = 0; i < fields.size(); i++) {
            ByteField attrib = AttributeUitl.getFieldAttribute((Field) fields.get(i));
            if (attrib != null) {
                TempField tempfield = new TempField();
                tempfield.attrib = attrib;
                tempfield.fieldInfo = (Field) fields.get(i);
                fieldArray.add(tempfield);
            }
        }
        Collections.sort(fieldArray, new TempFieldComparer());
        return fieldArray;
    }

    private ArrayList<Field> getAllField(Class cls) {
        ArrayList<Field> list = new ArrayList();
        for (Field field : cls.getDeclaredFields()) {
            list.add(field);
        }
        if (cls.getSuperclass() != Object.class) {
            list.addAll(getAllField(cls.getSuperclass()));
        }
        return list;
    }

    private boolean isWrapClass(Class<?> clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    private void addByteArray2List(List<Byte> list, byte[] array, int offset, int len) {
        int i = offset;
        while (i < array.length && i < len) {
            list.add(Byte.valueOf(array[i]));
            i++;
        }
    }

    private byte[] toByteArray(List in) {
        int n = in.size();
        byte[] ret = new byte[n];
        for (int i = 0; i < n; i++) {
            ret[i] = ((Byte) in.get(i)).byteValue();
        }
        return ret;
    }
}

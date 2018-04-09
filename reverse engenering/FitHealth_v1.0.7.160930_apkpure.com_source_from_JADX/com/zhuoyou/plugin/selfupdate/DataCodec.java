package com.zhuoyou.plugin.selfupdate;

import java.util.HashMap;

public interface DataCodec {
    HashMap<String, Object> splitMySelfData(String str);
}

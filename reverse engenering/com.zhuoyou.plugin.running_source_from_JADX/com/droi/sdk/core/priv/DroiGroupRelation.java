package com.droi.sdk.core.priv;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiObjectName;

@DroiObjectName("_Group_User_Relation")
public class DroiGroupRelation extends DroiObject {
    @DroiExpose
    public String GroupObjectId;
    @DroiExpose
    public String MemberGroupObjectId;
    @DroiExpose
    public String MemberUserObjectId;
}

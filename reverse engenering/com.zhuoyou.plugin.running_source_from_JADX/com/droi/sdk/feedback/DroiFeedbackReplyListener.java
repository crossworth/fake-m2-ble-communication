package com.droi.sdk.feedback;

import java.util.List;

public interface DroiFeedbackReplyListener {
    void onResult(int i, List<DroiFeedbackInfo> list);
}

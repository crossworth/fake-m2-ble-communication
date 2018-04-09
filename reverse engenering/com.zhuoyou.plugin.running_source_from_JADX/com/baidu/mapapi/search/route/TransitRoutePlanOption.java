package com.baidu.mapapi.search.route;

public class TransitRoutePlanOption {
    PlanNode f1777a = null;
    PlanNode f1778b = null;
    String f1779c = null;
    TransitPolicy f1780d = TransitPolicy.EBUS_TIME_FIRST;

    public enum TransitPolicy {
        EBUS_TIME_FIRST(3),
        EBUS_TRANSFER_FIRST(4),
        EBUS_WALK_FIRST(5),
        EBUS_NO_SUBWAY(6);
        
        private int f1776a;

        private TransitPolicy(int i) {
            this.f1776a = 0;
            this.f1776a = i;
        }

        public int getInt() {
            return this.f1776a;
        }
    }

    public TransitRoutePlanOption city(String str) {
        this.f1779c = str;
        return this;
    }

    public TransitRoutePlanOption from(PlanNode planNode) {
        this.f1777a = planNode;
        return this;
    }

    public TransitRoutePlanOption policy(TransitPolicy transitPolicy) {
        this.f1780d = transitPolicy;
        return this;
    }

    public TransitRoutePlanOption to(PlanNode planNode) {
        this.f1778b = planNode;
        return this;
    }
}

package org.andengine.util.adt.data.operator;

public enum StringOperator {
    EQUALS {
        public boolean check(String pStringA, String pStringB) {
            return pStringA.equals(pStringB);
        }
    },
    EQUALS_IGNORE_CASE {
        public boolean check(String pStringA, String pStringB) {
            return pStringA.equalsIgnoreCase(pStringB);
        }
    },
    NOT_EQUALS {
        public boolean check(String pStringA, String pStringB) {
            return !pStringA.equals(pStringB);
        }
    },
    NOT_EQUALS_IGNORE_CASE {
        public boolean check(String pStringA, String pStringB) {
            return !pStringA.equalsIgnoreCase(pStringB);
        }
    },
    CONTAINS {
        public boolean check(String pStringA, String pStringB) {
            return pStringA.contains(pStringB);
        }
    },
    NOT_CONTAINS {
        public boolean check(String pStringA, String pStringB) {
            return !pStringA.contains(pStringB);
        }
    },
    STARTS_WITH {
        public boolean check(String pStringA, String pStringB) {
            return pStringA.startsWith(pStringB);
        }
    },
    NOT_STARTS_WITH {
        public boolean check(String pStringA, String pStringB) {
            return !pStringA.startsWith(pStringB);
        }
    },
    ENDS_WITH {
        public boolean check(String pStringA, String pStringB) {
            return pStringA.endsWith(pStringB);
        }
    },
    NOT_ENDS_WITH {
        public boolean check(String pStringA, String pStringB) {
            return !pStringA.endsWith(pStringB);
        }
    };

    public abstract boolean check(String str, String str2);
}

package org.andengine.util.adt.data.operator;

public enum IntOperator {
    EQUALS {
        public boolean check(int pIntA, int pIntB) {
            return pIntA == pIntB;
        }
    },
    NOT_EQUALS {
        public boolean check(int pIntA, int pIntB) {
            return pIntA != pIntB;
        }
    },
    LESS_THAN {
        public boolean check(int pIntA, int pIntB) {
            return pIntA < pIntB;
        }
    },
    LESS_OR_EQUAL_THAN {
        public boolean check(int pIntA, int pIntB) {
            return pIntA <= pIntB;
        }
    },
    MORE_THAN {
        public boolean check(int pIntA, int pIntB) {
            return pIntA > pIntB;
        }
    },
    MORE_OR_EQUAL_THAN {
        public boolean check(int pIntA, int pIntB) {
            return pIntA >= pIntB;
        }
    };

    public abstract boolean check(int i, int i2);
}

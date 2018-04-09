package org.andengine.util.adt.data.operator;

public enum DoubleOperator {
    EQUALS {
        public boolean check(double pDoubleA, double pDoubleB) {
            return pDoubleA == pDoubleB;
        }
    },
    NOT_EQUALS {
        public boolean check(double pDoubleA, double pDoubleB) {
            return pDoubleA != pDoubleB;
        }
    },
    LESS_THAN {
        public boolean check(double pDoubleA, double pDoubleB) {
            return pDoubleA < pDoubleB;
        }
    },
    LESS_OR_EQUAL_THAN {
        public boolean check(double pDoubleA, double pDoubleB) {
            return pDoubleA <= pDoubleB;
        }
    },
    MORE_THAN {
        public boolean check(double pDoubleA, double pDoubleB) {
            return pDoubleA > pDoubleB;
        }
    },
    MORE_OR_EQUAL_THAN {
        public boolean check(double pDoubleA, double pDoubleB) {
            return pDoubleA >= pDoubleB;
        }
    };

    public abstract boolean check(double d, double d2);
}

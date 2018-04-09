package org.andengine.util.adt.data.operator;

public enum ShortOperator {
    EQUALS {
        public boolean check(short pShortA, short pShortB) {
            return pShortA == pShortB;
        }
    },
    NOT_EQUALS {
        public boolean check(short pShortA, short pShortB) {
            return pShortA != pShortB;
        }
    },
    LESS_THAN {
        public boolean check(short pShortA, short pShortB) {
            return pShortA < pShortB;
        }
    },
    LESS_OR_EQUAL_THAN {
        public boolean check(short pShortA, short pShortB) {
            return pShortA <= pShortB;
        }
    },
    MORE_THAN {
        public boolean check(short pShortA, short pShortB) {
            return pShortA > pShortB;
        }
    },
    MORE_OR_EQUAL_THAN {
        public boolean check(short pShortA, short pShortB) {
            return pShortA >= pShortB;
        }
    };

    public abstract boolean check(short s, short s2);
}

package org.andengine.util.adt.data.operator;

public enum CharOperator {
    EQUALS {
        public boolean check(char pCharA, char pCharB) {
            return pCharA == pCharB;
        }
    },
    NOT_EQUALS {
        public boolean check(char pCharA, char pCharB) {
            return pCharA != pCharB;
        }
    },
    LESS_THAN {
        public boolean check(char pCharA, char pCharB) {
            return pCharA < pCharB;
        }
    },
    LESS_OR_EQUAL_THAN {
        public boolean check(char pCharA, char pCharB) {
            return pCharA <= pCharB;
        }
    },
    MORE_THAN {
        public boolean check(char pCharA, char pCharB) {
            return pCharA > pCharB;
        }
    },
    MORE_OR_EQUAL_THAN {
        public boolean check(char pCharA, char pCharB) {
            return pCharA >= pCharB;
        }
    };

    public abstract boolean check(char c, char c2);
}

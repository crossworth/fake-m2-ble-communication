package org.andengine.util.adt.data.operator;

public enum FloatOperator {
    EQUALS {
        public boolean check(float pFloatA, float pFloatB) {
            return pFloatA == pFloatB;
        }
    },
    NOT_EQUALS {
        public boolean check(float pFloatA, float pFloatB) {
            return pFloatA != pFloatB;
        }
    },
    LESS_THAN {
        public boolean check(float pFloatA, float pFloatB) {
            return pFloatA < pFloatB;
        }
    },
    LESS_OR_EQUAL_THAN {
        public boolean check(float pFloatA, float pFloatB) {
            return pFloatA <= pFloatB;
        }
    },
    MORE_THAN {
        public boolean check(float pFloatA, float pFloatB) {
            return pFloatA > pFloatB;
        }
    },
    MORE_OR_EQUAL_THAN {
        public boolean check(float pFloatA, float pFloatB) {
            return pFloatA >= pFloatB;
        }
    };

    public abstract boolean check(float f, float f2);
}

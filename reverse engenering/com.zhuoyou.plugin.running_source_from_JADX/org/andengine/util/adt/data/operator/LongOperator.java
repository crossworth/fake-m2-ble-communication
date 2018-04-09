package org.andengine.util.adt.data.operator;

public enum LongOperator {
    EQUALS {
        public boolean check(long pLongA, long pLongB) {
            return pLongA == pLongB;
        }
    },
    NOT_EQUALS {
        public boolean check(long pLongA, long pLongB) {
            return pLongA != pLongB;
        }
    },
    LESS_THAN {
        public boolean check(long pLongA, long pLongB) {
            return pLongA < pLongB;
        }
    },
    LESS_OR_EQUAL_THAN {
        public boolean check(long pLongA, long pLongB) {
            return pLongA <= pLongB;
        }
    },
    MORE_THAN {
        public boolean check(long pLongA, long pLongB) {
            return pLongA > pLongB;
        }
    },
    MORE_OR_EQUAL_THAN {
        public boolean check(long pLongA, long pLongB) {
            return pLongA >= pLongB;
        }
    };

    public abstract boolean check(long j, long j2);
}

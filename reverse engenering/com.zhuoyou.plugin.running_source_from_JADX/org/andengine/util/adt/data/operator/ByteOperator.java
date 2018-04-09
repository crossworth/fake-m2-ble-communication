package org.andengine.util.adt.data.operator;

public enum ByteOperator {
    EQUALS {
        public boolean check(byte pByteA, byte pByteB) {
            return pByteA == pByteB;
        }
    },
    NOT_EQUALS {
        public boolean check(byte pByteA, byte pByteB) {
            return pByteA != pByteB;
        }
    },
    LESS_THAN {
        public boolean check(byte pByteA, byte pByteB) {
            return pByteA < pByteB;
        }
    },
    LESS_OR_EQUAL_THAN {
        public boolean check(byte pByteA, byte pByteB) {
            return pByteA <= pByteB;
        }
    },
    MORE_THAN {
        public boolean check(byte pByteA, byte pByteB) {
            return pByteA > pByteB;
        }
    },
    MORE_OR_EQUAL_THAN {
        public boolean check(byte pByteA, byte pByteB) {
            return pByteA >= pByteB;
        }
    };

    public abstract boolean check(byte b, byte b2);
}

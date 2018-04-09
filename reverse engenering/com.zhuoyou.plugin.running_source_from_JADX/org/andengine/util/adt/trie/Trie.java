package org.andengine.util.adt.trie;

import android.util.SparseArray;

public class Trie implements ITrie {
    private static final int CHILDREN_SIZE_DEFAULT = 26;
    private final TrieNode mRoot = new TrieNode();

    public static class TrieNode implements ITrie {
        private SparseArray<TrieNode> mChildren;
        private boolean mWordEndFlag;

        public TrieNode() {
            this(false);
        }

        public TrieNode(boolean pWordEndFlag) {
            this.mWordEndFlag = pWordEndFlag;
        }

        public void add(CharSequence pCharSequence) {
            int length = pCharSequence.length();
            if (length != 0) {
                add(pCharSequence, 0, length);
            }
        }

        public void add(CharSequence pCharSequence, int pStart, int pEnd) {
            if (this.mChildren == null) {
                this.mChildren = new SparseArray(26);
            }
            char character = pCharSequence.charAt(pStart);
            TrieNode child = (TrieNode) this.mChildren.get(character);
            if (child == null) {
                child = new TrieNode();
                this.mChildren.put(character, child);
            }
            if (pStart < pEnd - 1) {
                child.add(pCharSequence, pStart + 1, pEnd);
            } else {
                child.mWordEndFlag = true;
            }
        }

        public boolean contains(CharSequence pCharSequence) {
            int length = pCharSequence.length();
            if (length != 0) {
                return contains(pCharSequence, 0, length);
            }
            throw new IllegalArgumentException();
        }

        public boolean contains(CharSequence pCharSequence, int pStart, int pEnd) {
            if (this.mChildren == null) {
                return false;
            }
            TrieNode child = (TrieNode) this.mChildren.get(pCharSequence.charAt(pStart));
            if (child == null) {
                return false;
            }
            if (pStart < pEnd - 1) {
                return child.contains(pCharSequence, pStart + 1, pEnd);
            }
            return child.mWordEndFlag;
        }
    }

    public void add(CharSequence pCharSequence) {
        this.mRoot.add(pCharSequence);
    }

    public void add(CharSequence pCharSequence, int pStart, int pEnd) {
        this.mRoot.add(pCharSequence, pStart, pEnd);
    }

    public boolean contains(CharSequence pCharSequence) {
        return this.mRoot.contains(pCharSequence);
    }

    public boolean contains(CharSequence pCharSequence, int pStart, int pEnd) {
        return this.mRoot.contains(pCharSequence, pStart, pEnd);
    }
}

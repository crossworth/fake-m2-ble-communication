package org.andengine.util.adt.trie;

public interface ITrie {
    void add(CharSequence charSequence);

    void add(CharSequence charSequence, int i, int i2);

    boolean contains(CharSequence charSequence);

    boolean contains(CharSequence charSequence, int i, int i2);
}

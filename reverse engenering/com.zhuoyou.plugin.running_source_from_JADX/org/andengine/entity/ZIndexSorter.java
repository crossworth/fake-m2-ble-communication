package org.andengine.entity;

import java.util.Comparator;
import java.util.List;
import org.andengine.util.adt.list.IList;
import org.andengine.util.algorithm.sort.InsertionSorter;

public class ZIndexSorter extends InsertionSorter<IEntity> {
    private static ZIndexSorter INSTANCE;
    private final Comparator<IEntity> mZIndexComparator = new C20491();

    class C20491 implements Comparator<IEntity> {
        C20491() {
        }

        public int compare(IEntity pEntityA, IEntity pEntityB) {
            return pEntityA.getZIndex() - pEntityB.getZIndex();
        }
    }

    private ZIndexSorter() {
    }

    public static ZIndexSorter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ZIndexSorter();
        }
        return INSTANCE;
    }

    public void sort(IEntity[] pEntities) {
        sort((Object[]) pEntities, this.mZIndexComparator);
    }

    public void sort(IEntity[] pEntities, int pStart, int pEnd) {
        sort((Object[]) pEntities, pStart, pEnd, this.mZIndexComparator);
    }

    public void sort(List<IEntity> pEntities) {
        sort((List) pEntities, this.mZIndexComparator);
    }

    public void sort(List<IEntity> pEntities, int pStart, int pEnd) {
        sort((List) pEntities, pStart, pEnd, this.mZIndexComparator);
    }

    public void sort(IList<IEntity> pEntities) {
        sort((IList) pEntities, this.mZIndexComparator);
    }

    public void sort(IList<IEntity> pEntities, int pStart, int pEnd) {
        sort((IList) pEntities, pStart, pEnd, this.mZIndexComparator);
    }
}

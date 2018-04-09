package de.greenrobot.dao.query;

import de.greenrobot.dao.AbstractDao;
import java.util.Date;

abstract class AbstractQueryWithLimit<T> extends AbstractQuery<T> {
    protected final int limitPosition;
    protected final int offsetPosition;

    protected AbstractQueryWithLimit(AbstractDao<T, ?> dao, String sql, String[] initialValues, int limitPosition, int offsetPosition) {
        super(dao, sql, initialValues);
        this.limitPosition = limitPosition;
        this.offsetPosition = offsetPosition;
    }

    public void setParameter(int index, Object parameter) {
        if (index < 0 || !(index == this.limitPosition || index == this.offsetPosition)) {
            super.setParameter(index, parameter);
            return;
        }
        throw new IllegalArgumentException("Illegal parameter index: " + index);
    }

    public void setParameter(int index, Date parameter) {
        setParameter(index, parameter != null ? Long.valueOf(parameter.getTime()) : null);
    }

    public void setParameter(int index, Boolean parameter) {
        Object converted;
        if (parameter != null) {
            converted = Integer.valueOf(parameter.booleanValue() ? 1 : 0);
        } else {
            converted = null;
        }
        setParameter(index, converted);
    }

    public void setLimit(int limit) {
        checkThread();
        if (this.limitPosition == -1) {
            throw new IllegalStateException("Limit must be set with QueryBuilder before it can be used here");
        }
        this.parameters[this.limitPosition] = Integer.toString(limit);
    }

    public void setOffset(int offset) {
        checkThread();
        if (this.offsetPosition == -1) {
            throw new IllegalStateException("Offset must be set with QueryBuilder before it can be used here");
        }
        this.parameters[this.offsetPosition] = Integer.toString(offset);
    }
}

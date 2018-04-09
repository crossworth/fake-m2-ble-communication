package de.greenrobot.dao.test;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoLog;
import de.greenrobot.dao.InternalUnitTestDaoAccess;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.identityscope.IdentityScope;

public abstract class AbstractDaoTest<D extends AbstractDao<T, K>, T, K> extends DbTest {
    protected D dao;
    protected InternalUnitTestDaoAccess<T, K> daoAccess;
    protected final Class<D> daoClass;
    protected IdentityScope<K, T> identityScopeForDao;
    protected Property pkColumn;

    public AbstractDaoTest(Class<D> daoClass) {
        this(daoClass, true);
    }

    public AbstractDaoTest(Class<D> daoClass, boolean inMemory) {
        super(inMemory);
        this.daoClass = daoClass;
    }

    public void setIdentityScopeBeforeSetUp(IdentityScope<K, T> identityScope) {
        this.identityScopeForDao = identityScope;
    }

    protected void setUp() throws Exception {
        super.setUp();
        try {
            setUpTableForDao();
            this.daoAccess = new InternalUnitTestDaoAccess(this.db, this.daoClass, this.identityScopeForDao);
            this.dao = this.daoAccess.getDao();
        } catch (Exception e) {
            throw new RuntimeException("Could not prepare DAO Test", e);
        }
    }

    protected void setUpTableForDao() throws Exception {
        try {
            this.daoClass.getMethod("createTable", new Class[]{SQLiteDatabase.class, Boolean.TYPE}).invoke(null, new Object[]{this.db, Boolean.valueOf(false)});
        } catch (NoSuchMethodException e) {
            DaoLog.m4565i("No createTable method");
        }
    }

    protected void clearIdentityScopeIfAny() {
        if (this.identityScopeForDao != null) {
            this.identityScopeForDao.clear();
            DaoLog.m4561d("Identity scope cleared");
            return;
        }
        DaoLog.m4561d("No identity scope to clear");
    }

    protected void logTableDump() {
        logTableDump(this.dao.getTablename());
    }
}

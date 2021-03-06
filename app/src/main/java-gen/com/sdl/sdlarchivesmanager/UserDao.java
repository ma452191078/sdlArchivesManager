package com.sdl.sdlarchivesmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.sdl.sdlarchivesmanager.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property User_Num = new Property(1, String.class, "User_Num", false, "USER__NUM");
        public final static Property User_Pass = new Property(2, String.class, "User_Pass", false, "USER__PASS");
        public final static Property User_Name = new Property(3, String.class, "User_Name", false, "USER__NAME");
        public final static Property User_Regin = new Property(4, String.class, "User_Regin", false, "USER__REGIN");
        public final static Property User_Role = new Property(5, String.class, "User_Role", false, "USER__ROLE");
        public final static Property User_Status = new Property(6, Boolean.class, "User_Status", false, "USER__STATUS");
        public final static Property User_Date = new Property(7, java.util.Date.class, "User_Date", false, "USER__DATE");
    };


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER__NUM\" TEXT NOT NULL ," + // 1: User_Num
                "\"USER__PASS\" TEXT," + // 2: User_Pass
                "\"USER__NAME\" TEXT," + // 3: User_Name
                "\"USER__REGIN\" TEXT," + // 4: User_Regin
                "\"USER__ROLE\" TEXT," + // 5: User_Role
                "\"USER__STATUS\" INTEGER," + // 6: User_Status
                "\"USER__DATE\" INTEGER);"); // 7: User_Date
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getUser_Num());
 
        String User_Pass = entity.getUser_Pass();
        if (User_Pass != null) {
            stmt.bindString(3, User_Pass);
        }
 
        String User_Name = entity.getUser_Name();
        if (User_Name != null) {
            stmt.bindString(4, User_Name);
        }
 
        String User_Regin = entity.getUser_Regin();
        if (User_Regin != null) {
            stmt.bindString(5, User_Regin);
        }
 
        String User_Role = entity.getUser_Role();
        if (User_Role != null) {
            stmt.bindString(6, User_Role);
        }
 
        Boolean User_Status = entity.getUser_Status();
        if (User_Status != null) {
            stmt.bindLong(7, User_Status ? 1L: 0L);
        }
 
        java.util.Date User_Date = entity.getUser_Date();
        if (User_Date != null) {
            stmt.bindLong(8, User_Date.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // User_Num
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // User_Pass
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // User_Name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // User_Regin
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // User_Role
            cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0, // User_Status
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)) // User_Date
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUser_Num(cursor.getString(offset + 1));
        entity.setUser_Pass(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUser_Name(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUser_Regin(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUser_Role(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUser_Status(cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0);
        entity.setUser_Date(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}

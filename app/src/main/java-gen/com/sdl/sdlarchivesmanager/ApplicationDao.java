package com.sdl.sdlarchivesmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.sdl.sdlarchivesmanager.Application;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "APPLICATION".
*/
public class ApplicationDao extends AbstractDao<Application, Long> {

    public static final String TABLENAME = "APPLICATION";

    /**
     * Properties of entity Application.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property App_Name = new Property(1, String.class, "App_Name", false, "APP__NAME");
        public final static Property App_Owner = new Property(2, String.class, "App_Owner", false, "APP__OWNER");
        public final static Property App_Type = new Property(3, String.class, "App_Type", false, "APP__TYPE");
        public final static Property App_Level = new Property(4, String.class, "App_Level", false, "APP__LEVEL");
        public final static Property App_Uplevel = new Property(5, String.class, "App_Uplevel", false, "APP__UPLEVEL");
        public final static Property App_Phone = new Property(6, String.class, "App_Phone", false, "APP__PHONE");
        public final static Property App_Province = new Property(7, String.class, "App_Province", false, "APP__PROVINCE");
        public final static Property App_City = new Property(8, String.class, "App_City", false, "APP__CITY");
        public final static Property App_Country = new Property(9, String.class, "App_Country", false, "APP__COUNTRY");
        public final static Property App_Town = new Property(10, String.class, "App_Town", false, "APP__TOWN");
        public final static Property App_Address = new Property(11, String.class, "App_Address", false, "APP__ADDRESS");
        public final static Property App_LngLat = new Property(12, String.class, "App_LngLat", false, "APP__LNG_LAT");
        public final static Property App_Contract = new Property(13, String.class, "App_Contract", false, "APP__CONTRACT");
        public final static Property App_IdCardF = new Property(14, String.class, "App_IdCardF", false, "APP__ID_CARD_F");
        public final static Property App_IdCardB = new Property(15, String.class, "App_IdCardB", false, "APP__ID_CARD_B");
        public final static Property App_Licence = new Property(16, String.class, "App_Licence", false, "APP__LICENCE");
        public final static Property App_BankNum = new Property(17, String.class, "App_BankNum", false, "APP__BANK_NUM");
        public final static Property App_BankName = new Property(18, String.class, "App_BankName", false, "APP__BANK_NAME");
        public final static Property App_BankName2 = new Property(19, String.class, "App_BankName2", false, "APP__BANK_NAME2");
        public final static Property App_BankPhone = new Property(20, String.class, "App_BankPhone", false, "APP__BANK_PHONE");
        public final static Property App_BankInvoice = new Property(21, String.class, "App_BankInvoice", false, "APP__BANK_INVOICE");
        public final static Property App_Send = new Property(22, String.class, "App_Send", false, "APP__SEND");
        public final static Property App_Status = new Property(23, String.class, "App_Status", false, "APP__STATUS");
        public final static Property App_TimeFlag = new Property(24, java.util.Date.class, "App_TimeFlag", false, "APP__TIME_FLAG");
    };


    public ApplicationDao(DaoConfig config) {
        super(config);
    }
    
    public ApplicationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"APPLICATION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"APP__NAME\" TEXT," + // 1: App_Name
                "\"APP__OWNER\" TEXT," + // 2: App_Owner
                "\"APP__TYPE\" TEXT," + // 3: App_Type
                "\"APP__LEVEL\" TEXT," + // 4: App_Level
                "\"APP__UPLEVEL\" TEXT," + // 5: App_Uplevel
                "\"APP__PHONE\" TEXT," + // 6: App_Phone
                "\"APP__PROVINCE\" TEXT," + // 7: App_Province
                "\"APP__CITY\" TEXT," + // 8: App_City
                "\"APP__COUNTRY\" TEXT," + // 9: App_Country
                "\"APP__TOWN\" TEXT," + // 10: App_Town
                "\"APP__ADDRESS\" TEXT," + // 11: App_Address
                "\"APP__LNG_LAT\" TEXT," + // 12: App_LngLat
                "\"APP__CONTRACT\" TEXT," + // 13: App_Contract
                "\"APP__ID_CARD_F\" TEXT," + // 14: App_IdCardF
                "\"APP__ID_CARD_B\" TEXT," + // 15: App_IdCardB
                "\"APP__LICENCE\" TEXT," + // 16: App_Licence
                "\"APP__BANK_NUM\" TEXT," + // 17: App_BankNum
                "\"APP__BANK_NAME\" TEXT," + // 18: App_BankName
                "\"APP__BANK_NAME2\" TEXT," + // 19: App_BankName2
                "\"APP__BANK_PHONE\" TEXT," + // 20: App_BankPhone
                "\"APP__BANK_INVOICE\" TEXT," + // 21: App_BankInvoice
                "\"APP__SEND\" TEXT," + // 22: App_Send
                "\"APP__STATUS\" TEXT," + // 23: App_Status
                "\"APP__TIME_FLAG\" INTEGER);"); // 24: App_TimeFlag
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"APPLICATION\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Application entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String App_Name = entity.getApp_Name();
        if (App_Name != null) {
            stmt.bindString(2, App_Name);
        }
 
        String App_Owner = entity.getApp_Owner();
        if (App_Owner != null) {
            stmt.bindString(3, App_Owner);
        }
 
        String App_Type = entity.getApp_Type();
        if (App_Type != null) {
            stmt.bindString(4, App_Type);
        }
 
        String App_Level = entity.getApp_Level();
        if (App_Level != null) {
            stmt.bindString(5, App_Level);
        }
 
        String App_Uplevel = entity.getApp_Uplevel();
        if (App_Uplevel != null) {
            stmt.bindString(6, App_Uplevel);
        }
 
        String App_Phone = entity.getApp_Phone();
        if (App_Phone != null) {
            stmt.bindString(7, App_Phone);
        }
 
        String App_Province = entity.getApp_Province();
        if (App_Province != null) {
            stmt.bindString(8, App_Province);
        }
 
        String App_City = entity.getApp_City();
        if (App_City != null) {
            stmt.bindString(9, App_City);
        }
 
        String App_Country = entity.getApp_Country();
        if (App_Country != null) {
            stmt.bindString(10, App_Country);
        }
 
        String App_Town = entity.getApp_Town();
        if (App_Town != null) {
            stmt.bindString(11, App_Town);
        }
 
        String App_Address = entity.getApp_Address();
        if (App_Address != null) {
            stmt.bindString(12, App_Address);
        }
 
        String App_LngLat = entity.getApp_LngLat();
        if (App_LngLat != null) {
            stmt.bindString(13, App_LngLat);
        }
 
        String App_Contract = entity.getApp_Contract();
        if (App_Contract != null) {
            stmt.bindString(14, App_Contract);
        }
 
        String App_IdCardF = entity.getApp_IdCardF();
        if (App_IdCardF != null) {
            stmt.bindString(15, App_IdCardF);
        }
 
        String App_IdCardB = entity.getApp_IdCardB();
        if (App_IdCardB != null) {
            stmt.bindString(16, App_IdCardB);
        }
 
        String App_Licence = entity.getApp_Licence();
        if (App_Licence != null) {
            stmt.bindString(17, App_Licence);
        }
 
        String App_BankNum = entity.getApp_BankNum();
        if (App_BankNum != null) {
            stmt.bindString(18, App_BankNum);
        }
 
        String App_BankName = entity.getApp_BankName();
        if (App_BankName != null) {
            stmt.bindString(19, App_BankName);
        }
 
        String App_BankName2 = entity.getApp_BankName2();
        if (App_BankName2 != null) {
            stmt.bindString(20, App_BankName2);
        }
 
        String App_BankPhone = entity.getApp_BankPhone();
        if (App_BankPhone != null) {
            stmt.bindString(21, App_BankPhone);
        }
 
        String App_BankInvoice = entity.getApp_BankInvoice();
        if (App_BankInvoice != null) {
            stmt.bindString(22, App_BankInvoice);
        }
 
        String App_Send = entity.getApp_Send();
        if (App_Send != null) {
            stmt.bindString(23, App_Send);
        }
 
        String App_Status = entity.getApp_Status();
        if (App_Status != null) {
            stmt.bindString(24, App_Status);
        }
 
        java.util.Date App_TimeFlag = entity.getApp_TimeFlag();
        if (App_TimeFlag != null) {
            stmt.bindLong(25, App_TimeFlag.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Application readEntity(Cursor cursor, int offset) {
        Application entity = new Application( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // App_Name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // App_Owner
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // App_Type
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // App_Level
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // App_Uplevel
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // App_Phone
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // App_Province
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // App_City
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // App_Country
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // App_Town
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // App_Address
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // App_LngLat
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // App_Contract
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // App_IdCardF
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // App_IdCardB
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // App_Licence
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // App_BankNum
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // App_BankName
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // App_BankName2
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // App_BankPhone
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // App_BankInvoice
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // App_Send
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // App_Status
            cursor.isNull(offset + 24) ? null : new java.util.Date(cursor.getLong(offset + 24)) // App_TimeFlag
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Application entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setApp_Name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setApp_Owner(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setApp_Type(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setApp_Level(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setApp_Uplevel(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setApp_Phone(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setApp_Province(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setApp_City(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setApp_Country(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setApp_Town(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setApp_Address(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setApp_LngLat(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setApp_Contract(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setApp_IdCardF(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setApp_IdCardB(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setApp_Licence(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setApp_BankNum(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setApp_BankName(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setApp_BankName2(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setApp_BankPhone(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setApp_BankInvoice(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setApp_Send(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setApp_Status(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setApp_TimeFlag(cursor.isNull(offset + 24) ? null : new java.util.Date(cursor.getLong(offset + 24)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Application entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Application entity) {
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

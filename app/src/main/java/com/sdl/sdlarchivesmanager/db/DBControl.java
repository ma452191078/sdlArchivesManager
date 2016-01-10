package com.sdl.sdlarchivesmanager.db;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sdl.sdlarchivesmanager.DaoMaster;
import com.sdl.sdlarchivesmanager.DaoSession;

public class DBControl extends Application{

	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	public static  SQLiteDatabase db;
	public static final String DB_NAME = "archives.db";

  /** 
   * 取得DaoMaster 
   *  
   * @param context 
   * @return 
   */  
  public static DaoMaster getDaoMaster(Context context) {  
      if (daoMaster == null) {  
          DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,DB_NAME, null);
          daoMaster = new DaoMaster(helper.getWritableDatabase());  
      }  
      return daoMaster;  
  }  
   
  /** 
   * 取得DaoSession 
   *  
   * @param context 
   * @return 
   */  
  public static DaoSession getDaoSession(Context context) {  
      if (daoSession == null) {  
          if (daoMaster == null) {  
              daoMaster = getDaoMaster(context);  
          }  
          daoSession = daoMaster.newSession();  
      }  
      return daoSession;  
  }  
  /** 
   * 得到Datebase 
   *  
   * @param context 
   * @return 
   */  
  public static SQLiteDatabase getSQLDatebase(Context context) {  
      if (daoSession == null) {  
          if (daoMaster == null) {  
              daoMaster = getDaoMaster(context);  
          }  
          db = daoMaster.getDatabase();  
      }  
      return db;  
  }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
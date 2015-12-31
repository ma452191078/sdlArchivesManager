package com.sdl.sdlarchivesmanager.db;


import android.content.Context;

import com.sdl.sdlarchivesmanager.Application;
import com.sdl.sdlarchivesmanager.ApplicationDao;
import com.sdl.sdlarchivesmanager.BankDao;
import com.sdl.sdlarchivesmanager.ClientDao;
import com.sdl.sdlarchivesmanager.DaoSession;
import com.sdl.sdlarchivesmanager.User;
import com.sdl.sdlarchivesmanager.UserDao;
import com.sdl.sdlarchivesmanager.util.GetDateUtil;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class DBHelper {
    private static final String TAG = DBHelper.class.getSimpleName();
    private static DBHelper instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private ApplicationDao applicationDao;
    private BankDao bankDao;
    private ClientDao clientDao;
    private UserDao userDao;

    private DBHelper() {
    }

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper();
            if (appContext == null) {
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = DBControl.getDaoSession(context);
            instance.applicationDao = instance.mDaoSession.getApplicationDao();
            instance.bankDao = instance.mDaoSession.getBankDao();
            instance.clientDao = instance.mDaoSession.getClientDao();
            instance.userDao = instance.mDaoSession.getUserDao();
        }
        return instance;
    }

    public void dropApplicationTable() {
            ApplicationDao.dropTable(mDaoSession.getDatabase(), true);
    }

    public void dropBankTable() {
        BankDao.dropTable(mDaoSession.getDatabase(), true);
    }

    public void dropClientTable() {
        ClientDao.dropTable(mDaoSession.getDatabase(), true);
    }

    public void dropUserTable() {
        UserDao.dropTable(mDaoSession.getDatabase(), true);
    }

    public void dropAllTable() {
        ApplicationDao.dropTable(mDaoSession.getDatabase(), true);
        BankDao.dropTable(mDaoSession.getDatabase(), true);
        ClientDao.dropTable(mDaoSession.getDatabase(), true);
        UserDao.dropTable(mDaoSession.getDatabase(), true);
    }

    public void createAllTable() {
        ApplicationDao.createTable(mDaoSession.getDatabase(), true);
        BankDao.createTable(mDaoSession.getDatabase(), true);
        ClientDao.createTable(mDaoSession.getDatabase(), true);
        UserDao.createTable(mDaoSession.getDatabase(), true);

    }

    public boolean addUser(User user) {
        try {
            userDao.insertOrReplace(user);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public boolean updateUser(User user) {
        try {
            userDao.update(user);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public User loadUserByNum(String userNum) {

        Query query = userDao.queryBuilder()
                .where(UserDao.Properties.User_Num.eq(userNum))
                .orderAsc(UserDao.Properties.User_Date)
                .build();
        List<User> userList = query.list();
        User user = userList.get(0);
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        return user;
    }

    public User loadUserByStatus() {
        Query query = userDao.queryBuilder()
                .where(UserDao.Properties.User_Status.eq(true))
                .orderAsc(UserDao.Properties.User_Date)
                .build();
        List<User> userList = query.list();
        if (userList.size() > 0){
            User user = userList.get(0);
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
            return user;
        }else {
            return null;
        }
    }

    public boolean existUser(String userNum) {
        boolean status;
        Query query = userDao.queryBuilder()
                .where(UserDao.Properties.User_Num.eq(userNum))
                .orderAsc(UserDao.Properties.User_Date)
                .build();
        List<User> userList = query.list();
        if (userList.size() > 0) {
            status = true;
        } else {
            status = false;
        }
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        return status;
    }

    public boolean loadUserStatus(String userNum) {
        boolean status;
        Date userDate;
        Date localDate;
        int diff;
        Query query = userDao.queryBuilder()
                .where(UserDao.Properties.User_Num.eq(userNum))
                .orderAsc(UserDao.Properties.User_Date)
                .build();
        List<User> userList = query.list();
        if (userList.size() == 0) {
            status = false;
        } else {
            userDate = userList.get(0).getUser_Date();
            localDate = new Date(System.currentTimeMillis());//获取当前时间

            diff = new GetDateUtil().getDateDayCount(userDate, localDate);
            if (diff > 30) {
                status = false;
            } else {
                status = true;
            }
        }

        return status;
    }

    public void setOtherUserFalse(String userNum){
        Query query = userDao.queryBuilder()
                .where(UserDao.Properties.User_Num.notEq(userNum))
                .orderAsc(UserDao.Properties.User_Date)
                .build();
        List<User> userList = query.list();
        if (userList.size() > 0) {
            for (int i = 0; i < userList.size(); i++){
                userList.get(i).setUser_Status(false);
            }
            userDao.updateInTx(userList);
        }
    }

    public boolean addApplication(Application app){
        try{
            applicationDao.insertOrReplace(app);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public void updateApplication(Date timeFlag, Application application){
        applicationDao.update(application);
    }

    public boolean existApplication(Date timeFlag){
        boolean status;
        Query query = applicationDao.queryBuilder()
                .where(ApplicationDao.Properties.App_TimeFlag.eq(timeFlag))
                .orderAsc(ApplicationDao.Properties.App_TimeFlag)
                .build();
        List<Application> appList = query.list();
        if (appList.size() > 0) {
            status = true;
        } else {
            status = false;
        }
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        return status;
    }

    public Application loadApplication(Date timeFlag){
        Application app = null;
        Query query = applicationDao.queryBuilder()
                .where(ApplicationDao.Properties.App_TimeFlag.eq(timeFlag))
                .orderAsc(ApplicationDao.Properties.App_TimeFlag)
                .build();
        List<Application> appList = query.list();
        if (appList.size() > 0) {
            app = appList.get(0);
        }
        return app;
    }
}

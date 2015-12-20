package com.sdl.sdlarchivesmanager.db;

import com.sdl.sdlarchivesmanager.DaoMaster;
import com.sdl.sdlarchivesmanager.DaoSession;
import com.sdl.sdlarchivesmanager.User;

import de.greenrobot.dao.test.AbstractDaoSessionTest;

/**
 * Created by majingyuan on 15/12/20.
 */
public class DbUser extends AbstractDaoSessionTest<DaoMaster, DaoSession> {

    public DbUser() {
        super(DaoMaster.class);
    }

    public boolean addUser(User user){
        try {
            daoSession.insert(user);
            return true;
        }catch (Exception ex){
            return false;
        }

    }

}

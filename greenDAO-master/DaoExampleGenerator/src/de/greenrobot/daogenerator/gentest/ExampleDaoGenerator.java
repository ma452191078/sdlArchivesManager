/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class ExampleDaoGenerator {


    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "de.greenrobot.daoexample");

        addNote(schema);
        addCustomerOrder(schema);

        new DaoGenerator().generateAll(schema, "../DaoExample/src/main/java");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    private static void addCustomerOrder(Schema schema) {
        //        用户表
        Entity user = schema.addEntity("User");
        //user.setTableName("USER");  //表名
        user.addIdProperty().autoincrement();   //ID
        user.addStringProperty("User_Num").notNull();  //业务员编号
        user.addStringProperty("User_Name"); //业务员姓名
        user.addStringProperty("User_Regin");    //业务员区域
        user.addStringProperty("User_Role");     //用户角色,业务员或大区经理
        user.addDateProperty("User_Date");
    }

}

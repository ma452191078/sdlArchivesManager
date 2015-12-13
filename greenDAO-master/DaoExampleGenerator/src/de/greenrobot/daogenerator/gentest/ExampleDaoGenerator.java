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
        Schema schema = new Schema(1, "com.sdl.sdlarchivesmanager");
//        用户
        addUser(schema);
//        经销商

        new DaoGenerator().generateAll(schema, "../app/src/main/java-gen");
    }

    private static void addUser(Schema schema) {
//        用户表
        Entity user = schema.addEntity("User");
        //user.setTableName("USER");  //表名
        user.addIdProperty().autoincrement();   //ID
        user.addStringProperty("User_Num").notNull();  //业务员编号
        user.addStringProperty("User_Name"); //业务员姓名
        user.addStringProperty("User_Regin");    //业务员区域
        user.addStringProperty("User_Role");     //用户角色,业务员或大区经理
        user.addDateProperty("User_Date");

//        经销商表
        Entity client = schema.addEntity("Client");
        client.addIdProperty().autoincrement();
        client.addStringProperty("Client_Num").notNull();    //经销商编号
        client.addStringProperty("Client_Name");     //经销商名称
        client.addStringProperty("Client_Owner");    //法人代表
        client.addStringProperty("Client_Type");     //经销商类型,经销商/种植大户
        client.addStringProperty("Client_Level");    //经销商层级,一级/二级/三级
        client.addStringProperty("Client_Uplevel");  //上级经销商编号
        client.addStringProperty("Client_Phone");    //经销商电话
        client.addStringProperty("Client_Province");    //省
        client.addStringProperty("Client_City");    //市
        client.addStringProperty("Client_Country"); //县
        client.addStringProperty("Client_Town");    //乡镇
        client.addStringProperty("Client_Address"); //详细地址
        client.addStringProperty("Client_LngLat");  //经纬度

//        银行信息
        Entity bank = schema.addEntity("Bank");
        bank.addIdProperty().autoincrement();
        bank.addStringProperty("Bank_ClientNum");   //经销商编号
        bank.addStringProperty("Bank_Num");     //银行卡号
        bank.addStringProperty("Bank_Name");    //银行名称
        bank.addStringProperty("Bank_Name2");   //支行名称
        bank.addStringProperty("Bank_Phone");   //电话
        bank.addStringProperty("Bank_Invoice");  //发票类型,专用发票,普通发票

//        申请表
        Entity application = schema.addEntity("Application");
        application.addIdProperty();

    }

}
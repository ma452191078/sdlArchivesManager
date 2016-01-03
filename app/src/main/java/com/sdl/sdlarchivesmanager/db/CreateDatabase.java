package com.sdl.sdlarchivesmanager.db;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by majingyuan on 15/12/13.
 * 创建数据库
 */
public class CreateDatabase {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.sdl.sdlarchivesmanager");
//        用户
        addUser(schema);
//        经销商

        new DaoGenerator().generateAll(schema, "/Users/majingyuan/AndroidStudioProjects/sdlArchivesManager/app/src/main/java-gen");
    }

    private static void addUser(Schema schema) {
//        用户表
        Entity user = schema.addEntity("User");
        //user.setTableName("USER");  //表名
        user.addIdProperty().autoincrement();   //ID
        user.addStringProperty("User_Num").notNull();  //业务员编号
        user.addStringProperty("User_Pass");    //密码
        user.addStringProperty("User_Name"); //业务员姓名
        user.addStringProperty("User_Regin");    //业务员区域
        user.addStringProperty("User_Role");     //用户角色,业务员或大区经理
        user.addBooleanProperty("User_Status");   //用户登录状态,true可用,false不可用
        user.addDateProperty("User_Date");

//        经销商表
        Entity client = schema.addEntity("Client");
        client.addIdProperty().autoincrement();
        client.addStringProperty("Client_Num").notNull();    //经销商编号
        client.addStringProperty("Client_Name");     //经销商名称
        client.addStringProperty("Client_Owner");    //法人代表
        client.addStringProperty("Client_Type");     //经销商类型,经销商0/种植大户1
        client.addStringProperty("Client_Level");    //经销商层级,一级1/二级2/三级3
        client.addStringProperty("Client_Uplevel");  //上级经销商编号
        client.addStringProperty("Client_Phone");    //经销商电话
        client.addStringProperty("Client_Province");    //省
        client.addStringProperty("Client_City");    //市
        client.addStringProperty("Client_Country"); //县
        client.addStringProperty("Client_Town");    //乡镇
        client.addStringProperty("Client_Address"); //详细地址
        client.addStringProperty("Client_LngLat");  //经纬度
        client.addStringProperty("Client_Contract");    //营业执照
        client.addStringProperty("Client_IdCardF"); //身份证正面
        client.addStringProperty("Client_IdCardB"); //身份证反面
        client.addStringProperty("Client_Licence"); //经销商协议书```

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
        application.addIdProperty().autoincrement();
        application.addStringProperty("App_Name");     //经销商名称
        application.addStringProperty("App_Owner");    //法人代表
        application.addStringProperty("App_Type");     //经销商类型,经销商0/种植大户1
        application.addStringProperty("App_Level");    //经销商层级,一级1/二级2/三级3
        application.addStringProperty("App_Uplevel");  //上级经销商编号
        application.addStringProperty("App_Phone");    //经销商电话
        application.addStringProperty("App_Province");    //省
        application.addStringProperty("App_City");    //市
        application.addStringProperty("App_Country"); //县
        application.addStringProperty("App_Town");    //乡镇
        application.addStringProperty("App_Address"); //详细地址
        application.addStringProperty("App_LngLat");  //经纬度
        application.addStringProperty("App_Contract");    //营业执照
        application.addStringProperty("App_IdCardF"); //身份证正面
        application.addStringProperty("App_IdCardB"); //身份证反面
        application.addStringProperty("App_Licence"); //经销商协议书
        application.addStringProperty("App_BankNum");     //银行卡号
        application.addStringProperty("App_BankName");    //银行名称
        application.addStringProperty("App_BankOwner");   //户主
        application.addStringProperty("App_InvoiceType");  //发票类型,0专用发票,1普通发票
        application.addStringProperty("App_InvoiceBankNum");    //对公账号
        application.addStringProperty("App_InvoiceBankName");   //开户行
        application.addStringProperty("App_InvoiceBankName2");   //支行
        application.addStringProperty("App_InvoiceBankOwner");  //户主
        application.addStringProperty("App_InvoiceBankPhone");   //电话
        application.addStringProperty("App_Send");      //0已发送,1未发送,2资料不完整
        application.addStringProperty("App_Status");    //审核状态
        application.addDateProperty("App_TimeFlag");    //记录创建时间

        Entity address = schema.addEntity("Address");
        address.addIdProperty().primaryKey().autoincrement();
        address.addStringProperty("Addr_Code");
        address.addStringProperty("Addr_Name");
        address.addStringProperty("Addr_UpCode");
        address.addStringProperty("Addr_Level");
    }



}

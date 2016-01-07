package com.sdl.sdlarchivesmanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.Application;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.db.DBHelper;
import com.sdl.sdlarchivesmanager.util.PhotoUtil;

/**
 * Created by majingyuan on 15/12/26.
 * 经销商详情
 */
public class ClientInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private LinearLayout llInvoice;
    private TextView tvClientType;  //经销商类型
    private TextView tvClientLevel; //经销商层级
    private TextView tvClientName;  //经销商名称
    private TextView tvClientOwner; //经销商法人
    private TextView tvClientPhone; //联系电话
    private TextView tvClientAddr;   //地区
    private TextView tvClientAddr2; //详细地址
    private TextView tvBankName;    //开户行
    private TextView tvBankNum;     //银行卡号
    private TextView tvInvoiceType; //发票类型
    private TextView tvInvoiceNum;  //对公账号
    private TextView tvInvoiceName; //开户行
    private TextView tvInvoiceName2;    //支行名称
    private TextView tvInvoiceOwner;    //户主
    private TextView tvInvoicePhone;    //对公电话
    private ImageView ivContract;   //营业执照
    private ImageView ivIDCardF;     //身份证正面
    private ImageView ivIDCardB;    //身份证背面
    private ImageView ivLicence;    //营业执照
    private TextView tvTittle;  //标题栏

    private DBHelper dbHelper;
    private Application app;
    private PhotoUtil photoUtil;
    private long id;
    private String fileContract;
    private String fileIDCardF;
    private String fileIDCardB;
    private String fileLicence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientinfo);
        dbHelper = DBHelper.getInstance(this);
        photoUtil = new PhotoUtil(this);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getLong("id", id);
            app = dbHelper.loadApplicationByID(id);
        }

//        组件声明
        createWidget();
//        属性设置
        setWidget();

        llBack.setOnClickListener(this);
    }

    //    组件声明
    private void createWidget() {
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llInvoice = (LinearLayout) findViewById(R.id.ll_invoice);

        tvClientType = (TextView) findViewById(R.id.tv_clienttype);  //经销商类型
        tvClientLevel = (TextView) findViewById(R.id.tv_clientlevel); //经销商层级
        tvClientName = (TextView) findViewById(R.id.tv_clientname);  //经销商名称
        tvClientOwner = (TextView) findViewById(R.id.tv_clientowner); //经销商法人
        tvClientPhone = (TextView) findViewById(R.id.tv_clientphone); //联系电话
        tvClientAddr = (TextView) findViewById(R.id.tv_clientaddr);   //地区
        tvClientAddr2 = (TextView) findViewById(R.id.tv_clientaddr2); //详细地址
        tvBankName = (TextView) findViewById(R.id.tv_bankname);    //开户行
        tvBankNum = (TextView) findViewById(R.id.tv_banknum);     //银行卡号
        tvInvoiceType = (TextView) findViewById(R.id.tv_invoicetype); //发票类型
        tvInvoiceNum = (TextView) findViewById(R.id.tv_invoicenum);  //对公账号
        tvInvoiceName = (TextView) findViewById(R.id.tv_invoicename); //开户行
        tvInvoiceName2 = (TextView) findViewById(R.id.tv_invoicename2);    //支行名称
        tvInvoiceOwner = (TextView) findViewById(R.id.tv_invoiceowner);
        tvInvoicePhone = (TextView) findViewById(R.id.tv_invoicephone);    //对公电话
        tvTittle = (TextView) findViewById(R.id.tv_tittle);

    }

    //    属性设置
    private void setWidget() {
        llBack.setOnClickListener(this);
        tvTittle.setText(R.string.title_activity_clientinfo);
        tvClientType.setText(app.getApp_Type());
        tvClientLevel.setText(app.getApp_Level());
        tvClientName.setText(app.getApp_Name());
        tvClientOwner.setText(app.getApp_Owner());
        tvClientPhone.setText(app.getApp_Phone());
        tvClientAddr.setText(app.getApp_Address());
        tvClientAddr2.setText(app.getApp_Address());

        tvBankName.setText(app.getApp_BankName());
        tvBankNum.setText(app.getApp_BankNum());
        tvInvoiceType.setText(app.getApp_InvoiceType());
        if (app.getApp_InvoiceType().equals("0")){
            llInvoice.setVisibility(View.VISIBLE);
            tvInvoiceNum.setText(app.getApp_InvoiceBankNum());
            tvInvoiceName.setText(app.getApp_InvoiceBankName());
            tvInvoiceName2.setText(app.getApp_InvoiceBankName2());
            tvInvoicePhone.setText(app.getApp_InvoiceBankPhone());
            tvInvoiceOwner.setText(app.getApp_InvoiceBankOwner());
        }else {
            llInvoice.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            default:
                break;
        }
    }
}

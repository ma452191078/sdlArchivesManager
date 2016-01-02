package com.sdl.sdlarchivesmanager.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.sdl.sdlarchivesmanager.util.UriUtil;

/**
 * Created by majingyuan on 15/12/26.
 * 经销商详情
 */
public class ActivityClientInfo extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private LinearLayout llNext;
    private LinearLayout llInvoice;
    private TextView tvNext;
    private TextView tvStatus;
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
        setContentView(R.layout.activity_confirm);
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
        llNext = (LinearLayout) findViewById(R.id.ll_next);
        llInvoice = (LinearLayout) findViewById(R.id.ll_invoice);
        tvNext = (TextView) findViewById(R.id.tv_next);
        tvStatus = (TextView) findViewById(R.id.tv_status);
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
        ivContract = (ImageView) findViewById(R.id.iv_contract);   //营业执照
        ivIDCardF = (ImageView) findViewById(R.id.iv_idcardf);     //身份证正面
        ivIDCardB = (ImageView) findViewById(R.id.iv_idcardb);    //身份证背面
        ivLicence = (ImageView) findViewById(R.id.iv_licence);    //营业执照

        tvTittle = (TextView) findViewById(R.id.tv_tittle);

    }

    //    属性设置
    private void setWidget() {
        llNext.setVisibility(View.INVISIBLE);
        llBack.setOnClickListener(this);
        tvTittle.setText(R.string.title_activity_clientinfo);

        tvStatus.setText(app.getApp_Status());
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
        }

        fileContract = new UriUtil().UriToFile(this, Uri.parse(app.getApp_Contract()));
        ivContract.setImageBitmap(photoUtil.createThumbnail(fileContract, 10));

        fileIDCardF = new UriUtil().UriToFile(this,Uri.parse(app.getApp_IdCardF()));
        ivIDCardF.setImageBitmap(photoUtil.createThumbnail(fileIDCardF, 10));

        fileIDCardB = new UriUtil().UriToFile(this,Uri.parse(app.getApp_IdCardB()));
        ivIDCardB.setImageBitmap(photoUtil.createThumbnail(fileIDCardB, 10));

        fileLicence = new UriUtil().UriToFile(this,Uri.parse(app.getApp_Licence()));
        ivLicence.setImageBitmap(photoUtil.createThumbnail(fileLicence, 10));

        ivContract.setOnClickListener(this);
        ivIDCardF.setOnClickListener(this);
        ivIDCardB.setOnClickListener(this);
        ivLicence.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Bitmap myBitmap;
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.iv_contract:
                myBitmap = BitmapFactory.decodeFile(fileContract);
                new PhotoUtil(this).getBigPicture(myBitmap,this);
                break;
            case R.id.iv_idcardf:
                myBitmap = BitmapFactory.decodeFile(fileIDCardF);
                new PhotoUtil(this).getBigPicture(myBitmap,this);
                break;
            case R.id.iv_idcardb:
                myBitmap = BitmapFactory.decodeFile(fileIDCardB);
                new PhotoUtil(this).getBigPicture(myBitmap,this);
                break;
            case R.id.iv_licence:
                myBitmap = BitmapFactory.decodeFile(fileLicence);
                new PhotoUtil(this).getBigPicture(myBitmap,this);
                break;
            default:
                break;
        }
    }
}

package com.sdl.sdlarchivesmanager.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.sdl.sdlarchivesmanager.Application;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.bean.BitmapCatchBean;
import com.sdl.sdlarchivesmanager.db.DBHelper;
import com.sdl.sdlarchivesmanager.util.GetAddressUtil;
import com.sdl.sdlarchivesmanager.util.PhotoUtil;
import com.sdl.sdlarchivesmanager.util.SysApplication;

/**
 * Created by majingyuan on 15/12/26.
 * 申请单信息
 */
public class ArchiveInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private LinearLayout llNext;
    private LinearLayout llInvoice;
    private LinearLayout llUpLevel;
    private TextView tvNext;
    private TextView tvStatus;
    private TextView tvClientType;  //经销商类型
    private TextView tvClientLevel; //经销商层级
    private TextView tvClientUpLevel;   //上级商
    private TextView tvClientName;  //经销商名称
    private TextView tvClientOwner; //经销商法人
    private TextView tvClientPhone; //联系电话
    private TextView tvClientAddr;   //地区
    private TextView tvClientAddr2; //详细地址
    private TextView tvBankName;    //开户行
    private TextView tvBankNum;     //银行卡号
    private TextView tvBankOwner;   //户主
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
    private ImageView ivGrouPhoto;  //经销商合影
    private Button btCommit;    //提交按钮
    private ProgressDialog mDialog ;

    private DBHelper dbHelper;
    private Application app;
    private PhotoUtil photoUtil;
    private long id;
    private String fileContract;
    private String fileIDCardF;
    private String fileIDCardB;
    private String fileLicence;
    private String strSource;
    private String COMMIT = "提交";
    private String CHANGE = "修改";
    private String HOME = "home";
    private static String TGA = "com.sdl.sdlarchivesmanager";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        SysApplication.getInstance().addActivity(this);
        dbHelper = DBHelper.getInstance(this);
        photoUtil = new PhotoUtil(this);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getLong("id");
            app = dbHelper.loadApplicationByID(id);
        }

//        组件声明
        createWidget();
//        属性设置
        setWidget();

    }

    //    组件声明
    private void createWidget() {
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llNext = (LinearLayout) findViewById(R.id.ll_next);
        llInvoice = (LinearLayout) findViewById(R.id.ll_invoice);
        llUpLevel = (LinearLayout) findViewById(R.id.ll_uplevel);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        tvClientType = (TextView) findViewById(R.id.tv_clienttype);  //经销商类型
        tvClientLevel = (TextView) findViewById(R.id.tv_clientlevel); //经销商层级
        tvClientUpLevel = (TextView) findViewById(R.id.tv_uplevel);     //上级商
        tvClientName = (TextView) findViewById(R.id.tv_clientname);  //经销商名称
        tvClientOwner = (TextView) findViewById(R.id.tv_clientowner); //经销商法人
        tvClientPhone = (TextView) findViewById(R.id.tv_clientphone); //联系电话
        tvClientAddr = (TextView) findViewById(R.id.tv_clientaddr);   //地区
        tvClientAddr2 = (TextView) findViewById(R.id.tv_clientaddr2); //详细地址
        tvBankName = (TextView) findViewById(R.id.tv_bankname);    //开户行
        tvBankNum = (TextView) findViewById(R.id.tv_banknum);     //银行卡号
        tvBankOwner = (TextView) findViewById(R.id.tv_bankowner);   //户主
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
        ivGrouPhoto = (ImageView) findViewById(R.id.iv_grouphoto);
        btCommit = (Button) findViewById(R.id.bt_commit);

        mDialog = new ProgressDialog(this) ;
        mDialog.setCanceledOnTouchOutside(false);
    }

    //    属性设置
    private void setWidget() {

        llBack.setOnClickListener(this);
        llNext.setVisibility(View.INVISIBLE);
        btCommit.setVisibility(View.GONE);

        tvStatus.setText(app.getApp_Status());
        if (app.getApp_Type().equals("0")) {
            tvClientType.setText("经销商");
        } else if (app.getApp_Type().equals("1")) {
            tvClientType.setText("种植大户");
        }
        if (app.getApp_Level().equals("1")) {
            tvClientLevel.setText("一级商");
            llUpLevel.setVisibility(View.GONE);
        } else if (app.getApp_Level().equals("2")) {
            tvClientLevel.setText("二级商");
            tvClientUpLevel.setText(app.getApp_Uplevel());
        } else if (app.getApp_Level().equals("3")) {
            tvClientLevel.setText("三级商");
            tvClientUpLevel.setText(app.getApp_Uplevel());
        }

        tvClientName.setText(app.getApp_Name());
        tvClientOwner.setText(app.getApp_Owner());
        tvClientPhone.setText(app.getApp_Phone());
        tvClientAddr.setText(getAddress(app));
        tvClientAddr2.setText(app.getApp_Address());

        tvBankName.setText(app.getApp_BankName());
        tvBankNum.setText(app.getApp_BankNum());
        tvBankOwner.setText(app.getApp_BankOwner());
        tvInvoiceType.setText(app.getApp_InvoiceType());

        if (app.getApp_InvoiceType().equals("0")) {
            llInvoice.setVisibility(View.VISIBLE);
            tvInvoiceType.setText("增值税专用发票");
            tvInvoiceNum.setText(app.getApp_InvoiceBankNum());
            tvInvoiceName.setText(app.getApp_InvoiceBankName());
            tvInvoiceName2.setText(app.getApp_InvoiceBankName2());
            tvInvoicePhone.setText(app.getApp_InvoiceBankPhone());
            tvInvoiceOwner.setText(app.getApp_InvoiceBankOwner());
        } else {
            tvInvoiceType.setText("普通发票");
        }

        if (app.getApp_Contract() != null) {
            getImageCatch(app.getApp_Contract(), ivContract);
            ivContract.setOnClickListener(this);
        }

        if (app.getApp_IdCardF() != null) {
            getImageCatch(app.getApp_IdCardF(), ivIDCardF);
            ivIDCardF.setOnClickListener(this);
        }

        if (app.getApp_IdCardB() != null) {
            getImageCatch(app.getApp_IdCardB(), ivIDCardB);
            ivIDCardB.setOnClickListener(this);
        }

        if (app.getApp_Licence() != null) {
            getImageCatch(app.getApp_Licence(), ivLicence);
            ivLicence.setOnClickListener(this);
        }

        if (app.getApp_GroupPhoto() != null) {
            getImageCatch(app.getApp_GroupPhoto(), ivGrouPhoto);
            ivGrouPhoto.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        Bitmap myBitmap;
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.iv_contract:
                myBitmap = new BitmapCatchBean().getBitmap(app.getApp_Contract());

                break;
            case R.id.iv_idcardf:
                myBitmap = new BitmapCatchBean().getBitmap(app.getApp_IdCardF());
                new PhotoUtil(this).getBigPicture(myBitmap,this);
                break;
            case R.id.iv_idcardb:
                myBitmap = new BitmapCatchBean().getBitmap(app.getApp_IdCardB());
                new PhotoUtil(this).getBigPicture(myBitmap,this);
                break;
            case R.id.iv_licence:
                myBitmap =new BitmapCatchBean().getBitmap(app.getApp_Licence());
                new PhotoUtil(this).getBigPicture(myBitmap,this);
                break;
            case R.id.iv_grouphoto:
                myBitmap = new BitmapCatchBean().getBitmap(app.getApp_GroupPhoto());
                new PhotoUtil(this).getBigPicture(myBitmap,this);
                break;
            default:
                break;
        }
    }

    protected String getAddress(Application application){

        GetAddressUtil getAddressUtil = new GetAddressUtil(ArchiveInfoActivity.this);

        StringBuilder addr = new StringBuilder(); //地区名称,由省市县乡镇组合
        if (application.getApp_Province() != null)
            addr.append(getAddressUtil.getAddressByCode(app.getApp_Province(), "0", "province"));

        if (application.getApp_City() != null)
            addr.append(dbHelper.loadAddressByCode(application.getApp_City()));

        if (application.getApp_Country() != null)
            addr.append(dbHelper.loadAddressByCode(application.getApp_Country()));

        if (application.getApp_Town() != null)
            addr.append(dbHelper.loadAddressByCode(application.getApp_Town()));
        return addr.toString();

    }

    /**
     * 获取图片缓存
     * @param url 图片地址
     * @param iv  图片控件
     */
    private void getImageCatch(String url, ImageView iv){
        //实例化对象
        ImageLoader imageLoader = new ImageLoader(ArchiveApplication.getHttpQueues(),new BitmapCatchBean());
        //图片监听,设置默认和错误图像
        ImageLoader.ImageListener imageListener = imageLoader.getImageListener(iv,R.mipmap.picture,R.mipmap.picture);
        //加载图片
        imageLoader.get(url, imageListener, 0, 0);
    }
}

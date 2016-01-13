package com.sdl.sdlarchivesmanager.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sdl.sdlarchivesmanager.Application;
import com.sdl.sdlarchivesmanager.Bank;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.db.DBHelper;
import com.sdl.sdlarchivesmanager.util.FilePath;
import com.sdl.sdlarchivesmanager.util.GetDateUtil;
import com.sdl.sdlarchivesmanager.util.PhotoUtil;
import com.sdl.sdlarchivesmanager.util.SysApplication;
import com.sdl.sdlarchivesmanager.util.UriUtil;

import java.io.File;

/**
 * create by majingyuan on 2015-12-05 20:31:29
 * 第二步,银行信息
 */
public class BankInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_BANK = 2000;
    private String timeFlag;
    private DBHelper dbHelper;
    private Application app = new Application();
    private String strBankCode; //银行编号
    private CharSequence[] items;   //提示框文本,数组形式,可以自定义
    private File tempFile = new FilePath().getPhotoName();
    private String imgUri;
    private PhotoUtil photoUtil;
    private String fileContract;

    private LinearLayout llBack;
    private LinearLayout llNext;
    private LinearLayout llInvoice;
    private TextView tvTittle;
    private EditText etBankNum;     //银行卡号
    private TextView tvBankName;    //开户行
    private EditText etBankOwner;   //户主
    private RadioButton rbInvoiceTypeZ;     //发票类型,专用发票0
    private RadioButton rbInvoiceTypeP;     //发票类型,普通发票1
    private EditText etInvoiceNum;  //对公账号
    private TextView tvInvoiceName; //开户行
    private EditText etInvoiceName2;    //支行
    private EditText etInvoiceOwner;    //户主
    private EditText etInvoicePhone;    //电话
    private EditText etInvoiceVatNum;   //增值税号
    private EditText etInvoiceAddr;     //地址
    private ImageView ivInvoiceImage;   //税务登记证


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_bankinfo);
        SysApplication.getInstance().addActivity(this);

        dbHelper = DBHelper.getInstance(this);
        photoUtil = new PhotoUtil(BankInfoActivity.this);
        Bundle bundle = this.getIntent().getExtras();

        createWidget();
        setWidget();

        if (bundle != null) {

            timeFlag = bundle.getString("timeflag");
            app = dbHelper.loadApplication(new GetDateUtil().getDate(timeFlag));
            bindData();
        }


    }

    //    声明屏幕组件
    protected void createWidget() {
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llNext = (LinearLayout) findViewById(R.id.ll_next);
        llInvoice = (LinearLayout) findViewById(R.id.ll_invoice);
        tvTittle = (TextView) findViewById(R.id.tv_tittle);
        etBankNum = (EditText) findViewById(R.id.et_banknumber);
        tvBankName = (TextView) findViewById(R.id.tv_bankname);
        etBankOwner = (EditText) findViewById(R.id.et_bankowner);
        rbInvoiceTypeZ = (RadioButton) findViewById(R.id.rb_zyfp);
        rbInvoiceTypeP = (RadioButton) findViewById(R.id.rb_ptfp);
        etInvoiceNum = (EditText) findViewById(R.id.et_invoicebanknum);
        tvInvoiceName = (TextView) findViewById(R.id.tv_invoicebankname);
        etInvoiceName2 = (EditText) findViewById(R.id.et_invoicebankname2);
        etInvoiceOwner = (EditText) findViewById(R.id.et_invoicebankowner);
        etInvoicePhone = (EditText) findViewById(R.id.et_invoicebankphone);
        etInvoiceVatNum = (EditText) findViewById(R.id.et_invoicevatnum);
        etInvoiceAddr = (EditText) findViewById(R.id.et_invoiceaddr);
        ivInvoiceImage = (ImageView) findViewById(R.id.iv_invoiceimage);
    }

    //    设置屏幕组件属性
    protected void setWidget() {
        llBack.setOnClickListener(this);
        llNext.setOnClickListener(this);
        tvTittle.setText(R.string.archives_bankinfo);
        tvBankName.setOnClickListener(this);
        tvInvoiceName.setOnClickListener(this);
        rbInvoiceTypeZ.setOnClickListener(this);
        rbInvoiceTypeP.setOnClickListener(this);
        ivInvoiceImage.setOnClickListener(this);
    }

    protected void bindData(){
        if (app != null) {

            if (app.getApp_BankNum() != null) {
                Bank bank = dbHelper.loadBankItem(app.getApp_BankNum());
                tvBankName.setText(bank.getBank_Name());
            }
            etBankNum.setText(app.getApp_BankNum());
            etBankOwner.setText(app.getApp_BankOwner());
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.ll_next:
                goNextStep();
                Bundle bundle = new Bundle();
                bundle.putLong("id", app.getId());
                intent.setClass(BankInfoActivity.this, LicenceActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.tv_bankname:
                intent.setClass(BankInfoActivity.this, AddrListActivity.class);
                startActivityForResult(intent, RESULT_BANK);
                break;
            case R.id.tv_invoicebankname:
                break;
            case R.id.rb_zyfp:
                llInvoice.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_ptfp:
                llInvoice.setVisibility(View.GONE);
                break;
            case R.id.iv_invoiceimage:
                getPhoto();
            default:
                break;
        }
    }

    protected void goNextStep() {
        if (app != null) {

            app.setApp_BankNum(etBankNum.getText().toString().trim());
            app.setApp_BankName(strBankCode);
            app.setApp_BankOwner(etBankOwner.getText().toString().trim());
            if (rbInvoiceTypeZ.isChecked()) {
                app.setApp_InvoiceType("0");
                app.setApp_InvoiceBankNum(etInvoiceNum.getText().toString().trim());
                app.setApp_InvoiceBankName(tvInvoiceName.getText().toString());
                app.setApp_InvoiceBankName2(etInvoiceName2.getText().toString().trim());
                app.setApp_InvoiceBankOwner(etInvoiceOwner.getText().toString().trim());
                app.setApp_InvoiceBankPhone(etInvoicePhone.getText().toString().trim());
                app.setApp_InvoiceVatNum(etInvoiceVatNum.getText().toString().trim());
                app.setApp_InvoiceAddr(etInvoiceAddr.getText().toString().trim());
                app.setApp_InvoiceImage(imgUri);
            } else {
                app.setApp_InvoiceType("1");
            }
            dbHelper.updateApplication(app);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_BANK && resultCode == 2001) {
            tvBankName.setText(data.getStringExtra("result"));
            strBankCode = data.getStringExtra("bankcode");
        }else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoUtil.PHOTO_REQUEST_CAMERA:// 当选择拍照时调用
                    setPhoto(Uri.fromFile(tempFile));
                    break;
                case PhotoUtil.PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                    // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                    if (data != null) {

                        setPhoto(data.getData());
                    } else {
                        Toast.makeText(BankInfoActivity.this, "请重新选择图片", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PhotoUtil.PHOTO_REQUEST_CUT:// 返回的结果
                    if (data != null)
                        setPhoto(data.getData());
                    break;
            }
        }
    }

    private void getPhoto() {

        AlertDialog.Builder builder = new AlertDialog.Builder(BankInfoActivity.this);
        items = new CharSequence[]{"拍照上传", "从相册选择"};

        builder.setTitle("请选择图片来源");
        builder.setCancelable(true);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        photoUtil.getCameraPhoto();
                        break;
                    case 1:
                        photoUtil.getGalleryPhoto();
                        break;
                    default:
                        break;
                }
            }
        });
        builder.create().show();

    }

    private void setPhoto(Uri uri) {

        fileContract = new UriUtil().UriToFile(getApplicationContext(),uri);
        ivInvoiceImage.setImageBitmap(photoUtil.createThumbnail(fileContract, 10));
        imgUri = uri.toString();
    }
}

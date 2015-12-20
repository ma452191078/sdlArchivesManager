package com.sdl.sdlarchivesmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.User;
import com.sdl.sdlarchivesmanager.db.DbUser;
import com.sdl.sdlarchivesmanager.util.SharePreferenceUtil;
import com.sdl.sdlarchivesmanager.util.UpdateManager;
import com.sdl.sdlarchivesmanager.util.sdlClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by majingyuan on 15/12/20.
 * 登录界面
 */
public class ActivityLogin extends AppCompatActivity {

    private EditText etUserName,etUserPass;
    private Button btLogin;
    private String userName, userPass, userId;

    private RequestParams params;
    private static final String baseurl = "HttpLoginAction!salesLogin?";
    private boolean newtworstatus = true;
    private UpdateManager mUpdateManager;
    private static	int newVerCode = 1;
    private static	String newVerName = "archive.apk";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (EditText) findViewById(R.id.et_login_user);
        etUserPass = (EditText) findViewById(R.id.et_login_pass);
        btLogin = (Button) findViewById(R.id.button_login);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = etUserName.getText().toString();
                userPass = etUserPass.getText().toString();
                setLoginLocal(userId,userPass);
            }
        });

        /*点击回车键，隐藏键盘并登录*/
        etUserPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) view
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                view.getApplicationWindowToken(), 0);
                    }
                    userId = etUserName.getText().toString();
                    userPass = etUserPass.getText().toString();
                    setLoginLocal(userId, userPass);
//                    getloginstatus(userName, userPass);
                    return true;
                }
                return false;
            }
        });

        if(newtworstatus)
        {
            mUpdateManager = new UpdateManager(this);
            //这里来检测版本是否需要更新
            getServerVer();

        }
        else
        {
            Toast.makeText(this,
                    "你没有开启网络连接！！请开启网络连接,否则影响使用",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean NetWorkStatus() {
        // TODO Auto-generated method stub
        boolean netSatus = false;
        ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        cwjManager.getActiveNetworkInfo();

        if (cwjManager.getActiveNetworkInfo() != null) {
            netSatus = cwjManager.getActiveNetworkInfo().isAvailable();
        }
        return netSatus;
    }

    protected void setLoginLocal(String userid, String password){

        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        Date lastLoginDate = new Date(System.currentTimeMillis() - ((long) (Math.random() * 1000 * 60 * 60 * 24 * 365)));
        User loginUser = new User();
        loginUser.setUser_Name("马靖沅");
        loginUser.setUser_Pass(password);
        loginUser.setUser_Num(userid);
        loginUser.setUser_Date(lastLoginDate);
        if (new DbUser().addUser(loginUser)){
            bundle.putString("personName", userid);
            bundle.putString("personAccount", password);

            intent.setClass(ActivityLogin.this, MainActivity.class);

            intent.putExtras(bundle);
            startActivity(intent);
            ActivityLogin.this.finish();
        }else {
            Toast.makeText(ActivityLogin.this,"写入用户数据失败", Toast.LENGTH_SHORT).show();
        }
    }

    protected void getloginstatus(String username2, String pwd2) {
        // TODO Auto-generated method stub
        params = new RequestParams();
        params.put("username", userName);
        params.put("password", userPass);
        sdlClient.post(baseurl, params, new JsonHttpResponseHandler()

        {

            public void onSuccess(int arg0, PreferenceActivity.Header[] arg1, JSONObject arg2) {
                //解析JSON
                String strdata = arg2.toString();
                Intent intent = new Intent();
                Bundle args = new Bundle();
                try {

                    String flag = arg2.getString("flag");
                    SharePreferenceUtil.setPrefString(ActivityLogin.this, "flag", flag);

                    if (flag.equals("账户或密码错误")) {
                        //提示密码错误
                        Toast.makeText(getApplicationContext(), "账户或密码错误", Toast.LENGTH_SHORT).show();
                    } else {

                        JSONObject user = arg2.getJSONObject("user");
                        String pname = user.getString("personName");
                        String pid = user.getString("personAccount");
                        String role = user.getString("role");
                        String dep_id = user.getString("deptId");

                        Date lastLoginDate = new Date(System.currentTimeMillis() - ((long) (Math.random() * 1000 * 60 * 60 * 24 * 365)));
                        User loginUser = new User();
                        loginUser.setUser_Name(pname);
                        loginUser.setUser_Pass(userPass);
                        loginUser.setUser_Num(pid);
                        loginUser.setUser_Date(lastLoginDate);
                        if (new DbUser().addUser(loginUser)){
                            args.putString("personName", pname);
                            args.putString("personAccount", pid);

                            intent.setClass(ActivityLogin.this, MainActivity.class);

                            intent.putExtras(args);
                            startActivity(intent);
                            ActivityLogin.this.finish();
                        }else {
                            Toast.makeText(ActivityLogin.this,"写入用户数据失败", Toast.LENGTH_SHORT).show();
                        }


                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, PreferenceActivity.Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "登录超时，请检查您的网络连接。", Toast.LENGTH_SHORT).show();
            }

        });
    }

    //得到服务器上的版本
    public  void getServerVer() {
        getuserData("http://eb.shidanli.cn/AppSyncAction!getAppLastVersion");//HttpUtils.getData("http://test.56539.cn/version.aspx");

    }
    protected void getuserData(String url)
    {

        sdlClient.post(url,null,   new JsonHttpResponseHandler()

        {

            public void onSuccess(int arg0, PreferenceActivity.Header[] arg1, JSONObject arg2) {
                //解析JSON

                try {
                    int  vercode = arg2.getInt("verCode");
                    newVerCode = vercode;

                    {
                        //获得本地版本
                        vercode = mUpdateManager.getVerCode(ActivityLogin.this); // 获得版本
                        if (newVerCode > vercode) {

                            mUpdateManager.checkUpdateInfo();  ; // 更新新版本
                        }
                        else
                        {
                            //继续进行
                        }
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });


    }

}

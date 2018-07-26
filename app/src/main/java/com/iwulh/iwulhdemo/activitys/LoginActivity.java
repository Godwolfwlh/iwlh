package com.iwulh.iwulhdemo.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iwulh.iwulhdemo.R;
import com.iwulh.iwulhdemo.bean.LoginBean;
import com.iwulh.iwulhdemo.okhttp.CallBackUtil;
import com.iwulh.iwulhdemo.okhttp.OkhttpUtil;
import com.iwulh.iwulhdemo.utils.ACache;
import com.iwulh.iwulhdemo.utils.AppToastMgr;
import com.iwulh.iwulhdemo.utils.BaseActivity;
import com.iwulh.iwulhdemo.utils.Constants;

import java.util.HashMap;

import okhttp3.Call;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText editPerson, editCode;
    private Button btn;
    private boolean autoLogin = false;
    public static String loginname;
    private String password;
    private boolean progressShow;
    private ProgressDialog pd;
    private LoginBean bean;
    private LoginHandler handler;
    private LoginThread loginThread;
    private Thread thread;
    private ACache acache;
    private ActionBar actionBar;

    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        actionBar = getSupportActionBar();
        actionBar.hide();
        btn = findById(R.id.bn_common_login);
        btn.setOnClickListener(this);
        editCode = findById(R.id.et_password);
        editPerson = findById(R.id.et_username);
        acache = ACache.get(this);
        pd = new ProgressDialog(LoginActivity.this);  //初始化等待动画
        handler = new LoginHandler();
    }

    @Override
    public void initData() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bn_common_login:  //登录按钮
                progressShow = true;
                /**
                 * 设置监听
                 * */
                pd.setCanceledOnTouchOutside(false);
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        progressShow = false;   //设置Boolean值为false
                    }
                });
                pd.setMessage("正在登录....");  //等待动画的标题
                pd.show();  //显示等待动画
                login(v);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }

    /**
     * 登录
     *
     * @param view
     */
    public void login(View view) {

        loginname = editPerson.getText().toString().trim(); //去除空格，获取用户名
        password = editCode.getText().toString().trim();  //去除空格，获取密码

        if (TextUtils.isEmpty(loginname)) { //判断用户名是不是为空
            pd.dismiss();    //等待条消失
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {  //判断密码是不是空
            pd.dismiss();    //等待条消失
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("loginName", loginname);
        paramsMap.put("userPassword", password);
        OkhttpUtil.okHttpGet(Constants.USER_LOGIN, paramsMap, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Log.i("LOGINHTTP", "onFailure：" + e);
                loginThread = new LoginThread(bean,3);
                thread = new Thread(loginThread);
                thread.start();//开始线程
            }

            @Override
            public void onResponse(String response) {
                Log.i("LOGINHTTP", "onResponse：" + response);
                Gson gson = new Gson();
                bean = gson.fromJson(response, LoginBean.class);
                switch (bean.getCode()) {
                    case 0:
                        loginThread = new LoginThread(bean,0);
                        thread = new Thread(loginThread);
                        thread.start();//开始线程
                        break;
                    case 1:
                        loginThread = new LoginThread(bean,1);
                        thread = new Thread(loginThread);
                        thread.start();//开始线程
                        break;
                    case 2:
                        loginThread = new LoginThread(bean,2);
                        thread = new Thread(loginThread);
                        thread.start();//开始线程
                        break;
                }
            }
        });
    }

    private class LoginThread implements Runnable{
        private LoginBean bean;
        private int code;

        public LoginThread(LoginBean bean, int code){
            this.bean = bean;
            this.code = code;
        }
        @Override
        public void run() {
            //在此处睡眠两秒
            try {
                Thread.sleep(2000);  //在此处睡眠两秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = new Message();
            message.obj = bean;
            message.what = code;
            handler.sendMessage(message);
        }
    }

    private class LoginHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    /**
                     * 两秒之后
                     * */
                    pd.dismiss();    //等待条消失
                    bean = (LoginBean) msg.obj;
                    AppToastMgr.shortToast(LoginActivity.this, bean.getMsg().concat(",请从新输入..."));
                    break;
                case 1:
                    /**
                     * 两秒之后
                     * */
                    pd.dismiss();    //等待条消失
                    bean = (LoginBean) msg.obj;

                    switch (bean.getData().getState()){
                        case 0:
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);  //进入主界面
                            bean.setState(1);
                            bean.getData().setState(1);
                            acache.put("LOGINBEAN_PAECELABLE",bean,60*60*24*30);
                            startActivity(intent);  //开始跳转
                            finish();  //finish掉此界面
                            break;
                        case 1:
                            AppToastMgr.shortToast(LoginActivity.this, "该用户已在其他地方登入");
                            break;
                    }
                    break;
                case 2:
                    /**
                     * 两秒之后
                     * */
                    pd.dismiss();    //等待条消失
                    AppToastMgr.shortToast(LoginActivity.this, "密码错我,请从新输入...");
                    break;
                case 3:
                    /**
                     * 两秒之后
                     * */
                    pd.dismiss();    //等待条消失
                    AppToastMgr.shortToast(LoginActivity.this, "网络异常，请检查您的网络！");
                    break;
            }
            super.handleMessage(msg);
        }
    }
}

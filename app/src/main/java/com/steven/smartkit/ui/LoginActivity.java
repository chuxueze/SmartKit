package com.steven.smartkit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.steven.smartkit.MainActivity;
import com.steven.smartkit.R;
import com.steven.smartkit.entity.SmartKitUser;
import com.steven.smartkit.utils.SmartKitLog;
import com.steven.smartkit.utils.SmartKitShareUtils;
import com.steven.smartkit.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登录页面
 * Created by Steven on 2018/2/21.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_registered;
    private EditText et_name;
    private EditText et_password;
    private Button btnLogin;
    private CheckBox keep_password;

    private TextView tv_forget;

    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btn_registered = (Button) findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        keep_password = (CheckBox) findViewById(R.id.keep_password);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);


        boolean isKeep = SmartKitShareUtils.getBoolean(this, "keeppass", false);
        keep_password.setChecked(isKeep);

        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        // 屏蔽外点击无效
        dialog.setCancelable(false);

        if (isKeep) {
            String name = SmartKitShareUtils.getString(this, "name", "");
            String password = SmartKitShareUtils.getString(this, "password", "");
            et_name.setText(name);
            et_password.setText(password);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget:
                //TODO:
                startActivity(new Intent(this, RegisteredActivity.class));
                // startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.btn_registered:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.btnLogin:
                //1.获取输入框的值
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    dialog.show();
                    //登录
                    final SmartKitUser user = new SmartKitUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<SmartKitUser>() {
                        @Override
                        public void done(SmartKitUser myUser, BmobException e) {
                            dialog.dismiss();
                            if(myUser != null){
                                SmartKitLog.i("【用户登录成功】" + myUser.getUsername());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }else {
                                SmartKitLog.i("【用户登录失败】" );
                                Toast.makeText(LoginActivity.this, "登录失败：[" + e.toString() + "]", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    //假设我现在输入用户名和密码，但是我不点击登录，而是直接退出了
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        SmartKitShareUtils.putBoolean(this, "keeppass", keep_password.isChecked());

        //是否记住密码
        if (keep_password.isChecked()) {
            //记住用户名和密码
            SmartKitShareUtils.putString(this, "name", et_name.getText().toString().trim());
            SmartKitShareUtils.putString(this, "password", et_password.getText().toString().trim());
        } else {
            SmartKitShareUtils.deleShare(this, "name");
            SmartKitShareUtils.deleShare(this, "password");
        }
    }

}

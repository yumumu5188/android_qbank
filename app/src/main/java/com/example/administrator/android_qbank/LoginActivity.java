package com.example.administrator.android_qbank;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import widget.LoadingDialog;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
@ContentView(value = R.layout.loginactivity)
public class LoginActivity extends AppCompatActivity{

    @ViewInject(value = R.id.et_username)
    private EditText et_username;
    @ViewInject(value = R.id.et_userpassword)
    private EditText et_userpassword;
    @ViewInject(value = R.id.btn_login)
    private Button btn_login;
    @ViewInject(value = R.id.tv_forget_password)
    private TextView tv_forget_password;
    @ViewInject(value = R.id.tv_register)
    private TextView tv_register;

    LoadingDialog dialog;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        toolbar.setTitle("登录");//设置Toolbar标题
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);

    }

    @Event(value = R.id.btn_login,type = View.OnClickListener.class)
    private void LoginClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
            {
                dialog = new LoadingDialog(this);
                dialog.setCancelable(false);
                dialog.show();
                String username = et_username.getText().toString();
                String userpassword = et_userpassword.getText().toString();
                RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/login");
                params.addBodyParameter("username",username);
                params.addBodyParameter("password",userpassword);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println(result+"+++++++LoginActivity     yemian ");
                        try {
                            JSONObject json = new JSONObject(result);
                            String success = json.getString("success");
                            if (success.equals("true")){
                                String user = json.getString("user");
                                JSONObject json2 = new JSONObject(user);
                                int id = json2.getInt("id");
                                String uname = json2.getString("username");
                                String nname = json2.getString("nickname");
                                String passw = json2.getString("password");
                                String telephone = json2.getString("telephone");
                                //String picurl = json2.getString("picurl");
                                System.out.println(id+uname+nname+passw+telephone+"000000000000000000000");
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }else{
                                String reason = json.getString("reason");
                                Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("error+loginactivity+99999999");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
                        System.out.println("onFinish()");
                        //dialog.dismiss();
                    }
                });
            }break;
        }
    }

    @Event(value = R.id.tv_forget_password,type = View.OnClickListener.class)
    private void BackTop(View view){
        switch (view.getId()){
            case R.id.tv_forget_password:
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }break;
        }
    }

    @Event(value = R.id.tv_register,type = View.OnClickListener.class)
    private void RegisterClick(View view){
        switch (view.getId()){
            case R.id.tv_register:
            {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }break;
        }
    }

}

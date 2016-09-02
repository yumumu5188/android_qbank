package com.example.administrator.android_qbank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
@ContentView(value = R.layout.registeractivity)
public class RegisterActivity extends AppCompatActivity {

    @ViewInject(value = R.id.et_name)
    private EditText et_name;
    @ViewInject(value = R.id.et_password)
    private EditText et_password;
    @ViewInject(value = R.id.et_nickname)
    private EditText et_nickname;
    @ViewInject(value = R.id.et_phone)
    private EditText et_phone;
    @ViewInject(value = R.id.reg_ok)
    private Button reg_ok;

    LoadingDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setTitle("注册账号");
    }
    @Event(value = R.id.reg_ok,type = View.OnClickListener.class)
    private void RegOkClick(View view){
        switch (view.getId()){
            case R.id.reg_ok:
            {
                dialog = new LoadingDialog(this);
                dialog.setCancelable(false);
                dialog.show();
                String name = et_name.getText().toString();
                String password = et_password.getText().toString();
                String nickname = et_nickname.getText().toString();
                String phone = et_phone.getText().toString();
                RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/registe");
                params.addBodyParameter("username",name);
                params.addBodyParameter("password",password);
                params.addBodyParameter("nickname",nickname);
                params.addBodyParameter("telephone",phone);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("========="+result);
                        try {
                            JSONObject json = new JSONObject(result);
                            String success = json.getString("success");
                            if (success.equals("true")){
                                Toast.makeText(RegisterActivity.this, "注册成功，现在可以登录", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("error***********************");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
                        dialog.dismiss();
                    }
                });


            }break;
        }
    }
}

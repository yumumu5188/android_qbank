package com.example.administrator.android_qbank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
@ContentView(value = R.layout.settingactivity)
public class SettingActivity extends AppCompatActivity{
    @ViewInject(R.id.setting_a)
    private TextView tv_setting_a;
    @ViewInject(R.id.setting_b)
    private TextView tv_setting_b;
    @ViewInject(R.id.setting_e)
    private TextView tv_setting_e;
    @ViewInject(R.id.setting_f)
    private TextView tv_setting_f;
    @ViewInject(R.id.setting_g)
    private TextView tv_setting_g;
    @ViewInject(R.id.setting_c)
    private CheckBox cb_setting_c;
    @ViewInject(R.id.setting_d)
    private CheckBox cb_setting_d;

    private Toolbar toolbar;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    EditText et_setting_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        toolbar.setTitle("设置");//设置Toolbar标题
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref = getSharedPreferences("data",MODE_PRIVATE);
        editor = pref.edit();
        cb_setting_d.setChecked(pref.getBoolean("three",true));
        cb_setting_c.setChecked(pref.getBoolean("second",false));
        if (!pref.getBoolean("second",false))
            tv_setting_b.setText("已关闭");
        cb_setting_c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editor.putBoolean("second",true);
                    editor.commit();
                    tv_setting_b.setText("已开启");
                    //Toast.makeText(SettingActivity.this, "你选中了自动登录", Toast.LENGTH_SHORT).show();
                }
                else{
                    editor.putBoolean("second",false);
                    editor.commit();
                    tv_setting_b.setText("已关闭");
                }
            }
        });
        cb_setting_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editor.putBoolean("three",true);
                    editor.commit();
                }
                else{
                    editor.putBoolean("three",false);
                    editor.commit();
                }
                //Toast.makeText(SettingActivity.this, "你选中了显示图片", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Event(value = {R.id.setting_a,R.id.setting_e,R.id.setting_f,R.id.setting_g},type = View.OnClickListener.class)
    private void SetingTextViewClick(View view){
        switch (view.getId()){
            case R.id.setting_a:{
                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setIcon(R.mipmap.login_user_hightlighted);
                builder.setTitle("请填入你的信息：");
                LayoutInflater inflater = LayoutInflater.from(this);
                View nicknameview = inflater.inflate(R.layout.item3,null);
                builder.setView(nicknameview);
                et_setting_name = (EditText) nicknameview.findViewById(R.id.et_setting_name);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nickname = et_setting_name.getText().toString();
                        editor.putString("nickname",nickname);
                        editor.commit();
                    }
                });
                builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                Toast.makeText(SettingActivity.this, "你点击了昵称设置", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.setting_e:{
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
                Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(SettingActivity.this, "你点击了退出登录", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.setting_f:{
                Toast.makeText(SettingActivity.this, "清除缓存完成", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.setting_g:{
                Toast.makeText(SettingActivity.this, "待开发", Toast.LENGTH_SHORT).show();
            }break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

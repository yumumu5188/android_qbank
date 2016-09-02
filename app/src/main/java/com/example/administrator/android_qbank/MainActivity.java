package com.example.administrator.android_qbank;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.GridViewAdapter;
import pojo.GridViewMessages;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
@ContentView(value = R.layout.mainactivity)
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @ViewInject(value = R.id.ln_fenlei)
    private LinearLayout ln_feilei;
    @ViewInject(value = R.id.ln_chazhao)
    private LinearLayout ln_chazhao;
    @ViewInject(value = R.id.ln_chengjiu)
    private LinearLayout ln_chengjiu;
    @ViewInject(value = R.id.ln_shoucang)
    private LinearLayout ln_shoucang;
    @ViewInject(value = R.id.tv_shezhi)
    private TextView tv_shezhi;
    @ViewInject(value = R.id.tv_tuichu)
    private TextView tv_tuichu;
    @ViewInject(value = R.id.test_gridview)
    private GridView test_gridview;

    List<GridViewMessages> list;
    GridViewAdapter gridviewadapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        list = new ArrayList<GridViewMessages>();
        toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mdrawerlayout);
        toolbar.setTitle("分类练习");//设置Toolbar标题
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.menu_exercise) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //mAnimationDrawable.stop();
                System.out.println("打开了侧滑菜单");
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //mAnimationDrawable.start();
                System.out.println("关闭了侧滑菜单");
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        init();
        test_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "你点击了"+position, Toast.LENGTH_SHORT).show();
                GridViewMessages gvm = list.get(position);
                Intent intent = new Intent(MainActivity.this,LeibieListActivity.class);
                intent.putExtra("gvm",gvm);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
            }
        });
    }

    //通过消息机制确保加载完全的list传入gridviewadapter
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0){
                System.out.println("Handler 收到了List加载完全的消息");
                gridviewadapter = new GridViewAdapter(MainActivity.this,list);
                test_gridview.setAdapter(gridviewadapter);
            }
            return false;
        }
    });
    //初始化list集合swiprefreshlayout
    private void init(){
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/catalog?method=list");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result+"......result result+result+result+result+result");
                try {
                    JSONArray jsonarray = new JSONArray(result);
                    for (int i = 0;i < jsonarray.length();i++){
                        JSONObject json = jsonarray.getJSONObject(i);
                        int id = json.getInt("id");
                        String icon = json.getString("icon");
                        String name = json.getString("name");
                        list.add(new GridViewMessages(id,icon,name));
                        //gridviewadapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {}
            @Override
            public void onCancelled(CancelledException cex) {}
            @Override
            public void onFinished() {}
        });
    }
    //侧滑菜单中的设置和退出的点击事件
    @Event(value = {R.id.tv_shezhi,R.id.tv_tuichu},type = View.OnClickListener.class)
    private void TextViewClick(View view){
        switch (view.getId()){
            case R.id.tv_shezhi:
            {
                //Toast.makeText(MainActivity.this, "nidianleshezhi", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
            }break;
            case R.id.tv_tuichu:
            {
                //Toast.makeText(MainActivity.this, "nidianletuichu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }break;
        }
    }
    //侧滑菜单中分类练习等四项的点击事件
    @Event(value = {R.id.ln_fenlei,R.id.ln_chazhao,R.id.ln_chengjiu,R.id.ln_shoucang},type = View.OnClickListener.class)
    private void LinlayoutClick(View view){
        setselect(view);
        switch (view.getId()){
            case R.id.ln_fenlei:
            {
                mDrawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this, "woshifenlei", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.ln_chazhao:
            {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
            }break;
            case R.id.ln_chengjiu:
            {
                mDrawerLayout.closeDrawers();
            }break;
            case R.id.ln_shoucang:
            {

            }break;
        }
    }
    private void setselect(View view){
        ln_feilei.setSelected(view.getId() == R.id.ln_fenlei);
        ln_chazhao.setSelected(view.getId() == R.id.ln_chazhao);
        ln_chengjiu.setSelected(view.getId() == R.id.ln_chengjiu);
        ln_shoucang.setSelected(view.getId() == R.id.ln_shoucang);
    }
    //toolbar的关联以及搜索按钮的点击事件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sousuo:
                Toast.makeText(MainActivity.this, "跳转页面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
//android.support.v7.app.ActionBar actionBar;
//private AnimationDrawable mAnimationDrawable;
//private ImageView ivRunningMan;
//        setTitle("分类练习");
//        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//ivRunningMan = (ImageView) findViewById(R.id.iv_main);
//ivRunningMan.setBackgroundResource(R.drawable.loading_01);
//mAnimationDrawable = (AnimationDrawable) ivRunningMan.getBackground();
//mAnimationDrawable.start();
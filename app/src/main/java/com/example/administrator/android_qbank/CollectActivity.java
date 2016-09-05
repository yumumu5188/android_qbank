package com.example.administrator.android_qbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.SearchAdapter;
import helper.MyDBHelper;
import pojo.Questions;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
@ContentView(value = R.layout.collectactivity)
public class CollectActivity extends AppCompatActivity{
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
    @ViewInject(value = R.id.lv_03)
    private ListView lv_03;
    @ViewInject(R.id.tv_xiaoli)
    private TextView tv_xiaoli;

    List<Questions> collectlist;
    SearchAdapter sa;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mdrawerlayout);
        toolbar.setTitle("我的收藏");//设置Toolbar标题
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
                System.out.println("collect打开了侧滑菜单");
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //mAnimationDrawable.start();
                System.out.println("collect关闭了侧滑菜单");
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        pref = getSharedPreferences("data",MODE_PRIVATE);
        editor = pref.edit();
        init();
        lv_03.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Questions question = collectlist.get(position);
                Intent intent = new Intent(CollectActivity.this,QuestionsFragmentActivity.class);
                intent.putExtra("question",question);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_xiaoli.setText(pref.getString("nickname","xiaoli"));
    }

    //获取list
    private void init(){
        collectlist = new ArrayList<Questions>();
        MyDBHelper helper = new MyDBHelper(CollectActivity.this);
        SQLiteDatabase sd = helper.getWritableDatabase();
        Cursor cursor = sd.rawQuery("select * from question",null);
        while (cursor.moveToNext()){
            Questions question = new Questions();
            int id = cursor.getInt(0);
            question.setId(id);
            String pub = cursor.getString(1);
            long pubTime = Long.parseLong(pub);
            question.setPubTime(pubTime);
            int typeid = cursor.getInt(2);
            question.setTypeid(typeid);
            int cataid = cursor.getInt(3);
            question.setCataid(cataid);
            String content = cursor.getString(4);
            question.setContent(content);
            String answer = cursor.getString(5);
            question.setAnswer(answer);
            String options = cursor.getString(6);
            question.setOptions(options);
            collectlist.add(question);
        }
        sa = new SearchAdapter(CollectActivity.this,collectlist);
        lv_03.setAdapter(sa);
    }


    //侧滑菜单中的设置和退出的点击事件
    @Event(value = {R.id.tv_shezhi,R.id.tv_tuichu},type = View.OnClickListener.class)
    private void TextViewClick(View view){
        switch (view.getId()){
            case R.id.tv_shezhi:
            {
                //Toast.makeText(MainActivity.this, "nidianleshezhi", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CollectActivity.this,SettingActivity.class);
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
                onBackPressed();
//                Intent intent = new Intent(CollectActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
                //Toast.makeText(MainActivity.this, "woshifenlei", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.ln_chazhao:
            {
                Intent intent = new Intent(CollectActivity.this,SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
            }break;
            case R.id.ln_chengjiu:
            {
                onBackPressed();
//                Intent intent = new Intent(CollectActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
            }break;
            case R.id.ln_shoucang:
            {
                Toast.makeText(CollectActivity.this, "这是收藏页", Toast.LENGTH_SHORT).show();
            }break;
        }
    }
    private void setselect(View view){
        ln_feilei.setSelected(view.getId() == R.id.ln_fenlei);
        ln_chazhao.setSelected(view.getId() == R.id.ln_chazhao);
        ln_chengjiu.setSelected(view.getId() == R.id.ln_chengjiu);
        ln_shoucang.setSelected(view.getId() == R.id.ln_shoucang);
    }


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
                Toast.makeText(CollectActivity.this, "跳转页面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CollectActivity.this,SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

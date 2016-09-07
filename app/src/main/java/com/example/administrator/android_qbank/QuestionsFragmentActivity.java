package com.example.administrator.android_qbank;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import fragment.JudgeFragment;
import fragment.MultiSelectFragment;
import fragment.ShortAnswerFragment;
import fragment.SingleFragment;
import fragmentadapter.QuestionsFragmentAdapter;
import helper.MyDBHelper;
import pojo.Questions;
import widget.MyViewPager;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
@ContentView(value = R.layout.questionsfragmentactivity)
public class QuestionsFragmentActivity extends AppCompatActivity{

    @ViewInject(value = R.id.v_02)
    private MyViewPager v_02;
    @ViewInject(value = R.id.im_shangyiti)
    private ImageView im_shangyiti;
    @ViewInject(value = R.id.im_shoucang)
    private ImageView im_shoucang;
    @ViewInject(value = R.id.im_xiayiti)
    private ImageView im_xiayiti;

    private Toolbar toolbar;
    private Questions question;
    int typeid;
    int id;
    private SingleFragment singlefragment;
    private MultiSelectFragment multiselectfragment;
    private JudgeFragment judgefragment;
    private ShortAnswerFragment shortanswerfragment;
    private List<Fragment> fragmentlist;
    private QuestionsFragmentAdapter questionsfragmentadapter;
    MyDBHelper helper;
    SQLiteDatabase sd;
    int size;
    int leibie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        toolbar.setTitle("题目详情");//设置Toolbar标题
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        question = (Questions) intent.getSerializableExtra("question");
        size = intent.getIntExtra("size",0);
        typeid = question.getTypeid();
        Aid = typeid;
        id = question.getId();
        Cid = id;
        leibie = intent.getIntExtra("leibie",0);
        if (leibie == 2)
            toolbar.setTitle((Cid-2)+"/"+size+"题目详情");
        else
            toolbar.setTitle(Cid+"/"+size+"题目详情");
        selectfragment(typeid);
    }
    private void selectfragment(int typeid){
        fragmentlist = new ArrayList<Fragment>();
        singlefragment = new SingleFragment();
        multiselectfragment = new MultiSelectFragment();
        judgefragment = new JudgeFragment();
        shortanswerfragment = new ShortAnswerFragment();
        fragmentlist.add(singlefragment);
        fragmentlist.add(multiselectfragment);
        fragmentlist.add(judgefragment);
        fragmentlist.add(shortanswerfragment);
        questionsfragmentadapter = new QuestionsFragmentAdapter(getSupportFragmentManager(),fragmentlist);
        v_02.setAdapter(questionsfragmentadapter);
        if (typeid == 1){
            singlefragment.setSingleQuestions(id);
            v_02.setCurrentItem(0);
        }
        if (typeid == 2) {
            multiselectfragment.setMultiSelectQuestions(id);
            v_02.setCurrentItem(1);
        }
        if (typeid == 3) {
            judgefragment.setJudgeQuestions(id);
            v_02.setCurrentItem(2);
        }
        if (typeid == 4) {
            shortanswerfragment.setShortAnswerQuestions(id);
            v_02.setCurrentItem(3);
        }
    }
    @Event(value = {R.id.im_shangyiti,R.id.im_xiayiti,R.id.im_shoucang},type = View.OnClickListener.class)
    private void ImageViewClick(View view){
        switch (view.getId()){
            case R.id.im_shangyiti:
            {
                if (leibie == 2){
                    if (Cid == 3){
                        Toast.makeText(QuestionsFragmentActivity.this, "这已是第一题", Toast.LENGTH_SHORT).show();
                    } else {
                        upnext = 99;
                        getUp();
                    }
                }else {
                    if (Cid == 1) {
                        Toast.makeText(QuestionsFragmentActivity.this, "这已是第一题", Toast.LENGTH_SHORT).show();
                    } else {
                        upnext = 99;
                        getUp();
                    }
                }
            }break;
            case R.id.im_shoucang:
            {
                a = -1;
                helper = new MyDBHelper(QuestionsFragmentActivity.this);
                sd = helper.getWritableDatabase();
                Cursor cursor = sd.rawQuery("select * from question",null);
                while (cursor.moveToNext()){
                    a = cursor.getInt(0);
                    if (a == Cid)
                    {
                        Toast.makeText(QuestionsFragmentActivity.this, "该题已被收藏过", Toast.LENGTH_SHORT).show();
                        a = 0;
                        break;
                    }
                }
                if (a>0||a==-1){
                    Collect();
                }
            }break;
            case R.id.im_xiayiti:
            {
                if (leibie == 1){
                    if (Cid == 2){
                        Toast.makeText(QuestionsFragmentActivity.this, "这已是最后一题", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        upnext = 88;
                        getNext();
                    }
                }else {
                    if (Cid == 9)
                        Toast.makeText(QuestionsFragmentActivity.this, "这已是最后一题", Toast.LENGTH_SHORT).show();
                    else {
                        upnext = 88;
                        getNext();
                    }
                }
            }break;
        }
    }
    int a;
    int b,c,d;
    String e, f,g,h;
    private void Collect(){
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=findone");
        params.addBodyParameter("id",String.valueOf(Cid));
        params.addBodyParameter("user_id",String.valueOf(2));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json1 = new JSONObject(result);
                    h = json1.getLong("pubTime")+"";
                    e = json1.getString("content");
                    f = json1.getString("answer");
                    c = json1.getInt("typeid");
                    d = json1.getInt("cataid");
                    b = json1.getInt("id");
                    if ((Aid==1)||(Aid==2)) {
                        g = json1.getString("options");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sd.execSQL("insert into question(id,pubTime,typeid,cataid,content,answer,options) values(?,?,?,?,?,?,?)",new Object[]{b,h,c,d,e,f,g});
                Toast.makeText(QuestionsFragmentActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {}
            @Override
            public void onCancelled(CancelledException cex) {}
            @Override
            public void onFinished() {}
        });
    }



    private void getUp(){
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=prev");
        params.addBodyParameter("id",String.valueOf(Cid));
        params.addBodyParameter("user_id",String.valueOf(2));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getQuestionsMessages(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {}
            @Override
            public void onCancelled(CancelledException cex) {}
            @Override
            public void onFinished() {}
        });
    }
    private void getNext(){
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=next");
        params.addBodyParameter("id",String.valueOf(Cid));
        params.addBodyParameter("user_id",String.valueOf(2));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getQuestionsMessages(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {}
            @Override
            public void onCancelled(CancelledException cex) {}
            @Override
            public void onFinished() {}
        });
    }
    int Aid,Bid,Cid;
    int upnext = 0;
    String title1,title2,title3,title4;
    String Acontent;
    String Aanwser;
    String options;
    private void getQuestionsMessages(String result){
        try {
            JSONObject json1 = new JSONObject(result);
            Acontent = json1.getString("content");
            Aanwser = json1.getString("answer");
            Aid = json1.getInt("typeid");
            Bid = json1.getInt("cataid");
            Cid = json1.getInt("id");
            if ((Aid==1)||(Aid==2)) {
                options = json1.getString("options");
                JSONArray jsonarray2 = new JSONArray(options);
                for (int j = 0; j < jsonarray2.length(); j++) {
                    JSONObject json3 = jsonarray2.getJSONObject(j);
                    if (j == 0)
                        title1 = json3.getString("title");
                    if (j == 1)
                        title2 = json3.getString("title");
                    if (j == 2)
                        title3 = json3.getString("title");
                    if (j == 3)
                        title4 = json3.getString("title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(Acontent+Aanwser+Aid+Bid+Cid);
        if (upnext == 99){
            switch (Aid){
                case 1:
                {
                    singlefragment.setSingleQuestions(Cid);
                    if (Aid == questionsfragmentadapter.getId()+1)
                        singlefragment.settext(Acontent,Aanwser,title1,title2,title3,title4);
                    v_02.setCurrentItem(0);
                }break;
                case 2:{
                    multiselectfragment.setMultiSelectQuestions(Cid);
                    if (Aid == questionsfragmentadapter.getId()+1)
                        multiselectfragment.settext(Acontent,Aanwser,title1,title2,title3,title4);
                    v_02.setCurrentItem(1);
                }break;
                case 3:{
                    judgefragment.setJudgeQuestions(Cid);
                    if (Aid == questionsfragmentadapter.getId()+1)
                        judgefragment.settext(Acontent,Aanwser);
                    v_02.setCurrentItem(2);
                }break;
                case 4:{
                    shortanswerfragment.setShortAnswerQuestions(Cid);
                    if (Aid == questionsfragmentadapter.getId()+1)
                            shortanswerfragment.settext(Acontent,Aanwser);
                    v_02.setCurrentItem(3);
                    shortanswerfragment.settext(Acontent,Aanwser);
                }break;
            }
            toolbar.setTitle(Cid+"/"+size+"题目详情");
            if (leibie == 2)
                toolbar.setTitle((Cid-2)+"/"+size+"题目详情");
        }
        if (upnext == 88){
            switch (Aid){
                case 1:
                {
                    singlefragment.setSingleQuestions(Cid);
                    if (Aid == questionsfragmentadapter.getId()+1) {
                        singlefragment.settext(Acontent, Aanwser, title1, title2, title3, title4);
                    }
                    v_02.setCurrentItem(0);
                }break;
                case 2:{
                    multiselectfragment.setMultiSelectQuestions(Cid);
                    if (Aid == questionsfragmentadapter.getId()+1) {
                        multiselectfragment.settext(Acontent, Aanwser, title1, title2, title3, title4);
                    }
                    v_02.setCurrentItem(1);
                }break;
                case 3:{
                    judgefragment.setJudgeQuestions(Cid);
                    if (Aid == questionsfragmentadapter.getId()+1) {
                        judgefragment.settext(Acontent, Aanwser);
                    }
                    v_02.setCurrentItem(2);
                }break;
                case 4:{
                    shortanswerfragment.setShortAnswerQuestions(Cid);
                    if (Aid == questionsfragmentadapter.getId()+1){
                        System.out.println("该方法执行了");
                        shortanswerfragment.settext(Acontent,Aanwser);
                    } else{
                        v_02.setCurrentItem(3);
                        shortanswerfragment.settext(Acontent,Aanwser);
                    }

                }break;
            }
            toolbar.setTitle(Cid+"/"+size+"题目详情");
            if (leibie == 2)
                toolbar.setTitle((Cid-2)+"/"+size+"题目详情");
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

package com.example.administrator.android_qbank;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.SearchAdapter;
import pojo.GridViewMessages;
import pojo.Questions;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
@ContentView(value = R.layout.leibielistactivity)
public class LeibieListActivity extends AppCompatActivity {

    @ViewInject(value = R.id.lv_02)
    private ListView lv_02;
    @ViewInject(value = R.id.srl2)
    private SwipeRefreshLayout srl2;

    private Toolbar toolbar;
    private int catelogId;
    private List<Questions> leibielist;
    private SearchAdapter leibieadapter;
    private int a;
    LinearLayout ln_ln;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Intent intent = getIntent();
        GridViewMessages gvm = (GridViewMessages) intent.getSerializableExtra("gvm");
        String title = gvm.getName();
        catelogId = gvm.getId();
        toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        toolbar.setTitle(title);//设置Toolbar标题
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();
        lv_02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Questions question = leibielist.get(position);
                Intent intent = new Intent(LeibieListActivity.this,QuestionsFragmentActivity.class);
                intent.putExtra("question",question);
                intent.putExtra("size",leibielist.size());
                intent.putExtra("leibie",catelogId);
                startActivity(intent);
            }
        });
        srl2.setColorSchemeResources(R.color.color1,R.color.color2,R.color.color3,R.color.color4);
        srl2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler haaa = new Handler();
                haaa.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srl2.setRefreshing(false);
                    }
                },2000);
            }
        });
        LayoutInflater inflater = LayoutInflater.from(LeibieListActivity.this);
        ln_ln = (LinearLayout) inflater.inflate(R.layout.footview,null);
        lv_02.addFooterView(ln_ln);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 2){
                leibieadapter = new SearchAdapter(LeibieListActivity.this,leibielist);
                lv_02.setAdapter(leibieadapter);
            }
            return false;
        }
    });

    private void getData(){
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=list");
        //这的传值有问题
        params.addBodyParameter("catelogId",String.valueOf(catelogId));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                pushData(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {}
            @Override
            public void onCancelled(CancelledException cex) {}
            @Override
            public void onFinished() {}
        });
    }
    private void pushData(String result){
        leibielist = new ArrayList<Questions>();
        try {
            JSONObject json1 = new JSONObject(result);
            JSONArray jsonarray1 = json1.getJSONArray("content");
            int page = json1.getInt("page");
            int totalElements = json1.getInt("totalElements");
            int totalPages = json1.getInt("totalPages");
            int size = json1.getInt("size");
            for (int i = 0;i < jsonarray1.length();i++){
                Questions ques = new Questions();
                ques.setTotalElements(totalElements);
                JSONObject json2 = jsonarray1.getJSONObject(i);
                System.out.println(json2.length()+"...");
                if (json2.length() == 6){
                    String content = json2.getString("content");
                    ques.setContent(content);
                    int id = json2.getInt("id");
                    ques.setId(id);
                    long pubTime = json2.getLong("pubTime");
                    ques.setPubTime(pubTime);
                    int typeid = json2.getInt("typeid");
                    ques.setTypeid(typeid);
                    String answer = json2.getString("answer");
                    ques.setAnswer(answer);
                    int cataid = json2.getInt("cataid");
                    a = cataid;
                    ques.setCataid(cataid);
                    System.out.println(ques.toString());
                }else if (json2.length() == 7){
                    String content = json2.getString("content");
                    ques.setContent(content);
                    int id = json2.getInt("id");
                    ques.setId(id);
                    long pubTime = json2.getLong("pubTime");
                    ques.setPubTime(pubTime);
                    int typeid = json2.getInt("typeid");
                    ques.setTypeid(typeid);
                    String answer = json2.getString("answer");
                    ques.setAnswer(answer);
                    int cataid = json2.getInt("cataid");
                    a = cataid;
                    ques.setCataid(cataid);
                    String options = json2.getString("options");
                    ques.setOptions(options);
                    System.out.println(ques.toString());
                }
                if (catelogId == a)
               leibielist.add(ques);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        message.what = 2;
        handler.sendMessage(message);
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

package com.example.administrator.android_qbank;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

import adapter.SearchAdapter;
import pojo.Questions;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
@ContentView(value = R.layout.searchactivity)
public class SearchActivity extends AppCompatActivity{

    @ViewInject(value = R.id.tv_timu)
    private TextView tv_timu;
    @ViewInject(value = R.id.et_timu)
    private EditText et_timu;
    @ViewInject(value = R.id.lv_01)
    private ListView lv_01;

    private Toolbar toolbar;
    SearchAdapter searchadapter;
    List<Questions> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        toolbar.setTitle("题目搜索");//设置Toolbar标题
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<Questions>();
        lv_01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Questions question = list.get(position);
                Intent intent = new Intent(SearchActivity.this,QuestionsFragmentActivity.class);
                intent.putExtra("question",question);
                startActivity(intent);
            }
        });
    }

    @Event(value = R.id.tv_timu,type = View.OnClickListener.class)
    private void SearchClick(View view){
        switch (view.getId()){
            case R.id.tv_timu:
            {
                list.clear();
                String questionName = et_timu.getText().toString();
                RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=list");
                params.addBodyParameter("questionName",questionName);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        //System.out.println(result+".........zhe shi sou suo jie guo ");
                        PushJson(result);
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {}
                    @Override
                    public void onCancelled(CancelledException cex) {}
                    @Override
                    public void onFinished() {}
                });
            }break;
        }
    }

    private void PushJson(String result){
        try {
            JSONObject json1 = new JSONObject(result);
            JSONArray jsonarray1 = json1.getJSONArray("content");
            //page是当前页号
            int page = json1.getInt("page");
            //totalElements是总元素数量
            int totalElements = json1.getInt("totalElements");
            //totalPages是总页数
            int totalPages = json1.getInt("totalPages");
            //size是每页要显示数
            int size = json1.getInt("size");
            for (int i = 0;i < jsonarray1.length();i++){
                Questions questions = new Questions();
                questions.setTotalElements(totalElements);
                JSONObject json2 = jsonarray1.getJSONObject(i);
                System.out.println(json2.length()+"...");
                if (json2.length() == 6){
                    String content = json2.getString("content");
                    questions.setContent(content);
                    int id = json2.getInt("id");
                    questions.setId(id);
                    long pubTime = json2.getLong("pubTime");
                    questions.setPubTime(pubTime);
                    int typeid = json2.getInt("typeid");
                    questions.setTypeid(typeid);
                    String answer = json2.getString("answer");
                    questions.setAnswer(answer);
                    int cataid = json2.getInt("cataid");
                    questions.setCataid(cataid);
                    System.out.println(questions.toString());
                }else if (json2.length() == 7){
                    String content = json2.getString("content");
                    questions.setContent(content);
                    int id = json2.getInt("id");
                    questions.setId(id);
                    long pubTime = json2.getLong("pubTime");
                    questions.setPubTime(pubTime);
                    int typeid = json2.getInt("typeid");
                    questions.setTypeid(typeid);
                    String answer = json2.getString("answer");
                    questions.setAnswer(answer);
                    int cataid = json2.getInt("cataid");
                    questions.setCataid(cataid);
                    String options = json2.getString("options");
                    questions.setOptions(options);
                    System.out.println(questions.toString());
                    JSONArray jsonarray2 = new JSONArray(options);
                    for (int j = 0;j < jsonarray2.length();j++){
                        JSONObject json3 = jsonarray2.getJSONObject(j);
                        String title = json3.getString("title");
                        //System.out.println("..."+title);
                        boolean checked = json3.getBoolean("checked");
                        //System.out.println("..."+checked);
                    }
                }
                list.add(questions);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1){
                searchadapter = new SearchAdapter(SearchActivity.this,list);
                lv_01.setAdapter(searchadapter);
            }
            return false;
        }
    });
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

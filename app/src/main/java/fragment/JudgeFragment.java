package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.android_qbank.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import pojo.Questions;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class JudgeFragment extends Fragment{
    private Questions question;
    int id;
    TextView tv_judge_a,tv_judge_c;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.judgefragment,null);
        tv_judge_a = (TextView) view.findViewById(R.id.tv_judge_a);
        tv_judge_c = (TextView) view.findViewById(R.id.tv_judge_c);
        getData();
        return view;
    }
    public void setJudgeQuestions(int id){
        this.id = id;
    }
    public void getData(){
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=findone");
        params.addBodyParameter("id",String.valueOf(id));
        params.addBodyParameter("user_id",String.valueOf(2));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                setUIData(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {}
            @Override
            public void onCancelled(CancelledException cex) {}
            @Override
            public void onFinished() {}
        });
    }
    private void setUIData(String result){
        try {
            JSONObject json1 = new JSONObject(result);
            String content = json1.getString("content");
            tv_judge_a.setText(content);
            String answer = json1.getString("answer");
            tv_judge_c.setText(answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void settext(String a,String b){
        tv_judge_a.setText(a);
        tv_judge_c.setText(b);
    }
}

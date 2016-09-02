package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.android_qbank.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import pojo.Questions;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class MultiSelectFragment extends Fragment{
    private Questions question;
    int id;
    TextView tv_multiselect_a,tv_multiselect_b,tv_multiselect_c,tv_multiselect_d,tv_multiselect_e;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.multiselectfragment,null);
        tv_multiselect_a = (TextView) view.findViewById(R.id.tv_multiselect_a);
        tv_multiselect_b = (TextView) view.findViewById(R.id.tv_multiselect_b);
        tv_multiselect_c = (TextView) view.findViewById(R.id.tv_multiselect_c);
        tv_multiselect_d = (TextView) view.findViewById(R.id.tv_multiselect_d);
        tv_multiselect_e = (TextView) view.findViewById(R.id.tv_multiselect_e);
        getData(id);
        return view;
    }
    public void setMultiSelectQuestions(int id){
        this.id = id;
    }
    public void getData(int id){
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
            tv_multiselect_a.setText(content);
            String options = json1.getString("options");
            JSONArray jsonarray2 = new JSONArray(options);
            for (int j = 0;j < jsonarray2.length();j++){
                JSONObject json3 = jsonarray2.getJSONObject(j);
                String title = json3.getString("title");
                if (j == 0)
                    tv_multiselect_b.setText(title);
                if (j == 1)
                    tv_multiselect_c.setText(title);
                if (j == 2)
                    tv_multiselect_d.setText(title);
                if (j == 3)
                    tv_multiselect_e.setText(title);
                boolean checked = json3.getBoolean("checked");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void settext(String a,String b,String c,String d,String e,String f){
        tv_multiselect_a.setText(a);
        tv_multiselect_b.setText(c);
        tv_multiselect_c.setText(d);
        tv_multiselect_d.setText(e);
        tv_multiselect_e.setText(f);
    }
}

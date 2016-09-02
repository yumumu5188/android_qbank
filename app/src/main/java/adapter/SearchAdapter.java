package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.android_qbank.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pojo.Questions;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class SearchAdapter extends BaseAdapter {
    private List<Questions> list;
    private Context context;
    public SearchAdapter(Context context,List<Questions> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchViewHolder searchViewHolder = null;
        if (convertView == null){
            searchViewHolder = new SearchViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item2,null);
            searchViewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            searchViewHolder.tv_typeid = (TextView) convertView.findViewById(R.id.tv_typeid);
            searchViewHolder.tv_pubTime = (TextView) convertView.findViewById(R.id.tv_pubTime);
            convertView.setTag(searchViewHolder);
        }else{
            searchViewHolder = (SearchViewHolder) convertView.getTag();
        }

        searchViewHolder.tv_content.setText(list.get(position).getContent());
        if (list.get(position).getTypeid() == 1)
            searchViewHolder.tv_typeid.setText("单选");
        if (list.get(position).getTypeid() == 2)
            searchViewHolder.tv_typeid.setText("多选");
        if (list.get(position).getTypeid() == 3)
            searchViewHolder.tv_typeid.setText("判断");
        if (list.get(position).getTypeid() == 4)
            searchViewHolder.tv_typeid.setText("简答");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date= new Date((Long)list.get(position).getPubTime());
        String s = sdf.format(date);
        searchViewHolder.tv_pubTime.setText(s);

        return convertView;
    }
    public class SearchViewHolder{
        TextView tv_content;
        TextView tv_typeid;
        TextView tv_pubTime;
    }
}

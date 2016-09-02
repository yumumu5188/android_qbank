package adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.android_qbank.R;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import pojo.GridViewMessages;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class GridViewAdapter extends BaseAdapter{
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    List<GridViewMessages> list;
    Context context;
    boolean fff;
    public GridViewAdapter(Context context,List<GridViewMessages> list){
        this.context = context;
        this.list = list;
        pref = context.getSharedPreferences("data",context.MODE_PRIVATE);
        editor = pref.edit();
        fff = pref.getBoolean("three",true);
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

        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item,null);
            holder.textview = (TextView) convertView.findViewById(R.id.grid_tv);
            holder.imageview = (ImageView) convertView.findViewById(R.id.grid_im);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textview.setText(list.get(position).getName().toString());
        if (fff){
            getPicture(list.get(position).getIcon(),holder);
        }

        return convertView;
    }
    private void getPicture(String icon,ViewHolder holder){
        String url = "http://115.29.136.118/web-question/"+icon;
        //设置加载图片的参数
        ImageOptions.Builder options = new ImageOptions.Builder();
        //设置图片缩放模式
        options.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        //设置下载中显示的图片
        options.setLoadingDrawableId(R.drawable.loading_01);
        //得到ImageOptions的对象
        ImageOptions build = options.build();
        x.image().bind(holder.imageview, url, build, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {}
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {}
            @Override
            public void onCancelled(CancelledException cex) {}
            @Override
            public void onFinished() {}
        });
    }
    public class ViewHolder{
        ImageView imageview;
        TextView textview;
    }
}

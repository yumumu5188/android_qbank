package com.example.administrator.android_qbank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import fragment.FirstFragment;
import fragment.SecondFragment;
import fragment.ThreeFragment;
import fragmentadapter.GuideAdapter;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
@ContentView(value = R.layout.fragmentactivity)
public class FragmentActivity extends AppCompatActivity{

    @ViewInject(value = R.id.v_01)
    private ViewPager v_01;
    @ViewInject(value = R.id.iv_dot_1)
    private ImageView iv_dot_01;
    @ViewInject(value = R.id.iv_dot_2)
    private ImageView iv_dot_02;
    @ViewInject(value = R.id.iv_dot_3)
    private ImageView iv_dot_03;
    List<Fragment> list;
    GuideAdapter guideadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setTitle("欢迎你");
        list = new ArrayList<Fragment>();
        list.add(new FirstFragment());
        list.add(new SecondFragment());
        list.add(new ThreeFragment());
        guideadapter = new GuideAdapter(getSupportFragmentManager(),list);
        v_01.setAdapter(guideadapter);
        v_01.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                iv_dot_01.setImageResource(R.mipmap.page_indicator_focused);
                iv_dot_02.setImageResource(R.mipmap.page_indicator_focused);
                iv_dot_03.setImageResource(R.mipmap.page_indicator_focused);
                if (position == 0){
                    iv_dot_01.setImageResource(R.mipmap.page_indicator_unfocused);
                }
                if (position == 1){
                    iv_dot_02.setImageResource(R.mipmap.page_indicator_unfocused);
                }
                if (position == 2){
                    iv_dot_03.setImageResource(R.mipmap.page_indicator_unfocused);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}

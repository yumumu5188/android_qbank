package fragmentadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class QuestionsFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentlist;
    int id;
    public QuestionsFragmentAdapter(FragmentManager fm,List<Fragment> fragmentlist) {
        super(fm);
        this.fragmentlist = fragmentlist;
    }

    @Override
    public Fragment getItem(int position) {
        id = position;
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentlist.size();
    }
    public int getId(){
        return id;
    }
}

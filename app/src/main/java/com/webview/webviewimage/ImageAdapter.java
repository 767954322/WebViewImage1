package com.webview.webviewimage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ss on 2017/10/24.
 */

public class ImageAdapter extends FragmentStatePagerAdapter {
    private List<String> mlist;

    public ImageAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        mlist = list;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ImageDetailFragment.newInstance(mlist.get(position));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        final ImageDetailFragment fragment = (ImageDetailFragment) object;
        // As the item gets destroyed we try and cancel any existing work.
        fragment.cancelWork();
        super.destroyItem(container, position, object);
    }
}

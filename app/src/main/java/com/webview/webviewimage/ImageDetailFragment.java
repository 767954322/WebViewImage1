package com.webview.webviewimage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by ss on 2017/10/24.
 */

public class ImageDetailFragment extends Fragment {

    private String mimage;
    private PhotoView mImageView;
    /**
     * 生成图像片段的新实例的工厂方法
     *       要加载的父适配器内的那个图像
     * @return 返回ImageDetailFragment新实例
     */
    public static ImageDetailFragment newInstance(String image ) {
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();

        args.putString("image", image);
        f.setArguments(args);

        return f;
    }

    /**
     * 空构造函数
     */
    public ImageDetailFragment() {
    }

    /**
     * 从外填充图像，使用工厂模式
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mimage = getArguments() != null ? getArguments().getString("image") : "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image, container, false);
        mImageView = (PhotoView) v.findViewById(R.id.imageView);
        Glide.with(getActivity()).load(mimage).into(mImageView);
        return v;
    }
    public void cancelWork() {
        mImageView.setImageDrawable(null);
        mImageView = null;
    }

}

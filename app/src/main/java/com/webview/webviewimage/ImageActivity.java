package com.webview.webviewimage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ss on 2017/10/23.
 */

public class ImageActivity extends AppCompatActivity {
    private ImageAdapter mAdapter;
    private ViewPager mPager;

    private ImageView back_button;
    private int mposition = 0;
    private TextView number1;
    private int number;
    private List<String> imglist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initview();
    }

    private void initview() {
        mPager = (ViewPager) findViewById(R.id.pager);

        back_button = (ImageView) findViewById(R.id.back_button);
        number1 = (TextView) findViewById(R.id.number1);

        number = getIntent().getIntExtra("number",-1);
        imglist = (List<String>) getIntent().getSerializableExtra("list");
        number1.setText((number+1)+"/"+imglist.size());

        mAdapter = new ImageAdapter(getSupportFragmentManager(),imglist);
        mPager.setAdapter(mAdapter);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view!=null){
                    finish();
                }

            }
        });

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 把当前显示的position传递出去
                mposition = position;
                if ( imglist!= null && imglist.size() > 0){
                    number1.setText((mposition+1)+"/"+imglist.size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if(number!= -1) {
            mPager.setCurrentItem(number);
        }
    }
}

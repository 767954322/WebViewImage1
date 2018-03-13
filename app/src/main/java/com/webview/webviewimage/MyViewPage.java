package com.webview.webviewimage;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ss on 2017/10/24.
 * 自定义Viewpage 解决事件分发
 */

public class MyViewPage extends ViewPager {

    public MyViewPage(Context context) {
        super(context);
    }

    public MyViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            //不理会
            return false;
        }catch(ArrayIndexOutOfBoundsException e ){
            //不理会
            return false;
        }
    }
}

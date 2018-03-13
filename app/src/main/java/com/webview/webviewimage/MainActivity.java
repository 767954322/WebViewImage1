package com.webview.webviewimage;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
//    private String url = "http://app.jstyle.cn/jm_interface_1_2/index.php/home/article/app_articledesc?rid=44246";
    private String url = "http://zhan.renren.com/hdwallpapers?from=template&checked=true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webView);
        initView();
    }

    private void initView() {
        WebSettings mWebSettings = mWebView.getSettings();
        // 设置是否可缩放 仅支持双击缩放，不支持触摸缩放
        mWebSettings.setSupportZoom(true);
        //设置自适应屏幕，两者合用
        mWebSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        //设置编码格式
        mWebSettings.setDefaultTextEncodingName("utf-8");
        //支持自动加载图片
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setBackgroundColor(0); // 设置背景色
        mWebView.setWebViewClient(new myWebviewClient());

        mWebView.addJavascriptInterface(new JavascriptInterface(), "imageListener");
        mWebView.loadUrl(url);
    }


    public class JavascriptInterface {
        @android.webkit.JavascriptInterface
        public void openImage(String imageUrl, String img) {
            int position = 0;
            String[] imgs = imageUrl.split(",");
            ArrayList<String> imgUrlList = new ArrayList<>();
            for (String s : imgs) {
                imgUrlList.add(s);
            }
            for (int i = 0; i < imgUrlList.size(); i++) {
                if (img.equals(imgUrlList.get(i))) {
                    position = i;
                }
            }
            Intent intent = new Intent(MainActivity.this, ImageActivity.class);
            intent.putExtra("number", position);
            intent.putExtra("list", (Serializable) imgUrlList);
            startActivity(intent);
        }
    }

    private class myWebviewClient extends WebViewClient {

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public void onPageFinished(WebView view, String url) {
            //网页加载完成 走JS代码
            clickImage();
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
//            //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
//            //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
//
//            return true;
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
//            //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
//
//            return true;
//        }

    }

    private void clickImage() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        //if(isShow && objs[i].width>80)是将小图片过滤点,不显示小图片,如果没有限制,可去掉
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "var imgUrl = \"\";" +
                "var filter = [\"img//EventHead.png\",\"img//kong.png\",\"hdtz//button.png\"];" +
                "var isShow = true;" +
                "for(var i=0;i<objs.length;i++){" +
                "for(var j=0;j<filter.length;j++){" +
                "if(objs[i].src.indexOf(filter[j])>=0) {" +
                "isShow = false; break;}}" +
                "if(isShow && objs[i].width>80){" +
                "imgUrl += objs[i].src + ',';isShow = true;" +
                "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imageListener.openImage(imgUrl,this.src);" +
                "    }" +
                "}" +
                "}" +
                "})()"
        );
    }

}
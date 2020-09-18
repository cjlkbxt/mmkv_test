package com.medbit.medbit_patient.ui;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.medbit.lib_base.base.BaseActivity;
import com.medbit.lib_base.constants.Constant;
import com.medbit.medbit_patient.R;

public class WebviewActivity extends BaseActivity {

    private WebView mWebView;
    private Button mBtnTest;
    private String mBedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBedId = getIntent().getStringExtra(Constant.KEY_BED_ID);
        String mSiteCode = getIntent().getStringExtra(Constant.KEY_SITE_CODE);
        mWebView = findViewById(R.id.webview);
        mBtnTest = findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(view -> fastClick());
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
//        mWebView.loadUrl("http://localhost:3001/patientApp/account/mid.html?siteCode=b310543&bedId=26");
        String url = "http://192.168.10.212:3001/patientApp/account/mid.html?siteCode=" + mSiteCode + "&bedId=" + mBedId;
//        String url = "http://192.168.10.126:17100/patientApp/account/crf.html?patientCode=290001&visitId=1&patientName=%E9%99%88%E6%B3%A2&siteCode=b310541&siteName=%E8%A5%BF%E4%BA%AC%E6%B6%88%E5%8C%96%E4%B8%80%E7%A7%91&inHospitalDate=2019-8-10&bedId=1&dutyNurse=%E8%B5%B5%E4%B8%BD%E4%B8%BD&staffId=A11#1";
        mWebView.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        doBack();
    }

    private void doBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    private long mTempTime;//临时时间
    private int mClickNum;//点击次数

    //五次点击之后进入隐藏入口
    private void fastClick() {//点击隐藏入口
        long time = System.currentTimeMillis();
        if (mTempTime == 0) {//第一次点击隐藏入口,保存临时时间
            mTempTime = time;//
        } else {
            if (time - mTempTime > 2000) {//这里设置两秒的超时时间,如果超过两秒,重置状态
                mTempTime = time;//给临时时间和点击次数进行初始化
                mClickNum = 0;
                return;
            }
            mClickNum++;
            mTempTime = time;
            if (mClickNum == 4) {//因为一次点击走if语句
                mTempTime = 0;//给临时时间和点击次数进行初始化
                mClickNum = 0;
                Toast.makeText(this, "当前床位号为：" + mBedId, Toast.LENGTH_SHORT).show();
            }
        }
    }


}

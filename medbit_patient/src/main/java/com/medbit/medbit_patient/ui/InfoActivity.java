package com.medbit.medbit_patient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.medbit.lib_base.base.BaseActivity;
import com.medbit.lib_base.constants.Constant;
import com.medbit.medbit_patient.R;
import com.tencent.mmkv.MMKV;

public class InfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvBedId;
    private TextView mTvConfirm;
    private TextView mTvSuggest;

    private MMKV mMultiMmkv;
    private String mBedId;
    private String mSiteCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mTvBedId = findViewById(R.id.tv_bed_id);
        mTvConfirm = findViewById(R.id.tv_confirm);
        mTvSuggest = findViewById(R.id.tv_suggest);
        mTvConfirm.setOnClickListener(this);
        mMultiMmkv = MMKV.mmkvWithID(Constant.MMAPID, MMKV.MULTI_PROCESS_MODE);
        mBedId = mMultiMmkv.decodeString(Constant.KEY_BED_ID, "");
        mSiteCode = mMultiMmkv.decodeString(Constant.KEY_SITE_CODE, "");
        if (TextUtils.isEmpty(mBedId)) {
            mTvBedId.setText("您还未设置床位号");
            mTvSuggest.setText("请到管理端进行设置");
        } else {
            mTvBedId.setText("当前床位号为:" + mBedId);
            mTvSuggest.setText("如需修改，请到管理端进行修改");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                Intent intent = new Intent(this, WebviewActivity.class);
                intent.putExtra(Constant.KEY_BED_ID, mBedId);
                intent.putExtra(Constant.KEY_SITE_CODE, mSiteCode);
                startActivity(intent);
                finish();
                break;
        }
    }
}
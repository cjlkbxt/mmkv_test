package com.medbit.medbit_manage.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.medbit.lib_base.base.BaseActivity;
import com.medbit.lib_base.constants.Constant;
import com.medbit.medbit_manage.R;
import com.tencent.mmkv.MMKV;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtBedId;
    private EditText mEtSiteCode;
    private TextView mTvSave;
    private MMKV mMultiMmkv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mMultiMmkv = MMKV.mmkvWithID(Constant.MMAPID, MMKV.MULTI_PROCESS_MODE);

        mEtBedId = findViewById(R.id.et_bed_id);
        mEtSiteCode = findViewById(R.id.et_site_code);
        mTvSave = findViewById(R.id.tv_save);

        String bedId = mMultiMmkv.decodeString(Constant.KEY_BED_ID, "111");
        mEtBedId.setText(bedId);
        mEtBedId.setSelection(bedId.length());

        String siteCode = mMultiMmkv.decodeString(Constant.KEY_SITE_CODE, "b310541");
        mEtSiteCode.setText(siteCode);
        mEtSiteCode.setSelection(siteCode.length());

        mTvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                if (TextUtils.isEmpty(mEtBedId.getEditableText().toString().trim())) {
                    Toast.makeText(this, "请输入床位号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mEtSiteCode.getEditableText().toString().trim())) {
                    Toast.makeText(this, "请输入中心号", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean isSuccess = mMultiMmkv.encode(Constant.KEY_BED_ID, mEtBedId.getEditableText().toString().trim());
                mMultiMmkv.encode(Constant.KEY_SITE_CODE, mEtSiteCode.getEditableText().toString().trim());
                if (isSuccess) {
                    Toast.makeText(this, "床位号:" + mMultiMmkv.decodeString(Constant.KEY_BED_ID) + "\n设置成功！", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

}
package com.medbit.medbit_manage.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.medbit.lib_base.base.BaseActivity;
import com.medbit.lib_base.constants.Constant;
import com.medbit.medbit_manage.R;
import com.tencent.mmkv.MMKV;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtBedNum;
    private EditText mEtDepartmentNum;
    private TextView mConfirm;
    private MMKV mMultiMmkv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mMultiMmkv = MMKV.mmkvWithID(Constant.MMAPID, MMKV.MULTI_PROCESS_MODE);

        mEtBedNum = findViewById(R.id.et_input);
        mEtDepartmentNum = findViewById(R.id.et_input2);

        mEtBedNum.setText(mMultiMmkv.decodeString(Constant.KEY_BEDNUM));
        mEtDepartmentNum.setText(mMultiMmkv.decodeString(Constant.KEY_DEPARTMENTNUM));
        mConfirm = findViewById(R.id.tv_confirm);
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                mMultiMmkv.encode(Constant.KEY_BEDNUM, mEtBedNum.getEditableText().toString().trim());
                mMultiMmkv.encode(Constant.KEY_DEPARTMENTNUM, mEtDepartmentNum.getEditableText().toString().trim());
                Toast.makeText(this, "设置成功！", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }
}
package com.medbit.medbit_manage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.medbit.lib_base.base.BaseActivity;
import com.medbit.lib_base.constants.Constant;
import com.medbit.medbit_manage.R;
import com.medbit.medbit_manage.view.ClearEditText;
import com.tencent.mmkv.MMKV;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ClearEditText mClearEditText;
    private TextView mTvConfirm;
    private Button mBtnClear;
    private MMKV mMultiMmkv;

    private long mTempTime;//临时时间
    private int mClickNum;//点击次数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mMultiMmkv = MMKV.mmkvWithID(Constant.MMAPID, MMKV.MULTI_PROCESS_MODE);
        initViews();
        initListener();
    }

    private void initViews() {
        mClearEditText = findViewById(R.id.et_input);
        mTvConfirm = findViewById(R.id.tv_confirm);
        mBtnClear = findViewById(R.id.btn_clear);
        if (TextUtils.isEmpty(mMultiMmkv.decodeString(Constant.KEY_PSW, ""))) {
            mClearEditText.setHint("请设置管理员密码");
        } else {
            mClearEditText.setHint("请输入管理员密码");
        }
    }

    private void initListener() {
        mTvConfirm.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                if (!TextUtils.isEmpty(mClearEditText.getEditableText().toString().trim())) {
                    if (TextUtils.isEmpty(mMultiMmkv.decodeString(Constant.KEY_PSW))) { //没设置过密码
                        boolean isSuccess = mMultiMmkv.encode(Constant.KEY_PSW, mClearEditText.getEditableText().toString().trim());
                        if (isSuccess) {
                            Toast.makeText(this, "设置成功！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, SettingActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "设置失败，请重新设置！", Toast.LENGTH_SHORT).show();
                        }
                    } else { //设置过密码
                        if (TextUtils.equals(mClearEditText.getEditableText().toString().trim(), mMultiMmkv.decodeString(Constant.KEY_PSW))) {
                            Intent intent = new Intent(this, SettingActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "密码不正确，请重新输入！", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "请输入管理员密码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_clear:
                fastClick();
                break;
            default:
                break;
        }
    }

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
                boolean isSuccess = mMultiMmkv.encode(Constant.KEY_PSW, "");
                if (isSuccess) {
                    Toast.makeText(this, "密码已清除，请重新设置密码", Toast.LENGTH_SHORT).show();
                    mClearEditText.setHint("请设置管理员密码");
                }
            }
        }
    }
}
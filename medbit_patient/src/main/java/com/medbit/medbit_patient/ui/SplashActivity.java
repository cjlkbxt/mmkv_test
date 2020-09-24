package com.medbit.medbit_patient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.medbit.lib_base.base.BaseActivity;
import com.medbit.lib_base.constants.Constant;
import com.medbit.medbit_patient.R;
import com.tencent.mmkv.MMKV;

import java.util.List;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        requestAll(new OnPermissionsResultListener() {
            @Override
            public void OnSuccess() {
                MMKV.initialize(Constant.ROOT_DIR);
                Intent intent = new Intent(SplashActivity.this, WebviewActivity.class);
                startActivity(intent);
                finish();
            }


            @Override
            public void OnFail(List<String> failedPermissionList) {
                Toast.makeText(SplashActivity.this, "患者端需要请求存储权限", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
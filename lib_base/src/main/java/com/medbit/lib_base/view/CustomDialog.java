package com.medbit.lib_base.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class CustomDialog extends Dialog {
    private Context mContext;
    private View mContentView;
    private int mGravity;
    private boolean mCanCancelable;
    private int mHeight;
    private int mWidth;
    private int mAnimation;
    private int mPadding;


    public CustomDialog(Builder builder) {
        super(builder.context);
        this.mContext = builder.context;
        this.mHeight = builder.height;
        this.mWidth = builder.width;
        this.mCanCancelable = builder.canCancelable;
        this.mGravity = builder.gravity;
        this.mContentView = builder.contentView;
        this.mAnimation = builder.animation;
    }

    private CustomDialog(Builder builder, int style) {
        super(builder.context, style);
        this.mContext = builder.context;
        this.mHeight = builder.height;
        this.mWidth = builder.width;
        this.mCanCancelable = builder.canCancelable;
        this.mGravity = builder.gravity;
        this.mContentView = builder.contentView;
        this.mAnimation = builder.animation;
        this.mPadding = builder.padding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mContentView);
        // 获得窗体对象
        Window window = getWindow();
        // 设置窗体的对齐方式
        window.setGravity(mGravity);
        // 设置窗体动画
        if(mAnimation != -1){
            window.setWindowAnimations(mAnimation);
        }
        // 设置窗体的padding
        if(mPadding != -1){
            window.getDecorView().setPadding(mPadding, mPadding, mPadding, mPadding);
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = mWidth;
        lp.height = mHeight;
        window.setAttributes(lp);
        // 设置外部点击 取消dialog
        setCancelable(mCanCancelable);
    }

    @Override
    public void dismiss() {
        View view = getCurrentFocus();
        if(view instanceof TextView){
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        super.dismiss();
    }

    public static final class Builder {

        private Context context;
        private View contentView;
        private int style = -1;
        private int gravity;
        private boolean canCancelable;
        private int height;
        private int width;
        private int animation = -1;
        private int padding = -1;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentView(int resView) {
            contentView = LayoutInflater.from(context).inflate(resView, null);
            return this;
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder setStyle(int style) {
            this.style = style;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setCanCancelable(boolean canCancelable) {
            this.canCancelable = canCancelable;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setWindowAnimations(int animation) {
            this.animation = animation;
            return this;
        }

        public Builder setPadding(int padding) {
            this.padding = padding;
            return this;
        }

        public Builder setTitle(int viewRes, String title) {
            ((TextView)contentView.findViewById(viewRes)).setText(title);
            return this;
        }

        public Builder setContent(int viewRes, String content) {
            ((TextView)contentView.findViewById(viewRes)).setText(content);
            return this;
        }

        public Builder setContent(int viewRes, SpannableStringBuilder content) {
            ((TextView)contentView.findViewById(viewRes)).setText(content);
            return this;
        }

        public Builder setCustomContent1(int viewRes, String content) {
            ((TextView)contentView.findViewById(viewRes)).setText(content);
            return this;
        }

        public Builder setCustomContent2(int viewRes, String content) {
            ((TextView)contentView.findViewById(viewRes)).setText(content);
            return this;
        }

        public Builder setFirstBtnText(int viewRes, String content) {
            ((TextView)contentView.findViewById(viewRes)).setText(content);
            return this;
        }

        public Builder setSecondBtnText(int viewRes, String content) {
            ((TextView)contentView.findViewById(viewRes)).setText(content);
            return this;
        }

        public Builder setFirstClickListener(int viewRes, View.OnClickListener listener) {
            contentView.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

        public Builder setSecondClickListener(int viewRes, View.OnClickListener listener) {
            contentView.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }


        public CustomDialog build() {
            if (style != -1) {
                return new CustomDialog(this, style);
            } else {
                return new CustomDialog(this);
            }
        }
    }

}

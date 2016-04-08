package com.dkt.basemvc.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.dkt.basemvc.http.MyHttpCycleContext;
import com.dkt.basemvc.inter.DialogControl;
import com.dkt.basemvc.utils.AppManager;
import com.dkt.basemvc.utils.DialogHelp;

import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpTaskHandler;

/**
 *
 * Created by Administrator on 2016/4/7.
 */
public class BaseActivity extends Activity implements DialogControl, MyHttpCycleContext {

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private boolean isVisible;
    private ProgressDialog waitDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //添加activity到activity管理列表中
        AppManager.getAppManager().addActivity(this);

        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        //通过注解绑定控件
        ButterKnife.bind(this);

        init(savedInstanceState);

        isVisible = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
    }

    protected void init(Bundle savedInstanceState) {

    }

    protected int getLayoutId() {
        return 0;
    }

    protected void onBeforeSetContentLayout() {

    }

    @Override
    public void hideWaitDialog() {
        if (isVisible && waitDialog != null) {
            try {
                waitDialog.dismiss();
                waitDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ProgressDialog showWaitDialog() {
        return showWaitDialog("加载中...");
    }

    @Override
    public ProgressDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public ProgressDialog showWaitDialog(String message) {
        if (isVisible) {
            if (waitDialog == null) {
                waitDialog = DialogHelp.getWaitDialog(this, message);
            } else {
                waitDialog.setMessage(message);
                waitDialog.show();
            }
            return waitDialog;
        }
        return null;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
}

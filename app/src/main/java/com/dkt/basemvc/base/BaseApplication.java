package com.dkt.basemvc.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.dkt.basemvc.Constants;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;
import cn.finalteam.okhttpfinal.Part;
import okhttp3.Headers;
import okhttp3.Interceptor;

/**
 * Created by Administrator on 2016/4/7.
 */
public class BaseApplication extends Application {

    private static Context context;
    private static Resources resources;

    @Override
    public void onCreate() {
        super.onCreate();
        initOkhttpFinal();

        context = getApplicationContext();
        resources = context.getResources();
    }

    private void initOkhttpFinal() {
        List<Part> commonParams = new ArrayList<>();
        Headers commonHeaders = new Headers.Builder().build();

        List<Interceptor> interceptorList = new ArrayList<>();
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder()
                .setCommenParams(commonParams)
                .setCommenHeaders(commonHeaders)
                .setTimeout(Constants.REQ_TIMEOUT)
                .setInterceptors(interceptorList)
                .setDebug(true);
        OkHttpFinal.getInstance().init(builder.build());

    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) context;
    }

    public static Resources resources() {
        return resources;
    }
}

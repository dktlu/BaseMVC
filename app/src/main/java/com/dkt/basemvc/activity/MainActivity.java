package com.dkt.basemvc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dkt.basemvc.R;
import com.dkt.basemvc.utils.AppUtils;
import com.dkt.basemvc.utils.T;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(getApplicationContext(), AppUtils.getAppName(getApplicationContext()));
            }
        });
    }
}

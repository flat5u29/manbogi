package com.example.gwer.manbogi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Data extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        final DBHelper dbHelper
                = new DBHelper(getApplicationContext(), "MANBORECORD.db", null, 1);

        textView = (TextView)findViewById(R.id.textView);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        // 출력될 포맷 설정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

        textView.setText(dbHelper.getResult());


    }


}

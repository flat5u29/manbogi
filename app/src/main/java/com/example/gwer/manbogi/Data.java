package com.example.gwer.manbogi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Data extends AppCompatActivity {

    TextView textView;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        final DBHelper dbHelper
                = new DBHelper(getApplicationContext(), "MANBORECORD.db", null, 1);

        textView = (TextView)findViewById(R.id.textView);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        // 출력될 포맷 설정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        final String getTime = simpleDateFormat.format(date);

        textView.setText(dbHelper.getResult());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper.delete();

                textView.setText(dbHelper.getResult());

                onResume();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=getIntent();
        setResult(1,intent);
        finish();
    }
}

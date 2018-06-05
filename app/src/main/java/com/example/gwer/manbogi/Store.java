package com.example.gwer.manbogi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Store extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        btn = (Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cost = 30;
                MainActivity mainActivity = new MainActivity();
                if(mainActivity.coin >= cost) {
                    mainActivity.coin = mainActivity.coin - cost;
                    Toast.makeText(getApplicationContext(),"구매되었습니다. 남은코인 : "+mainActivity.coin, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "코인이 부족합니다."+mainActivity.coin, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

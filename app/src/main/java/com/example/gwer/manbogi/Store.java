package com.example.gwer.manbogi;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Store extends AppCompatActivity {

    int cost = 0;
    Button btn30, btn60, btn90, buy;
    TextView totalCost;

    LinearLayout itemLayout;

    Button[] items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        itemLayout = (LinearLayout)findViewById(R.id.itemLayout);

        btn30 = (Button) findViewById(R.id.btn30);
        btn60 = (Button) findViewById(R.id.btn60);
        btn90 = (Button) findViewById(R.id.btn90);
        buy = (Button) findViewById(R.id.buy);
        totalCost = (TextView) findViewById(R.id.totalCost);

        btn30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cost = cost + 30;
                totalCost.setText("총 가격 : ");
                totalCost.append("" + cost);
            }
        });
        btn60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cost = cost + 60;
                totalCost.setText("총 가격 : ");
                totalCost.append("" + cost);
            }
        });
        btn90.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cost = cost + 90;
                totalCost.setText("총 가격 : ");
                totalCost.append("" + cost);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                if (MainActivity.coin >= cost) {
                    intent.putExtra("cost", cost);
                } else {
                    Toast.makeText(getApplicationContext(), "코인이 부족합니다.", Toast.LENGTH_SHORT).show();
                }

                setResult(1, intent);
                finish();
            }
        });


    }
    //백버튼 눌렀을 때
    public void onBackPressed() {
        Intent intent=getIntent();
        intent.putExtra("cost",cost);
        setResult(1,intent);
        finish();

//  super.onBackPressed();
    }
}

package com.example.gwer.manbogi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    Intent manboService;
    BroadcastReceiver receiver;

    boolean flag = true;
    String serviceData;
    TextView countText, cointxt;
    Button playingBtn, exchange, menu;

    int stepCount, coin;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manboService = new Intent(this, StepCheckService.class);
        receiver = new PlayingReceiver();

        cointxt = (TextView) findViewById(R.id.cointxt);
        countText = (TextView) findViewById(R.id.stepText);
        playingBtn = (Button) findViewById(R.id.btnStopService);
        exchange = (Button) findViewById(R.id.exchange);
        menu = (Button) findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.menu1, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        Intent intent = new Intent();

                        switch (id){
                            case R.id.menu1 : // 스탯
                                break;

                            case R.id.menu2 : // 아이템창
                                break;

                            case R.id.menu3 : // 상점
                                intent = new Intent(MainActivity.this, Store.class);
                                break;

                            case R.id.menu4 : // 통계
                                break;

                            case R.id.menu5 : // 옵션
                                break;
                        }
                        startActivity(intent);
                        return true;
                    }
                });
            }
        });


        // TODO Auto-generated method stub
        try {

            IntentFilter mainFilter = new IntentFilter("com.example.manbogi");

            registerReceiver(receiver, mainFilter);
            startService(manboService);
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


         playingBtn.setOnClickListener(new View.OnClickListener() {


        public void onClick(View v) {
        // TODO Auto-generated method stub
        try {

            unregisterReceiver(receiver);

            stopService(manboService);

            // txtMsg.setText("After stoping Service:\n"+service.getClassName());
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


         }
        });

        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coin = coin + stepCount;
                stepCount = 0;
                cointxt.setText("코인 : " + coin);
                countText.setText("걸음 : " + stepCount);
                StepValue.Step = 0;
            }
        });

    }

    class PlayingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("PlayingReceiver", "IN");
           // serviceData = intent.getStringExtra("stepService");
            stepCount+=intent.getIntExtra("stepService",0);
            countText.setText("걸음 : " + stepCount);


        }
    }

}

package com.example.gwer.manbogi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    Intent manboService;
    BroadcastReceiver receiver;

    boolean flag = true;
    String serviceData;
    TextView countText, cointxt;
    Button playingBtn, exchange;

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

        playingBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (flag) {
                    // TODO Auto-generated method stub
                    try {

                        IntentFilter mainFilter = new IntentFilter("make.a.yong.manbo");

                        registerReceiver(receiver, mainFilter);
                        startService(manboService);
                    } catch (Exception e) {
                        // TODO: handle exception
                        Toast.makeText(getApplicationContext(), e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {

                    playingBtn.setText("Go !!");

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

                flag = !flag;

            }
        });

        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coin = coin+stepCount;
                stepCount = 0;
                cointxt.setText("코인 : "+coin);
                countText.setText("걸음 : "+stepCount);
                StepValue.Step = 0;
            }
        });

    }

    class PlayingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("PlayignReceiver", "IN");
            serviceData = intent.getStringExtra("stepService");
            countText.setText("걸음 : " + serviceData);
            stepCount = Integer.parseInt(serviceData);

        }
    }

}

package com.example.gwer.manbogi;

import android.app.AlertDialog;
import android.app.Dialog;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {

    Intent manboService;
    BroadcastReceiver receiver;

    boolean flag = true;
    TextView countText, cointxt;
    Button stopBtn, exchange, menu;

    int stepCount, coin, totalStepCount;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manboService = new Intent(this, StepCheckService.class);
        receiver = new PlayingReceiver();

        cointxt = (TextView) findViewById(R.id.cointxt);
        countText = (TextView) findViewById(R.id.stepText);
        stopBtn = (Button) findViewById(R.id.btnStopService);
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

                        switch (id) {
                            case R.id.menu1: // 스탯
                                break;

                            case R.id.menu2: // 아이템창
                                break;

                            case R.id.menu3: // 상점
                                intent = new Intent(MainActivity.this, Store.class);
                                break;

                            case R.id.menu4: // 통계
                                intent = new Intent(MainActivity.this, Data.class);
                                break;

                            case R.id.menu5: // 옵션
                                break;
                        }
                        startActivity(intent);
                        return true;
                    }
                });
            }
        });


        // 서비스 시작
        try {

            IntentFilter mainFilter = new IntentFilter("com.example.manbogi");

            registerReceiver(receiver, mainFilter);
            startService(manboService);
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


        //stop버튼 누르면 서비스 종료
       stopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {



                    stopService(manboService);
                    if(!countText.getText().equals("걸음 : 0")){ //걸음수 0이 아닐 때
                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("환전하지 않은 걸음이 있습니다.");
                        builder.setTitle("알림");
                        builder.setPositiveButton("확인",null);
                        builder.show();
                    }
                    else
                        unregisterReceiver(receiver);

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

                long now = System.currentTimeMillis();

                Date dat = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String getTime = sdf.format(dat);

                try{
//                    File files = new File("");
//                    //파일 유무를 확인합니다.
//                    if(files.exists()==true) {
//                    //파일이 있을시
//                        FileOutputStream fos = openFileOutput("data.txt", Context.MODE_APPEND);
//                        String text = getTime+totalStepCount;
//                        fos.write(text.getBytes());
//                        fos.close();
//                    } else {
                    //파일이 없을시
                        FileOutputStream fos = openFileOutput("data.txt", Context.MODE_PRIVATE);
                        String text = getTime+totalStepCount;
                        fos.write(text.getBytes());
                        fos.close();
//                    }
                }catch(IOException e){

                }
                stepCount = 0;
                cointxt.setText("코인 : " + coin);
                countText.setText("걸음 : " + stepCount);
            }
        });

    }

    class PlayingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            totalStepCount += intent.getIntExtra("stepService", 0);
            stepCount += intent.getIntExtra("stepService", 0);
            countText.setText("걸음 : " + stepCount);


        }
    }

}

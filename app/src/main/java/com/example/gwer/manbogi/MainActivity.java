package com.example.gwer.manbogi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
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
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {
    public static final String ROOT_DIR = "/data/data/com.example.gwer.manbogi/databases";
    Intent manboService;
    BroadcastReceiver receiver;
    private long backKeyPressedTime = 0;

    boolean flag = true;
    TextView countText, cointxt;
    Button stopBtn, exchange, menu;

    static int stepCount; //환전하기 전 걸음수
    static int totalStepCount; //오늘의 총걸음수
    static int coin;
    static int hunger, dirty, boring, love;
    long now;
    Date dat;
    SimpleDateFormat sdf;
    String getTime;
    DBHelper dbHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cointxt = (TextView) findViewById(R.id.cointxt);
        countText = (TextView) findViewById(R.id.steptxt);
        stopBtn = (Button) findViewById(R.id.btnStopService);
        exchange = (Button) findViewById(R.id.exchange);
        menu = (Button) findViewById(R.id.menu);
        setDB(this);


        now = System.currentTimeMillis();
        dat = new Date(now);
        sdf = new SimpleDateFormat("yyyyMMdd");
        getTime = sdf.format(dat);

        dbHelper
                = new DBHelper(getApplicationContext(), "MANBORECORD.db", null, 1);
        totalStepCount = dbHelper.stepCount(getTime);

        stepCount=StepCheckService.stepCount;
        countText.setText("걸음 : "+stepCount);

        final SoundPool sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        manboService = new Intent(this, StepCheckService.class);
        receiver = new PlayingReceiver();


        // 프레퍼런스(앱을 종료했다가 실행해도 데이터가 남아있음)
        SharedPreferences pref = getSharedPreferences("pre", 0);
        SharedPreferences.Editor myEditor = pref.edit();

        coin = pref.getInt("coinCount", 0);
        cointxt.setText("코인 : "+coin);


        //메뉴버튼 클릭리스너
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
                                startActivityForResult(intent, 1);
                                break;

                            case R.id.menu4: // 통계

                                checkDBData();

                                intent = new Intent(MainActivity.this, Data.class);
                                startActivityForResult(intent, 2);
                                break;

                            case R.id.menu5: // 옵션
                                break;
                        }
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
                    if (!countText.getText().equals("걸음 : 0")) { //걸음수 0이 아닐 때
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("환전하지 않은 걸음이 있습니다.");
                        builder.setTitle("알림");
                        builder.setPositiveButton("확인", null);
                        builder.show();
                    } else
                        unregisterReceiver(receiver);

                    // txtMsg.setText("After stoping Service:\n"+service.getClassName());
                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }


            }
        });

        // 환전 소리
        final int exchangeSound = sp.load(this, R.raw.coin01, 1);

        exchange.setSoundEffectsEnabled(false);
        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(exchangeSound, 1, 1, 0, 0, 1f);

                coin = coin + stepCount;


                stepCount = 0;
                cointxt.setText("코인 : " + coin);
                countText.setText("걸음 : " + stepCount);
            }
        });

    } // end of OnCreate


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //상점에서 산 물건 값 가져온 후 코인에서 빼기
        if (requestCode == 1) { //requestCode가 1이면 상점에서 돌아온 것.
            int cost = data.getIntExtra("cost", 0);
            coin = coin - cost;
            cointxt.setText("코인 : " + coin);
        }

    }

    //서비스에서 보내는 값가져와서 걸음수에 더해주기
    class PlayingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            totalStepCount += intent.getIntExtra("stepService", 0);
            stepCount = intent.getIntExtra("stepService", 0);
            countText.setText("걸음 : " + stepCount);
            Log.i("MyTag", "MainActivity: " + stepCount);

        }
    }

    //앱이 일시정지되면 프리퍼런스에 현재 텍스트뷰들의 값 저장
    public void onPause() {
        super.onPause();

        SharedPreferences pref = getSharedPreferences("pre", 0);
        SharedPreferences.Editor myEditor = pref.edit();
        myEditor.putInt("coinCount", coin);

        myEditor.commit();
    }

    //백버튼누르면 "한 번 더 누르면 종료됩니다"토스트 뜨게 하기
    public void onBackPressed() {


        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            checkDBData();
            finish();
        }
//  super.onBackPressed();
    }

    //오늘 날짜 데이터 있는지
    public void checkDBData() {
        if (!getTime.equals(dbHelper.select(getTime))) {
            dbHelper.insert(getTime, totalStepCount, coin);

        } else {

            dbHelper.update(getTime, totalStepCount, coin);

        }
    }

    //빈 데이터베이스 생성 후 assets폴더에 있는 DB를 복사해서 빈 데이터베이스에 붙여넣기
    public static void setDB(Context ctx) {
        File folder = new File(ROOT_DIR);
        if (folder.exists()) {
        } else {
            folder.mkdirs();
        }
        AssetManager assetManager = ctx.getResources().getAssets();
        File outfile = new File(ROOT_DIR + "ManbogiDB.sqlite");
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;
        try {
            is = assetManager.open("ManbogiDB.sqlite", AssetManager.ACCESS_BUFFER);
            filesize = is.available();
            if (outfile.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            } else {
            }
        } catch (IOException e) {
        }
    }

}

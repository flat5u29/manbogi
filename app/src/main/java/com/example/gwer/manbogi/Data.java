package com.example.gwer.manbogi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

public class Data extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        textView = (TextView)findViewById(R.id.textView);
    }

    public void load(){
        try{
            FileInputStream fis = openFileInput("data.txt");
            StringBuffer sb = new StringBuffer();
            byte data[] = new byte[255];
            int n = 0;
            while((n=fis.read(data))!=-1){
                sb.append(new String(data));
            }
            textView.setText(sb.toString());
            fis.close();
        }catch(IOException e){

        }
    }

    public void ccc(View view){
        load();
    }
}

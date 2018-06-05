package com.example.gwer.manbogi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Store extends AppCompatActivity {

    int count;
    LinearLayout[] items = new LinearLayout[count];
    TextView[] itemNames = new TextView[count], itemCosts= new TextView[count], itemEffects= new TextView[count];
    Button[] buttons = new Button[count];
    GridLayout itemLayout;
    LinearLayout selectList;

    LinearLayout item1;
    TextView itemName, itemCost, itemEffect;
    ImageView itemImg1;
    Button select1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        itemLayout = (GridLayout)findViewById(R.id.itemLayout);
        selectList = (LinearLayout)findViewById(R.id.selectList);
        item1 = (LinearLayout)findViewById(R.id.item1);
        itemImg1 = (ImageView)findViewById(R.id.itemImg1);
        itemName = (TextView)findViewById(R.id.itemName);
        itemCost = (TextView)findViewById(R.id.itemCost);
        itemEffect = (TextView)findViewById(R.id.itemEffect);
        select1 = (Button)findViewById(R.id.select1);


        itemImg1.setImageResource(R.drawable.items);
        itemName.setText("테스트 이름");
        itemCost.setText("테스트 가격");
        itemEffect.setText("테스트 효과");

        item1.addView(itemImg1);
        item1.addView(itemName);
        item1.addView(itemCost);
        item1.addView(itemEffect);

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectList.addView(select1);
                select1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectList.removeView(select1);
                    }
                });
            }
        });

        itemLayout.addView(item1);

//        for(int i = 0; i < items.length; i++){
//            items[i] = (LinearLayout) findViewById(R.id.item1+i);
//            itemNames[i] = (TextView) findViewById(R.id.itemName+i);
//            itemCosts[i] = (TextView) findViewById(R.id.itemCost+i);
//            itemEffects[i] = (TextView) findViewById(R.id.itemEffect+i);
//            buttons[i] = (Button) findViewById(R.id.select1+i);
//
//            items[i].addView(itemNames[i]);
//            items[i].addView(itemCosts[i]);
//            items[i].addView(itemEffects[i]);
//
//            itemNames[i].setText(i+"번째 아이템");
//
//            itemLayout.addView(items[i]);
//
//            // 아이템이름, 가격, 효과 텍스트 DB에서 가져와ㅏㅏ서 너ㅓㅓㅓㅓㅎ기
//
//            items[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    selectList.addView(buttons[v.getId()]);
//
//                    buttons[v.getId()].setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            selectList.removeView(buttons[v.getId()]);
//                        }
//                    });
//                }
//            });
//        }

    }
}

package org.techtown.cap2.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.cap2.BluetoothThread;
import org.techtown.cap2.BoomGameActivity;
import org.techtown.cap2.R;

import java.util.Random;

public class BoomGame_setting extends AppCompatActivity {
    private
    Button  plusButton,Button4;
    private TextView tv_count,minusButton;
    private int count = 2;
    Context context;
    private BluetoothThread bluetoothThread;

    private Button backBtn;

    @SuppressLint({"WrongViewCast", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom_game_setting);
        context = this;

        bluetoothThread = BluetoothThread.getInstance(this);
        Button4 = findViewById(R.id.imageButton4);

        minusButton = findViewById(R.id.minusBtn);
        plusButton = findViewById(R.id.plusbtn);
        tv_count = findViewById(R.id.tv_count);
        backBtn = findViewById(R.id.backBtn);
        tv_count.setText(String.valueOf(2));
        tv_count.setFocusable(false);
        tv_count.setClickable(false);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 2) {
                    count--;
                    tv_count.setText(String.valueOf(count));
                }
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count < 10) {
                    count++;
                    tv_count.setText(String.valueOf(count));
                }
            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                //int num1 = random.nextInt(count) + 1;
                sendDataToBluetooth3("0", "0", "0", String.valueOf(count));
                Intent intent = new Intent(BoomGame_setting.this, BoomGameActivity.class);
                startActivity(intent);
            }
        });


    }
    private void sendDataToBluetooth3(String message1, String message2, String message3, String message4) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData3(message1, message2, message3, message4);
    }
}
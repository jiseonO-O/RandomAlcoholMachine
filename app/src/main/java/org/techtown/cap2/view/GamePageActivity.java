package org.techtown.cap2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import org.techtown.cap2.BluetoothThread;
import org.techtown.cap2.BoomGameActivity;
import org.techtown.cap2.R;

import java.util.Random;

public class GamePageActivity extends AppCompatActivity {

    private Button rulletButton, sonByungHobutton, rollBombbutton, backButton;
    private String message1,message2,message3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        Random random = new Random();
        int num1 = random.nextInt(6); // Generates a random number between 0 and 5
        int num2 = random.nextInt(Math.max(0, 10 - num1 - 4)); // Generates a random number between 0 and (10 - num1 - 4)
        int num3 = 10 - num1 - num2;

        if (num3 < 0) {
            num1 = 0;
            num2 = 0;
            num3 = 0;
        }
        message1 = String.valueOf(num1);
        message2 = String.valueOf(num2);
        message3 = String.valueOf(num3);
        backButton = findViewById(R.id.back);
        rollBombbutton = findViewById(R.id.roll_bomb);
        rollBombbutton.setOnClickListener(view -> {
            Intent intent = new Intent(GamePageActivity.this, BoomGame_setting.class);
            startActivity(intent);
        });

        backButton.setOnClickListener(view -> {
            finish();
        });

        rulletButton = findViewById(R.id.rullet_button);
        sonByungHobutton = findViewById(R.id.son_byung_ho_button);


        rulletButton.setOnClickListener(view -> {
            showDialog01(1);
        });

        sonByungHobutton.setOnClickListener(view -> {
            showDialog01(2);
        });


    }

    private void showDialog01(int buttonIndex) {
        Dialog dialog01 = new Dialog(this);
        dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog01.setContentView(R.layout.activity_game_dialog);
        dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog01.show();

        Button noBtn = dialog01.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(view -> {
            // '아니오' 버튼 클릭 시 동작 구현
            if (buttonIndex == 1) {
                // 버튼 1에 대한 처리
                startActivity(new Intent(this, RouletteActivity.class));
            } else if (buttonIndex == 2) {
                // 버튼 2에 대한 처리
                sendDataToBluetooth(message1, message2, message3);
                startActivity(new Intent(this, Game2Activity.class));
            }
            dialog01.dismiss();
        });

        Button yesBtn = dialog01.findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(view -> {
            // '예' 버튼 클릭 시 동작 구현
            Intent intent = new Intent(this, Drink2Activity.class);
            startActivity(intent);
            dialog01.dismiss();
        });
    }

    private void sendDataToBluetooth(String message1, String message2, String message3) {
        // BluetoothThread 객체의 sendData 메서드 호출
        BluetoothThread.getInstance(this).sendData(message1, message2, message3);
    }
    private void sendDataToBluetooth2(String message1) {
        // BluetoothThread 객체의 sendData 메서드 호출
        BluetoothThread.getInstance(this).sendData2(message1);
    }
}

package org.techtown.cap2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.techtown.cap2.view.BoomGame_setting;
import org.techtown.cap2.view.GamePageActivity;

public class BoomGameActivity extends AppCompatActivity {

    static ImageView imageView;
//    private Button changeImageButton;
//    private int[] imageResources = {R.drawable.boom1, R.drawable.boom2, R.drawable.boom3};
//    private int currentImageIndex = 0;
    Button backBtn;
    private BluetoothThread bluetoothThread;
    Context context;
    private static AlertDialog confirmationDialog;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boomb_game);
        context = this;

        bluetoothThread = BluetoothThread.getInstance(this);
//        bluetoothThread.setOnNextCallBack(new BluetoothThread.OnNextCallBack() {
//            @Override
//            public void onNextButton() {
//                onBackPressed();
//            }
//        });
        showConfirmationDialog();
        imageView = findViewById(R.id.view);

        backBtn= findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                sendDataToBluetooth2("c");
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                sendDataToBluetooth2("c");
//            }
//        });
//    }

    //    public static void onNextButton() {
//        performButtonClick();
//    }
//
//    private static void performButtonClick() {
//        if (confirmationDialog != null && confirmationDialog.isShowing()) {
//            confirmationDialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
//        }
//    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("게임 방법")
                .setMessage("이 게임은 리모컨으로 진행됩니다.")
                .setPositiveButton("시작하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendDataToBluetooth2("r");
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        bluetoothThread.setDialog(dialog);
        dialog.show();
        
    }
    private void sendDataToBluetooth2(String message1) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData2(message1);
    }


}

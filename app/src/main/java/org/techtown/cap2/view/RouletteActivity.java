package org.techtown.cap2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import org.techtown.cap2.BluetoothThread;
import org.techtown.cap2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteActivity extends AppCompatActivity {
    private LuckyWheel luckyWheel;
    private String message1, message2, message3;
    private List<WheelItem> wheelItems;
    private BluetoothThread bluetoothThread;

    Context context;

    private String[] colors = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FFA500", "#800080", "#FFC0CB", "#808080", "#000000", "#FFC0CB"};
    private String[] animals = {"강아지", "고양이", "사자", "기린", "호랑이", "팬더", "드래곤", "토끼", "오리", "돼지"};

    public void sendDataToBluetooth2(String message1) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData2(message1);
    }

    public void sendDataToBluetooth3(String message1, String message2, String message3, String message4) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData3(message1, message2, message3, message4);
    }

    public void sendDataToBluetooth(String message1, String message2, String message3) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData(message1, message2, message3);
    }

    public void sendDataToBluetooth4(String message1, String message2, String message3, String message4) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData3(message1, message2, message3, message4);
    }

    String point;

    // AlertDialog 및 AlertDialog.Builder 객체를 클래스 멤버로 선언
    private AlertDialog alertDialog;
    private AlertDialog.Builder ad;
    private EditText input;

    private void setupPlayerCountDialog() {
        ad = new AlertDialog.Builder(RouletteActivity.this);
        ad.setIcon(R.drawable.roulette5);
        ad.setTitle("룰렛 인원 설정 2 ~ 10 사이에 값만 입력해주세요 \n (영어,한글,특수문자는 입력 시 작동이 안됩니다.");
        ad.setMessage("참여하는 인원을 적어주세요.");
        input = new EditText(RouletteActivity.this);
        ad.setView(input);
        ad.setCancelable(false);

        // AlertDialog를 생성합니다.
        alertDialog = ad.create();

        // 확인 버튼을 처음에 비활성화합니다.


        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString().trim();

                // 입력된 텍스트가 숫자인지 확인합니다.
                if (!isValidNumber(inputText)) {


                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    int number = Integer.parseInt(inputText);
                    if (number >= 2 && number <= 10) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    } else {

                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputText = input.getText().toString().trim();

                if (inputText.isEmpty()) {
                    // 아무 것도 입력되지 않았을 때 토스트 메시지를 표시하고 창을 닫지 않습니다.

                } else if (!isValidNumber(inputText)) {
                    // 유효하지 않은 숫자가 입력되었을 때 토스트 메시지를 표시하고 창을 닫지 않습니다.

                } else {
                    int count = Integer.parseInt(inputText);
                    wheelItems = new ArrayList<>();

                    Drawable d = getResources().getDrawable(R.drawable.roulette5, null);
                    Bitmap bitmap = drawableToBitmap(d);


                    // count가 colors와 animals 배열 길이를 초과하지 않도록 수정
                    int maxCount = Math.min(count, Math.min(colors.length, animals.length));
                    for (int i = 0; i < maxCount; i++) {
                        WheelItem wheelItem = new WheelItem(Color.parseColor(colors[i]), bitmap, animals[i]);
                        wheelItems.add(wheelItem);
                    }

                    luckyWheel.addWheelItems(wheelItems);
                    alertDialog.dismiss();
                }


            }

            private boolean isValidNumber(String input) {
                try {
                    Integer.parseInt(input);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        // AlertDialog를 보여줍니다.
        alertDialog.show();
    }


    private boolean isValidNumber(String inputText) {
        // 입력값이 비어있지 않고, 숫자로 변환 가능한 경우에만 true를 반환합니다.
        return !inputText.isEmpty() && inputText.matches("\\d+");
    }

    /**
     * 데이터 담기
     */
    private void generateWheelItems() {
        wheelItems = new ArrayList<>();

        Drawable d = getResources().getDrawable(R.drawable.roulette5, null);
        Bitmap bitmap = drawableToBitmap(d);

        wheelItems.add(new WheelItem(Color.parseColor("#F44336"), bitmap, "기 린"));
        wheelItems.add(new WheelItem(Color.parseColor("#E91E63"), bitmap, "강 아 지"));
        wheelItems.add(new WheelItem(Color.parseColor("#9C27B0"), bitmap, "고 양 이"));
        wheelItems.add(new WheelItem(Color.parseColor("#3F51B5"), bitmap, "드 래 곤"));
        wheelItems.add(new WheelItem(Color.parseColor("#1E88E5"), bitmap, "사  자"));
        wheelItems.add(new WheelItem(Color.parseColor("#009688"), bitmap, "토 끼"));

        luckyWheel.addWheelItems(wheelItems);
    }

    private void showToast(String message) {
        Toast.makeText(RouletteActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);
        context = this;


        bluetoothThread = BluetoothThread.getInstance(this);
        luckyWheel = findViewById(R.id.luck_wheel);

        setupPlayerCountDialog();
        generateWheelItems();

        Button backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button restartButton = findViewById(R.id.restart_btn);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setupPlayerCountDialog();


            }
        });


        if (getIntent().hasExtra("num1") && getIntent().hasExtra("num2") && getIntent().hasExtra("num3")) {
            message1 = getIntent().getStringExtra("num1");
            message2 = getIntent().getStringExtra("num2");
            message3 = getIntent().getStringExtra("num3");
        } else {
            Random random = new Random();
            int num1 = random.nextInt(10); // Generates a random number between 0 and 5
            int num2 = random.nextInt(10 - num1); // Generates a random number between 0 and (10 - num1 - 4)
            int num3 = 10 - num1 - num2;

            int numrandom3 = random.nextInt(3);

            if (numrandom3 == 0) {
                message1 = String.valueOf(num1);
                message2 = String.valueOf(num2);
                message3 = String.valueOf(num3);
            } else if (numrandom3 == 1) {
                message1 = String.valueOf(num2);
                message2 = String.valueOf(num1);
                message3 = String.valueOf(num3);
            } else {
                message1 = String.valueOf(num3);
                message2 = String.valueOf(num1);
                message3 = String.valueOf(num2);
            }
        }

        Button start = findViewById(R.id.spin_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().hasExtra("num1") && getIntent().hasExtra("num2") && getIntent().hasExtra("num3")) {
                    message1 = getIntent().getStringExtra("num1");
                    message2 = getIntent().getStringExtra("num2");
                    message3 = getIntent().getStringExtra("num3");
                } else {
                    Random random = new Random();
                    int num1 = random.nextInt(10); // Generates a random number between 0 and 5
                    int num2 = random.nextInt(10 - num1); // Generates a random number between 0 and (10 - num1 - 4)
                    int num3 = 10 - num1 - num2;

                    int numrandom3 = random.nextInt(3);

                    if (numrandom3 == 0) {
                        message1 = String.valueOf(num1);
                        message2 = String.valueOf(num2);
                        message3 = String.valueOf(num3);
                    } else if (numrandom3 == 1) {
                        message1 = String.valueOf(num2);
                        message2 = String.valueOf(num1);
                        message3 = String.valueOf(num3);
                    } else {
                        message1 = String.valueOf(num3);
                        message2 = String.valueOf(num1);
                        message3 = String.valueOf(num2);
                    }

                }

                sendDataToBluetooth(message1, message2, message3);
                Random random = new Random();
                point = String.valueOf(random.nextInt(Integer.parseInt(String.valueOf(input.getText()))) + 1);
                Log.d("Debug", "Random point: " + point); // 디버깅 로그

                luckyWheel.rotateWheelTo(Integer.parseInt(point));
            }
        });

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                Log.d("Debug", "onReachTarget called"); // 디버깅 로그

                // 랜덤 포인트를 사용하여 해당 포인트의 당첨자만을 표시
                int index = Integer.parseInt(point) - 1;
                //showWinnerDialog(animals[index]);
            }
        });

// showToast 메소드를 통해 토스트 메시지를 표시하는 함수 추가


    }


    /**
     * drawable -> bitmap
     *
     * @param drawable drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private void showWinnerDialog(String winnerName) {
        if (RouletteActivity.this.isFinishing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("축하합니다!")
                .setMessage("축하합니다! " + winnerName + "님!!!")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendDataToBluetooth2("g");
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
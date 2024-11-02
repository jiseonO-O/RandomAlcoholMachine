package org.techtown.cap2.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import org.techtown.cap2.BaseActivity;
import org.techtown.cap2.R;
import org.techtown.cap2.util.ViewflipperUtil;


public class MainActivity extends BaseActivity {
    private ViewFlipper viewflipper;
    private Button dbtn, gbtn, closeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initListener();
    }
    private void init() {
        dbtn = findViewById(R.id.Dbtn);
        gbtn = findViewById(R.id.Gbtn);
        closeBtn = findViewById(R.id.button5);
        viewflipper = findViewById(R.id.view);

        int images[] = {
                R.drawable.silla1,
                R.drawable.silla2
        };
        ViewflipperUtil viewFlipperUtil = new ViewflipperUtil(this, images);
        viewFlipperUtil.basicFlip(viewflipper);
    }

    private void initListener() {
        closeBtn.setOnClickListener(v -> {
            finishApp();
        });
        dbtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BeverMakingActivity.class);
            startActivity(intent);
        });
        gbtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GamePageActivity.class);
            startActivity(intent);
        });
        viewflipper.setOnClickListener(view -> {
            Intent vintetn = new Intent(Intent.ACTION_VIEW, Uri.parse("https://computer.silla.ac.kr/computer2016/"));
            startActivity(vintetn);
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishApp();
    }
}






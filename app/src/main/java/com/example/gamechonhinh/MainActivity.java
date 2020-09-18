package com.example.gamechonhinh;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView tvSorce, tvTime;
    private ImageView imgvInput, imgvOutput;
    private int score = 100, time_limit = 60, idImageInput, idImageOutput;
    private List<String> arrayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        imgvOutput.setOnClickListener(v -> changeActivity());
        arrayImage = Arrays.asList((getResources().getStringArray(R.array.list_img)));
        changeImageInput();
        startGame();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constant.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String s = data.getStringExtra(Constant.NAME_IMAGE);
            if (s != null) {
                idImageOutput = getResources().getIdentifier(s, Constant.DRAWABLE, getPackageName());
                imgvOutput.setImageResource(idImageOutput);
                if (idImageInput == idImageOutput) {
                    Toast.makeText(MainActivity.this, getString(R.string.Correct), Toast.LENGTH_LONG).show();
                    score += 10;
                    changeImageInput();
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.Incorrect), Toast.LENGTH_LONG).show();
                    score -= 10;
                }
            }
        } else if (requestCode == Constant.REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, getString(R.string.MainGameActivity_toast_back), Toast.LENGTH_SHORT).show();
            score -= 15;
        }
        if (score <= 0) {
            score = 0;
        }
        tvSorce.setText(score + "");
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void changeImageInput() {
        Collections.shuffle(arrayImage);
        idImageInput = getResources().getIdentifier(arrayImage.get(new Random().nextInt(17)), Constant.DRAWABLE, getPackageName());
        imgvInput.setImageResource(idImageInput);
    }

    private void anhXa() {
        tvSorce = findViewById(R.id.tv_score);
        tvTime = findViewById(R.id.tv_time_limit);
        imgvInput = findViewById(R.id.img_input);
        imgvOutput = findViewById(R.id.img_output);
    }

    private void startGame() {
        tvSorce.setText(score + "");
        tvTime.setText(showTime(time_limit));
        new CountDownTimer(2 * 60 * 1000, 1000) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTick(long millisUntilFinished) {
                if (time_limit == 0 || score <= 0) onFinish();
                tvTime.setText(showTime(time_limit--));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, getString(R.string.EndGame), Toast.LENGTH_SHORT).show();
                showDiagLog();
                cancel();
            }
        }.start();
    }

    private void showDiagLog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCanceledOnTouchOutside(false);
        TextView textView = dialog.findViewById(R.id.tv_score);
        textView.setText(" : " + score);
        ImageButton btnExit = dialog.findViewById(R.id.btn_exit);
        ImageButton btnRetry = dialog.findViewById(R.id.btn_retru);
        dialog.show();
        btnExit.setOnClickListener(v -> finish());
        btnRetry.setOnClickListener(v -> {
            dialog.dismiss();
            this.score = 100;
            this.time_limit = 120;
            startGame();

        });
    }

    private String showTime(int temp) {
        String minute = temp / 60 + "";
        String second = temp % 60 + "";
        if ((temp / 60) < 10) minute = "0" + minute;
        if ((temp % 60) < 10) second = "0" + second;
        return minute + " : " + second;
    }

    public void changeActivity() {
        startActivityForResult(new Intent(MainActivity.this, ChooseActivity.class), Constant.REQUEST_CODE);
    }
}

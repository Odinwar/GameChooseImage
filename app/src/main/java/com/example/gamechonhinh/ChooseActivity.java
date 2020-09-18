package com.example.gamechonhinh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private List<String> arrayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        tableLayout = findViewById(R.id.layout_table);
        arrayImage = Arrays.asList(getResources().getStringArray(R.array.list_img));
        Collections.shuffle(arrayImage);
        setImage(tableLayout);

    }

    private void setImage(TableLayout tableLayout) {
        int row = 6;
        int cell = 3;
        for (int i = 0; i < row; i++) {
            TableRow tablerow = new TableRow(this);
            for (int j = 0; j < cell; j++) {
                int position = cell * i + j;
                ImageView img = new ImageView(this);
                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        100,
                        getResources().getDisplayMetrics());
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(px, px);
                img.setLayoutParams(layoutParams);
                img.setImageResource((int) getResources().getIdentifier(arrayImage.get(position),
                        Constant.DRAWABLE, getPackageName()));
                img.setOnClickListener(v -> changeIntent(position));
                tablerow.addView(img);
            }
            tableLayout.addView(tablerow);
        }
    }

    private void changeIntent(int position) {
        Intent intent = new Intent();
        intent.putExtra(Constant.NAME_IMAGE, arrayImage.get(position));
        setResult(RESULT_OK, intent);
        finish();
    }
}

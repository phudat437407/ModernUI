package com.example.mordern_ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements
        OnSeekBarChangeListener {

    private SeekBar skControl;
    private LinearLayout[] panels = new LinearLayout[7];
    private int[] originalColor = new int[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panels[0] = (LinearLayout) findViewById(R.id.colorPanel1);
        panels[1] = (LinearLayout) findViewById(R.id.colorPanel2);
        panels[2] = (LinearLayout) findViewById(R.id.colorPanel3);
        panels[3] = (LinearLayout) findViewById(R.id.colorPanel4);
        panels[4] = (LinearLayout) findViewById(R.id.colorPanel5);
        panels[5] = (LinearLayout) findViewById(R.id.colorPanel6);
        panels[6] = (LinearLayout) findViewById(R.id.colorPanel7);
        skControl = (SeekBar) findViewById(R.id.skbColor);
        skControl.setOnSeekBarChangeListener(this);
        getOriginalColors();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.more_info) {
            showMoreInfo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setPanelsColors(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void showMoreInfo() {
        DialogFragment moreInfoFragment = new MoreInfoDialog();
        moreInfoFragment.show(getFragmentManager(), "moreInfo");
    }

    private void setPanelsColors(int progressChanged) {
        for (int i = 0; i < panels.length; i++) {
            int color = originalColor[i];
            if (color != 0xffffffff && color != 0xff888888) {
                int invertColor = (0xFFFFFF - color) | 0xFF000000;
                int r = (color >> 16) & 0x000000FF;
                int g = (color >> 8) & 0x000000FF;
                int b = (color >> 0) & 0x000000FF;
                int invr = (invertColor >> 16) & 0x000000FF;
                int invg = (invertColor >> 8) & 0x000000FF;
                int invb = (invertColor >> 0) & 0x000000FF;
                int newColor = Color.rgb(
                        (int) (r + ((invr - r) * (progressChanged / 100f))),
                        (int) (g + ((invg - g) * (progressChanged / 100f))),
                        (int) (b + ((invb - b) * (progressChanged / 100f))));

                panels[i].setBackgroundColor(newColor);
            }
        }
    }

    private void getOriginalColors() {
        for (int i = 0; i < panels.length; i++) {
            originalColor[i] = ((ColorDrawable) panels[i].getBackground())
                    .getColor();
        }
    }
}
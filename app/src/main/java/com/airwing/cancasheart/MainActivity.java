package com.airwing.cancasheart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Heart heart;
    private int x = 100, y = 100;
    private int rotation = 90;
    private RelativeLayout viewGroup;

    private boolean isFrist = true;
    private int tranX;
    private int tranY;
    private int screenWidth;
    private int screenHeight;
    private long time1;
    private long time2;
    private AlertDialog show;

    private RelativeLayout main_left_drawer_layout;
    public DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   heart = findViewById(R.id.heart);
        viewGroup = findViewById(R.id.viewGroup);

        //左边菜单
        main_left_drawer_layout = (RelativeLayout) findViewById(R.id.main_left_drawer_layout);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        showDialog();
     /*   heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             *//*   heart.setRotation(rotation);
                heart.setTranslationX(x);
                heart.setTranslationY(y);

                x += 100;
                y += 100;
                rotation += 90;*//*
            }
        });*/
    }

    private void initView() {
        screenWidth = viewGroup.getWidth();
        screenHeight = viewGroup.getHeight();
        addHeartView();
    }

    private void addHeartView() {
        tranX = 0;
        tranY = 0;
        time1 = 0;
        isFrist = true;
        viewGroup.removeAllViews();

        while (tranX < screenWidth) {
            //判断红心是否超出屏幕
            if (tranY > screenHeight) {
                tranX += 120;
                tranY = 0;
            }

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
            Heart heart = new Heart(MainActivity.this);
            if (ColorType == 5) {
                heart.setHeartRandomColor();
            } else {
                heart.setHearColor(ColorType);
            }
            viewGroup.addView(heart, layoutParams);
            heart.setTranslationY(tranY);
            heart.setTranslationX(tranX);
            tranY += 120;


            heart.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    if (isFrist) {
                        time1 = System.currentTimeMillis();
                        isFrist = false;
                        Toast.makeText(MainActivity.this, "开始计时了哦，加油！！", Toast.LENGTH_SHORT).show();
                    }
                    viewGroup.removeView(v);
                    if (viewGroup.getChildCount() == 1) {
                        time2 = System.currentTimeMillis();
                        try {
                            showTimeDialog(getDate(time1, time2));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getDate(long time1, long time2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        long between = Math.abs(time1 - time2);
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return day + "天" + hour + "小时" + min + "分" + s + "秒";
    }

    private void showTimeDialog(String date) {
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        //    设置Title的内容
        builder.setTitle("共计时间");
        //    设置Content来显示一个信息
        builder.setMessage(date);
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addHeartView();
                show.dismiss();
            }
        });
        //    显示出该对话框
        show = builder.show();
    }

    AlertDialog showTime;

    private void showDialog() {
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        //    设置Title的内容
        builder.setTitle("玩法");
        builder.setIcon(getResources().getDrawable(R.mipmap.ic_show));
        //    设置Content来显示一个信息
        builder.setMessage("俩人同时比赛，比赛点击红心时间。（当第一个红心点击时 开始计时）");
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showTime.dismiss();
            }
        });
        //    显示出该对话框
        showTime = builder.show();
    }

    int ColorType = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_red:
                ColorType = 0;
                addHeartView();
                break;
            case R.id.tv_yellow:
                ColorType = 1;
                addHeartView();
                break;
            case R.id.tv_blue:
                ColorType = 4;
                addHeartView();
                break;
            case R.id.tv_black:
                ColorType = 3;
                addHeartView();
                break;
            case R.id.tv_green:
                ColorType = 2;
                addHeartView();
                break;
            case R.id.tv_randomColor:
                ColorType = 5;
                addHeartView();
                break;
        }

    }

    //左边菜单开关事件
    public void openLeftLayout() {
        if (drawerLayout.isDrawerOpen(main_left_drawer_layout)) {
            drawerLayout.closeDrawer(main_left_drawer_layout);
        } else {
            drawerLayout.openDrawer(main_left_drawer_layout);
        }
    }

  /*  @RequiresApi(api = Build.VERSION_CODES.N)
    public String getNowDeta() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        Log.e("AAAAAAAA", "Date获取当前日期时间" + simpleDateFormat.format(date));
        return simpleDateFormat.format(date);
    }*/


}

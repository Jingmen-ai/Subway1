package com.example.subway1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class drive extends AppCompatActivity {
    private static final int msgKey1 = 1;
    private static final int msgKey2 = 2;
    Button Light1;
    Button Light2;
    TextView Time;
    double 路程=0;
    double 总路程=0;
    double 路程临时变量;
    TextView 当前区间;
    TextView 秒;
    TextView 速度;
    double 速度数值;
    double 加速度数值;
    String 读秒;
    int 秒数;
    int 报站N=0;
    int 报站A=0;
    private Chronometer timer;
    TextView 距下站;
    TextView 路程框;
    TextView 总路程框;
    TextView KM;
    TextView 超速;
    SeekBar seekBar;
    DecimalFormat DF=new DecimalFormat("0.00");
    File file1;
    Button 左red;
    Button 左gre;
    Button 右red;
    Button 右gre;
    TextView 屏蔽;
    TextView 提示;
    MediaPlayer mediaPlayer=new MediaPlayer();
    MediaPlayer mediaPlayer0=new MediaPlayer();
    MediaPlayer mediaPlayer1=new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);
        Time = (TextView) findViewById(R.id.textView00);
        Button 广播下一站=(Button) findViewById(R.id.button6);
        Button 广播到站=(Button)findViewById(R.id.button10);
        Button 开左门 =(Button)findViewById(R.id.button4);
        Button 关左门 =(Button)findViewById(R.id.button9);
        Button 开右门 =(Button)findViewById(R.id.button8);
        Button 关右门 =(Button)findViewById(R.id.button11);
        左red=(Button)findViewById(R.id.button12);
        左gre=(Button)findViewById(R.id.button13);
        右red=(Button)findViewById(R.id.button14);
        右gre=(Button)findViewById(R.id.button15);
        final TextView 档位=(TextView)findViewById(R.id.textView9);
        timer = (Chronometer)this.findViewById(R.id.chronometer);
        秒=(TextView)findViewById(R.id.textView3);
        final TextView 加速度=(TextView)findViewById(R.id.textView11);
        速度=(TextView)findViewById(R.id.textView13);
        当前区间=(TextView)findViewById(R.id.textView20);
        距下站=(TextView)findViewById(R.id.textView23);
        路程框=(TextView)findViewById(R.id.textView10) ;
        总路程框=(TextView)findViewById(R.id.textView24) ;
        超速=(TextView)findViewById(R.id.textView27) ;
        KM=(TextView)findViewById(R.id.textView22);
        屏蔽=(TextView)findViewById(R.id.textView28) ;
        提示=(TextView)findViewById(R.id.textView26) ;
        提示.setVisibility(View.INVISIBLE);
        Light1=(Button)findViewById(R.id.Light1) ;
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation1.setInterpolator(new LinearInterpolator());
        alphaAnimation1.setDuration(800);
        alphaAnimation1.setRepeatCount(Animation.INFINITE);
        alphaAnimation1.setRepeatMode(Animation.RESTART);
        Light1.setAnimation(alphaAnimation1);
        alphaAnimation1.start();
        seekBar=(SeekBar)findViewById(R.id.seekBar2);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    timer.setBase(SystemClock.elapsedRealtime());timer.start();
                    路程临时变量=路程临时变量+路程;
                    路程=0;
                    if(i==0) {档位.setText("紧急制动位");加速度.setText("-0.1m/s2");加速度数值=-0.1;}
                    else if(i==1){档位.setText("制动4级");加速度.setText("-0.04m/s2");加速度数值=-0.04;}
                    else if(i==2){档位.setText("制动3级");加速度.setText("-0.03m/s2");加速度数值=-0.03;}
                    else if(i==3){档位.setText("制动2级");加速度.setText("-0.02m/s2");加速度数值=-0.02;}
                    else if(i==4){档位.setText("制动1级");加速度.setText("-0.01m/s2");加速度数值=-0.01;}
                    else if(i==5){档位.setText("关机");加速度.setText("0.00m/s2");加速度数值=0;}
                    else if(i==6){档位.setText("牵引1级");加速度.setText("0.02m/s2");加速度数值=0.02;}
                    else if(i==7){档位.setText("牵引2级");加速度.setText("0.03m/s2");加速度数值=0.03;}
                    else if(i==8){档位.setText("牵引3级");加速度.setText("0.04m/s2");加速度数值=0.04;}
                    else if(i==9){档位.setText("牵引4级");加速度.setText("0.05m/s2");加速度数值=0.05;}
                    if(加速度数值>0&&速度数值==0){
                        if(ContextCompat.checkSelfPermission(drive.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(drive.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1); }
                        else {
                            mediaPlayer1.reset();
                            initMediaPlayer1(1);
                            mediaPlayer1.start();
                            mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    mediaPlayer1.reset();
                                    initMediaPlayer1(2);
                                    mediaPlayer1.start();
                                    //mediaPlayer1.setLooping(true);
                                }
                            });
                        }
                    }else if(速度数值>0){
                        if(ContextCompat.checkSelfPermission(drive.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(drive.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1); }
                        else {
                            initMediaPlayer1(2);
                            mediaPlayer1.start();
                            //mediaPlayer1.setLooping(true);
                        }
                    }
                    else if(加速度数值<=0&&速度数值==0)mediaPlayer1.reset();



            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        广播下一站.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.reset();
                mediaPlayer0.stop();
            报站N=报站N+1;
            String str=String.valueOf(报站N);
                if(ContextCompat.checkSelfPermission(drive.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(drive.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1); }
                else {
                    initMediaPlayer(str);
                    mediaPlayer.start();
                }
            }
        });
        广播到站.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer0.reset();
                mediaPlayer.stop();
                报站A=报站A+1;
                String str=String.valueOf(报站A);
                if(ContextCompat.checkSelfPermission(drive.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(drive.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1); }
                else {
                    initMediaPlayer0(str);
                    mediaPlayer0.start();
                }
            }
        });
        开左门.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(速度数值>0) 提示.setVisibility(View.VISIBLE);else {
                左gre.setVisibility(View.VISIBLE);
                左red.setVisibility(View.INVISIBLE);
                    提示.setVisibility(View.INVISIBLE);
            }}
        });
        关左门.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                左gre.setVisibility(View.INVISIBLE);
                左red.setVisibility(View.VISIBLE);
            }
        });
        开右门.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(速度数值>0) 提示.setVisibility(View.VISIBLE);else {
                右gre.setVisibility(View.VISIBLE);
                右red.setVisibility(View.INVISIBLE);
                    提示.setVisibility(View.INVISIBLE);
            }}
        });
        关右门.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                右gre.setVisibility(View.INVISIBLE);
                右red.setVisibility(View.VISIBLE);
            }
        });

        new TimeThread().start();
        new TimeThreadHigh().start();
    }
    private void initMediaPlayer(String str) {
        try {
             file1=new File("/data/data/com.example.subway1/files/","N"+str+".mp3");
            mediaPlayer.setDataSource(file1.getPath());
            mediaPlayer.prepare();
        }catch (Exception e) {e.printStackTrace();}
    }
    private void initMediaPlayer1(int i) {
        try {
            if(i==1){
                file1=new File("/data/data/com.example.subway1/files/","ENGINE1.mp3");
                mediaPlayer1.setDataSource(file1.getPath());
                mediaPlayer1.prepare();}
            else if(i==2){
                file1=new File("/data/data/com.example.subway1/files/","ENGINE2.mp3");
                mediaPlayer1.setDataSource(file1.getPath());
                mediaPlayer1.prepare();
            }
        }catch (Exception e) {e.printStackTrace();}
    }
    private void initMediaPlayer0(String str) {
        try {
            file1=new File("/data/data/com.example.subway1/files/","A"+str+".mp3");
            mediaPlayer0.setDataSource(file1.getPath());
            mediaPlayer0.prepare();
        }catch (Exception e) {e.printStackTrace();}
    }
    public class TimeThreadHigh extends Thread{
        @Override
        public void run() {
            super.run();
            do{
                try {
                    Thread.sleep(100);
                    Message msg1 = new Message();
                    msg1.what = msgKey2;
                    mHandler1.sendMessage(msg1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }
    private Handler mHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey2:
                    速度数值=速度数值+秒数*加速度数值;
                    if(速度数值<0)速度数值=0;
                    速度.setText(DF.format(速度数值)+"m/s");
                    KM.setText(DF.format(速度数值*3.6)+"km/h");
                    路程=速度数值*秒数;
                    路程框.setText(DF.format(路程)+"m");
                    总路程=路程临时变量+路程;
                    总路程框.setText(DF.format(总路程)+"m");
                    break;
                default:
                    break;
            }
        }
    };
        public class TimeThread extends  Thread{
            @Override
            public void run() {
                super.run();
                do{
                    try {
                        Thread.sleep(1000);
                        Message msg = new Message();
                        msg.what = msgKey1;
                        mHandler.sendMessage(msg);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while (true);
            }
        }
        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case msgKey1:
                        long time = System.currentTimeMillis();
                        Date date = new Date(time);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 EEE");
                        Time.setText(format.format(date));
                        读秒=timer.getText().toString();
                        String[] split = 读秒.split(":");
                        String string3 = split[0];
                        int min = Integer.parseInt(string3);
                        int Mins =min*60;
                        int  SS =Integer.parseInt(split[1]);
                        秒数 =Mins+SS;
                        秒.setText(String.valueOf(秒数)+"s");
                        if(左gre.getVisibility()==View.VISIBLE||右gre.getVisibility()==View.VISIBLE){
                            屏蔽.setVisibility(View.VISIBLE);
                            seekBar.setVisibility(View.GONE);
                        }else{
                            屏蔽.setVisibility(View.GONE);
                            seekBar.setVisibility(View.VISIBLE);
                        }
                        if(速度数值*3.6>75){
                            seekBar.setProgress(3);
                            超速.setVisibility(View.VISIBLE);
                        }else 超速.setVisibility(View.INVISIBLE);
                        if(速度数值==0&&加速度数值<=0)mediaPlayer1.reset();
                        if(路程==0) 当前区间.setText("四惠东 站");
                        else if(路程>0&&路程<17)当前区间.setText("四惠东→四惠");
                        else if(路程>17&&路程<33)当前区间.setText("四惠→大望路");
                        else if(路程>3387&&路程<4772)当前区间.setText("大望路→国贸");
                        else if(路程>4772&&路程<5562)当前区间.setText("国贸→永安里");
                        else if(路程>5562&&路程<6939)当前区间.setText("永安里→建国门");
                        else if(路程>6939&&路程<8169)当前区间.setText("建国门→东单");
                        else if(路程>8169&&路程<8943)当前区间.setText("东单→王府井");
                        else if(路程>8943&&路程<9795)当前区间.setText("四惠东→四惠");
                        else if(路程>0&&路程<10720)当前区间.setText("四惠东→四惠");
                        else if(路程>0&&路程<1714)当前区间.setText("四惠东→四惠");
                        else if(路程>0&&路程<1714)当前区间.setText("四惠东→四惠");
                        else if(路程>0&&路程<1714)当前区间.setText("四惠东→四惠");
                        else if(路程>0&&路程<1714)当前区间.setText("四惠东→四惠");
                        else if(路程>0&&路程<1714)当前区间.setText("四惠东→四惠");
                        else if(路程>0&&路程<1714)当前区间.setText("四惠东→四惠");
                        else 当前区间.setText("苹果园→福寿岭");
                        break;
                    default:
                        break;
                }
            }
        };
    }


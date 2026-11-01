package com.islandify.lite;
import android.app.*;import android.os.*;import android.view.*;import android.animation.*;import android.content.*;import android.graphics.*;
public class IslandService extends Service {
View islandView; WindowManager wm;
public IBinder onBind(Intent i){return null;}
public int onStartCommand(Intent intent,int flags,int id){
wm=(WindowManager)getSystemService(WINDOW_SERVICE);
islandView=new View(this);islandView.setBackgroundColor(android.graphics.Color.BLACK);
WindowManager.LayoutParams p=new WindowManager.LayoutParams(350,100,WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,android.graphics.PixelFormat.TRANSLUCENT);
p.gravity=android.view.Gravity.TOP|android.view.Gravity.CENTER_HORIZONTAL;p.y=15;
try{wm.addView(islandView,p);}catch(Exception e){}
ObjectAnimator sx=ObjectAnimator.ofFloat(islandView,"scaleX",1f,1.4f,1f);ObjectAnimator sy=ObjectAnimator.ofFloat(islandView,"scaleY",1f,1.4f,1f);
AnimatorSet set=new AnimatorSet();set.playTogether(sx,sy);set.setDuration(800);set.start();
return START_STICKY;}}

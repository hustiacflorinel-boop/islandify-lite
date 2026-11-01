package com.islandify.lite;

import android.app.*;
import android.os.*;
import android.view.*;
import android.animation.*;
import android.content.*;
import android.graphics.*;
import java.io.*;
import java.net.*;

public class IslandService extends Service {
    View islandView;
    WindowManager wm;
    
    public IBinder onBind(Intent i){ return null; }
    
    public int onStartCommand(Intent intent, int flags, int startId){
        wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        islandView = new View(this);
        islandView.setBackgroundColor(Color.BLACK);
        
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
            350, 100,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT);
        p.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        p.y = 15;
        
        try {
            wm.addView(islandView, p);
            pulseAnimation();
        } catch (Exception e){}
        
        return START_STICKY;
    }
    
    // ANIMATIA TA PULS 100%->140%
    void pulseAnimation(){
        ObjectAnimator sx = ObjectAnimator.ofFloat(islandView, "scaleX", 1f, 1.4f, 1f, 1.2f, 1f);
        ObjectAnimator sy = ObjectAnimator.ofFloat(islandView, "scaleY", 1f, 1.4f, 1f, 1.2f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(sx, sy);
        set.setDuration(800);
        set.setInterpolator(new OvershootInterpolator());
        set.start();
    }
    
    // VERSURI LEGAL - Lrclib.net
    void fetchLyrics(String artist, String track){
        new Thread(() -> {
            try {
                String url = "https://lrclib.net/api/get?artist_name=" + artist + "&track_name=" + track;
                HttpURLConnection conn = (HttpURLConnection)new java.net.URL(url).openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String res=""; String line;
                while((line=br.readLine())!=null) res+=line;
            } catch (Exception e){}
        }).start();
    }
}

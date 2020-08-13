package com.emedicare;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ANDROID GOURAB on 11/29/2016.
 */

public class SBTimer {


    private Timer timer;
    private SBTimerListener timerListener;

    public SBTimer(SBTimerListener timerListener) {
        this.timerListener = timerListener;
    }

    public void start( int delay, int period) {

        final Handler mHandler = new Handler();

        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {

            public void run() {

                timerListener.onTick();
            }
        };

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                mHandler.post(mUpdateResults);

            }

        }, delay, period);
    }

    public void stop() {
        timer.cancel();
    }

    public interface SBTimerListener {

        public void onTick();

    }


}

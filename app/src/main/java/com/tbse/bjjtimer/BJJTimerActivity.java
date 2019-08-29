package com.tbse.bjjtimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tbse.bjjtimer.systemuihelper.SystemUiHelper;
import com.tbse.bjjtimer.util.SystemUiHider;

import java.util.HashMap;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class BJJTimerActivity extends Activity {

    private static final String TAG = "BJJTimer";

    HashMap<String, Integer> info;
    TextView red_score_tv;
    TextView red_advantage_tv;
    TextView red_penalty_tv;
    TextView blue_score_tv;
    TextView blue_advantage_tv;
    TextView blue_penalty_tv;
    TextView timer_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        setContentView(R.layout.activity_bjjtimer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        info = new HashMap<>();
        info.put("red_score", 0);
        info.put("red_advantage", 0);
        info.put("red_penalty", 0);

        info.put("blue_score", 0);
        info.put("blue_advantage", 0);
        info.put("blue_penalty", 0);

        info.put("time", 0); // in seconds

        red_score_tv = (TextView) findViewById(R.id.red_score);
        red_advantage_tv = (TextView) findViewById(R.id.red_advantage);
        red_penalty_tv = (TextView) findViewById(R.id.red_penalty);
        blue_score_tv = (TextView) findViewById(R.id.blue_score);
        blue_advantage_tv = (TextView) findViewById(R.id.blue_advantage);
        blue_penalty_tv = (TextView) findViewById(R.id.blue_penalty);
        timer_tv = (TextView) findViewById(R.id.timerTV);

        blue_score_tv.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                info.put("blue_score", info.get("blue_score")+1);
                blue_score_tv.setText(String.format("%02d", info.get("blue_score")));
            }

            public void onSwipeLeft() {
                if (info.get("blue_score") == 0) return;
                info.put("blue_score", info.get("blue_score")-1);
                blue_score_tv.setText(String.format("%02d", info.get("blue_score")));
            }
        });
        blue_penalty_tv.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                info.put("blue_penalty", info.get("blue_penalty")+1);
                blue_penalty_tv.setText(String.format("%02d", info.get("blue_penalty")));
            }

            public void onSwipeLeft() {
                if (info.get("blue_penalty") == 0) return;
                info.put("blue_penalty", info.get("blue_penalty")-1);
                blue_penalty_tv.setText(String.format("%02d", info.get("blue_penalty")));
            }
        });
        blue_advantage_tv.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                info.put("blue_advantage", info.get("blue_advantage")+1);
                blue_advantage_tv.setText(String.format("%02d", info.get("blue_advantage")));
            }

            public void onSwipeLeft() {
                if (info.get("blue_advantage") == 0) return;
                info.put("blue_advantage", info.get("blue_advantage")-1);
                blue_advantage_tv.setText(String.format("%02d", info.get("blue_advantage")));
            }
        });
        red_score_tv.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                info.put("red_score", info.get("red_score")+1);
                red_score_tv.setText(String.format("%02d", info.get("red_score")));
            }

            public void onSwipeLeft() {
                if (info.get("red_score") == 0) return;
                info.put("red_score", info.get("red_score")-1);
                red_score_tv.setText(String.format("%02d", info.get("red_score")));
            }
        });
        red_advantage_tv.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                info.put("red_advantage", info.get("red_advantage")+1);
                red_advantage_tv.setText(String.format("%02d", info.get("red_advantage")));
            }

            public void onSwipeLeft() {
                if (info.get("red_advantage") == 0) return;
                info.put("red_advantage", info.get("red_advantage")-1);
                red_advantage_tv.setText(String.format("%02d", info.get("red_advantage")));
            }
        });
        red_penalty_tv.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                info.put("red_penalty", info.get("red_penalty")+1);
                red_penalty_tv.setText(String.format("%02d", info.get("red_penalty")));
            }

            public void onSwipeLeft() {
                if (info.get("red_penalty") == 0) return;
                info.put("red_penalty", info.get("red_penalty") - 1);
                red_penalty_tv.setText(String.format("%02d", info.get("red_penalty")));
            }
        });
        timer_tv.setOnTouchListener(new OnSwipeTouchListener(this) {

            public void onSwipeRight() {
                if (myTimer != null) return;
                Log.d(TAG, "swipe right - time is " + info.get("time"));
                info.put("time", info.get("time") + 60);
                updateTimerTV();
            }

            public void onSwipeLeft() {
                if (myTimer != null) return;
                if (info.get("time") < 60) {
                    info.put("time", 0);
                } else {
                    info.put("time", info.get("time")-60);
                }
                updateTimerTV();
            }
        });

    }

    public void pauseClicked(View v) {
        if (myTimer != null) {
            myTimer.cancel();
            myTimer = null;
        }

    }

    public void playClicked(View v) {
        if (myTimer == null) {
            myTimer = new MyTimer(info.get("time"));
            myTimer.start();
        }
    }

    public void resetClicked(View v) {
        if (myTimer != null) {
            myTimer.cancel();
            myTimer = null;
        }

        info.put("red_score", 0);
        info.put("red_advantage", 0);
        info.put("red_penalty", 0);

        info.put("blue_score", 0);
        info.put("blue_advantage", 0);
        info.put("blue_penalty", 0);

        info.put("time", 0); // in seconds
        updateNumbersTVs();
        updateTimerTV();
    }

    private void updateNumbersTVs() {
        blue_score_tv.setText(String.format("%02d", info.get("blue_score")));
        blue_penalty_tv.setText(String.format("%02d", info.get("blue_penalty")));
        blue_advantage_tv.setText(String.format("%02d", info.get("blue_advantage")));
        red_score_tv.setText(String.format("%02d", info.get("red_score")));
        red_penalty_tv.setText(String.format("%02d", info.get("red_penalty")));
        red_advantage_tv.setText(String.format("%02d", info.get("red_advantage")));
    }

    private void updateTimerTV() {
        int seconds = info.get("time")%60;
        int minutes = info.get("time")/60;
        Log.d(TAG, minutes + ":" + seconds);
        if (timer_tv != null)
            timer_tv.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private MyTimer myTimer;

    private class MyTimer extends CountDownTimer {

        public MyTimer(int seconds) {
            super(1000L * seconds, 25L);

        }

        @Override
        public void onTick(long ms) {
            if (Math.round((float)ms / 1000.0f) != info.get("time"))
            {
                info.put("time", Math.round((float)ms / 1000.0f));
            }
            updateTimerTV();
        }

        @Override
        public void onFinish() {
            info.put("time", 0);
            myTimer = null;
            updateTimerTV();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        SystemUiHelper uiHelper =  new SystemUiHelper(this, SystemUiHelper.LEVEL_IMMERSIVE ,flags);
        uiHelper.hide();

    }
}

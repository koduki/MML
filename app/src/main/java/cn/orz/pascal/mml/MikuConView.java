package cn.orz.pascal.mml;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by koduki on 12/22/13.
 */
public class MikuConView extends View {
    final Map<Runnable, Integer> animationQueue = new HashMap<Runnable, Integer>();
    final Context context;

    Character character;
    Dialog dialog;

    private boolean isInitializedScreen;

    public MikuConView(Context context) throws InterruptedException {
        super(context);
        this.context = context;
        this.isInitializedScreen = false;

        this.startAnimation();
    }

    public void initScreen() {
        if(this.isInitializedScreen == false) {
            this.character = new Character(context, this.getHeight(), this.getWidth());
            this.dialog = new Dialog(10, 10, 150, 150);

            isInitializedScreen = true;
        }
    }

    void startAnimation() {
        final Handler handler = new Handler();
        final Runnable requestRedraw = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.d("TEST", "タスク実行2\t" + new Date());
                for (Map.Entry<Runnable, Integer> event : animationQueue.entrySet()) {
                    Log.i("TEST", "key:"+ event.getKey() +"\t" + "key:"+ event.getValue()+"\t"+"queue size:" + animationQueue.size());
                    if (event.getValue() == 0) {
                        event.getKey().run();
                        animationQueue.remove(event.getKey());
                    } else {
                        animationQueue.put(event.getKey(), event.getValue() - 1);
                    }
                }
                handler.post(requestRedraw);
            }
        };
        Timer timer = new Timer("時間間隔タイマー");
        timer.schedule(task, 0, 1 * 1000);
    }

    @Override
    public void onDraw(Canvas canvas) {
        this.character.draw(canvas);
        this.dialog.draw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.character.changeImage("miku003");
        this.animationQueue.put(this.character.getChangeImageEvent("miku001"), 3);
        invalidate();

        Log.i("TEST", "x:" + event.getX() + "\ty:" + event.getY());

        return super.onTouchEvent(event);
    }


}

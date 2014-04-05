package cn.orz.pascal.mml;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

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

    final Character character;
    final Dialog dialog;
    final Map<Runnable, Integer> animationQueue;

    public MikuConView(Context context) throws InterruptedException {
        super(context);

        // WindowManager取得
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        // Displayインスタンス生成
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        int width = size.x;
        int height = disp.getHeight();

        this.character = new Character(context, height, width);
        this.dialog = new Dialog(10, 10, 150, 150);

        this.animationQueue = new HashMap<Runnable, Integer>();


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
                Log.d("TEST", "タスク実行2" + new Date());
                for (Map.Entry<Runnable, Integer> event : animationQueue.entrySet()) {
                    if (event.getValue() == 0) {
                        event.getKey().run();
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

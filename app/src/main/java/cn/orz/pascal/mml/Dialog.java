package cn.orz.pascal.mml;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by koduki on 1/18/14.
 */
public class Dialog {
    int x;
    int y;
    int height;
    int width;
    int color;

    public Dialog(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.color = Color.GRAY;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(this.color);

        RectF rectF = new RectF(this.x, this.y, this.x + this.width, this.y + this.height);

        canvas.drawRoundRect(
                rectF,
                10,
                10,
                paint);
    }
}

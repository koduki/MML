package cn.orz.pascal.mml;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by koduki on 1/18/14.
 */
public class Character {
    Map<String, Bitmap> images;
    Bitmap image;
    int height;
    int width;

    public Character(Context context, int displayHeight, int displayWidth) {
        Resources res = context.getResources();
        this.images = new HashMap<String, Bitmap>();
        this.images.put("miku001", BitmapFactory.decodeResource(res, R.drawable.miku001));
        this.images.put("miku003", BitmapFactory.decodeResource(res, R.drawable.miku003));

        this.image = this.images.get("miku001");

        double imageRate = displayHeight / (this.image.getHeight() * 1.0);
        this.height = (int) (this.image.getHeight() * imageRate);
        this.width = (int) (this.image.getWidth() * imageRate);
    }

    public void draw(Canvas canvas) {
        // modify scale
        this.image = Bitmap.createScaledBitmap(this.image, this.width, this.height, true);
        canvas.drawBitmap(this.image, 0, 0, null);
    }

    public void changeImage(String key) {
        this.image = this.images.get(key);
    }

    public Runnable getChangeImageEvent(final String key) {
        return new Runnable() {
            @Override
            public void run() {
                changeImage(key);
            }
        };
    }
}

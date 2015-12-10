package com.ms365.sparkgallery.collage;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public final class CollageUtils {

    public static Bitmap glueBitmaps(Bitmap _left, Bitmap _right){
        if (_left == null || _right == null) return null;
        int sizePart = _left.getWidth();

        Bitmap glueBitmap = Bitmap.createBitmap(2 * sizePart, 2 * sizePart, Bitmap.Config.ARGB_8888);
        Canvas glueCanvas = new Canvas(glueBitmap);

        glueCanvas.drawBitmap(_left, 0f, 0f, null);
        glueCanvas.drawBitmap(_right, sizePart, 0f, null);

        return glueBitmap;
    }
}

package com.ms365.sparkgallery.collage;

import android.graphics.Bitmap;

import com.ms365.sparkgallery.gallery.model.Image;

public interface IImagesController {
    void setLeftImage(Image _image);
    void setRightImage(Image _image);
    void goneImage(boolean _rightSide);
    Bitmap getResultImageBitmap();
}

package com.ms365.sparkgallery.gallery;

import com.ms365.sparkgallery.gallery.model.Image;

public interface IListEventForCollage {
    void showLeft(Image _image);
    void showRight(Image _image);
    void goneLeft();
    void goneRight();
}

package com.ms365.sparkgallery.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public final class SquareImageView extends ImageView {

    public SquareImageView(Context _context) {
        super(_context);
    }

    public SquareImageView(Context _context, AttributeSet attrs) {
        super(_context, attrs);
    }

    public SquareImageView(Context _context, AttributeSet _attrs, int _defStyleAttr) {
        super(_context, _attrs, _defStyleAttr);
    }

    @Override
    protected void onMeasure(int _widthMeasureSpec, int _heightMeasureSpec) {
        super.onMeasure(_heightMeasureSpec, _heightMeasureSpec);
    }
}

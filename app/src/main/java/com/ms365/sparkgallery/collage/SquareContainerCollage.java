package com.ms365.sparkgallery.collage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SquareContainerCollage extends FrameLayout {

    public SquareContainerCollage(Context _context) {
        super(_context);
    }

    public SquareContainerCollage(Context _context, AttributeSet _attrs) {
        super(_context, _attrs);
    }

    public SquareContainerCollage(Context _context, AttributeSet _attrs, int _defStyleAttr) {
        super(_context, _attrs, _defStyleAttr);
    }

    @Override
    protected void onMeasure(int _widthMeasureSpec, int _heightMeasureSpec) {
        super.onMeasure(_widthMeasureSpec, _widthMeasureSpec);
    }
}

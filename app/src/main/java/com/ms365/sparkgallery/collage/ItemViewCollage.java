package com.ms365.sparkgallery.collage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.ms365.sparkgallery.R;

public final class ItemViewCollage extends FrameLayout implements View.OnClickListener{

    private SubsamplingScaleImageView   mImageScalable;
    private ICloseListener              mCloseListener;

    public ItemViewCollage(Context _context) {
        super(_context);
        init();
    }

    public ItemViewCollage(Context _context, AttributeSet _attrs) {
        super(_context, _attrs);
        init();
    }

    public ItemViewCollage(Context _context, AttributeSet _attrs, int _defStyleAttr) {
        super(_context, _attrs, _defStyleAttr);
        init();
    }

    public void setOnCloseListener(ICloseListener _iCloseListener){
        this.mCloseListener = _iCloseListener;
    }

    private void init(){
        inflate(getContext(), R.layout.custom_view_item_collage, this);
        mImageScalable = (SubsamplingScaleImageView) findViewById(R.id.scivImage_CVIC);

        findViewById(R.id.ivClose_CVIC).setOnClickListener(this);
    }

    public SubsamplingScaleImageView getImageScalable() {
        return mImageScalable;
    }

    @Override
    public void onClick(View v) {
        if (mCloseListener != null)
            mCloseListener.onClose(getId());
    }

    public interface ICloseListener{
        void onClose(int _sideId);
    }
}

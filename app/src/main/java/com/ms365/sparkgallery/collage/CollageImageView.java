package com.ms365.sparkgallery.collage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.ms365.sparkgallery.R;
import com.ms365.sparkgallery.gallery.model.Image;

import java.util.concurrent.atomic.AtomicBoolean;

public final class CollageImageView extends SquareContainerCollage implements IImagesController, View.OnClickListener, ItemViewCollage.ICloseListener {

    //************* Variables *************//
    private AtomicBoolean               mIsLeftImage  = new AtomicBoolean(false);
    private AtomicBoolean               mIsRightImage = new AtomicBoolean(false);
    private AtomicBoolean               mIsCropState  = new AtomicBoolean(true);
    private IInternalClosingListener    mCloseListener;
    private Image                       mLeftImage, mRightImage;

    //************** Views *****************//
    private ItemViewCollage             mLeftItemViewCollage, mRightViewItemCollage;
    private ImageButton                 mCropButton;


    public CollageImageView(Context _context) {
        super(_context);
        init(_context);
    }

    public CollageImageView(Context _context, AttributeSet _attrs) {
        super(_context, _attrs);
        init(_context);
    }

    public CollageImageView(Context _context, AttributeSet _attrs, int _defStyleAttr) {
        super(_context, _attrs, _defStyleAttr);
        init(_context);
    }

    private void init(Context _context) {
        inflate(_context, R.layout.custom_view_image_collage, this);
        mLeftItemViewCollage    = (ItemViewCollage) findViewById(R.id.ivcLeft_CVIC);
        mRightViewItemCollage   = (ItemViewCollage) findViewById(R.id.ivcRight_CVIC);
        mCropButton             = (ImageButton) findViewById(R.id.ivCropAction_CVIC);

        mLeftItemViewCollage    .setOnCloseListener(this);
        mRightViewItemCollage   .setOnCloseListener(this);
        mCropButton             .setOnClickListener(this);

        updateVisibilityImages();
        updateCropParams(false);
    }

    private void updateVisibilityImages() {
        mLeftItemViewCollage    .setVisibility(mIsLeftImage.get() ? VISIBLE : GONE);
        mRightViewItemCollage   .setVisibility(mIsRightImage.get() ? VISIBLE : GONE);
    }

    private void updateCropParams(boolean _visibility) {
        if (mCropButton != null) {
            mCropButton.setVisibility(_visibility ? VISIBLE : GONE);
            mCropButton.setImageResource(mIsCropState.get() ? R.drawable.ic_crop : R.drawable.ic_uncrop);
        }
    }

    @Override
    public void setLeftImage(Image _image) {
        if (mLeftItemViewCollage != null) {
            mLeftImage = _image;
            mIsLeftImage.set(true);
            updateVisibilityImages();

            mIsCropState.set(true);
            updateCropParams(!mIsRightImage.get());

            // You must me add listener and change visibility after load image, because see previous image when loading current
            mLeftItemViewCollage.getImageScalable().setImage(ImageSource.resource(mLeftImage.resDrawableId));
        }
    }

    @Override
    public void setRightImage(Image _image) {
        if (mRightViewItemCollage != null) {
            mRightImage = _image;
            mIsRightImage.set(true);
            updateVisibilityImages();

            mIsCropState.set(true);
            updateCropParams(!mIsLeftImage.get());

            // You must me add listener and change visibility after load image, because see previous image when loading current
            mRightViewItemCollage.getImageScalable().setImage(ImageSource.resource(mRightImage.resDrawableId));
        }
    }

    @Override
    public void goneImage(boolean _rightSide) {
        if (_rightSide)
            mIsRightImage.set(false);
        else
            mIsLeftImage.set(false);
        updateCropParams(mIsLeftImage.get() || mIsRightImage.get());
        updateVisibilityImages();
    }

    @Override
    public Bitmap getResultImageBitmap() {
        if (mIsRightImage.get())
            return CollageUtils.glueBitmaps(getCollageDrawingCache(mLeftItemViewCollage.getImageScalable()), getCollageDrawingCache(mRightViewItemCollage.getImageScalable()));
        else return getCollageDrawingCache(mLeftItemViewCollage.getImageScalable());
    }

    private Bitmap getCollageDrawingCache(SubsamplingScaleImageView v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return b;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCropAction_CVIC:
                cropActionFor(mIsLeftImage.get() ? mLeftItemViewCollage.getImageScalable() : mRightViewItemCollage.getImageScalable());
                break;
        }
    }

    private void cropActionFor(SubsamplingScaleImageView _scaleImageView) {
        if (mIsCropState.get()) {
            mIsCropState.set(false);
            updateCropParams(true);
            int vPadding = _scaleImageView.getPaddingBottom() + _scaleImageView.getPaddingTop();
            int hPadding = _scaleImageView.getPaddingLeft() + _scaleImageView.getPaddingRight();
            float scaleCoefficient = Math.max(
                    (float) (_scaleImageView.getWidth() - hPadding) /
                            (float) _scaleImageView.getSWidth(), (float) (_scaleImageView.getHeight() - vPadding) / (float) _scaleImageView.getSHeight());

            _scaleImageView.animateScaleAndCenter(scaleCoefficient, _scaleImageView.getCenter()).start();
        } else {
            mIsCropState.set(true);
            updateCropParams(true);
            _scaleImageView.animateScaleAndCenter(0f, _scaleImageView.getCenter()).start();
        }
    }

    @Override
    public void onClose(int _sideId) {
        switch (_sideId){
            case R.id.ivcLeft_CVIC:
                goneImage(false);
                if (mCloseListener != null)
                    mCloseListener.onCloseInInternal(mLeftImage);
                break;
            case R.id.ivcRight_CVIC:
                goneImage(true);
                if (mCloseListener != null)
                    mCloseListener.onCloseInInternal(mRightImage);
                break;
        }
    }

    public void setOnInternalCloseListener(IInternalClosingListener internalCloseListener){
        mCloseListener = internalCloseListener;
    }

    public interface IInternalClosingListener{
        void onCloseInInternal(Image _imageClosable);
    }
}

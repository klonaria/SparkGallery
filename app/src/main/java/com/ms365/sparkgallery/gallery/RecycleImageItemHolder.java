package com.ms365.sparkgallery.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ms365.sparkgallery.R;
import com.ms365.sparkgallery.gallery.model.Image;
import com.squareup.picasso.Picasso;

public final class RecycleImageItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    //*************** Views ***************//
    private ImageView                   mImageThumb, mIsChoosedLabel;

    //************* Variables *************//
    private OnImageItemHolderListener   mImageItemHolderListener;
    private Image                       mImage;

    public RecycleImageItemHolder(View _itemView, OnImageItemHolderListener _imageItemHolderListener) {
        super(_itemView);
        mImageItemHolderListener    = _imageItemHolderListener;
        mImageThumb                 = (ImageView) _itemView.findViewById(R.id.sivImageILG);
        mIsChoosedLabel             = (ImageView) _itemView.findViewById(R.id.ivIsChoosed_ILG);
        _itemView                   .setOnClickListener(this);
    }

    public void update(Context _context, Image _image, boolean _isChoosed){
        int dp60    = (int) (60 * _context.getResources().getDisplayMetrics().density);
        mImage      = _image;

        Picasso.with(_context)
                .load(mImage.resDrawableId)
                .resize(dp60, dp60)
                .into(mImageThumb);

        mIsChoosedLabel.setVisibility(_isChoosed ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        mImageItemHolderListener.onClick(mImage);
    }

    public interface OnImageItemHolderListener {
        void onClick(Image _image);
    }

}

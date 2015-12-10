package com.ms365.sparkgallery.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ms365.sparkgallery.R;
import com.ms365.sparkgallery.gallery.model.Image;

import java.util.ArrayList;

public final class RecycleListImagesAdapter extends RecyclerView.Adapter<RecycleImageItemHolder> implements RecycleImageItemHolder.OnImageItemHolderListener {

    //************* Variables *************//
    private Context                 mContext;
    private ArrayList<Image>        mImages                 = new ArrayList<>();
    private IListEventForCollage    mListEventForCollage;
    private int                     mLeftIdChecked          = -1,
                                    mRightIdChecked         = -1;

    public RecycleListImagesAdapter(Context _context, ArrayList<Image> _images, IListEventForCollage _listEventForCollage) {
        this.mContext = _context;
        this.mImages = _images;
        this.mListEventForCollage = _listEventForCollage;
    }

    public void closeItem(Image imageClose){
        if (imageClose.id == mLeftIdChecked) {
            mLeftIdChecked = -1;
            notifyDataSetChanged();
        }
        else
        if (imageClose.id == mRightIdChecked) {
            mRightIdChecked = -1;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecycleImageItemHolder onCreateViewHolder(ViewGroup _parent, int _viewType) {
        return new RecycleImageItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_gallery, _parent, false), this);
    }

    @Override
    public void onBindViewHolder(RecycleImageItemHolder _holder, int _position) {
        Image tempImage = mImages.get(_position);
        _holder.update(mContext, tempImage, tempImage.id == mLeftIdChecked || tempImage.id == mRightIdChecked);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    @Override
    public void onClick(Image _image) {
        if (setId(_image))
            notifyDataSetChanged();
    }

    private boolean setId(Image _image) {
        if (mLeftIdChecked == _image.id) {
            mLeftIdChecked = -1;
            mListEventForCollage.goneLeft();
            return true;
        } else if (mRightIdChecked == _image.id) {
            mRightIdChecked = -1;
            mListEventForCollage.goneRight();
            return true;
        } else {
            if (mLeftIdChecked == -1) {
                mLeftIdChecked = _image.id;
                mListEventForCollage.showLeft(_image);
                return true;
            } else {
                if (mRightIdChecked == -1) {
                    mRightIdChecked = _image.id;
                    mListEventForCollage.showRight(_image);
                    return true;
                }
            }
        }
        return false;
    }
}

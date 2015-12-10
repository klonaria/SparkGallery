package com.ms365.sparkgallery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ms365.sparkgallery.collage.CollageImageView;
import com.ms365.sparkgallery.gallery.IListEventForCollage;
import com.ms365.sparkgallery.gallery.RecycleListImagesAdapter;
import com.ms365.sparkgallery.gallery.model.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener, IListEventForCollage, CollageImageView.IInternalClosingListener{

    private RecycleListImagesAdapter mImagesAdapter;

    //*************** Views ***************//
    private CollageImageView    mCollageImageView;
    private RecyclerView        mListImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configViews();
    }

    private void configViews(){
        mCollageImageView   = (CollageImageView)    findViewById(R.id.eiiCustomCropperView_AM);
        mListImages         = (RecyclerView)        findViewById(R.id.rvListImages_AM);

        mListImages.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false));
        mListImages.setAdapter(mImagesAdapter = new RecycleListImagesAdapter(this, mockImages(), this));

        mCollageImageView.setOnInternalCloseListener(this);
        findViewById(R.id.ivAcceptGluing_AM).setOnClickListener(this);
    }

    private ArrayList<Image> mockImages(){
        ArrayList<Image> images = new ArrayList<>();
        images.add(new Image(0, R.drawable.test1));
        images.add(new Image(1, R.drawable.test2));
        images.add(new Image(2, R.drawable.test3));
        images.add(new Image(3, R.drawable.test4));
        images.add(new Image(4, R.drawable.test5));
        images.add(new Image(5, R.drawable.test6));
        images.add(new Image(6, R.drawable.test7));
        images.add(new Image(7, R.drawable.test8));
        images.add(new Image(8, R.drawable.test9));
        images.add(new Image(9, R.drawable.test10));
        images.add(new Image(10, R.drawable.test11));
        images.add(new Image(11, R.drawable.test12));
        return images;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivAcceptGluing_AM:
                saveResultIntoFile(mCollageImageView.getResultImageBitmap());
                break;
        }
    }

    private void saveResultIntoFile(Bitmap bitmap){
        if (bitmap == null) return;
        FileOutputStream out = null;
        try {
            File outputFile = new File(Environment.getExternalStorageDirectory() + "/sparkGluedImage.jpg");
            if (outputFile.exists())
                outputFile.delete();
            out = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Toast.makeText(this, "Image saved in file\n" + outputFile.getPath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showLeft(Image image) {
        mCollageImageView.setLeftImage(image);
    }

    @Override
    public void showRight(Image image) {
        mCollageImageView.setRightImage(image);
    }

    @Override
    public void goneLeft() {
        mCollageImageView.goneImage(false);
    }

    @Override
    public void goneRight() {
        mCollageImageView.goneImage(true);
    }

    @Override
    public void onCloseInInternal(Image _imageClosable) {
        if (mImagesAdapter != null)
            mImagesAdapter.closeItem(_imageClosable);
    }
}

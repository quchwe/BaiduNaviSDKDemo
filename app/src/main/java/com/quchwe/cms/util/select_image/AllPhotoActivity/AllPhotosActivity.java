package com.quchwe.cms.util.select_image.AllPhotoActivity;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.main_frame.MainFrameActivity;
import com.quchwe.cms.util.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by quchwe on 2016/8/5 0005.
 */
public class AllPhotosActivity extends BaseActivity implements View.OnClickListener, SeletPhotoContract.View {

    public static final String TAG = "ALL_PHOTO";
    public static ArrayList<String> SELECTED_IMAGES = new ArrayList<>();
    public static ArrayList<String> CURRENTDIR_IMAGES = new ArrayList<>();
    public static final int SELECT_PHOTO_RESULT_OK = 1;
    public static final String SELECTED_IMAGE = "selectedImages";
    public static final String ALL_IMAGES = "allImages";
    public static final String SELECT_LIMITS = "limits";
    public int limit = 3;
    @Bind(R.id.back)
    Button back;
    @Bind(R.id.btn_preview)
    Button preview;
    @Bind(R.id.picture_count)
    TextView pictureCount;
    @Bind(R.id.complete)
    TextView complete;
    @Bind(R.id.rv_album_grid)
    RecyclerView myRecyler;

    int currentAllSlectedPos;
    int previewSelectPos;
    ImageSelectAdapter adapter;

    private SeletPhotoContract.Presenter mPresenter;
    private ArrayList<String> selectPicturePath = new ArrayList<>();
    String currentSelect;
    ArrayList<String> selectedImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idle_select_image);

        ButterKnife.bind(this);

        limit = getIntent().getIntExtra(SELECT_LIMITS,3);

        selectedImages = getIntent().getStringArrayListExtra("select");
        mPresenter = new SelectPhotoPresenter(this, this);

    }

    @OnClick({R.id.back, R.id.complete, R.id.btn_preview})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.complete:

                Intent k = new Intent(this, MainFrameActivity.class);
                k.putStringArrayListExtra(SELECTED_IMAGE, selectPicturePath);
                setResult(Activity.RESULT_OK, k);
                finish();
                break;
            case R.id.btn_preview:
//                toPreview();
        }

    }

    @Override
    public void setPictureSelectCount(int count) {
        pictureCount.setText("" + count);
    }

    @Override
    public void toPreview() {

    }

    @Override
    public void setRecylerView(List<String> paths) {
        adapter = new ImageSelectAdapter(this, paths, selectedImages, limit);
        myRecyler.setLayoutManager(new GridLayoutManager(this, 4));
        myRecyler.setAdapter(adapter);
        myRecyler.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void currentSelected(String picturePath) {
        currentSelect = adapter.getCurrentSelect();
        currentAllSlectedPos = adapter.getPosition();

    }

    @Override
    public void setPresenter(@NonNull SeletPhotoContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setSlectPicturePath(ArrayList<String> selectedImages) {
        selectPicturePath = selectedImages;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);

    }


    @Override
    public void finish() {


        super.finish();
    }

    public void setSelectedPos(int currentAllSelectedPos, int previewSelectPos) {
        this.currentAllSlectedPos = currentAllSelectedPos;
        this.previewSelectPos = previewSelectPos;
    }

}

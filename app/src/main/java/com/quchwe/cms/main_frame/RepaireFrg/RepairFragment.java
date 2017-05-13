package com.quchwe.cms.main_frame.RepaireFrg;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;


import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;
import com.bumptech.glide.Glide;
import com.quchwe.cms.myinfo.ImageAdapter;
import com.quchwe.cms.util.NormalUtil.DialogUtils;

import com.quchwe.cms.util.NormalUtil.LocationUtil;
import com.quchwe.cms.util.NormalUtil.ToastUtil;
import com.quchwe.cms.util.PhotoViewActivity;
import com.quchwe.cms.util.base.BaseFragment;

import com.quchwe.cms.util.select_image.AllPhotoActivity.AllPhotosActivity;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


import static com.quchwe.cms.util.NormalUtil.FileUtils.createImageFile;


/**
 * Created by quchwe on 2017/4/10 0010.
 */

public class RepairFragment extends BaseFragment<RepaireContract.Presenter> implements RepaireContract.View {

    public static final String TAG = "RepairFrag-";
    public static final int INPUT_FILE_REQUEST_CODE = 23;
    public static final int SELECT_PHOTO = 12;
    private String mCameraPhotoPath;
    List<String> results = new ArrayList<>();
    Uri came_photo_path = null;

    public static RepairFragment newInstance() {

        Bundle args = new Bundle();

        RepairFragment fragment = new RepairFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.et_phone_number)
    TextView etPhoneNUmber;
    @Bind(R.id.et_drivingLicenseId)
    EditText etDLID;
    @Bind(R.id.et_desc)
    EditText etDesc;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.textTitle)
    TextView title;
    @Bind(R.id.tv_more)
    TextView more;
    @Bind(R.id.et_type)
    EditText type;
    @Bind(R.id.rv_images)
    RecyclerView imageRec;
    @Bind(R.id.iv_add)
    ImageView add;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.frag_repaire, container, false);
        CURRENT_FRAGMENT = TAG;
        ButterKnife.bind(this, root);

        initView();
        return root;
    }

    private void initView() {
        title.setText("车辆报修");
        more.setVisibility(View.VISIBLE);
        more.setText("提交");
        mPresenter.setDefaultData();
    }

    @OnClick({R.id.back, R.id.tv_more, R.id.iv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.tv_more:
                createRepair();
                break;
            case R.id.iv_add:
                showSetHeadImageDialog();
                break;
        }
    }

    @Override
    public void createRepair() {
        String accidentType = type.getText().toString();
        String description = etDesc.getText().toString();
        String drvId = etDLID.getText().toString();

        if (drvId == null || drvId.equals("")) {
            ToastUtil.showToast(getContext(), "请选择车牌号");
            return;
        }
        if (accidentType == null || accidentType.equals("")) {
            ToastUtil.showToast(getContext(), "请输入故障类型");
            return;
        }

        File f[] = new File[results.size()];
        for (int i = 0; i < results.size(); i++) {
            f[i] = new File(results.get(i));
        }
        mPresenter.createRepair(accidentType, description, drvId, f);
    }




    private void chooseImage() {

//        if (image3.getVisibility() == View.VISIBLE) {
//            ToastUtil.showToast(getContext(), "最多只能三张图片！");
//            return;
//        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
            } catch (IOException ex) {
                // Error occurred while creating the File
//                        Log.e(TAG, "Unable to create Image File", ex);
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
            } else {
                takePictureIntent = null;
            }
        }

        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent[] intentArray;
        if (takePictureIntent != null) {
            intentArray = new Intent[]{takePictureIntent};
        } else {
            intentArray = new Intent[0];
        }

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "选择图片");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

        startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
    }


    @Override
    public void setDefaultData(String phone, String driveId) {
        etPhoneNUmber.setText(phone);
        etDLID.setText(driveId);
    }

    @Override
    public void showSuccess() {
        ToastUtil.showToast(getContext(), "提交成功");
    }

    @Override
    public void showFailed(String errMsg) {
        ToastUtil.showToast(getContext(), errMsg);
    }


    @Override
    public void setPresenter(RepaireContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
            ArrayList<String> selectPaths = data.getStringArrayListExtra(AllPhotosActivity.SELECTED_IMAGE);
            System.out.println(selectPaths.size());
            if (selectPaths.size() > 0) {
                results.addAll(selectPaths);
            }
            setImage();
        }

        // Check that the response is a good one
        if (requestCode == INPUT_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            mCameraPhotoPath = came_photo_path.toString();
            // System.out.println(headImagePath);
            try {
                File file = new File(new URI(mCameraPhotoPath));
                results.add(file.getPath());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            setImage();

        }
    }


    private void setImage() {

//        if (results.size() == 0) {
//            return;
//        }
//        if (image1.getVisibility() == View.VISIBLE) {
//            if (image2.getVisibility() == View.VISIBLE) {
//                if (image3.getVisibility() == View.VISIBLE) {
//
//                } else {
//                    image3.setVisibility(View.VISIBLE);
//                    Glide.with(getContext()).load(results.get(2)).into(image3);
//                }
//            } else {
//                image2.setVisibility(View.VISIBLE);
//                Glide.with(getContext()).load(results.get(1)).into(image2);
//            }
//        } else {
//            image1.setVisibility(View.VISIBLE);
//            Glide.with(getContext()).load(results.get(0)).into(image1);
//        }
        if (results.size() != 0) {
            ImageAdapter adapter = new ImageAdapter(getContext(), results);
            imageRec.setLayoutManager(new GridLayoutManager(getContext(), 3));
            imageRec.setItemAnimator(new DefaultItemAnimator());
            imageRec.setAdapter(adapter);
        }
    }

    private int setSelectLimit() {
        return 3 - results.size();
    }

    private void showSetHeadImageDialog() {
        final DialogUtils.DialogMenu2 dialogMenu2 = new DialogUtils.DialogMenu2(getContext());
        dialogMenu2.addMenuItem("拍照");
        dialogMenu2.addMenuItem("从手机相册选择");
        dialogMenu2.setMenuListener(new DialogUtils.DialogMenu2.MenuListener() {
            @Override
            public void onItemSelected(int position, String item) {
                if (position == 0) {
                    dialogMenu2.cancel();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        came_photo_path = Uri.fromFile(createImageFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    intent.putExtra(MediaStore.EXTRA_OUTPUT, came_photo_path);
                    intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                    startActivityForResult(intent, INPUT_FILE_REQUEST_CODE);

                }
                if (position == 1) {
                    dialogMenu2.cancel();
                    Intent k = new Intent(getContext(), AllPhotosActivity.class);
                    k.putExtra(AllPhotosActivity.SELECT_LIMITS, setSelectLimit());
                    k.putStringArrayListExtra("select",(ArrayList<String>) results);
                    startActivityForResult(k, SELECT_PHOTO);

                    //getActivity().startActivityForResult(new Intent(getContext(), AllIPhotosActivity.class));
                }
            }

            @Override
            public void onCancel() {
                dialogMenu2.dismiss();

            }
        });
        dialogMenu2.initView();
        dialogMenu2.show();
        LocationUtil.bottom_FillWidth(getActivity(), dialogMenu2);
    }

}


package com.quchwe.cms.myinfo.InfoMainFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.navi.sdkdemo.R;
import com.baidu.platform.comapi.map.B;
import com.bumptech.glide.Glide;
import com.quchwe.cms.data.beans.SysUser;
import com.quchwe.cms.util.NormalUtil.DialogUtils;
import com.quchwe.cms.util.NormalUtil.LocationUtil;
import com.quchwe.cms.util.NormalUtil.ToastUtil;
import com.quchwe.cms.util.base.BaseFragment;
import com.quchwe.cms.util.base.BaseRecyclerViewAdapter;
import com.quchwe.cms.util.select_image.AllPhotoActivity.AllPhotosActivity;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quchwe.cms.main_frame.RepaireFrg.RepairFragment.INPUT_FILE_REQUEST_CODE;
import static com.quchwe.cms.main_frame.RepaireFrg.RepairFragment.SELECT_PHOTO;
import static com.quchwe.cms.util.NormalUtil.FileUtils.createImageFile;

/**
 * Created by quchwe on 2017/4/12 0012.
 */

public class InfoMainFragment extends BaseFragment<InfoMainContract.Presenter> implements InfoMainContract.View {
    public static final String TAG = "infoMainFrag-";


    public static InfoMainFragment newInstance() {

        Bundle args = new Bundle();

        InfoMainFragment fragment = new InfoMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    Uri came_photo_path = null;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.textTitle)
    TextView title;
    @Bind(R.id.rl_headImage)
    RelativeLayout headImageLayout;
    @Bind(R.id.iv_headImage)
    ImageView ivHeadImage;
    @Bind(R.id.rl_nick_name)
    RelativeLayout nickNameLayout;
    @Bind(R.id.tv_nick_name)
    TextView tvNickName;
    @Bind(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @Bind(R.id.rl_address)
    RelativeLayout addressLayout;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.rl_sex)
    RelativeLayout sexLayout;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.rl_signature)
    RelativeLayout signatureLayout;
    @Bind(R.id.tv_signature)
    TextView tvSignature;

    String imagePath = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.frag_info_main, container, false);

        ButterKnife.bind(this, root);
        initView();
        return root;
    }

    private void initView() {
        title.setText(getString(R.string.self_info));
        mPresenter.setDefaultData();
    }

    @OnClick({R.id.back, R.id.rl_headImage, R.id.rl_nick_name, R.id.rl_address, R.id.rl_sex, R.id.rl_signature})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.rl_headImage:
                showSetHeadImageDialog();
                break;
            case R.id.rl_nick_name:
                showEditDialog(tvNickName, getString(R.string.nick_name));
                break;
            case R.id.rl_address:
                showEditDialog(tvAddress, getString(R.string.address));
                break;
            case R.id.rl_sex:
                mPresenter.setSexDialog();
                break;
            case R.id.rl_signature:
                showEditDialog(tvSignature, getString(R.string.signature));
                break;
        }
    }

    @Override
    public void setPresenter(InfoMainContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setDefaultData(SysUser user) {
        if (!user.getHeadImage().equals("")) {
            Glide.with(getContext()).load(user.getHeadImage()).into(ivHeadImage);
        }
        tvNickName.setText(user.getUserName());
        tvPhoneNumber.setText(user.getPhoneNumber());
        tvAddress.setText(user.getAddress());
        tvSex.setText(user.getSex());
    }

    @Override
    public void setSexString(String s) {
        tvSex.setText(s);
    }

    @Override
    public void setSexDialog(SelectDialogItemAdapter adapter) {


//
//        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
//
//        dialog.show();
//        Window window = dialog.getWindow();
//
//        window.setContentView(R.layout.dialog_choose_sex);
//        TextView t = (TextView) window.findViewById(R.id.tv_dialog_title);
//        t.setText(getString(R.string.sex));
//        RecyclerView numberList = (RecyclerView) window.findViewById(R.id.rv_number_list);
//        numberList.setLayoutManager(new LinearLayoutManager(getContext()));
//        numberList.setItemAnimator(new DefaultItemAnimator());
//        numberList.setAdapter(adapter);
//
//        adapter.setOnclikListener(new BaseRecyclerViewAdapter.OnItemclickListener() {
//            @Override
//            public void onClick(View v, int position) {
//                setSexString(sexList.get(position));
//            }
//
//            @Override
//            public void onLongClick(View v, int position) {
//
//            }
//        });
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
                    k.putExtra(AllPhotosActivity.SELECT_LIMITS, 1);
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


    public void showEditDialog(final TextView textView, String title) {
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();

        dialog.show();
        Window window = dialog.getWindow();

        window.setContentView(R.layout.dialog_edit);
        TextView t = (TextView) window.findViewById(R.id.tv_dialog_title);
        final EditText et = (EditText) window.findViewById(R.id.et_dialog);
        et.setFocusable(true);
        Button confirm = (Button) window.findViewById(R.id.confirm);
        Button cancel = (Button) window.findViewById(R.id.cancel);
       KeyBoard(et,true);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et.getText().toString() != null && !et.getText().toString().equals("")) {
                    textView.setText(et.getText().toString());
                    dialog.dismiss();
                } else ToastUtil.showToast(getContext(), "请输入内容");

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        t.setText(title);

    }

    @Override
    public void onDestroyView() {

        SysUser user = new SysUser();
        if (imagePath != null) {
            user.setHeadImage(imagePath);
        }
        if (tvSex.getText().toString() != null) {
            user.setSex(tvSex.getText().toString());
        }
        if (tvSignature.getText().toString() != null) {
            user.setSignature(tvSignature.getText().toString());
        }
        user.setPhoneNumber(tvPhoneNumber.getText().toString());
        user.setAddress(tvAddress.getText().toString());
        mPresenter.updateUserInfo(user);
        imagePath = null;
        super.onDestroyView();
    }

    private void showKeyboard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
            ArrayList<String> selectPaths = data.getStringArrayListExtra(AllPhotosActivity.SELECTED_IMAGE);
            System.out.println(selectPaths.size());
            if (selectPaths.size() > 0) {
                Glide.with(getContext()).load(selectPaths.get(0)).into(ivHeadImage);
                imagePath = selectPaths.get(0);
            }

        }

        // Check that the response is a good one
        if (requestCode == INPUT_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Glide.with(getContext()).load(came_photo_path).into(ivHeadImage);

            try {
                File f = new File(new URI(came_photo_path.toString()));
                imagePath = f.getPath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    public static void KeyBoard(final EditText txtSearchKey, final boolean status) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager)
                        txtSearchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (status) {
                    m.showSoftInput(txtSearchKey, InputMethodManager.SHOW_FORCED);
                } else {
                    m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
                }
            }
        }, 300);
    }

}

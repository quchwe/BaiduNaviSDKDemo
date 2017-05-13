package com.quchwe.cms.main_frame;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.baidu.navi.sdkdemo.R;
import com.quchwe.cms.data.DataRepository;
import com.quchwe.cms.data.beans.BaseBean;
import com.quchwe.cms.data.beans.Car;
import com.quchwe.cms.main_frame.HomeFrag.HomeContract;
import com.quchwe.cms.main_frame.HomeFrag.HomeFragment;
import com.quchwe.cms.main_frame.HomeFrag.HomePresenter;
import com.quchwe.cms.main_frame.MineFrag.MineContract;
import com.quchwe.cms.main_frame.MineFrag.MineFragment;
import com.quchwe.cms.main_frame.MineFrag.MinePresenter;
import com.quchwe.cms.main_frame.RepaireFrg.RepairFragment;
import com.quchwe.cms.main_frame.RepaireFrg.RepaireContract;
import com.quchwe.cms.main_frame.RepaireFrg.RepairPresenter;
import com.quchwe.cms.myinfo.CarInfoActivity;
import com.quchwe.cms.myinfo.IInfoList;
import com.quchwe.cms.util.NormalUtil.ToastUtil;
import com.quchwe.cms.util.base.BaseActivity;
import com.quchwe.cms.util.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import zxing.activity.CaptureActivity;

public class MainFrameActivity extends BaseActivity {

    public static final String TAG = "MainFrameActi-";
    private HomeContract.View mHomeView;
    private HomeContract.Presenter mHomePresenter;
    private MineContract.View mmineView;
    private RepaireContract.View mRepairView;
    private RepaireContract.Presenter mRepairPresenter;

    public static int SCAN_QR = 10;

    private MineContract.Presenter mminePresenter;
    private DataRepository mDataRepository = DataRepository.instance(this);

    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;
    @Bind(R.id.frame)
    FrameLayout frame;

    @Bind(R.id.navigation)
    BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);
        ButterKnife.bind(this);
        initView();
        initAllFragment();
        initAllPresenter();

        BaseFragment frag = (BaseFragment) mFragmentManager.findFragmentById(R.id.frame);

        if (frag == null) {
            frag = getmHomeView();
            mFragmentManager.beginTransaction().add(R.id.frame, frag, HomeFragment.TAG).commit();
        }

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        getPersimmions();
    }

    private void initView() {
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);

                return true;
            }
        });
    }

    private void initAllPresenter() {
        mHomePresenter = new HomePresenter(mDataRepository, this, getmHomeView());
        mminePresenter = new MinePresenter(mDataRepository, this, getmMineView());
        mRepairPresenter = new RepairPresenter(this, getmRepairView(), mDataRepository);
    }

    private void initAllFragment() {
        Fragment frag = null;
        frag = mFragmentManager.findFragmentByTag(HomeFragment.TAG);
        mHomeView = (frag == null) ? HomeFragment.newInstance() : (HomeFragment) frag;
        frag = mFragmentManager.findFragmentByTag(MineFragment.TAG);
        mmineView = (frag == null) ? MineFragment.newInstance() : (MineFragment) frag;
        frag = mFragmentManager.findFragmentByTag(RepairFragment.TAG);
        mRepairView = (frag == null) ? RepairFragment.newInstance() : (RepairFragment) frag;

    }

    private void selectFragment(MenuItem item) {
        Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.menu_home:
                HomeFragment homeFragment = (HomeFragment) mFragmentManager.findFragmentByTag(HomeFragment.TAG);
                if (homeFragment == null) {
                    homeFragment = getmHomeView();
                }
                if (BaseFragment.CURRENT_FRAGMENT.equals(HomeFragment.TAG)) {
                    break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, homeFragment, HomeFragment.TAG)
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right,
                                R.anim.slide_in_left, R.anim.slide_out_left)
                        .addToBackStack(HomeFragment.TAG)
                        .commit();
                break;
            case R.id.menu_search:
                startActivityForResult((new Intent(this, CaptureActivity.class)), SCAN_QR);
                break;
            case R.id.menu_mine:
                MineFragment mineFragment = (MineFragment) mFragmentManager.findFragmentByTag(MineFragment.TAG);
                if (mineFragment == null) {
                    mineFragment = getmMineView();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, mineFragment, MineFragment.TAG)
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right,
                                R.anim.slide_in_left, R.anim.slide_out_left)
                        .addToBackStack(null)
                        .commit();
                break;
        }

        // update selected item

    }

    public MineFragment getmMineView() {
        return (MineFragment) mmineView;
    }

    public HomeFragment getmHomeView() {
        return (HomeFragment) mHomeView;
    }

    public RepairFragment getmRepairView() {
        return (RepairFragment) mRepairView;
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RepairFragment.INPUT_FILE_REQUEST_CODE || requestCode == RepairFragment.SELECT_PHOTO) {
            RepairFragment fragment = (RepairFragment) mFragmentManager.findFragmentByTag(RepairFragment.TAG);
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == SCAN_QR && resultCode == Activity.RESULT_OK) {
            String qrString = data.getStringExtra(CaptureActivity.QR_STRING);
            //车辆二维码：gd:carId;
            if (qrString.startsWith("gd")){
                String carId = qrString.split(":")[1];
                toCarInfo(carId);

            }else if (qrString.startsWith("http")){
                Intent intent = new Intent(this,WebActivity.class);
                intent.putExtra("url",qrString);
                startActivity(intent);
            }else {
                ToastUtil.showToast(this,"未获取到具体数据");
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && BaseFragment.CURRENT_FRAGMENT != null && BaseFragment.CURRENT_FRAGMENT.equals(HomeFragment.TAG)) {
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);

    }

    public void toCarInfo(String carId){
        mDataRepository.getMyCars(this,carId,0)
                .subscribe(new Subscriber<BaseBean<List<IInfoList>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ",e );
                        ToastUtil.showToast(MainFrameActivity.this,"获取信息失败");
                    }

                    @Override
                    public void onNext(BaseBean<List<IInfoList>> listBaseBean) {
                        if (listBaseBean.getErrCode()==BaseBean.SUCCESS_CODE){

                            if (listBaseBean.getResultInfo().size()>0) {
                                Car car = (Car) listBaseBean.getResultInfo().get(0);

                                Intent intent = new Intent(MainFrameActivity.this, CarInfoActivity.class);
                                intent.putExtra("intent", "qrcode");
                                intent.putExtra("car",car);
                                startActivity(intent);
                            }
                            else {
                                ToastUtil.showToast(MainFrameActivity.this,"未查询到该车辆信息");
                            }
                        }else {
                            ToastUtil.showToast(MainFrameActivity.this,"获取信息失败");
                        }
                    }
                });
    }
}

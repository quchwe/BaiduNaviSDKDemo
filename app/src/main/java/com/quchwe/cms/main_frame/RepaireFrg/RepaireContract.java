package com.quchwe.cms.main_frame.RepaireFrg;

import com.quchwe.cms.util.base.IBasePresenter;
import com.quchwe.cms.util.base.IBaseView;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by quchwe on 2017/4/10 0010.
 */

public interface RepaireContract  {

    interface Presenter extends IBasePresenter{


        void setDefaultData();

        void createRepair(String type, String desc, String drvId, File... files);
    }

    interface View extends IBaseView<Presenter>{

        void createRepair() throws URISyntaxException;

        void setDefaultData(String phone, String driveId);

        void showSuccess();

        void showFailed(String errMsg);
    }
}

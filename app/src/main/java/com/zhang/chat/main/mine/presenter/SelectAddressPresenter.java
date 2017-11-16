package com.zhang.chat.main.mine.presenter;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zhang.chat.app.App;
import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.main.mine.contract.MineContract;
import com.zhang.chat.main.mine.contract.SelectAddressContract;
import com.zhang.chat.net.ApiSubscribe;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;

/**
 * Create by ZhangYan on 2017/9/16.
 */

public class SelectAddressPresenter extends SelectAddressContract.Presenter {


    @Override
    public void onStart() {
        AppLog.e(this.getClass().getName() + " onStart()");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 更新用户信息
     *
     * @param type
     * @param user
     */
    @Override
    public void updateUser(final int type, final User user) {
        mModel.updateUserNet(type, user).subscribe(new ApiSubscribe<User>(context, TAG, 0, false) {
            @Override
            public void onSuccess(int whichRequest, User user1) {
                AppLog.e("============================");
                mModel.updateAddressBySql(user);
                //更新UI
                mView.locationFinish(user.getU_NationID(), user.getU_Province(), user.getU_City(), user.getAddress_message());
            }

            @Override
            public void onError(int whichRequest, Throwable e) {
                e.printStackTrace();
            }
        });
    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    public void startLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(App.getApplication());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    AppLog.e("位置：" + aMapLocation.getAddress());

                    aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    final String country = aMapLocation.getCountry();//国家信息
                    final String province = aMapLocation.getProvince();//省信息
                    final String city = aMapLocation.getCity();//城市信息
                    final String district = aMapLocation.getDistrict();//城区信息
                    final String street = aMapLocation.getStreet();//街道信息
                    final String streetNum = aMapLocation.getStreetNum();//街道门牌号信息
                    final String cityCode = aMapLocation.getCityCode();//城市编码
                    final String adCode = aMapLocation.getAdCode();//地区编码
                    User user = new User();
                    user.setM_Id(Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME)));
                    user.setU_City(city);
                    user.setU_NationID(country);
                    user.setU_Province(province);
                    user.setAddress_message(String.valueOf(street + district + streetNum));
                    updateUser(MinePresenter.UPDATE_ADDRESS, user);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    AppLog.e("AmapError" + "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                    mView.locationFail();
                }
            }
        }
    }
}

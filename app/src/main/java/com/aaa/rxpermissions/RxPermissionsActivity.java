package com.aaa.rxpermissions;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class RxPermissionsActivity extends AppCompatActivity {
    /**
     * 权限组
     */
    private static final String[] permissionsGroup = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_permissions);
        findViewById(R.id.onclick_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRequestEach(v);
            }
        });
    }

/*
    private void 方法一() {
//        创建 RxPermissions 实例
        RxPermissions rxPermissions = new RxPermissions(RxPermissionsActivity.this);
        rxPermissions.request(permissionsGroup)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(RxPermissionsActivity.this, "已获取权限，可以干想干的咯", Toast.LENGTH_LONG)
                                    .show();
                        } else {
//只有用户拒绝开启权限，且选了不再提示时，才会走这里，否则会一直请求开启
                            Toast.makeText(RxPermissionsActivity.this, "主人，我被禁止啦，去设置权限设置那把我打开哟", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }*/

    /**
     * request例子:
     * 不支持返回权限名;
     * 返回的权限结果:全部同意时值true,否则值为false
     */
    public void testRequest(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permissionsGroup)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });

    }

    /**
     * requestEach例子:
     * 把每一个权限的名称和申请结果都列出来
     */
    public void testRequestEach(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(permissionsGroup)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                        } else {
                            // 用户拒绝了该权限，而且选中『不再询问』那么下次启动时，就不会提示出来了，
                        }
                    }
                });
    }

    /**
     * requestEachCombined例子:
     * 返回的权限名称:将多个权限名合并成一个
     * 返回的权限结果:全部同意时值true,否则值为false
     */
    public void testRequestEachCombined(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEachCombined(permissionsGroup)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {

                    }
                });
    }

    /**
     * ensure例子:
     * 必须配合rxjava,回调结果与request一样
     */
    public void testEnsure(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);
        Observable.timer(10, TimeUnit.MILLISECONDS)
                .compose(rxPermissions.ensure(permissionsGroup))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.i("cxw", "申请结果:" + aBoolean);
                    }
                });
    }

    /**
     * ensureEach例子:
     * 必须配合rxjava,回调结果跟requestEach一样
     */
    public void testEnsureEach(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);
        //
        Observable.timer(10, TimeUnit.MILLISECONDS)
                .compose(rxPermissions.ensureEach(permissionsGroup))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        Log.i("cxw", "权限名称:" + permission.name + ",申请结果:" + permission.granted);
                    }
                });
    }

    /**
     * ensureEachCombined例子:
     * 必须配合rxjava,回调结果跟requestEachCombined一样
     */
    public void testEnsureEachCombined(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);
        Observable.timer(10, TimeUnit.MILLISECONDS)
                .compose(rxPermissions.ensureEachCombined(permissionsGroup))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        Log.i("cxw", "权限名称:" + permission.name + ",申请结果:" + permission.granted);
                    }
                });
    }
}

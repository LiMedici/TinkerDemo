package com.medici.tinker;

import android.content.Context;

import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created by medici on 18/3/3.
 * @functon 对Tinker的所有api做一层封装
 */
public class TinkerManager {

    private static boolean isInstalled = false;

    private static ApplicationLike mAppLike;

    private static CustomPatchListener mPatchListener;

    /**
     * 完成Tinker的初始化
     *
     * @param applicationLike
     */
    public static void installTinker(ApplicationLike applicationLike) {
        mAppLike = applicationLike;
        if (isInstalled) {
            return;
        }

        mPatchListener = new CustomPatchListener(getApplicationContext());

        LoadReporter loadReporter = new DefaultLoadReporter(getApplicationContext());
        PatchReporter patchReporter = new DefaultPatchReporter(getApplicationContext());

        AbstractPatch upgradePatchProcessor = new UpgradePatch();
        //完成Tinker初始化
        TinkerInstaller.install(applicationLike,
                loadReporter,
                patchReporter,
                mPatchListener,
                CustomResultService.class,
                upgradePatchProcessor);

        isInstalled = true;
    }

    /**
     * 完成Patch文件的加载
     * @param path patch文件的路径
     * @param md5Value md5验证
     */
    public static void loadPatch(String path, String md5Value) {
        if (Tinker.isTinkerInstalled()) {
            mPatchListener.setCurrentMD5(md5Value);
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }
    }

    /**
     * 通过ApplicationLike获取Context
     * @return 上下文对象
     */
    private static Context getApplicationContext() {
        if (mAppLike != null) {
            return mAppLike.getApplication().getApplicationContext();
        }
        return null;
    }
}

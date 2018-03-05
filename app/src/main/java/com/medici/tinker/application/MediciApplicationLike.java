package com.medici.tinker.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.medici.tinker.TinkerManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * ***************************************
 *
 * @desc:
 * @author：李宗好
 * @time: 2018/3/3 0003 12:54
 * @email：lzh@cnbisoft.com
 * @version：
 * @history: ***************************************
 */
@DefaultLifeCycle(
        //application name to generate
        application = "com.medici.tinker.application.MediciApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL)
public class MediciApplicationLike extends ApplicationLike{

    public MediciApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        TinkerManager.installTinker(this);
    }
}

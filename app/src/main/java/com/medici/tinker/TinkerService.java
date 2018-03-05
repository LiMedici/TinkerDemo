package com.medici.tinker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import java.io.File;

/**
 * @author mrmedici
 * @function 应用程序Tinker更新服务：
 * 1.从服务器下载patch文件
 * 2.使用TinkerManager完成patch文件加载
 * 3.patch文件会在下次进程启动时生效
 */
public class TinkerService extends Service {

    /**
     * 文件后缀名
     */
    private static final String FILE_END = ".apk";
    /**
     * 下载patch文件信息
     */
    private static final int DOWNLOAD_PATCH = 0x01;
    /**
     * 检查是否有patch更新
     */
    private static final int UPDATE_PATCH = 0x02;
    /**
     * patch要保存的文件夹
     */
    private String mPatchFileDir;
    /**
     * patch文件保存路径
     */
    private String mFilePatch;


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case UPDATE_PATCH:
                    checkPatchInfo();
                    break;
                case DOWNLOAD_PATCH:
                    downloadPatch();
                    break;
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //检查是否有patch更新
        mHandler.sendEmptyMessage(UPDATE_PATCH);
        //被系统回收不再重启
        return START_NOT_STICKY;
    }


    @Override
    /**
     * 用来与被启动者通信的接口
     */
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 对外提供启动Service方法
     * @param context 上下文对象
     */
    public static void runTinkerService(Context context) {
        try {
            Intent intent = new Intent(context, TinkerService.class);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化变量
     */
    private void init() {
        mPatchFileDir = getCacheDir().getAbsolutePath() + "/patch/";
        File patchFileDir = new File(mPatchFileDir);
        try {
            if (patchFileDir == null || !patchFileDir.exists()) {
                //文件夹不存在则创建
                patchFileDir.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //无法正常创建文件，则终止服务
            stopSelf();
        }
    }

    /**
     * 检查patch文件的更新
     */
    private void checkPatchInfo() {

    }

    /**
     * 下载patch文件
     */
    private void downloadPatch() {

        mFilePatch = mPatchFileDir.concat(String.valueOf(System.currentTimeMillis()))
                .concat(FILE_END);

    }
}

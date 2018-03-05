package com.medici.tinker;

import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;

/**
 * @author: medici
 * @function: 1.较验patch文件是否合法  2.启动Service去安装patch文件
 * @date: 18/3/3
 */
public class CustomPatchListener extends DefaultPatchListener {

    private String currentMD5;

    public void setCurrentMD5(String md5Value) {

        this.currentMD5 = md5Value;
    }

    public CustomPatchListener(Context context) {
        super(context);
    }

    @Override
    protected int patchCheck(String path, String patchMd5) {
        return super.patchCheck(path, patchMd5);
    }
}

package com.sky;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
    }

    private void reInstall(Uri path) {
        if (null == path) {
            return;
        }
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(path, "application/vnd.android.package-archive");//第二个参数不要问为什么 就这样写
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(install);
    }

    private Uri getPath() {
        String apkPath = null;
        if (Environment
                .getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            apkPath = Environment.getExternalStorageDirectory().getPath() + "/install/app-debug.apk";
        } else {
            Toast.makeText(this, "沒SD卡没辙", Toast.LENGTH_SHORT).show();
            return null;
        }
        File apkFile = new File(apkPath);
        if (!apkFile.exists()) {
            Toast.makeText(this, "把安装包带install文件夹拷进SD卡根目录", Toast.LENGTH_SHORT).show();
            return null;
        }
        return Uri.fromFile(new File(apkPath));
    }

    @Override
    public void onClick(View v) {
        reInstall(getPath());
    }
}

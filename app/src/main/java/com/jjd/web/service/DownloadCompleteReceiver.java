package com.jjd.web.service;


import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.jjd.web.sp.Sp;

import java.io.File;


// DownloadManager.ACTION_DOWNLOAD_COMPLETE
public class DownloadCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        long id = Sp.inst(context).getLong(Sp.SP_DOWNLOAD_ID);
        String url = Sp.inst(context).getString(Sp.SP_DOWNLOAD_URL);
        if (reference != -1 && reference == id) {

            Intent data = new Intent(Intent.ACTION_VIEW);

            File fileDonwloaded = new File(url);
            if (fileDonwloaded.exists()) {
                Uri u = Uri.fromFile(fileDonwloaded);
                data.setDataAndType(u, "application/vnd.android.package-archive");
                data.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(data);
            }
            Sp.inst(context).saveData(Sp.SP_DOWNLOAD_ID, 0);
            Sp.inst(context).saveData(Sp.SP_DOWNLOAD_URL, "");
        }
    }
}

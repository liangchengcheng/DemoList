package fwq.hdsx.com.demolist;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProgressUtil {

    private  static Dialog loadingDialog ;

    public static Dialog showProgressWithMsg(Context context, String msg){
        LayoutInflater inflater = LayoutInflater.from(context);
        View v;
        if (Build.VERSION.SDK_INT < 14) {
            // 得到加载view
            v = inflater.inflate(R.layout.progressbar_lay, null);
        } else {
            // 得到加载view
            v = inflater.inflate(R.layout.layout_progress, null);
        }

        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);
        TextView tipTextView = (TextView) v.findViewById(R.id.progress_tv);
        tipTextView.setText(msg);
        loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
        return loadingDialog;
    }

    public static void closeProgress(){
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}

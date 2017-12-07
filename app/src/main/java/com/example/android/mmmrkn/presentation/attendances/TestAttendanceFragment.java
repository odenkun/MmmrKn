package com.example.android.mmmrkn.presentation.attendances;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.android.mmmrkn.R;
/**
 * Created by 15110012 on 2017/12/01.
 */

public class TestAttendanceFragment  extends DialogFragment
{

    public static TestAttendanceFragment newInstance()
    {
        return new TestAttendanceFragment();
    }
    //ダイアログの作成
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.attendances_viewbase, null);

        builder.setView(dialogView)
                .setTitle("Fragment Dialog");
               /* .setPositiveButton("", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ((MainActivity)activity).onTestDialogOKClick();
                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        TestDialogFragment.this.dismiss();
                    }
                });
                */
        return builder.create();
    }
}
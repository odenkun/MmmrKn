package com.example.android.mmmrkn.presentation.attendances_list;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.mmmrkn.R;


public class AttendancesListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //タブレット、スマホの確認してレイアウトset
        if (isHoneycombTablet(this)) {
            setContentView(R.layout.activity_attendances_list);
        } else {
            setContentView(R.layout.activity_attendances_list);
        }
    }

    //タブレットかスマホの確認
    public static boolean isHoneycomb() {
        return true;
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    public static boolean isHoneycombTablet(Context context) {
        return isHoneycomb() && isTablet(context);
    }

}

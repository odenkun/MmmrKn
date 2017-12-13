package com.example.android.mmmrkn.presentation.attendances.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.presentation.attendances.AttendancesActivity;


public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.ViewHolder>{
    private String[] list;
    private Context context;
    
    
    public CardRecyclerAdapter(Context context, String[] stringArray) {
        super();
        this.list = stringArray;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return list.length;
    }
    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        vh.textView_main.setText(list[position]);
        vh.textView_sub.setText("異常なし");
        vh.imageView.setImageResource(R.mipmap.ic_launcher);
        vh.layout.setOnClickListener(view-> {

            //フラグメントを非表示にする
            AttendancesActivity activity = (AttendancesActivity)view.getContext();
            TestAttendanceFragment fragment = (TestAttendanceFragment) activity.getSupportFragmentManager().findFragmentByTag(AttendancesActivity.TAG);
            fragment.dismiss();

        });
    }
    //Viewを纏めたフォルダの作成
    @Override
    public CardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.attendances_recycler, parent, false);
        return new ViewHolder(v);
    }
    //Viewフォルダの初期化設定
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_main;
        TextView textView_sub;
        LinearLayout layout;
        ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            textView_main = v.findViewById(R.id.textView_main);
            textView_sub = v.findViewById(R.id.textView_sub);
            layout = v.findViewById(R.id.layout);
            imageView = v.findViewById(R.id.imageView);
        }
    }
}
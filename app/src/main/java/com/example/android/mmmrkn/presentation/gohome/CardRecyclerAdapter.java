package com.example.android.mmmrkn.presentation.gohome;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.mmmrkn.R;

/**
 * Created by 15110012 on 2017/11/21.
 */

public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.ViewHolder>{
    private String[] list;
    private Context context;
    //listの中にvaluesのstringsを挿入
    public CardRecyclerAdapter(Context context, String[] stringArray) {
        super();
        this.list = stringArray;
        this.context = context;
    }
    //getItemCountで項目数取得、項目数を返す
    @Override
    public int getItemCount() {
        return list.length;
    }
    //Cardの中身表示
    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        vh.textView_main.setText(list[position]);
        vh.textView_sub.setText("異常なし");
        vh.imageView.setImageResource(R.mipmap.ic_launcher);
        //クリックしたときに移動
        vh.layout.setOnClickListener(view-> {
            GoHomeActivity activity = (GoHomeActivity) view.getContext();
            TextView name=activity.findViewById(R.id.text_Name);
            name.setText(list[position]);
            Button back=activity.findViewById(R.id.button_Back);
            back.setText("戻る");
            TestDialogFragment fragment = (TestDialogFragment) activity.getSupportFragmentManager().findFragmentByTag(GoHomeActivity.TAG);
            fragment.dismiss();
        });
    }
    //Viewを纏めたフォルダの作成
    @Override
    public CardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    //Viewフォルダの初期化設定
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_main;
        TextView textView_sub;
        LinearLayout layout;
        ImageView imageView;

        public ViewHolder(View v) {
            super(v);

            Log.d("vh","called");

            textView_main = v.findViewById(R.id.textView_main);
            textView_sub = v.findViewById(R.id.textView_sub);
            layout = v.findViewById(R.id.layout);
            imageView = v.findViewById(R.id.imageView);
        }
    }
}
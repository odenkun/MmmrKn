package com.example.android.mmmrkn.presentation.student_search;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.mmmrkn.R;


public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.ViewHolder>{
    private String[] list;
    private Context context;
    //listの中にvaluesのstringsを挿入
    public CardRecyclerAdapter(Context context,String[] stringArray) {
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
        vh.textView_group.setText("さくら組");
        //vh.condition.setImageDrawable();
        //vh.layout.setBackgroundColor(Color.rgb(250, 219, 218));
        //vh.layout.setBackgroundColor(Color.rgb(217, 233, 250));
        vh.imageView.setImageResource(R.mipmap.ic_launcher);
        //クリック処理
        vh.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //
            }
        });
    }
    //Viewを纏めたフォルダの作成
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.student_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    //Viewフォルダの初期化設定
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_main;
        TextView textView_sub;
        TextView textView_group;
        LinearLayout layout;
        ImageView imageView;
        ImageView condition;

        public ViewHolder(View v) {
            super(v);
            textView_main = v.findViewById(R.id.textView_main);
            layout = v.findViewById(R.id.cardLayout);
            imageView = v.findViewById(R.id.imageView);
            textView_group =v.findViewById(R.id.textView_group);
            imageView = v.findViewById(R.id.condition);
            //Drawable drawable = getResources().getDrawable(~);
        }
    }
}
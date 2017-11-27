package com.example.android.mmmrkn.presentation.party;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mmmrkn.R;


public class PartyCardRecyclerAdapter extends RecyclerView.Adapter<PartyCardRecyclerAdapter.ViewHolder>{
    private String[] list;
    private Context context;
    //listの中にvaluesのstringsを挿入
    public PartyCardRecyclerAdapter(Context context, String[] stringArray) {
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
        vh.textView_class.setText(list[position]);

    }
    //Viewを纏めたフォルダの作成
    @Override
    public PartyCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.layout_party_recycler_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    //Viewフォルダの初期化設定
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_class;

        public ViewHolder(View v) {
            super(v);
            textView_class =(TextView)v.findViewById(R.id.textView_class);
        }
    }
}
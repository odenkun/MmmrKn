package com.example.android.mmmrkn.presentation.party;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.infra.entity.Party;

import java.util.List;


public class PartyCardRecyclerAdapter extends RecyclerView.Adapter<PartyCardRecyclerAdapter.ViewHolder>{
    private Context context;
    private List<Party> parties;
    //listの中にvaluesのstringsを挿入
    public PartyCardRecyclerAdapter(Context context, List<Party> partiesArrayList) {
        super();
        this.parties = partiesArrayList;
        this.context = context;
    }
    //getItemCountで項目数取得、項目数を返す
    @Override
    public int getItemCount() {
        if (parties != null) {
            return parties.size();
        } else {
            return 0;
        }
    }

    //Cardの中身表示
    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        //サイズ、nullチェック
        if (parties != null && parties.size() > position && parties.get(position) != null) {
            //Ormaから持ってきたデータ代入
            vh.name.setText(parties.get(position).getName());
        }
        // クリック時、モード選択画面に移動
        vh.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
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
        TextView name;
        LinearLayout layout;

        public ViewHolder(View v) {
            super(v);
            name =(TextView)v.findViewById(R.id.textView_party);
            layout = (LinearLayout)v.findViewById(R.id.layout);
        }
    }
}
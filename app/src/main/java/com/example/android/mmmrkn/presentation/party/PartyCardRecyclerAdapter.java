package com.example.android.mmmrkn.presentation.party;


import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.presentation.attendances.AttendancesActivity;
import com.example.android.mmmrkn.presentation.attendances_list.AttendancesDialog;

import java.util.List;


public class PartyCardRecyclerAdapter extends RecyclerView.Adapter<PartyCardRecyclerAdapter.ViewHolder> {
    private AttendancesDialog dialogFragment;
    private List<Party> parties;

    //listの中にvaluesのstringsを挿入
    public PartyCardRecyclerAdapter(AttendancesDialog dialogFragment, List<Party> partiesArrayList) {
        super();
        this.dialogFragment = dialogFragment;
        this.parties = partiesArrayList;
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
                    String partyId;

                    @Override
                    public void onClick(View v) {
                        dialogFragment.onSelectParty(partyId);
                        dialogFragment.dismiss();
                    }

                    public View.OnClickListener setPartyId(String partyId) {
                        this.partyId = partyId;
                        return this;
                    }
                }.setPartyId(parties.get(position).getPartyId())
        );
    }

    //Viewを纏めたフォルダの作成
    @Override
    public PartyCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(dialogFragment.getContext());
        View v = layoutInflater.inflate(R.layout.party_recycler, parent, false);
        return new ViewHolder(v);
    }

    //Viewフォルダの初期化設定
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        LinearLayout layout;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.textView_party);
            layout = (LinearLayout) v.findViewById(R.id.layout);
        }
    }
}
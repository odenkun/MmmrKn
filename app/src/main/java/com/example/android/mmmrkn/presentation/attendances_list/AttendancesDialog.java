package com.example.android.mmmrkn.presentation.attendances_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.example.android.mmmrkn.R;

import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.presentation.App;


import java.util.List;

//クラスを選ぶダイアログ。出席リストの上に表示される
public class AttendancesDialog extends DialogFragment {

    Contract contract;
    List<Party> partyList;

    private AttendancesDialog setContract(Contract contract) {
        this.contract = contract;
        return this;
    }

    private AttendancesDialog setPartyList(List<Party> partyList) {
        this.partyList = partyList;
        return this;
    }

    public static AttendancesDialog newInstance(List<Party> partyList,Contract contract) {
        return new AttendancesDialog().setPartyList(partyList).setContract(contract);
    }

    //ダイアログの作成
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ((App) getContext().getApplicationContext())
                .getComponent()
                .inject(this);
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.activity_party_search, null);
        PartyCardRecyclerView recyclerView = dialogView.findViewById(R.id.recycler_party);
        recyclerView.onPartyListFetch(this,partyList);


        //ダイアログ表示
        builder.setView(dialogView)
                .setTitle("クラス一覧");

        return builder.create();
    }

    @Override
    public void onDetach() {
        this.contract = null;
        this.partyList = null;
        super.onDetach();
    }

    public void onSelectParty (String partyId, String partyName) {
        contract.onSelectParty(partyId,partyName);
    }

    public interface Contract {
        void onSelectParty(String partyId,String partyName);
    }
}
package com.example.android.mmmrkn.presentation.attendances_list;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.attendancesList.AttendancesListModule;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.App;

import java.util.List;

import javax.inject.Inject;


public class AttendancesListActivity extends AppCompatActivity implements AttendancesListPresenter.Contract, AttendancesDialog.Contract{
    static final int TEST_DIALOG = 0;

    private static final String STUDENT_KEY = "STUDENTKEY";

    @Inject
    AttendancesListPresenter presenter;

    List<Party> partyList;
    private List<Student> studentList;
    private Party party;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //タブレット、スマホの確認してレイアウトset
        if (isHoneycombTablet(this)) {
            setContentView(R.layout.layout_attendances_recycler);
        } else {
            setContentView(R.layout.layout_attendances_recycler);
        }
        ((App) getApplication())
                .getComponent()
                .plus(new AttendancesListModule(this))
                .inject(this);
        presenter.fetchParties();
        //クラス一覧ボタンの押下処理
        findViewById(R.id.button_party).setOnClickListener(arg -> {
            showFragmentDialog(TEST_DIALOG);
            //ダイアログの表示
        });

    }


//    @Override
//    public void onFragmentListClick(String select) {
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        StudentProfileFragment fragment = new StudentProfileFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("name", select);
//        fragment.setArguments(bundle);
//        if(isHoneycombTablet(this)){
//            ft.add(R.id.ft_student_profile,fragment);
//        }else{
//            ft.add(R.id.ft_student_profile,fragment);
//        }
//        ft.addToBackStack(null);
//        ft.commit();
//    }


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
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (studentList != null) {
            outState.putSerializable(STUDENT_KEY, studentList.toArray(new Student[]{}));
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        findViewById(R.id.button_party).callOnClick();
    }

    /**
     * フラグメントダイアログを表示する。
     */

    public void showFragmentDialog(int id) {
        if (partyList == null) {
            Toast.makeText(this, "通信の途中または失敗しました。", Toast.LENGTH_LONG).show();
            return;
        }
        switch (id) {
            case TEST_DIALOG:
                DialogFragment dialogFragment = AttendancesDialog.newInstance(partyList, this);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(dialogFragment, null);
                ft.commitAllowingStateLoss();

        }
    }


    @Override
    public void onPartyListFetch(List<Party> partyList) {
        //ぐるぐるとめる
        if (partyList != null) {
            this.partyList = partyList;
        } else {
            Toast.makeText(this, "通信に失敗しました", Toast.LENGTH_LONG).show();
        }
    }

    //Ormaから生徒一覧をListに代入
    @Override
    public void onEntryListFetched(List<Student> attendancesList) {
        if (attendancesList == null) {
            DialogFragment newFragment = new AttendancesListCardRecyclerAdapter.AlertDialogFragment();
            FragmentManager manager = getSupportFragmentManager();
            newFragment.show(manager,"該当児童は0人です");
            return;
        }
        AttendancesListCardRecyclerView cardRecyclerView = findViewById(R.id.recycler_attendances);
        cardRecyclerView.onStudentListFetch(this, attendancesList,this.party);
        studentList = attendancesList;
        //データとしてlog出力なし
    }


    @Override
    protected void onDestroy() {
        //通信の結果を受け取らなくする
        presenter.dispose();
        super.onDestroy();
    }

    @Override
    public void onSelectParty(String partyId, String partyName) {
        presenter.fetchEntryList(partyId);
        Party party = new Party();
        party.setPartyId(partyId);
        party.setName(partyName);
        this.party = party;
        TextView viewParty = this.findViewById(R.id.textView_party);
        viewParty.setText(partyName + "組");
    }
}

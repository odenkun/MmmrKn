package com.example.android.mmmrkn.presentation.attendances_list;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.attendancesList.AttendancesListModule;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.gohome.TestDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;


public class AttendancesListActivity extends AppCompatActivity implements AttendancesListPresenter.Contract, AttendancesDialog.Contract {
    static final int TEST_DIALOG = 0;

    private static final String STUDENT_KEY = "STUDENTKEY";

    @Inject
    AttendancesListPresenter presenter;

    List<Party> partyList;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_attendances_recycler);
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
                dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
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
        AttendancesListCardRecyclerView cardRecyclerView = findViewById(R.id.recycler_attendances);
        cardRecyclerView.onStudentListFetch(this, attendancesList);
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
        TextView viewParty = this.findViewById(R.id.textView_party);
        viewParty.setText(partyName + "組");
    }
}

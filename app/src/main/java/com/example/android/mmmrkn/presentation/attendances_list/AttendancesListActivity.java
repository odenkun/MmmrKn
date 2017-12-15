package com.example.android.mmmrkn.presentation.attendances_list;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.attendancesList.AttendancesModule;
import com.example.android.mmmrkn.infra.entity.Attendances;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.infra.entity.StudentProfile;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.gohome.TestDialogFragment;

import java.util.List;

import javax.inject.Inject;



public class AttendancesListActivity extends AppCompatActivity
        implements AttendancesPresenter.Contract,AttendancesDialog.Contract {
    static final int TEST_DIALOG = 0;

    private static final String STUDENT_KEY = "STUDENTKEY";
    private static final String PARTY_KEY = "PARTYKEY";

    private List<StudentProfile> studentProfiles;
    private List<Party> parties;

    @Inject
    AttendancesPresenter presenter;

    List<Party> partyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_attendances_recycler);

        ((App) getApplication())
                .getComponent()
                .plus(new AttendancesModule(this))
                .inject(this);
        parties = (List<Party>) savedInstanceState.getSerializable(PARTY_KEY);

        if (parties == null) {
            //クラス一覧取得の通信
            presenter.fetchParties();
        }
        //studentProfiles = (List<StudentProfile>) savedInstanceState.getSerializable(STUDENT_KEY);
        studentProfiles = (List<StudentProfile>) savedInstanceState.getSerializable(STUDENT_KEY);
        //Object oreMap = ((Object[]) savedInstanceState.getSerializable(STUDENT_KEY));


        //クラス一覧ボタンの押下処理
        findViewById(R.id.button_party).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                showFragmentDialog(TEST_DIALOG);
                //ダイアログの表示
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //outState.putSerializable(STUDENT_KEY, studentProfiles);
        //outState.putSerializable(PARTY_KEY, parties);
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
            Toast.makeText(this,"通信の途中または失敗しました。",Toast.LENGTH_LONG).show();
            return;
        }
        switch (id) {
            case TEST_DIALOG:
                DialogFragment dialogFragment = AttendancesDialog.newInstance(partyList,this);
                dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
        }
    }

    //Ormaから生徒一覧をListに代入
    @Override
    public void onEntryListFetched(List<Attendances> attendancesList) {
        AttendancesListCardRecyclerView cardRecyclerView = findViewById(R.id.recycler_attendances);

        cardRecyclerView.onStudentListFetch(this, attendancesList);
        //データとしてlog出力なし
    }

    @Override
    public void onPartyListFetch(List<Party> partyList) {
        //ぐるぐるとめる

        if (partyList != null) {
            this.partyList = partyList;
        }else {
            Toast.makeText(this,"通信に失敗しました",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onDestroy() {
        //通信の結果を受け取らなくする
        presenter.dispose();
        super.onDestroy();
    }

    @Override
    public void onSelectParty(String partyId) {
        presenter.fetchEntryList(partyId);
    }
}
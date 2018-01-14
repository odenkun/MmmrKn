package com.example.android.mmmrkn.presentation.attendances_list.students;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.di.attendancesList.AttendancesListModule;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.attendances_list.AttendancesListActivity;

import java.util.List;

import javax.inject.Inject;

public class AttendancesFragment extends Fragment implements AttendancesListPresenter.Contract{

    private static final String STUDENT_KEY = "STUDENT_KEY", PARTY_KEY = "PARTY_KEY";

    AttendancesListCardRecyclerView recyclerView;
    ArrayAdapter<String> adapter;
    List<Party> parties;
    Party selectedParty;

    List<Student> attendances;
    @Inject
    AttendancesListPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.attendances_viewbase, container, false );
        ((App) getActivity ().getApplication())
                .getComponent()
                .plus(new AttendancesListModule (this))
                .inject(this);

        presenter.fetchParties ();

        // RecyclerViewの参照を取得
        recyclerView = view.findViewById(R.id.recycler_attendances);
        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity ()));

        adapter = new ArrayAdapter<>(getActivity (), R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        AppCompatSpinner spinner = (AppCompatSpinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirst = true;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst) {
                    isFirst = false;
                    return;
                }
                Spinner spinner = (Spinner)parent;
                String selectedName = (String)spinner.getSelectedItem();
                for ( Party party : parties ) {
                    if (selectedName.equals ( party.getName () )) {
                        selectedParty = party;
                        presenter.fetchEntryList ( party.getPartyId () );
                        return;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    @Override
    public void onPartyListFetch(List<Party> partyList) {
        //ぐるぐるとめる
        if (partyList != null) {
            this.parties = partyList;
            for ( Party party : partyList ) {
                adapter.add(party.getName ());
            }
            adapter.notifyDataSetChanged ();
            if (partyList.size () > 0) {
                selectedParty = partyList.get(0);
                presenter.fetchEntryList (partyList.get ( 0 ).getPartyId () );
            }
        } else {
            Toast.makeText(getActivity (), "通信に失敗しました", Toast.LENGTH_LONG).show();
        }
    }

    //Ormaから生徒一覧をListに代入
    @Override
    public void onEntryListFetched(List<Student> attendancesList) {
        if (attendancesList == null) {
            DialogFragment newFragment = new AttendancesListCardRecyclerAdapter.AlertDialogFragment();
            FragmentManager manager = ((AttendancesListActivity)getActivity()).getSupportFragmentManager();
            newFragment.show(manager,"該当児童は0人です");
            recyclerView.onStudentListIsEmpty ( selectedParty.getName () );
            return;
        }
        attendances = attendancesList;
        recyclerView.onStudentListFetch ( attendancesList, selectedParty.getName ());
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (parties != null) {
            outState.putSerializable(PARTY_KEY, parties.toArray(new Party[]{}));
        }
        if (attendances != null) {
            outState.putSerializable(STUDENT_KEY, attendances.toArray(new Student[]{}));
        }
    }
    @Override
    public void onDestroy() {
        //通信の結果を受け取らなくする
        presenter.dispose();
        super.onDestroy();
    }
}

package com.example.android.mmmrkn.presentation.attendances_list;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.infra.entity.Student;

import java.util.List;

/**
 * Created by 15110009 on 2017/12/19.
 */

public class AttendancesFragment extends Fragment {

    private final Activity mActivity = null;

    // RecyclerViewとAdapter
    private RecyclerView mRecyclerView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate ( R.layout.attendances_viewbase, container, false );

        // RecyclerViewの参照を取得
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_attendances);
        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        return mView;
    }


    public void onActivityCreated(Bundle savedInstanceState, List<Student> studentTList) {
        super.onActivityCreated(savedInstanceState);
        Party party = new Party();
        party.setName("test");
        party.setPartyId("test");

        AttendancesListCardRecyclerAdapter atteAda = new AttendancesListCardRecyclerAdapter ( mActivity, studentTList, party );
        mRecyclerView.setAdapter( atteAda );
    }

    //ListのClick情報を通知するListener
    public interface onFragmentListClickedListener {
        void onFragmentListClick ( String select );
    }

    //Interfaceを登録する
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onFragmentListClickedListener listener = (onFragmentListClickedListener) activity;
    }
}

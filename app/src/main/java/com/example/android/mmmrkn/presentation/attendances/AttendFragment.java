package com.example.android.mmmrkn.presentation.attendances;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.FragmentAttendBinding;
import com.example.android.mmmrkn.di.attendances.AttendancesModule;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.App;

import java.util.List;

import javax.inject.Inject;


public class AttendFragment extends Fragment implements AttendFragmentPresenter.Contract{
    @Inject
    AttendFragmentPresenter presenter;

    public AttendFragment () {
        // Required empty public constructor
    }

    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        FragmentAttendBinding binding = DataBindingUtil.inflate (inflater, R.layout.fragment_attend ,container,true);
        Student profile = new Student ();
        profile.setName ( "aaaaaaa" );
        binding.setStudent ( profile );
        return binding.getRoot ();
    }


    @Override
    public void onAttach ( Context context ) {
        super.onAttach ( context );
        ( (App) getActivity().getApplication () )
                .getComponent ()
                .plus ( new AttendancesModule ( this ) )
                .inject ( this );
    }

    void onScanQR (String studentId) {
        presenter.fetchStudent ( studentId );
    }

    @Override
    public void onDetach () {
        super.onDetach ();
        presenter.dispose ();
    }
    @Override
    public void onConnectionFailed () {
        Toast.makeText ( getActivity (), "接続に失敗しました。", Toast.LENGTH_SHORT ).show ();
        getActivity ().finish();
    }

    @Override
    public void onBTDeviceDisconnected () {
        Toast.makeText ( getActivity (), "マイクが取り外されました。", Toast.LENGTH_SHORT ).show ();
        getActivity ().finish();
    }

    @Override
    public void onNameRecognized ( List<Student> profiles ) {
        if (profiles == null || profiles.size () < 1) {
            throw new RuntimeException ( "リストが不正" );
        }
        if (profiles.size () == 1) {
            // TODO: 2017/12/20 バインディングに設定

        }else {
            // TODO: 2017/12/20 ダイアログ表示

        }
    }
}

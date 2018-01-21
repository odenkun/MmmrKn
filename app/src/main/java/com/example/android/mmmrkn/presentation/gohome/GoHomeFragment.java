package com.example.android.mmmrkn.presentation.gohome;


import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.FragmentGoHomeBinding;
import com.example.android.mmmrkn.di.attendances.AttendancesModule;
import com.example.android.mmmrkn.di.gohome.GoHomeModule;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.example.android.mmmrkn.presentation.App;
import com.example.android.mmmrkn.presentation.qr.QRFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GoHomeFragment extends Fragment implements GoHomePresenter.Contract {

    public ObservableField<Student> student = new ObservableField <> (  );
    private Teacher teacher;
    @Inject
    public GoHomePresenter presenter;

    public GoHomeFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {
        FragmentGoHomeBinding binding = DataBindingUtil.inflate ( inflater, R.layout.fragment_go_home, container, true );
        binding.setFragment ( this );
        App app = (App) getActivity ().getApplication ();
        teacher = app.getTeacher ();
        app.getComponent ()
                .plus ( new GoHomeModule ( this ) )
                .inject ( this );
        return binding.getRoot ();
    }
    @Override
    public void onStart () {
        super.onStart ();
        EventBus.getDefault ().register ( this );
    }

    @Override
    public void onStop () {
        super.onStop ();
        EventBus.getDefault ().unregister ( this );

    }
    public void onDropBtnClick(View view) {
        presenter.registerAttend ( student.get (), teacher.getTeacherId () );
    }

    @Override
    public void onStudentFetched ( Student student ) {
        if (student != null) {
            this.student.set ( student );

        }else{
            Toast.makeText ( getContext (),"生徒情報の取得に失敗しました。", Toast.LENGTH_LONG ).show ();
        }
    }

    @Override
    public void onGoHomeRegistered ( boolean result ) {
        if (result) {
            Toast.makeText ( getContext (),"降園を登録しました。", Toast.LENGTH_SHORT ).show ();
            student = null;
        }else{
            Toast.makeText ( getContext (),"降園を登録に失敗しました。", Toast.LENGTH_SHORT ).show ();
        }
    }

    @Subscribe
    public void onScanQR ( QRFragment.QREvent qrEvent ) {
        if (student.get() != null) {
            if ( qrEvent.studentId.equals ( student.get().getStudentId () ) ) {
                return;
            }
        }
//        Student student = new Student ();
//        student.setStudentId ( qrEvent.studentId );
//        viewModel.setStudent ( student );
        // TODO: 2017/12/26 くるくるの表示
        presenter.fetchStudent ( qrEvent.studentId );
    }
}

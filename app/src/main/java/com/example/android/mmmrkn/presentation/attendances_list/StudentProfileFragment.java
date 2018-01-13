package com.example.android.mmmrkn.presentation.attendances_list;

import android.app.Fragment;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mmmrkn.BR;
import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.ActivityStudentProfileBinding;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.attendances_list.students.AttendancesListCardRecyclerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import timber.log.Timber;

/**
 * Created by 15110009 on 2017/12/19.
 */

public class StudentProfileFragment extends Fragment {
    ViewModel viewModel;

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

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActivityStudentProfileBinding binding = DataBindingUtil.inflate ( inflater, R.layout.activity_student_profile, container, false );
        viewModel = new ViewModel ();
        Student student = new Student ();
        student.setName ( "naaaaaaaa" );
        viewModel.setStudent ( student );
        binding.setViewmodel ( viewModel );

        return binding.getRoot ();
    }
    @Subscribe
    public void onStudentSelected( AttendancesListCardRecyclerAdapter.StudentSelectedEvent event) {
        viewModel.setStudent ( event.getStudent () );
    }
    public static class ViewModel extends BaseObservable {
        Student student;
        public Student getStudent () {
            return student;
        }
        public void setStudent ( Student student ) {
            this.student = student;
            Timber.d(student.toString ());
            notifyPropertyChanged (BR.student);
        }
    }
}

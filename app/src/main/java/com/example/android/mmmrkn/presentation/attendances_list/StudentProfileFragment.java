package com.example.android.mmmrkn.presentation.attendances_list;

import android.app.Fragment;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mmmrkn.BR;
import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.databinding.FragmentStudentProfileBinding;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.attendances_list.students.AttendancesListCardRecyclerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import timber.log.Timber;

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
        FragmentStudentProfileBinding binding = DataBindingUtil.inflate ( inflater, R.layout.fragment_student_profile, container, false );
        viewModel = new ViewModel ();
        binding.setViewmodel ( viewModel );

        return binding.getRoot ();
    }
    @Subscribe
    public void onStudentSelected( AttendancesListCardRecyclerAdapter.StudentSelectedEvent event) {
        viewModel.setSelectedStudent ( event.getStudent () );
    }


    public static class ViewModel extends BaseObservable {

        private Student selectedStudent;
        public  static  final  String good = "good";
        public  static  final  String subtle = "subtle";
        public  static  final  String bad = "bad";
        @Bindable
        public Student getSelectedStudent () {
            return selectedStudent;
        }
        public void setSelectedStudent ( Student student ) {
            this.selectedStudent = student;
            Timber.d(student.toString ());
            notifyPropertyChanged ( BR.selectedStudent );
        }
    }
}

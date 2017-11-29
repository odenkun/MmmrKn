package com.example.android.mmmrkn.presentation.studentprofile;

import com.example.android.mmmrkn.infra.api.StudentsService;
import com.example.android.mmmrkn.infra.entity.StudentProfile;
import com.example.android.mmmrkn.presentation.Presenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by 15110016 on 2017/11/21.
 */

public class StudentProfilePresenter extends Presenter{

    private Contract contract;

    private StudentsService studentsService;

    @Inject
    public StudentProfilePresenter(Contract contract,StudentsService studentsService){
        this.contract = contract;
        this.studentsService = studentsService;

    }


    void  fetchProfile(String studentId){
        //まだ終わっていない通信があるとき中断
        if (this.hasDisposables ()) {
            return;
        }

        disposables.add(studentsService.getStudentProfile(studentId)
        .subscribeOn(Schedulers.io ())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(studentProfile->{
            Timber.d("プロフィール取得");
            contract.onFetchComplete(studentProfile);
        },e->{
            Timber.e(e);
            contract.onFetchComplete(null);
        }));
    }



    public interface Contract {
        /**
         *
         * @param  sProfile
         */
        void  onFetchComplete (StudentProfile sProfile);
    }
}

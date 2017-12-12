package com.example.android.mmmrkn.presentation.studentprofile;

import com.example.android.mmmrkn.infra.api.StudentsService;
import com.example.android.mmmrkn.infra.entity.AttendancesLog;
import com.example.android.mmmrkn.infra.entity.StudentProfile;
import com.example.android.mmmrkn.presentation.Presenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by 友末 on 2017/11/21.
 *
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
        // 現在の時刻を取得
        Date date = new Date();
        // 表示形式を設定
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd");
        String today = sdf.format(date);
        
        
        disposables.add(studentsService.getStudentProfile(studentId)
        .subscribeOn(Schedulers.io ())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(studentProfile->{
            Timber.d("プロフィール取得");
            contract.onFetchProfileComplete(studentProfile);
        },e->{
            Timber.e(e);
            contract.onFetchProfileComplete(null);
        }));
        
        disposables.add(studentsService.getAttendanceLog(studentId,today)
                .subscribeOn(Schedulers.io ())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(AttendanceLog->{
                    Timber.d("登園ログ取得");
                    contract.onFetchAttendancesLogComplete(AttendanceLog);
                },e->{
                    Timber.e(e);
                    contract.onFetchAttendancesLogComplete(null);
                }));
    }
    //参照の切断
    @Override
    public void dispose(){
        super.dispose();
        this.contract = null;
    }


    public interface Contract {
        /**
         * 
         * @param studentProfile
         */
        void  onFetchProfileComplete (StudentProfile studentProfile);
    
        /**
         *
         * @param attendancesLog
         */
        void  onFetchAttendancesLogComplete(AttendancesLog attendancesLog);
    }
}

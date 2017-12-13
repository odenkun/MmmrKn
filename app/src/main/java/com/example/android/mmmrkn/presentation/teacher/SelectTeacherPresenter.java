package com.example.android.mmmrkn.presentation.teacher;

import com.example.android.mmmrkn.infra.api.TeacherService;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.example.android.mmmrkn.presentation.Presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SelectTeacherPresenter extends Presenter {
    
    private final TeacherService teacherService;
    private Contract contract;



    @Inject
    public SelectTeacherPresenter ( Contract contract, TeacherService teacherService ) {
        this.contract = contract;
        this.teacherService = teacherService;
    }

    /**
     * 保育士リストの取得
     */
    public void fetchTeachers () {
        disposables.add (
                teacherService.getTeachers ()
                        .subscribeOn ( Schedulers.io () )
                        .observeOn ( AndroidSchedulers.mainThread () )
                        .subscribe ( teacherList -> {
                            Timber.d ( "先生とれたよ" );
                            contract.onTeachersFetched ( teacherList );
                        }, e -> {
                            Timber.e ( e );
                            contract.onTeachersFetched ( null );
                        } ) );
    }

    //プレゼンターがビューにやって欲しいことリスト
    public interface Contract {
        /**
         *
         * @param t 通信結果の保育士の一覧
         */
        void onTeachersFetched ( List <Teacher> t );
    }
    //参照の切断
    @Override
    public void dispose(){
        super.dispose();
        this.contract = null;
    }
}
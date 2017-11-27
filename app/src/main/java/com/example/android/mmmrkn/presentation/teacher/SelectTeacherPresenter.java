package com.example.android.mmmrkn.presentation.teacher;

import com.example.android.mmmrkn.infra.api.TeacherService;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.example.android.mmmrkn.infra.repository.TeacherRepository;
import com.example.android.mmmrkn.presentation.Presenter;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cookie;
import timber.log.Timber;

public class SelectTeacherPresenter extends Presenter {

    private Contract contract;

    private TeacherRepository teacherRepository;


    @Inject
    public SelectTeacherPresenter ( Contract contract, TeacherRepository teacherRepository ) {
        this.contract = contract;
        this.teacherRepository = teacherRepository;
    }

    /**
     * 保育士リストの取得
     */
    public void fetchTeachers () {
        disposables.add (
                teacherRepository.getTeachers ()
                        .subscribeOn ( Schedulers.io () )
                        .observeOn ( AndroidSchedulers.mainThread () )
                        .subscribe ( teacherList -> {
                            Timber.d ( "sensei toretayo" );
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
}

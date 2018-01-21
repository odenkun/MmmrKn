package com.example.android.mmmrkn.presentation.gohome;

import com.example.android.mmmrkn.infra.api.StudentsService;
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.Presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by 15110016 on 2017/12/05.
 */

public class GoHomePresenter extends Presenter {

	StudentsService studentsService;
	Contract contract;
	@Inject
	public GoHomePresenter ( Contract contract, StudentsService studentsService) {
		this.contract = contract;
		this.studentsService = studentsService;
	}
	void registerAttend ( Student student, String teacherId ) {
		Timber.e(student.toString ());
		disposables.add (
				studentsService.setStudentgoHome (
						student.getStudentId (),
						teacherId,
						String.valueOf(new Date ().getTime ()))
						.subscribeOn ( Schedulers.io () )
						.observeOn ( AndroidSchedulers.mainThread () )
						.subscribe ( () -> {
							Timber.d ( "登録完了。" );
							contract.onGoHomeRegistered ( true );
						}, e -> {
							Timber.e ( e );
							contract.onGoHomeRegistered ( false );
						} )
		);
	}

	void fetchStudent ( String studentId ) {
		if ( studentId == null ) {
			throw new RuntimeException ( "studentId is null" );
		}
		disposables.add (
				studentsService.getStudent ( studentId )
						.subscribeOn ( Schedulers.io () )
						.observeOn ( AndroidSchedulers.mainThread () )
						.subscribe ( student -> {
							Timber.d ( "生徒とれたよ" );
							contract.onStudentFetched ( student );
						}, e -> {
							Timber.e ( e );
							contract.onStudentFetched ( null );
						} )
		);

	}
	//参照の切断
	@Override
	public void dispose(){
		super.dispose();
		 contract = null;
	}
	public interface Contract{
		void onGoHomeRegistered(boolean f);
		void onStudentFetched(Student student);
	}
}

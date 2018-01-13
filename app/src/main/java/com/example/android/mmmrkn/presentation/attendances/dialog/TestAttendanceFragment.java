package com.example.android.mmmrkn.presentation.attendances.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.infra.entity.Student;

import java.util.List;


public class TestAttendanceFragment  extends DialogFragment
{

    List<Student> students;

    public TestAttendanceFragment setStudents ( List <Student> students ) {
        this.students = students;
        return this;
    }

    public static TestAttendanceFragment newInstance( List<Student> students)
    {
        return new TestAttendanceFragment().setStudents ( students );
    }
    //ダイアログの作成
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.attendances_viewbase, null);

        builder.setView(dialogView)
                .setTitle("Fragment Dialog");
        return builder.create();
    }
}
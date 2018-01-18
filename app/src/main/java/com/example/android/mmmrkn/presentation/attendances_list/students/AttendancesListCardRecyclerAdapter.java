package com.example.android.mmmrkn.presentation.attendances_list.students;


import android.app.Dialog;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.content.Context;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.infra.entity.Party;
import com.example.android.mmmrkn.infra.entity.Student;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class AttendancesListCardRecyclerAdapter extends RecyclerView.Adapter<AttendancesListCardRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final List<Student> studentList = new ArrayList <> (  );
    private  String party;

    public AttendancesListCardRecyclerAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void swap( List<Student> studentList, String party)
    {
        this.party = party;
        this.studentList.clear ();
        if(studentList != null ) {
            this.studentList.addAll(studentList);
        }
        notifyDataSetChanged();
    }
    //Cardの中身表示
    @Override
    public void onBindViewHolder(AttendancesListCardRecyclerAdapter.ViewHolder vh, final int position) {
        //サイズ、nullチェック
        if (studentList.size() > position && studentList.get(position) != null) {
            Student student = studentList.get(position);
            //値挿入
            vh.name.setText(student.getName());
            //顔写真の挿入
            loadImage(vh.face, student.getStudentId () , student.getGender());
            int conditionId;
            //体調の代入
            if (student.getAttendance().getCondition().equals("good")) {
                if ("woman".equals ( student.getGender () )) {
                    conditionId = R.drawable.girl_happy;
                }else{
                    conditionId = R.drawable.boy_happy;
                }
            } else {
                if ("woman".equals ( student.getGender () )) {
                    conditionId = R.drawable.girl_sad;
                }else{
                    conditionId = R.drawable.boy_sad;
                }
            }
            vh.condition.setImageResource(conditionId);
            // クリック時、モード選択画面に移動
            vh.layout.setOnClickListener((v) -> EventBus.getDefault ().post ( new StudentSelectedEvent ( student ) ) );
        }
    }

    public static class AlertDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("登園児童は0人です")
                    .setPositiveButton("OK", ( dialog, id ) -> {
                        // FIRE ZE MISSILES!
                    } );
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }


    //Viewを纏めたフォルダの作成
    @Override
    public AttendancesListCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.student_recycler, parent, false);
        return new ViewHolder(v);
    }

    //Viewフォルダの初期化設定
    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final LinearLayout layout;
        final ImageView face;
        final ImageView condition;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.textView_name);
            layout = v.findViewById(R.id.layout);
            face = v.findViewById(R.id.imageview_picture);
            condition = v.findViewById(R.id.condition);
        }
    }

    //生徒の顔表示
    public static void loadImage(ImageView view, String picturePath, String gender) {
        Timber.d(gender);
        int frameColor = R.color.manFrame;
        int placeHolderID = R.drawable.boy_happy;
        if (gender != null && gender.equals("woman")) {
            frameColor = R.color.womanFrame;
            placeHolderID = R.drawable.girl_happy;
        }
        ColorStateList mBorderColor = ColorStateList.valueOf(frameColor);
//        String imageUrl = "https://mmmr-mock-api.mybluemix.net/images/students/" + picturePath + ".jpg";
        String imageUrl = "http://192.168.1.3:6001/images/students/" + picturePath + ".jpg";

        Picasso.with(view.getContext()).setLoggingEnabled(true);
        Picasso.with(view.getContext())
                .load( imageUrl)
                .placeholder(placeHolderID)
                .fit()
                .transform(new RoundedTransformationBuilder()
                        .borderWidthDp(6)
                        .oval(true)
                        .borderColor(frameColor)
                        .borderColor(mBorderColor)
                        .build())
                .into(view);
    }
    public static class StudentSelectedEvent {
        private final Student student;

        public StudentSelectedEvent ( Student student ) {
            this.student = student;
        }

        public Student getStudent () {
            return student;
        }
    }
}


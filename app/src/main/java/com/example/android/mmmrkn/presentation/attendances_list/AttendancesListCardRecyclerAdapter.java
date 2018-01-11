package com.example.android.mmmrkn.presentation.attendances_list;


import android.app.Activity;
import android.app.Dialog;

import android.support.v4.app.FragmentManager;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
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
import com.example.android.mmmrkn.presentation.studentprofile.StudentProfileActivity;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import timber.log.Timber;

public class AttendancesListCardRecyclerAdapter extends RecyclerView.Adapter<AttendancesListCardRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Student> studentList;
//    private AttendancesFragment.onFragmentListClickedListener listener;
    private Party party;


    public AttendancesListCardRecyclerAdapter(Context context, List<Student> attendanceTArray, Party party) {
        super();
        this.studentList = attendanceTArray;
        this.context = context;
        this.party = party;
    }

    @Override
    public int getItemCount() {
        if (studentList != null) {
            return studentList.size();
        } else {
            return 0;
        }
    }

    //Cardの中身表示
    @Override
    public void onBindViewHolder(AttendancesListCardRecyclerAdapter.ViewHolder vh, final int position) {
        //サイズ、nullチェック
        if (studentList != null && studentList.size() > position && studentList.get(position) != null) {
            //値挿入
            vh.name.setText(studentList.get(position).getName());
            //顔写真の挿入
            loadImage(vh.face, studentList.get(position).getPicturePath(), studentList.get(position).getGender());
            //体調の代入
            Timber.d(studentList.get(position).getAttendance().getCondition());
            if (studentList.get(position).getAttendance().getCondition().equals("good")) {
                vh.condition.setImageResource(R.drawable.smile);
            } else {
                vh.condition.setImageResource(R.drawable.cray);
            }
            // クリック時、モード選択画面に移動
            vh.layout.setOnClickListener((v) -> {
//                listener.onFragmentListClick(studentList.get(position).getName());
                Intent intent = new Intent(context, StudentProfileActivity.class);
                intent.putExtra("student", studentList.get(position));
                intent.putExtra("party", party);
                context.startActivity(intent);
            });
        }
    }

    public static class AlertDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("登園児童は0人です")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }


    //Viewを纏めたフォルダの作成
    @Override
    public AttendancesListCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.student_recycler, parent, false);
        AttendancesListCardRecyclerAdapter.ViewHolder viewHolder = new AttendancesListCardRecyclerAdapter.ViewHolder(v);
        return viewHolder;
    }

    //Viewフォルダの初期化設定
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        LinearLayout layout;
        ImageView face;
        ImageView condition;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.textView_name);
            layout = v.findViewById(R.id.layout);
            face = v.findViewById(R.id.imageview_picture);
            condition = v.findViewById(R.id.condition);
        }
    }

    //生徒の顔表示
    @BindingAdapter({"picturePath", "gender"})
    public static void loadImage(ImageView view, String picturePath, String gender) {
        Timber.d(gender);
        int frameColor = R.color.manFrame;
        int placeHolderID = R.drawable.back_color;
        if (gender != null && gender.equals("woman")) {
            frameColor = R.color.womanFrame;
            placeHolderID = R.drawable.back_color;
        }
        Picasso.with(view.getContext()).setLoggingEnabled(true);
        Picasso.with(view.getContext())
                .load("https://mmmr-mock-api.mybluemix.net/images/students/" + picturePath)
                .placeholder(placeHolderID)
                .fit()
                .transform(new RoundedTransformationBuilder()
                        .borderWidthDp(3)
                        .borderColor(R.color.manFrame)
                        .oval(true)
                        .build())
                .into(view);
    }

}


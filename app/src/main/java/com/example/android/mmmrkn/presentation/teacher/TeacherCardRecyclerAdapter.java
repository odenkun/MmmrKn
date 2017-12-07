package com.example.android.mmmrkn.presentation.teacher;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.example.android.mmmrkn.presentation.mode_select.ModeActivity;

import java.util.List;

import timber.log.Timber;


public class TeacherCardRecyclerAdapter extends RecyclerView.Adapter<TeacherCardRecyclerAdapter.ViewHolder>{
    private Context context;
    private List<Teacher> teachers;
    //listの中にvaluesのstringsを挿入
    public TeacherCardRecyclerAdapter(Context context, List<Teacher> teacherArrayList) {
        super();
        this.teachers = teacherArrayList;
        this.context = context;
    }
    //getItemCountで項目数取得、項目数を返す
    @Override
    public int getItemCount() {
        Timber.d("count");

        if (teachers != null) {
            return teachers.size();
        } else {
            return 0;
        }
    }

    //Cardの中身表示
    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        //サイズ、nullチェック
        if (teachers != null && teachers.size() > position && teachers.get(position) != null) {
            //Ormaから持ってきたデータ代入
            vh.name.setText(teachers.get(position).getName());
        }
        // クリック時、モード選択画面に移動
        vh.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,com.example.android.mmmrkn.presentation.mode_select.ModeActivity.class);
                intent.putExtra("teacherName",teachers.get(position).getName());
                intent.putExtra("teacherId",teachers.get(position).getTeacherId());
                context.startActivity(intent);
            }
        });

    }
    //Viewを纏めたフォルダの作成
    @Override
    public TeacherCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.layout_teacher_recycler_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    //Viewフォルダの初期化設定
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        LinearLayout layout;

        public ViewHolder(View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.teacherName);
            layout = (LinearLayout)v.findViewById(R.id.layout);
        }
    }
}
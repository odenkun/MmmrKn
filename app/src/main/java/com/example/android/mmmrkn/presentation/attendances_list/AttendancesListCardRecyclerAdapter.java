package com.example.android.mmmrkn.presentation.attendances_list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.infra.entity.Attendances;
import com.example.android.mmmrkn.presentation.studentprofile.StudentProfileActivity;

import java.util.List;

public class AttendancesListCardRecyclerAdapter extends RecyclerView.Adapter<AttendancesListCardRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Attendances> attendances;
    public AttendancesListCardRecyclerAdapter(Context context,List<Attendances> attendancesArray){
        super();
        this.attendances = attendancesArray;
        this.context =context;
    }
    @Override
    public int getItemCount() {
        if (attendances != null) {
            return attendances.size();
        } else {
            return 0;
        }
    }

    //Cardの中身表示
    @Override
    public void onBindViewHolder(AttendancesListCardRecyclerAdapter.ViewHolder vh, final int position) {
        //サイズ、nullチェック
        if (attendances != null && attendances.size() > position && attendances.get(position) != null) {
            //Ormaから持ってきたデータ代入
            vh.name.setText(attendances.get(position).getName());
        }
        // クリック時、モード選択画面に移動
        vh.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudentProfileActivity.class);
                intent.putExtra("studentId",attendances.get(position).getStudentId());
                context.startActivity(intent);
            }
        });
    }

    //Viewを纏めたフォルダの作成
    @Override
    public AttendancesListCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.party_recycler, parent, false);
        AttendancesListCardRecyclerAdapter.ViewHolder viewHolder = new AttendancesListCardRecyclerAdapter.ViewHolder(v);
        return viewHolder;
    }
    //Viewフォルダの初期化設定
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        LinearLayout layout;

        public ViewHolder(View v) {
            super(v);
            name =v.findViewById(R.id.textView_party);
            layout = v.findViewById(R.id.layout);
        }
    }

}

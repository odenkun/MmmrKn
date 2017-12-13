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
import com.example.android.mmmrkn.infra.entity.Student;
import com.example.android.mmmrkn.presentation.studentprofile.StudentProfileActivity;

import java.util.List;

public class AttendancesListCardRecyclerAdapter extends RecyclerView.Adapter<AttendancesListCardRecyclerAdapter.ViewHolder> {
	private Context context;
	private List<Student> studentList;
	
	public AttendancesListCardRecyclerAdapter(Context context, List<Student> attendanceTArray) {
		super();
		this.studentList = attendanceTArray;
		this.context = context;
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
			//Ormaから持ってきたデータ代入
			vh.name.setText(studentList.get(position).getName());
		}
		// クリック時、モード選択画面に移動
		vh.layout.setOnClickListener((v) -> {
			Intent intent = new Intent(context, StudentProfileActivity.class);
			intent.putExtra("student", studentList.get(position));
			context.startActivity(intent);
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
			name = v.findViewById(R.id.textView_party);
			layout = v.findViewById(R.id.layout);
		}
	}
	
}

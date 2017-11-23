package com.example.android.mmmrkn.presentation.teacher;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mmmrkn.databinding.CardTeacherBinding;
import com.example.android.mmmrkn.databinding.FragmentTeacherListBinding;
import com.example.android.mmmrkn.infra.entity.OrmaDatabase;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.example.android.mmmrkn.infra.entity.Teacher_Relation;
import com.github.gfx.android.orma.Relation;
import com.github.gfx.android.orma.widget.OrmaRecyclerViewAdapter;

import timber.log.Timber;

public class TeacherListFragment extends Fragment {

    OrmaDatabase orma;

    FragmentTeacherListBinding binding;

    Adapter adapter;

    private OnFragmentInteractionListener mListener;

    public TeacherListFragment () {

    }

    public TeacherListFragment setOrma ( OrmaDatabase orma ) {
        this.orma = orma;
        return this;
    }

    public static TeacherListFragment newInstance ( OrmaDatabase ormaDatabase ) {
        return new TeacherListFragment ().setOrma ( ormaDatabase );
    }

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {

        binding = FragmentTeacherListBinding.inflate(inflater, container, false);

        adapter = new Adapter(getContext(), orma.relationOfTeacher ());
        binding.list.setAdapter(adapter);

        return binding.getRoot();
    }

    public void onButtonPressed ( Teacher teacher ) {
        if ( mListener != null ) {
            mListener.onFragmentInteraction ( teacher );
        }
    }

    @Override
    public void onAttach ( Context context ) {
        super.onAttach ( context );
        if ( context instanceof OnFragmentInteractionListener ) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException ( context.toString ()
                    + " must implement OnFragmentInteractionListener" );
        }
    }

    @Override
    public void onDetach () {
        super.onDetach ();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction ( Teacher teacher );
    }

    static class VH extends RecyclerView.ViewHolder {

        CardTeacherBinding binding;

        public VH(LayoutInflater inflater, ViewGroup parent) {
            super(CardTeacherBinding.inflate(inflater, parent, false).getRoot());
            binding = DataBindingUtil.getBinding(itemView);
        }
    }


    static class Adapter extends OrmaRecyclerViewAdapter<Teacher, VH> {

        public Adapter( @NonNull Context context, @NonNull Relation<Teacher, ?> relation) {
            super(context, relation);
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public Teacher_Relation getRelation() {
            return (Teacher_Relation) super.getRelation();
        }

//        @Override
//        public int getItemCount () {
//            return getRelation ().createQueryObservable().count ();
//        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(final VH holder, int position) {
            final CardTeacherBinding binding = holder.binding;
            final Teacher teacher = getItem(position);
            binding.setTeacher ( teacher );
            binding.getRoot().setOnClickListener ( view -> Timber.d(teacher.toString ()) );
        }

    }
}

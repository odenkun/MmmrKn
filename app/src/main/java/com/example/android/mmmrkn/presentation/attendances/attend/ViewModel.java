package com.example.android.mmmrkn.presentation.attendances.attend;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.widget.ImageView;

import com.example.android.mmmrkn.BR;
import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.infra.entity.Attendance;
import com.example.android.mmmrkn.infra.entity.Student;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class ViewModel extends BaseObservable {
    private Student student;
    public final ObservableInt checkedBtn;
    public String summary ="";
    public String provisional = "";
    public String finalized = "";



    private String selectedFamily;
    private String selectedTime;


    public ViewModel () {
        checkedBtn = new ObservableInt ( R.id.radio_healthy );
    }

    @Bindable
    public Student getStudent () {
        return student;
    }

    public void setStudent ( Student student ) {
        checkedBtn.set ( R.id.radio_healthy );
        summary = "";
        selectedFamily = "";
        selectedTime = "";
        provisional = "";
        finalized = "";
        this.student = student;
        if ( student != null && student.getAttendance () == null ) {
            Timber.d(student.getStudentId ());
            student.setAttendance ( new Attendance () );
        }
        notifyPropertyChanged ( BR.student );
    }

    public void addDetail(boolean isProvisional, String text) {
        if (isProvisional) {
            provisional = text;
        }else{
            provisional = "";
            finalized = finalized + text;
        }
        Timber.d("%s%s", finalized, provisional);
        if (student != null ) {

            student.getAttendance ().setDetail ( summary + finalized + provisional );
            notifyPropertyChanged ( BR.student );
        }
    }

    void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
        summaryUpdate ();
    }
    void setSelectedFamily(String selectedFamily) {
        this.selectedFamily = selectedFamily;
        summaryUpdate ();
    }
    private void summaryUpdate() {
        if (student == null) {
            return;
        }
        if (empChk ( selectedFamily ) || empChk ( selectedTime )) {
            summary = "";
            if (empChk ( selectedTime )) {
                summary += selectedTime + "に";
            }
            if (empChk ( selectedFamily )) {
                summary += selectedFamily + "が";
            }
            summary += "迎えに来られます。\r\n";
            student.getAttendance ().setDetail ( summary + finalized + provisional );
            notifyPropertyChanged ( BR.student );
        }

    }

    boolean empChk ( String str ) {
        return str != null && !str.equals ( "" );
    }

    public boolean getCondition () {
        return checkedBtn.get () == R.id.radio_healthy;
    }


}
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
    public String provisional = "";
    public String finalized = "";




    public ViewModel () {
        checkedBtn = new ObservableInt ( R.id.radio_healthy );
    }

    @Bindable
    public Student getStudent () {
        return student;
    }

    public void setStudent ( Student student ) {
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
            student.getAttendance ().setDetail ( finalized + provisional );
            notifyPropertyChanged ( BR.student );
        }
    }

    public boolean getCondition () {
        return checkedBtn.get () == R.id.radio_healthy;
    }

    @BindingAdapter ( { "picturePath", "gender" })
    public static void loadImage ( ImageView view, String picturePath, String gender ) {

        int frameColor = R.color.manFrame;
        int placeHolderID = R.drawable.boy_happy;
        if ( gender != null && gender.equals ( "woman" ) ) {
            frameColor = R.color.womanFrame;
            placeHolderID = R.drawable.girl_happy;
        }

        String imageUrl = null;
        if (picturePath != null) {
//            imageUrl = "https://mmmr-mock-api.mybluemix.net/images/students/" + picturePath + ".jpg";
            imageUrl = "http://192.168.10.57:6001/images/students/" + picturePath + ".jpg";
        }

        Picasso.with ( view.getContext () )
                .load ( imageUrl )
                .placeholder ( placeHolderID )
                .fit ()
                .transform ( new RoundedTransformationBuilder ()
                        .borderColor ( frameColor )
                        .borderWidthDp ( 8 )
                        .oval ( true )
                        .build () )
                .into ( view );
    }
}
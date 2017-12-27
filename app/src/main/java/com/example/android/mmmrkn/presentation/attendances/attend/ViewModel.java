package com.example.android.mmmrkn.presentation.attendances.attend;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.widget.ImageView;

import com.example.android.mmmrkn.BR;
import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.infra.entity.Attendance;
import com.example.android.mmmrkn.infra.entity.Student;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

public class ViewModel extends BaseObservable {
    private Student student;
    public ObservableInt checkedBtn;

    @Bindable
    public Student getStudent () {
        return student;
    }

    public void setStudent ( Student student ) {
        if (student.getAttendance () == null) {
            Attendance attendance = new Attendance ();
            student.setAttendance ( attendance );
        }
        checkedBtn = new ObservableInt ( R.id.radio_healthy );
        this.student = student;
        notifyPropertyChanged ( BR.student );
    }

    public boolean getCondition () {
        return checkedBtn.get() == R.id.radio_healthy;
    }

    @BindingAdapter ( { "picturePath", "gender" })
    public static void loadImage ( ImageView view, String picturePath, String gender ) {

        int frameColor = R.color.manFrame;
        int placeHolderID = R.drawable.boy_happy;
        if ( gender != null && gender.equals ( "woman" ) ) {
            frameColor = R.color.womanFrame;
            placeHolderID = R.drawable.girl_happy;
        }
        Picasso.with ( view.getContext () ).setLoggingEnabled ( true );

        Picasso.with ( view.getContext () )
                .load ( "https://mmmr-mock-api.mybluemix.net/images/students/" + picturePath )
                .placeholder ( placeHolderID )
                .fit ()
                .transform ( new RoundedTransformationBuilder ()
                        .borderColor ( frameColor )
                        .borderWidthDp ( 6 )
                        .oval ( true )
                        .build () )
                .into ( view );
    }
}
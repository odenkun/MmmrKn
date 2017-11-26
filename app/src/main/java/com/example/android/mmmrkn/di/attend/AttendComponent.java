package com.example.android.mmmrkn.di.attend;

import com.example.android.mmmrkn.presentation.attend.AttendActivity;
import dagger.Subcomponent;

@Subcomponent (modules = {AttendModule.class})
public interface AttendComponent {

    void inject(AttendActivity attendActivity);
}
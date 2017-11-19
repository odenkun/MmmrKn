package com.example.android.mmmrkn.di.start;

import com.example.android.mmmrkn.presentation.StartActivity;
import dagger.Subcomponent;

@Subcomponent
public interface StartComponent {
    void inject(StartActivity m);

}

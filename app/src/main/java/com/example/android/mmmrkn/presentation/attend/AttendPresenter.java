package com.example.android.mmmrkn.presentation.attend;




import com.atilika.kuromoji.TokenizerBase;


import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.example.android.mmmrkn.presentation.Presenter;


import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class AttendPresenter extends Presenter {
    Contract contract;
    @Inject
    public AttendPresenter ( AttendPresenter.Contract contract ) {
        this.contract = contract;
    }


    void tokenize () {
        Tokenizer tokenizer = new Tokenizer.Builder().mode ( TokenizerBase.Mode.NORMAL ).build ();

        List<Token> tokens = tokenizer.tokenize("我輩は猫である。名前はまだない。");
        for ( Token token : tokens ) {
            Timber.d("Kuromoji" + token.getReading ());
        }
    }

    public interface Contract {

    }
}

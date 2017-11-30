package com.example.android.mmmrkn.presentation.cardview_gohome_dialog;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mmmrkn.R;
import com.example.android.mmmrkn.presentation.mode_select.ModeActivity;

public class GoHomeDialogActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gohome_dialog);


        Intent intent = this.getIntent();


        String studentId = intent.getStringExtra("sendText");
        //消してもいい

        //ボタンの可視化
        //findViewById(R.id.btn_Next).setVisibility(View.INVISIBLE);
        //ボタンの作成、押下した動き
        findViewById(R.id.btn_Next).setOnClickListener(view ->
                        showFragmentDialog(TEST_DIALOG)
                //StartActivity(new Intent(this,GoHomeDialogActivity.class));
                //finish();
        );

        //int ZidoId = intent.getIntExtra("ZidouId",0);
        //受け取った児童IDを取得



        //Button sendButton =  findViewById(R.id.Button_Next);
        //次の生徒へボタンの定義
        ImageView ImageFace = findViewById(R.id.Image_Face);
        //顔写真
        //TextView TextParty = findViewById(R.id.Text_Party);
        //組の名前
        TextView TextName = findViewById(R.id.Text_Name);
        //児童の名前
        //TextView TextCondition = findViewById(R.id.Text_Condition);
        //体調について

        Button back=findViewById(R.id.button_Back);
        //メニューに戻るor戻るボタン
        //Date NowDate = new Date();
        //当アクティビティが呼び出されたときの時間を日付型で取得。登園時刻の記入に使う？


        //下 /**/ 内はデータベースから値を取得して各項目に当てはめる用のやつ
        /*
        TextParty.setText("");
        TextName.setText("");
        TextCondition.setText("");
        ImageFace.setImage(""); //画像の大きさは自動で調整される、""内にファイルパスを入れる
        TextParty.setText("");

         */
        TextName.setText(studentId);
        //受け取ったIDを名前に入れる　消してよし
        ImageFace.setImageResource(R.drawable.ic_launcher_background);
        //顔写真の挿入　消してよし。

        //ボタンの状態変化
        if(TextName.getText()!="") {
            back.setText("戻る");
        }

        back.setOnClickListener(view ->{
            if(TextName.getText()==""){
                startActivity(new Intent(this,ModeActivity.class));
                finish();
            }
            else{
                startActivity(new Intent(this,GoHomeDialogActivity.class));
                finish();
            }
        });
    }
    /**フラグメントダイアログを表示する。*/
    final int TEST_DIALOG = 0;
    public void showFragmentDialog(int id)
    {
        switch(id){
            case TEST_DIALOG:
                DialogFragment dialogFragment = TestDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
        }
    }

    /*OKボタンが押されたことを感知する。*/
    /*public void onTestDialogOKClick()
    {
        Log.i("MainActivity : ", "OK clicked.");
    }*/


}
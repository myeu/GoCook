package com.example.marisayeung.gocook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.login.EvernoteLoginFragment;

public class EvernoteLogin extends AppCompatActivity implements EvernoteLoginFragment.ResultCallback {

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, EvernoteLogin.class));
    }

    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_evernote_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        b = (Button) findViewById(R.id.login);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EvernoteSession.getInstance().authenticate(EvernoteLogin.this);
                b.setEnabled(false);
            }
        });
    }

    @Override
    public void onLoginFinished(boolean successful) {
        if (successful) {
            finish();
        } else {
            b.setEnabled(true);
        }
    }
}

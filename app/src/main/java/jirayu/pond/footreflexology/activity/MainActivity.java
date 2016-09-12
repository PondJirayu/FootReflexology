package jirayu.pond.footreflexology.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import jirayu.pond.footreflexology.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvAppName;
    EditText editName;
    Button btnSignUp, btnIntoMainPage;
    String name;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setContentView(R.layout.activity_main);
        anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_anim);

        initInstances();
    }

    private void initInstances() {
        tvAppName = (TextView) findViewById(R.id.tvAppName);
        editName = (EditText) findViewById(R.id.editName);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnIntoMainPage = (Button) findViewById(R.id.btnIntoMainPage);
        anim.setDuration(500);
        tvAppName.startAnimation(anim);
        anim.setDuration(1000);
        editName.startAnimation(anim);
        anim.setDuration(1500);
        btnSignUp.startAnimation(anim);
        anim.setDuration(2000);
        btnIntoMainPage.startAnimation(anim);

        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSignUp) {
            name = editName.getText().toString();

            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}


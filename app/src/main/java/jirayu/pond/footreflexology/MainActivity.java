package jirayu.pond.footreflexology;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvAppName;
    EditText editName;
    Button btnSignUp, btnIntoMainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
    }

    private void initInstances() {
        tvAppName = (TextView) findViewById(R.id.tvAppName);
        editName = (EditText) findViewById(R.id.editName);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnIntoMainPage = (Button) findViewById(R.id.btnIntoMainPage);

        Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_anim);
        anim.setDuration(500);
        tvAppName.startAnimation(anim);
        anim.setDuration(1000);
        editName.startAnimation(anim);
        anim.setDuration(1500);
        btnSignUp.startAnimation(anim);
        anim.setDuration(2000);
        btnIntoMainPage.startAnimation(anim);
    }
}

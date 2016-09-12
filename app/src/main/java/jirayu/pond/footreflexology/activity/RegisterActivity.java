package jirayu.pond.footreflexology.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import jirayu.pond.footreflexology.R;

public class RegisterActivity extends AppCompatActivity {

    TextView tvName;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        initInstances();
    }

    private void initInstances() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText("ชื่อ " + name);
    }
}

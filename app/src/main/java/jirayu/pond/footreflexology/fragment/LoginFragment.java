package jirayu.pond.footreflexology.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.LoginActivity;
import jirayu.pond.footreflexology.activity.MainActivity;
import jirayu.pond.footreflexology.activity.RegisterActivity;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    TextView tvAppName;
    EditText editName;
    Button btnSignUp, btnIntoMainPage;
    Animation anim;
    RelativeLayout rootLayout;

    public LoginFragment() {
        super();
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_anim);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvAppName = (TextView) rootView.findViewById(R.id.tvAppName);
        editName = (EditText) rootView.findViewById(R.id.editName);
        btnSignUp = (Button) rootView.findViewById(R.id.btnSignUp);
        btnIntoMainPage = (Button) rootView.findViewById(R.id.btnIntoMainPage);
        rootLayout = (RelativeLayout) rootView.findViewById(R.id.rootLayout);

        anim.setDuration(500);
        tvAppName.startAnimation(anim);
        anim.setDuration(800);
        editName.startAnimation(anim);
        anim.setDuration(1300);
        btnSignUp.startAnimation(anim);
        anim.setDuration(1800);
        btnIntoMainPage.startAnimation(anim);

        btnSignUp.setOnClickListener(this);
        btnIntoMainPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSignUp) {
            if (isOnline()) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
            } else {
                Snackbar.make(rootLayout, "กรุณาเชื่อมต่ออินเทอร์เน็ต", Snackbar.LENGTH_LONG)
                        .setAction("เปิด WI-FI", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                                wifi.setWifiEnabled(true);
                            }
                        })
                        .show();
            }
        }
        if (v == btnIntoMainPage) {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    // check internet access
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

package jirayu.pond.footreflexology.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import java.io.IOException;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.MainActivity;
import jirayu.pond.footreflexology.dao.MemberItemDao;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

        // Play Animation
        anim.setDuration(500);
        tvAppName.startAnimation(anim);
        anim.setDuration(800);
        editName.startAnimation(anim);
        anim.setDuration(1100);
        btnSignUp.startAnimation(anim);
        anim.setDuration(1400);
        btnIntoMainPage.startAnimation(anim);

        // Handle Click Button
        btnSignUp.setOnClickListener(this);
        btnIntoMainPage.setOnClickListener(this);
    }

    // Handle Click Button
    @Override
    public void onClick(View v) {
        if (v == btnSignUp) {
            if (isOnline()) {
                Call<MemberItemDao> call = HttpManager.getInstance().getService().loadMemberList("members", "1769900332760");
                call.enqueue(new Callback<MemberItemDao>() {
                    @Override
                    public void onResponse(Call<MemberItemDao> call,
                                           Response<MemberItemDao> response) {
                        if (response.isSuccessful()) {
                            MemberItemDao dao = response.body();
                            Toast.makeText(getActivity(), dao.getFirstName(), Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberItemDao> call,
                                          Throwable t) {
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
//                Intent intent = new Intent(getContext(), RegisterActivity.class);
//                startActivity(intent);
            } else {
                Snackbar.make(rootLayout, "โปรดตรวจสอบการเชื่อมต่อเครือข่ายของคุณ", Snackbar.LENGTH_LONG).show();
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

    // Function Check Internet Access
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

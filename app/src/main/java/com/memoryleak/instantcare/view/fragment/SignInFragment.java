package com.memoryleak.instantcare.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.memoryleak.instantcare.R;
import com.memoryleak.instantcare.databinding.FragmentSignInBinding;
import com.memoryleak.instantcare.util.GoogleSignInHelper;
import com.memoryleak.instantcare.view.MainActivity;

import javax.inject.Inject;


public class SignInFragment extends Fragment implements GoogleSignInHelper.OnGoogleSignInSuccessListener {
    private static final String TAG = "SignInFragment";

    private MainActivity mainActivity;

    // views
    private FragmentSignInBinding mVB;

    @Inject
    GoogleSignInHelper googleSignInHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.getMainActivityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mVB = FragmentSignInBinding.inflate(inflater, container, false);
        return mVB.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mVB.signUpBtn.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.signUpFragment);
        });

        googleSignInHelper.setOnGoogleSignInSuccessListener(this);

        mVB.googleSignInBtn.setOnClickListener(v -> {
            Log.d(TAG, "onViewCreated: starting google sign in activity");
            googleSignInHelper.startGoogleSignInActivity();
        });
    }

    @Override
    public void onGoogleSignInSuccess(GoogleSignInAccount googleSignInAccount) {
        Log.d(TAG, "onGoogleSignInSuccess: google sign in completed.");
        NavHostFragment.findNavController(this)
                .navigate(R.id.homeFragment);
    }

    @Override
    public void onGoogleSignInFailure(Exception e) {
        Log.e(TAG, "onGoogleSignInFailure: failed google sing in", e);
        e.printStackTrace();
    }
}
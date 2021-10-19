package com.memoryleak.instantcare.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;

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

        initTransitions();
    }

    /**
     * @see <a href="https://thoughtbot.com/blog/android-interpolators-a-visual-guide">This</a>
     */
    private void initTransitions(){
        TransitionInflater inflater = TransitionInflater.from(getContext());

        //exit transition
        Transition slide = inflater.inflateTransition(android.R.transition.slide_left); // slide from right
        slide.setInterpolator(new AnticipateInterpolator());
        setExitTransition(slide);
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
        mVB.signUpTv.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.signUpFragment);
        });

        mVB.signInBtn.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.homeFragment);
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
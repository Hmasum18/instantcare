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
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.GoogleMap;
import com.memoryleak.instantcare.R;
import com.memoryleak.instantcare.databinding.FragmentHomeBinding;
import com.memoryleak.instantcare.util.GoogleSignInHelper;
import com.memoryleak.instantcare.view.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

import javax.inject.Inject;

public class HomeFragment extends Fragment{
    private static final String TAG = "HomeFragment";

    private MainActivity mainActivity;

    // views
    private FragmentHomeBinding mVB;
    private GoogleMap mMap;

    @Inject
    GoogleSignInHelper googleSignInHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.getMainActivityComponent().inject(this);

        initTransitions();
    }

    private void initTransitions() {
        TransitionInflater inflater = TransitionInflater.from(getContext());

        // enter transition
        Transition slide = inflater.inflateTransition(android.R.transition.slide_right); // slide from right to left
        slide.setInterpolator(new AnticipateInterpolator());
        setEnterTransition(slide);

        /*// exit transition
        slide = inflater.inflateTransition(android.R.transition.slide_left); // slide from left to right
        slide.setInterpolator(new LinearInterpolator());
        setExitTransition(slide);*/
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mVB = FragmentHomeBinding.inflate(inflater, container, false);
        return mVB.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDummyTextView();

        mVB.signOutBtn.setOnClickListener(v -> {
            Log.d(TAG, "onViewCreated: signing out from google.");
            googleSignInHelper.signOut();
            NavHostFragment.findNavController(this).popBackStack();
        });
    }

    private void initDummyTextView() {
       GoogleSignInAccount account = googleSignInHelper.getGoogleSignInAccount();
       if(account == null)
           return;

       StringBuilder accountInfo = new StringBuilder();
       accountInfo.append("name: ").append(account.getDisplayName()).append("\n")
       .append("Email: ").append(account.getEmail()).append("\n")
       .append("ImageUrl: ").append(account.getPhotoUrl()).append("\n")
       .append("id: ").append(account.getId()).append("\n")
       .append(account.zab());

       mVB.dummy.setText(accountInfo.toString());
    }

}
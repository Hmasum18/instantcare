package com.memoryleak.instantcare.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;

import com.memoryleak.instantcare.R;


public class SignUpFragment extends Fragment {





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }
}
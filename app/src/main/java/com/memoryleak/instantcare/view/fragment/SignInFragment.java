package com.memoryleak.instantcare.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.memoryleak.instantcare.R;
import com.memoryleak.instantcare.databinding.FragmentSignInBinding;
import com.memoryleak.instantcare.view.MainActivity;


public class SignInFragment extends Fragment {
    private MainActivity mainActivity;

    // views
    private FragmentSignInBinding mVB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.initDagger().inject(this);
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
    }
}
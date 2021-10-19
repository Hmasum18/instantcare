package com.memoryleak.instantcare.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.memoryleak.instantcare.R;
import com.memoryleak.instantcare.databinding.FragmentBloodSearchBinding;


public class BloodSearchFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "BloodSearchFragment";

    // view
    private FragmentBloodSearchBinding mVB;

    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mVB = FragmentBloodSearchBinding.inflate(inflater, container, false);
        return mVB.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        // initialize map
        initMap();
    }

    /**
     * get the map fragment and start the MapAsync
     */
    private void initMap() {
        Log.d(TAG, "initMap: ");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.googleMap);
        try {
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mMap = googleMap;
    }
}
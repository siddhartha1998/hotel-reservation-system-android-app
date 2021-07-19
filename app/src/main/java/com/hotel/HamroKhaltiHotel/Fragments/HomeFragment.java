package com.hotel.HamroKhaltiHotel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hotel.HamroKhaltiHotel.Activities.HomeActivity;
import com.hotel.HamroKhaltiHotel.R;


public class HomeFragment extends Fragment {

    LinearLayout gotoProfile;
    LinearLayout gotoHotel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_home, container, false);

        gotoProfile = v.findViewById(R.id.gotoProfile);

        gotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();

            }
        });

        gotoHotel = v.findViewById(R.id.gotoHotel);

        gotoHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HotelFragment()).addToBackStack(null).commit();

            }
        });

        return v;
    }
}
package com.hotel.HamroKhaltiHotel.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.models.Customer;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private static final String TAG = "siddhi" ;
    private TextView displayCustomerName, displayCustomerUsername,displayCustomerEmail,displayCustomerAddress,
            displayCustomerPhone,displayCustomerAge,displayCustomerGender;
   private Button btnEditProfile;
   private Customer customer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        displayCustomerName = v.findViewById(R.id.customerName);
        displayCustomerUsername = v.findViewById(R.id.CustomerUserName);
        displayCustomerEmail = v.findViewById(R.id.customerEmail);
        displayCustomerAddress = v.findViewById(R.id.customerAddress);
        displayCustomerPhone = v.findViewById(R.id.customerPhone);
        displayCustomerAge = v.findViewById(R.id.customerAge);
        displayCustomerGender = v.findViewById(R.id.customerGender);
        btnEditProfile = v.findViewById(R.id.editProfileButton);

         displayCustomerName.setEnabled(false);
         displayCustomerUsername.setEnabled(false);
         displayCustomerEmail.setEnabled(false);
         displayCustomerAddress.setEnabled(false);
         displayCustomerPhone.setEnabled(false);
         displayCustomerAge.setEnabled(false);
         displayCustomerGender.setEnabled(false);

         btnEditProfile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 EditCustomerFragment editCustomerFragment = new EditCustomerFragment();
                 Bundle bundle = new Bundle();
                 bundle.putParcelable("data",customer);
                 editCustomerFragment.setArguments(bundle);

                 getFragmentManager().beginTransaction().replace(R.id.fragment_container, editCustomerFragment).addToBackStack(null).commit();
             }
         });


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Log.d(TAG, "onCreateView: "+token);

        Call<Customer> call = ApiClient.getCustomerServices().getMyDetail(token);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(response.isSuccessful()){
                    customer = response.body();

                    displayCustomerName.setText(customer.getFullname());
                    displayCustomerUsername.setText(customer.getUsername());
                    displayCustomerEmail.setText(customer.getEmail());
                    displayCustomerAddress.setText(customer.getAddress());
                    displayCustomerPhone.setText(customer.getPhone());
                    displayCustomerAge.setText(customer.getAge());
                    displayCustomerGender.setText(customer.getGender());

                }else{
                    Log.d(TAG, "onResponse: something is wrong "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.d(TAG, "onFailure: Sorry cannot connect to the server "+t.getMessage());
            }
        });

        return v;
    }

}
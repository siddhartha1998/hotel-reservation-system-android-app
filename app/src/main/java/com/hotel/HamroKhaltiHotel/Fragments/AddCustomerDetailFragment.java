package com.hotel.HamroKhaltiHotel.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.models.Customer;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.models.Reservation;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AddCustomerDetailFragment extends Fragment {

    private TextView addCustomerName, addCustomerUserName, addCustomerEmail, addCustomerAddress,
            addCustomerPhone, addCustomerAge, addCustomerGender;
    private Button btnAddProfile;
    private Customer customer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_customer, container, false);

        addCustomerName = v.findViewById(R.id.addFullName);
        addCustomerUserName = v.findViewById(R.id.addUserName);
        addCustomerEmail = v.findViewById(R.id.addAddress);
        addCustomerAddress = v.findViewById(R.id.addEmail);
        addCustomerPhone = v.findViewById(R.id.addPhone);
        addCustomerAge = v.findViewById(R.id.addAge);
        addCustomerGender = v.findViewById(R.id.addGender);
        btnAddProfile = v.findViewById(R.id.btnAdd);


        btnAddProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customerName = addCustomerName.getText().toString();
                String customerAddress = addCustomerAddress.getText().toString();
                String customerPhone = addCustomerPhone.getText().toString();
                String customerAge = addCustomerAge.getText().toString();
                String customerGender = addCustomerGender.getText().toString();

                Customer cus = new Customer();
//                cus.setUsername(room.getHotel().getHotelName());
//                cus.setEmail(room.getRoomNumber().toString());
                cus.setFullname(customerName);
                cus.setAddress(customerAddress);
                cus.setPhone(customerPhone);
                cus.setAge(customerAge);
                cus.setGender(customerGender);

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token","");

                Call<MessageResponse>call = ApiClient.getCustomerServices().addCustomerDetail(token,cus);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getMessage().equals("Customer Details Save Successfully")){
                                Toast.makeText(getContext(),"Customer Detail save Successfully",Toast.LENGTH_SHORT).show();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
                                Log.d(TAG, "onResponse: Customer Detail saved successfully");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: Sorry cannot connect to the server "+t.getMessage());
                    }
                });
            }
        });

    return v;
}
}

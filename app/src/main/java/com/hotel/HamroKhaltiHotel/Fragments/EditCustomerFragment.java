package com.hotel.HamroKhaltiHotel.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.models.Customer;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditCustomerFragment extends Fragment {

    private static final String TAG = "siddhi" ;
    private TextView editCustomerName,editCustomerUserName, editCustomerEmail, editCustomerAddress,
            editCustomerPhone, editCustomerAge, editCustomerGender;
    private Button btnEditProfile;
    private Customer customer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_edit_customer, container, false);

        editCustomerName = v.findViewById(R.id.editfullname);
        editCustomerUserName = v.findViewById(R.id.editUserName);
        editCustomerEmail = v.findViewById(R.id.editEmail);
        editCustomerAddress = v.findViewById(R.id.editAddress);
        editCustomerPhone = v.findViewById(R.id.editPhone);
        editCustomerAge = v.findViewById(R.id.editAge);
        editCustomerGender = v.findViewById(R.id.editGender);

        editCustomerEmail.setEnabled(false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Long userId = sharedPreferences.getLong("id",0);

        customer = getArguments().getParcelable("data");
        Log.d(TAG, "onCreateView: "+customer.getFullname());

        editCustomerName.setText(customer.getFullname());
        editCustomerEmail.setText(customer.getEmail());
        editCustomerAddress.setText(customer.getAddress());
        editCustomerPhone.setText(customer.getPhone());
        editCustomerAge.setText(customer.getAge());
        editCustomerGender.setText(customer.getGender());

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editCustomerName.getText().toString().trim();
                String address = editCustomerAddress.getText().toString().trim();
                Long phone = Long.parseLong(editCustomerPhone.getText().toString().trim());
                Long age = Long.parseLong(editCustomerAge.getText().toString().trim());
                String gender = editCustomerGender.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    editCustomerName.setError("customername cannot be empty");
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    editCustomerAddress.setError("address cannot be empty");
                    return;
                }

                if(TextUtils.isEmpty(phone.toString())){
                    editCustomerPhone.setError("Phone cannot be empty");
                    return;
                }

                if(TextUtils.isEmpty(age.toString())){
                    editCustomerAge.setError("Age cannot be empty");
                    return;
                }

                if(TextUtils.isEmpty(gender)){
                    editCustomerGender.setError("Gender cannot be empty");
                    return;
                }
                if(editCustomerPhone.length()>10 || editCustomerPhone.length()<10){
                    editCustomerPhone.setError("Phone should be of 10 characters long");
                }

                if(editCustomerAge.length()>3){
                    editCustomerPhone.setError("Age should be of 3 characters long");
                }

                Customer user = new Customer();
                user.setFullname(name);
                user.setAddress(address);
                user.setPhone(phone.toString());
                user.setAge(age.toString());
                user.setGender(gender);

                Call<MessageResponse>call = ApiClient.getCustomerServices().EditCustomerDetail(token,userId,user);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (response.isSuccessful()){
                            Log.d(TAG, "onResponse: Updated successfully");
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
                        }else{
                            Log.d(TAG, "onResponse: Sorry something is wrong "+response.code());
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
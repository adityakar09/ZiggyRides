package com.example.zippyrides;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zippyrides.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SettingsFragment extends Fragment {

    private EditText Fullname;
    private EditText username;
    private EditText email;
    private Button update;

    private FirebaseDatabase db;
    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference();

        Fullname = view.findViewById(R.id.etFirstName);
        username = view.findViewById(R.id.etLastName);
        email = view.findViewById(R.id.etBio);
        update = view.findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { {
                if (TextUtils.isEmpty(Fullname.getText().toString())) {
                    Fullname.setError("Fullname is compulsory");
                    return;
                }

                if (TextUtils.isEmpty(username.getText().toString())) {
                    username.setError("Username is compulsory");
                    return;
                }

                 if (TextUtils.isEmpty(email.getText().toString())){
                     email.setError("Email is compulsory");
                     return;
                 }


            }

                String getName = Fullname.getText().toString();
                String getusername = username.getText().toString();
                String getEmail = email.getText().toString();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Fullname",getName);
                hashMap.put("username",getusername);
                hashMap.put("email",getEmail);



                databaseReference.child("Users")
                        .child(getName)
                        .setValue(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Data updated successfully", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });

        return view;
    }
}

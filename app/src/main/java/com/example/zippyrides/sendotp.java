package com.example.zippyrides;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class sendotp extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendotp);

        mAuth = FirebaseAuth.getInstance();
        final EditText inputMobile = findViewById(R.id.inputmobile);
        Button buttonGetOTP = findViewById(R.id.buttongetotp);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        
        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(sendotp.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                buttonGetOTP.setVisibility(View.INVISIBLE);

                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        progressBar.setVisibility(View.GONE);
                        buttonGetOTP.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        progressBar.setVisibility(View.GONE);
                        buttonGetOTP.setVisibility(View.VISIBLE);
                        Toast.makeText(sendotp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                   @Override
                   public void onCodeSent(@NonNull String verificationId,
                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        progressBar.setVisibility(View.GONE);
                        buttonGetOTP.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getApplicationContext(), recieveotp.class);
                        intent.putExtra("mobile", inputMobile.getText().toString());
                        intent.putExtra("verificationId", verificationId);
                        startActivity(intent);
                    }
                };

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber("+91" + inputMobile.getText().toString())       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(sendotp.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();

                PhoneAuthProvider.verifyPhoneNumber(options);


            }
        });
    }
}
package com.book.labmanagementsysytem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class Account extends Fragment {
    RelativeLayout signout;
    TextView txtName;
    TextView txtEmail;
    private GoogleSignInClient mGoogleSignInClient;
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_account, container, false);

         signout = v.findViewById(R.id.rlytsignout);
         txtName = v.findViewById(R.id.txtName);
         txtName.setText(getArguments().getString("name"));
         txtEmail = v.findViewById(R.id.txtemail);
         txtEmail.setText(getArguments().getString("email"));
         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestEmail()
                 .build();
         mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
         signout.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                 NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                 if (activeNetwork != null) {
                     mGoogleSignInClient.signOut()
                             .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     Intent i = new Intent(requireActivity(), SIgnIn.class);
                                     startActivity(i);
                                     requireActivity().finishAffinity();
                                 }
                             });

                 } else {
                     Toast.makeText(getContext(), "No internet connection, please try again.", Toast.LENGTH_LONG).show();
                 }
             }
         });

         return v;
    }
}
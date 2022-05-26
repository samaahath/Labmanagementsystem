package com.book.labmanagementsysytem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    Toolbar toolbar;
    FragmentTransaction ft;
    private FirebaseAuth mAuth;
    String customerid;
    String customername;
    String customeremail;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_equipments:
                    DisplayPage("Equipments");
                    return true;
                case R.id.navigation_chemicals:
                    DisplayPage("Chemicals");
                    return true;
                case R.id.navigation_account:
                    DisplayPage("Account");
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        customername = (getIntent().getStringExtra("name"));
        customeremail = (getIntent().getStringExtra("email"));
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Lab Management");
        mAuth = FirebaseAuth.getInstance();
        loginUserAccount();
        DisplayPage("Equipments");
    }

    private void loginUserAccount() {


        String email, password;
        email = "maryamsamaahath@gmail.com";
        password = "9609931260";


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                    customerid=mAuth.getCurrentUser().getUid();


                }
                else {
                    if (task.getException() != null)
                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();


                }
            }
        });
    }
    public void DisplayPage(String page) {
        Fragment fragment = null;
        try {
            switch (page) {

                case "Equipments":
                    Bundle bundle = new Bundle();
                    fragment = new Equipments();
                    fragment.setArguments(bundle);
                    break;
                case "Chemicals":
                    bundle = new Bundle();
                    fragment = new Chemicals();
                    fragment.setArguments(bundle);
                    break;
                case "Account":
                    bundle = new Bundle();
                    bundle.putString("name", customername);
                    bundle.putString("email", customeremail);
                    fragment = new Account();
                    fragment.setArguments(bundle);
                    break;
            }
            if (fragment != null) {
                toolbar.setTitle(page.substring(0, 1) + page.substring(1));
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }

        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

    }
}
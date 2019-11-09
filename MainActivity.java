package com.example.lmc_eatz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lmc_eatz.Model.User;
import com.example.lmc_eatz.common.common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Pass;
    private TextView Tv;
    private Button Login;
    private Button Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = (EditText) findViewById(R.id.edtphn);
        Pass = (EditText) findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);
        Register = (Button) findViewById(R.id.btnreg);
        Tv = (TextView) findViewById(R.id.tv1);
        //initialize firebase
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = db.getReference("user");



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Please Wait....");
            mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(Name.getText().toString()).exists()){
                            mDialog.dismiss();
                            User user = dataSnapshot.child(Name.getText().toString()).getValue(User.class);
                            if(user.getPassword().equals(Pass.getText().toString()))
                            {
                                Toast.makeText(MainActivity.this,"SignIn Successfully", Toast.LENGTH_SHORT).show();
                                Intent hom =new Intent(MainActivity.this,Nav.class);
                                common.currentUser = user;
                                startActivity(hom);
                                finish();
                            }
                            else{
                                Toast.makeText(MainActivity.this,"SignIn failed", Toast.LENGTH_SHORT).show();
                            }

                            }
                        else{
                        Toast.makeText(MainActivity.this,"User does not exist", Toast.LENGTH_SHORT).show();
                        }
                        mDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                register();
            }
        });
    }

    private void register(){
            Intent intent = new Intent(MainActivity.this,Register.class);
            startActivity(intent);
    }
}

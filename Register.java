package com.example.lmc_eatz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lmc_eatz.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private EditText Rphn;
    private TextView Rreg;
    private EditText Rname;
    private EditText Rpass;
    private Button Rbtn;
    private EditText Cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Rphn=(EditText) findViewById(R.id.regphn);
        Rreg=(TextView) findViewById(R.id.reg);
        Rname=(EditText) findViewById(R.id.regname);
        Rpass=(EditText) findViewById(R.id.regpass);
        Rbtn=(Button) findViewById(R.id.regbtn);
        Cp=(EditText) findViewById(R.id.cpass);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = db.getReference("user");
        Rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(Register.this);
                mDialog.setMessage("Please Wait....");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check if already user phone
                        if (dataSnapshot.child(Rphn.getText().toString()).exists()) {

                            Toast.makeText(Register.this, "Phone number already registered",Toast.LENGTH_SHORT);
                        }
                        else {
                            mDialog.dismiss();
                            String a = Rpass.getText().toString();
                            String b = Cp.getText().toString();
                            if(a.equals(b)) {
                                User user = new User(Rname.getText().toString(),a);
                                table_user.child(Rphn.getText().toString()).setValue(user);
                                    Toast.makeText(Register.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(Register.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });
            }
        });
     }
}

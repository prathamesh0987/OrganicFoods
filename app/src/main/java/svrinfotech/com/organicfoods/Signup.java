package svrinfotech.com.organicfoods;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import svrinfotech.com.organicfoods.firebase.Firebase;
import svrinfotech.com.organicfoods.pojo.SignupPojo;

public class Signup extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText contact;
    EditText address;
    EditText password;
    EditText confirm_password;
    Button submit;
    FirebaseAuth firebaseAuth;
    DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String nm=name.getText().toString();
                    if(TextUtils.isEmpty(nm)) {
                        name.setError("Name Can't Be Empty");
                    }
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String mail=email.getText().toString();
                    if(TextUtils.isEmpty(mail)) {
                        email.setError("E-Mail Can't Be Empty");
                    }
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String pass=password.getText().toString();
                    if(TextUtils.isEmpty(pass)) {
                        password.setError("Password Can't Be Empty");
                    } else if (pass.length()<6) {
                        password.setError("Password Length Must Be 6 ");
                    }
                }
            }
        });

        confirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String pass=password.getText().toString();
                    String confirmPass=confirm_password.getText().toString();
                    if(TextUtils.isEmpty(confirmPass)) {
                        confirm_password.setError("Password Can't Be Empty");
                    } else if (!confirmPass.equals(pass)) {
                        confirm_password.setError("Password Not Match");
                    }
                }
            }
        });

        contact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String cont=contact.getText().toString();
                    if(cont.length()!=10) {
                        contact.setError("Invalid Phone Number");
                    }
                }
            }
        });

        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String addr=address.getText().toString();
                    if(TextUtils.isEmpty(addr)) {
                        address.setError("Address Can't Be Empty");
                    }
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nm=name.getText().toString();
                final String mail=email.getText().toString();
                final String pass=password.getText().toString();
                final String addr=address.getText().toString();
                final String cont=contact.getText().toString();
                //final Intent login=new Intent(Signup.this,LoginActivity.class);
                if(!TextUtils.isEmpty(nm) && !TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(addr) && !TextUtils.isEmpty(cont)) {
                        firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    SignupPojo signupPojo=new SignupPojo();
                                    signupPojo.setName(nm);
                                    signupPojo.setEmail(mail);
                                    signupPojo.setContact_no(cont);
                                    signupPojo.setAddress(addr);
                                    signupPojo.setPassword(pass);
                                    userReference.child(nm).setValue(signupPojo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(Signup.this,"Thank You For Registration",Toast.LENGTH_LONG).show();
                //                                startActivity(login);
                                                finish();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Signup.this,"Something Went Wrong : "+e.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Signup.this,"Signup Failed Due To : "+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                } else {
                    Snackbar msg=Snackbar.make(findViewById(android.R.id.content),"Please Fill All Details",Snackbar.LENGTH_INDEFINITE);
                    msg.setActionTextColor(Color.RED);
                    msg.show();
                }
            }
        });
    }

    void init() {
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        contact=findViewById(R.id.contact);
        address=findViewById(R.id.address);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
        submit=findViewById(R.id.signup);
        firebaseAuth=Firebase.getFirebaseAuth();
        userReference=Firebase.getUserReference();
    }




}

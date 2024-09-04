package svrinfotech.com.organicfoods;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import svrinfotech.com.organicfoods.firebase.Firebase;


public class Login extends AppCompatActivity {

    EditText username,password;
    Button login;
    Button signup;
    TextView forgotPass;
    FirebaseAuth firebaseAuth;

    public Login() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        init();
    }

    void init() {
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup_btn);
        login=findViewById(R.id.login_btn);
        forgotPass=findViewById(R.id.forgot_password);
        firebaseAuth=Firebase.getFirebaseAuth();
    }

    @Override
    public void onResume() {
        super.onResume();
        final Intent intent=new Intent(getApplicationContext(),Signup.class);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        final Intent loginIntent=new Intent(getApplicationContext(),Product.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString().trim();
                String pass=password.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            startActivity(loginIntent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Login Failed Due To : "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

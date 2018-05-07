package cubex.mahesh.firebasesample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText et1,et2;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.RED);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.mybg)
        );

        et1  = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);

        mAuth = FirebaseAuth.getInstance();
    }

    public void login(View v){
        Task<AuthResult> task =  mAuth.signInWithEmailAndPassword(
                et1.getText().toString(),
                et2.getText().toString() );
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,
                            "Authentication Success",Toast.LENGTH_LONG).show();
     startActivity(new Intent(LoginActivity.this,
             DashboardActivity.class));

                }else{
                    Toast.makeText(LoginActivity.this,
                            "Authentication Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void register(View v){
   Task<AuthResult> task =  mAuth.createUserWithEmailAndPassword(
                et1.getText().toString(),
                et2.getText().toString() );
   task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
       @Override
       public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){
               Toast.makeText(LoginActivity.this,
"Authentication Success",Toast.LENGTH_LONG).show();
               startActivity(new Intent(LoginActivity.this,
                       UserDataActivity.class));
           }else{
               Toast.makeText(LoginActivity.this,
 "Authentication Failed",Toast.LENGTH_LONG).show();
           }
       }
   });
    }

}

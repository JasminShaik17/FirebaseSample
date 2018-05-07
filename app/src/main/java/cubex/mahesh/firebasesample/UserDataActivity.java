package cubex.mahesh.firebasesample;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDataActivity extends AppCompatActivity {
        EditText et1,et2,et3,et4;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.RED);
        setContentView(R.layout.activity_user_data);
        getSupportActionBar().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.mybg)
        );
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);

        mAuth = FirebaseAuth.getInstance();
    }

    public void submit(View v)
    {
        FirebaseDatabase database =
                FirebaseDatabase.getInstance();
        DatabaseReference myRef =
  database.getReference("user_info/"+mAuth.getUid());
    myRef.child("gender").setValue(et1.getText().toString());
    myRef.child("mno").setValue(et2.getText().toString());
    myRef.child("address").setValue(et3.getText().toString());
    myRef.child("name").setValue(et4.getText().toString());

    startActivity(new Intent(this,
            ProfilePicActivity.class));
     }
}

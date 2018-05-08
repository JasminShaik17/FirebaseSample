package cubex.mahesh.firebasesample;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;

public class DashboardActivity extends AppCompatActivity {
    ImageView iview;
    TextView tv1,tv2,tv3,tv4;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.RED);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.mybg)
        );

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        iview = findViewById(R.id.iview);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user_info/"+
                FirebaseAuth.getInstance().getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             Iterable<DataSnapshot> it =    dataSnapshot.getChildren();
             it.forEach(new Consumer<DataSnapshot>() {
                 @Override
                 public void accept(DataSnapshot dataSnapshot) {
              if(dataSnapshot.getKey().equalsIgnoreCase("name"))
              {
                  tv1.setText(dataSnapshot.getValue().toString());
              }
                     if(dataSnapshot.getKey().equalsIgnoreCase(
                             "gender"))
                     {
                         tv2.setText(dataSnapshot.getValue().toString());
                     }
                     if(dataSnapshot.getKey().equalsIgnoreCase("mno"))
                     {
                         tv3.setText(dataSnapshot.getValue().toString());
                     }
                     if(dataSnapshot.getKey().equalsIgnoreCase(
                             "address"))
                     {
                         tv4.setText(dataSnapshot.getValue().toString());
                     }
                     if(dataSnapshot.getKey().equalsIgnoreCase(
                             "profile_url"))
                     {
                      //   Glide.with(iview).load(dataSnapshot.getValue().toString());

                     Glide.with(DashboardActivity.this)
                             .load(dataSnapshot.getValue().toString()).into(iview);
                     }
                 }
             });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

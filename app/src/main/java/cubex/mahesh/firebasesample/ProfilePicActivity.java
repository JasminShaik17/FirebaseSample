package cubex.mahesh.firebasesample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class ProfilePicActivity extends AppCompatActivity {

    boolean cam_per ;
    ImageView iview;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic);
        iview = findViewById(R.id.iview);

        mStorageRef = FirebaseStorage.getInstance().
                    getReference(FirebaseAuth.getInstance().getUid());

     int status = ContextCompat.checkSelfPermission
             (this, Manifest.permission.CAMERA);
     if (status== PackageManager.PERMISSION_GRANTED)
     {
         cam_per = true;
     }else{
         ActivityCompat.requestPermissions(this,
                 new String[ ]{
                 Manifest.permission.CAMERA,
                 Manifest.permission.WRITE_EXTERNAL_STORAGE
                 },123);
     }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);

        if(grantResults[0]==PackageManager.PERMISSION_GRANTED
                && grantResults[1]==PackageManager.
                PERMISSION_GRANTED)
        {
            cam_per = true;
        }


    }

    public void camera(View v){
        if(cam_per){
            Intent i =
                    new Intent("android.media.action." +
                            "IMAGE_CAPTURE");
            startActivityForResult(i,124);
        }
    }
    public void gallery(View v){
                if(cam_per){
                        Intent i = new Intent( );
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        i.setType("image/*");
                        startActivityForResult(i,125);
                }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 124 && resultCode==RESULT_OK){
              Object o =   data.getExtras().get("data");
              iview.setImageBitmap((Bitmap)o);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ((Bitmap)o).compress(Bitmap.CompressFormat.PNG, 0
                    /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
            mStorageRef.child("profile.png").putStream(bs).
                    addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ProfilePicActivity.this,
                                        "File Uploaded successfully",
                                        Toast.LENGTH_LONG).show();

                              Uri u =   task.getResult().getDownloadUrl();
                             String s =  u.toString();
                                FirebaseDatabase database =
                                        FirebaseDatabase.getInstance();
                                DatabaseReference myRef =
                                        database.getReference("user_info/"+FirebaseAuth.getInstance().getUid());
                                myRef.child("profile_url").setValue(s);
                            }else{
                                Toast.makeText(ProfilePicActivity.this,
                                        "File Uploaded Failed",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
       if(requestCode==125 && resultCode==RESULT_OK){
           Uri u = data.getData();
            iview.setImageURI(u);
           File f = new File(u.getPath());
            mStorageRef.child(f.getName()).putFile(u).
                    addOnCompleteListener(new OnCompleteListener
                            <UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.
                                TaskSnapshot> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(ProfilePicActivity.this,
                                        "File Uploaded successfully",
                                        Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(ProfilePicActivity.this,
                                        "Failed to  Upload",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });


       }
    }
}

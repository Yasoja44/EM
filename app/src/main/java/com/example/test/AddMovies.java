package com.example.test;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddMovies extends AppCompatActivity {
    EditText name, desc, date, genre;
    DatePickerDialog datePickerDialog;
    Button dateBtn, addMovieBtn;
    FirebaseFirestore fStory;

    ImageView image;
    ImageButton btnPic, btnGallery;
    String currentPhotoPath;
    StorageReference storageReference;
    public static final int REQUEST_CODE1 = 1;
    public static final int REQUEST_CODE_CAMERA = 2;
    private static final int REQUEST_GALLERY = 5;
    String movieUri;
    FloatingActionButton logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movies);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        initDatePicker();
        dateBtn = findViewById(R.id.date_button);
        name = findViewById(R.id.movie_name);
        desc = findViewById(R.id.movie_desc);
        genre = findViewById(R.id.movie_genre);
        addMovieBtn = findViewById(R.id.add_movie_button);
        dateBtn.setText(getTodaysDate());
        fStory = FirebaseFirestore.getInstance();

        image = findViewById(R.id.image);
        btnPic = findViewById(R.id.cameraButton);
        btnGallery = findViewById(R.id.galleryButton);
        logoutBtn = findViewById(R.id.float_logout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AddMovies.this, Login.class);
                startActivity(intent);
            }
        });

        addMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference df = fStory.collection("Movies");
                Map<String, Object> movieInfo = new HashMap<>();
                movieInfo.put("MovieName", name.getText().toString());
                movieInfo.put("MovieDesc", desc.getText().toString());
                movieInfo.put("MovieReleaseDate", dateBtn.getText().toString());
                movieInfo.put("MovieGenre", genre.getText().toString());
                movieInfo.put("MoviePic", movieUri);

                df.add(movieInfo);
                Toast.makeText(AddMovies.this, "Movie Added", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddMovies.this, ViewAllMovies.class);
                startActivity(intent);
            }
        });

        ///////////////////////////////////////////////////////////

        storageReference = FirebaseStorage.getInstance().getReference();

        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, REQUEST_GALLERY);
            }
        });
    }

    //--------------DATE----------------------------------------------------
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DATE);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                dateBtn.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return getMonthFormat(month) + " " + dayOfMonth + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";

    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    //----------------------------------------------------------------------

    //--------PICTURE-------------------------------------------------------

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, REQUEST_CODE1);
        }else{
            dispatchTakePictureIntent();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE1){
            //Toast.makeText(this, String.valueOf(grantResults[0]), Toast.LENGTH_SHORT).show();
            if(grantResults.length > 0 /*&& grantResults[0] == PackageManager.PERMISSION_GRANTED*/)
            {
                dispatchTakePictureIntent();
            }else{
                Toast.makeText(this, "Camera Permission Needed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK){
            File f = new File(currentPhotoPath);
            //image.setImageURI(Uri.fromFile(f));
            Log.d("tag", "URL " + Uri.fromFile(f));
            //Toast.makeText(this, Uri.fromFile(f).toString(), Toast.LENGTH_SHORT).show();

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);

            uploadImageToFirebase(f.getName(), contentUri);
        }
        else if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK){
            Uri contentUri = data.getData();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
            //image.setImageURI(contentUri);

            uploadImageToFirebase(imageFileName, contentUri);

        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    private void uploadImageToFirebase(String imageFileName, Uri contentUri) {
        StorageReference refImage = storageReference.child("images/" + imageFileName);
        refImage.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                refImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(AddMovies.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        movieUri = uri.toString();
                        Picasso.get().load(uri).into(image);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddMovies.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
            }
        }
    }
    //----------------------------------------------------------------------

}
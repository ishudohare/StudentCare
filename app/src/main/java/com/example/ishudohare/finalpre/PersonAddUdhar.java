package com.example.ishudohare.finalpre;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class PersonAddUdhar extends AppCompatActivity {
    ImageView pic;
    TextView name;
    FileInputStream fis ;
    public static int RESULT_LOAD_IMAGE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_add_udhar);
        pic=(ImageView)findViewById(R.id.personpic);
        name=(TextView)findViewById(R.id.personname);
    }
    public void image(View view){
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            FileInputStream fis = null;
//            try {
//                fis = new FileInputStream(picturePath);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            byte[] img = new byte[0];
//            try {
//                img = new byte[fis.available()];
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
//            pic.setImageBitmap(bitmap);
//            System.out.println("icture path" + picturePath);
//            cursor.close();
//
//
//
// ImageView imageView = (ImageView) findViewById(R.id.personpic);
            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));


            Uri targetUri = data.getData();
            System.out.println("bla"+targetUri.toString());
            System.out.println("bla1" + targetUri.getPath());
            Bitmap bitmap = null;

            String path=getRealPathFromURI(this,targetUri);
System.out.println("path " + path);
            File imgfile=new File(path);
            System.out.println("file"+imgfile.toString());
            try {
                fis=new FileInputStream(imgfile);
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                e.printStackTrace();
            }
            //System.out.println("fis to string"+fis.toString());

            try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 700, 700, true);

            pic.setImageBitmap(resized);
        }
    }
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public void done(View view){
        String pname=name.getText().toString();
        System.out.println("save rpessed");
        byte[] img1=null;
        if(pname.isEmpty()|| fis==null){
            Toast.makeText(PersonAddUdhar.this, "Enter name to save", Toast.LENGTH_SHORT).show();
            return;
        }
        else{

            System.out.println("try to set byte array");

            try {
                img1=new byte[fis.available()];
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("try to read img1 in fis");

            try {
                fis.read(img1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("database to be updated");
            Udhardatabase entry=new Udhardatabase(this);
            String res="";
            try {
                entry.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            Deflater compressor = new Deflater();
//            compressor.setLevel(Deflater.BEST_COMPRESSION);
//
//            // Give the compressor the data to compress
//            compressor.setInput(img1);
//            compressor.finish();
//
//            // Create an expandable byte array to hold the compressed data.
//            // It is not necessary that the compressed data will be smaller than
//            // the uncompressed data.
//            ByteArrayOutputStream bos = new ByteArrayOutputStream(img1.length);
//
//            // Compress the data
//            byte[] buf = new byte[1024];
//            while (!compressor.finished()) {
//                int count = compressor.deflate(buf);
//                bos.write(buf, 0, count);
//            }
//            try {
//                bos.close();
//            } catch (IOException e) {
//            }
//
//            // Get the compressed data
//            byte[] compressedData = bos.toByteArray();

            entry.CreateEntry(pname,img1,res);
            setResult(RESULT_OK);
            finish();

            // Bitmap b = Bitmap.createBitmap(pic.getWidth(), pic.getHeight(),Bitmap.Config.ARGB_8888);
            //Canvas canvas = new Canvas(b);
            //pic.draw(canvas);
            //OutputStream fOut = null;
            //new File("/sdcard/studentlifepics").mkdir();
//            fOut = new FileOutputStream("/sdcard/myPaintings/image.jpg");
//            b.compress(Bitmap.CompressFormat.JPEG, 95, fOut);
//            Bitmap b = BitmapFactory.decodeResource(getResources(),
//                    pic);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//            byte[] img = bos.toByteArray();
        }
    }
}

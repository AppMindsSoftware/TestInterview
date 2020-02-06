package com.example.bhavanitest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SplashActivity extends Activity
{

    TextView current;
    Button screenShot;
    private TextView txtCurrentTime;
    String mPath="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* Thread myThread = null;
        Runnable myRunnableThread = new CountDownRunner();
        myThread= new Thread(myRunnableThread);
        myThread.start();*/

        current= (TextView)findViewById(R.id.current);
        screenShot = findViewById(R.id.screenShot);
        screenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStoragePermissionGranted())
                {
                    takeScreenshot();
                }

            }
        });

    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
             mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
            Log.e("TAG", "takeScreenshot: "+e );
        }
    }

    private void openScreenshot(File imageFile) {
        /*Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);*/

        String fileName = mPath;//Name of an image
       // String externalStorageDirectory = Environment.getExternalStorageDirectory().toString();
       // String myDir = externalStorageDirectory + "/saved_images/"; // the file will be in saved_images
        Uri uri = Uri.parse("file:///" + fileName );
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/html");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test Mail");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Launcher");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share Deal"));


    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v("TAG","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }


    public void doWork()
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa");

                    txtCurrentTime= (TextView)findViewById(R.id.txtCurrentTime);

                    Date systemDate = Calendar.getInstance().getTime();
                    String myDate = sdf.format(systemDate);
//                  txtCurrentTime.setText(myDate);

                    Date Date1 = sdf.parse(myDate);
                    Date Date2 = sdf.parse("02:50:00 pm");

                    long millse = Date1.getTime() - Date2.getTime();
                    long mills = Math.abs(millse);

                    int Hours = (int) (mills/(1000 * 60 * 60));
                    int Mins = (int) (mills/(1000*60)) % 60;
                    long Secs = (int) (mills / 1000) % 60;

                    String diff = Hours + ":" + Mins + ":" + Secs; // updated value every1 second
                    current.setText(diff);
                }
                catch (Exception e)
                {

                }
            }
        });
    }

    class CountDownRunner implements Runnable
    {
        // @Override
        public void run()
        {
            while(!Thread.currentThread().isInterrupted())
            {
                try
                {
                    doWork();
                    Thread.sleep(1000); // Pause of 1 Second
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
                catch(Exception e)
                {
                }
            }
        }
    }


}
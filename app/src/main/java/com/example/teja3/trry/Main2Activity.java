package com.example.teja3.trry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by teja3 on 13-07-2016.
 */
public class Main2Activity extends ActionBarActivity {

    int from_Where_I_Am_Coming = 0;

    String latitude;
    String longitude;

    MyService appLocationService1;

    ProgressBar Pbar;

    Timer timer1;
    TimerTask timerTask1;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();
    private DBHelper mydb ;

    TextView result1;
    TextView product1 ;
    TextView company1;
    TextView latilongi;
    //Button bt;
    //TextView longitude2;
    Button b;


    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        mydb = new DBHelper(this);

        result1 = (TextView) findViewById(R.id.textView5);
        Bundle extra = getIntent().getExtras();
        if(extra !=null)
        {
            String resu=extra.getString("res");
            result1.setText(resu);
        }
        product1 = (TextView) findViewById(R.id.editTextProduct);
        company1 = (TextView) findViewById(R.id.editTextCompany);

        b = (Button) findViewById(R.id.button1);


        Pbar = (ProgressBar)findViewById(R.id.progressBar1);
        Pbar.setVisibility(View.INVISIBLE);
        latilongi=(TextView) findViewById(R.id.textView7);
        latilongi.setText("");


        appLocationService1 = new MyService(
                Main2Activity.this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.saved_data) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void save(View view){


        String provider = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        //if(!provider.equals(""))
        if((provider.contains("gps"))||(provider.contains("network"))){
            Pbar.setVisibility(View.VISIBLE);
            b.setVisibility(View.INVISIBLE);

            startTimer();
            /*if(mydb.insertContact(result1.getText().toString(), product1.getText().toString(), company1.getText().toString(),latitude,longitude)) {
                Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
            }*/
            //Intent intent = new Intent(getApplicationContext(),BarcodeScanner.class);
            //startActivity(intent);

        }else {
            showSettingsAlert("GPS");
            //canToggleGPS();
            //turnGPSOn();
            //getlo();

            //download(arg0);


            //CheckEnableGPS();
        }


        //if(mydb.insertContact(result1.getText().toString(), product1.getText().toString(), company1.getText().toString())){
        //    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
        //}
        //Intent intent = new Intent(getApplicationContext(),BarcodeScanner.class);
        //startActivity(intent);
    }




    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Main2Activity.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled with High Accuracy! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        Main2Activity.this.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }




    public void startTimer() {
        //set a new Timer
        timer1 = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer1.schedule(timerTask1, 5000, 5000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer1 != null) {
            timer1.cancel();
            timer1 = null;
        }
    }


    public void initializeTimerTask() {

        timerTask1 = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {


                        Location nwLocation = appLocationService1
                                .getLocation(LocationManager.NETWORK_PROVIDER);

                        if (nwLocation != null) {
                            latitude = Double.toString(nwLocation.getLatitude());
                            longitude = Double.toString(nwLocation.getLongitude());
                            /*Toast.makeText(
                                    getApplicationContext(),
                                    "Mobile Location (NW): \nLatitude: " + latitude
                                            + "\nLongitude: " + longitude,
                                    Toast.LENGTH_LONG).show();*/


                            Pbar.setVisibility(View.INVISIBLE);
                            stoptimertask();
                            /*Toast.makeText(
                                    getApplicationContext(),
                                    "Mobile Location (GPS): \nLatitude: " + latitude
                                            + "\nLongitude: " + longitude,
                                    Toast.LENGTH_LONG).show();*/
                            if (mydb.insertContact(result1.getText().toString(), product1.getText().toString(), company1.getText().toString(), latitude, longitude)) {
                                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Saveddata.class);
                                //intent.putExtras(dataBundle);

                                startActivity(intent);
                            }} else {

                            //turnGPSOff();
                            //showSettingsAlert("NETWORK");


                            Location gpsLocation = appLocationService1
                                    .getLocation(LocationManager.GPS_PROVIDER);


                            if (gpsLocation == null) {


                            }
                            if (gpsLocation != null) {
                                latitude = Double.toString(gpsLocation.getLatitude());
                                longitude = Double.toString(gpsLocation.getLongitude());
                                Pbar.setVisibility(View.INVISIBLE);
                                stoptimertask();
                            /*Toast.makeText(
                                    getApplicationContext(),
                                    "Mobile Location (GPS): \nLatitude: " + latitude
                                            + "\nLongitude: " + longitude,
                                    Toast.LENGTH_LONG).show();*/
                                if (mydb.insertContact(result1.getText().toString(), product1.getText().toString(), company1.getText().toString(), latitude, longitude)) {
                                    Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Saveddata.class);
                                    //intent.putExtras(dataBundle);

                                    startActivity(intent);
                                }
                                //Intent intent = new Intent(getApplicationContext(),BarcodeScanner.class);
                                //startActivity(intent);
                                //gpsLocation = null;
                            } else {
                                //Pbar.setVisibility(View.VISIBLE);

                            }
                        }


                    }






                });
            }
        };
    }




}
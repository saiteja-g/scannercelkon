package com.example.teja3.trry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
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
public class MainUpd extends Activity {


    String latitude;
    String longitude;

    MyService appLocationService;

    final Handler handler = new Handler();

    Timer timer;
    TimerTask timerTask;
    Button b;

    private DBHelper mydb ;

    TextView result2;
    TextView product2 ;
    TextView company2;
    TextView latlong2;
    ProgressBar Pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatee);



        result2 = (TextView) findViewById(R.id.textView5);
        product2 = (TextView) findViewById(R.id.editTextProduct);
        company2 = (TextView) findViewById(R.id.editTextCompany);
        latlong2 = (TextView) findViewById(R.id.textView7);


        Pbar = (ProgressBar)findViewById(R.id.progressBar1);
        Pbar.setVisibility(View.INVISIBLE);

        b = (Button)findViewById(R.id.button1);
        //mydb = new DBHelper(this);
        //Bundle extras = getIntent().getExtras();
        //String Value = extras.getString("resu");

        //Toast.makeText(getApplicationContext(), ""+Value+"is", Toast.LENGTH_SHORT).show();

        //Cursor rs = mydb.getDataU(Value);



        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            String Value = extras.getString("resu");

            //if(Value>0){
            //means this is the view part not the add contact part.
            Cursor rs = mydb.getDataU(Value);
            //id_To_Update = Value;
            rs.moveToFirst();

            String result3 = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_RESULT));
            String product3 = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PRODUCT));
            String company3 = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_COMPANY));
            String latlong3 = "Latitude : "+rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_LATITUDE))+"\n"+"Longitude : "+rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_LONGITUDE));
            //latitude3  = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_COMPANY));


            if (!rs.isClosed())
            {
                rs.close();
            }
            //b.setVisibility(View.INVISIBLE);

            Pbar = (ProgressBar)findViewById(R.id.progressBar1);
            Pbar.setVisibility(View.INVISIBLE);

            result2.setText((CharSequence) result3);
            result2.setTextIsSelectable(true);
            //result2.setFocusable(false);
            //result2.setClickable(false);

            product2.setText((CharSequence) product3);
            //product2.setFocusable(false);
            //product2.setClickable(false);

            company2.setText((CharSequence) company3);
            //company2.setFocusable(false);
            //company2.setClickable(false);

            latlong2.setText(latlong3);
            latlong2.setTextIsSelectable(true);
            //latlong2.setFocusable(false);
            //latlong2.setClickable(true);



            appLocationService = new MyService(
                    MainUpd.this);
            //}
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_upd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }



    public void updatee(View view){


        String provider = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        //if(!provider.equals(""))
        if((provider.contains("gps"))&&(provider.contains("network"))){
            Pbar.setVisibility(View.VISIBLE);

            //Button b = (Button)findViewById(R.id.button1);

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
                MainUpd.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled with High Accuracy! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        MainUpd.this.startActivity(intent);
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
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, 5000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        Location gpsLocation = appLocationService
                                .getLocation(LocationManager.GPS_PROVIDER);


                        if (gpsLocation != null) {


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
                                if (mydb.updatebackend(result2.getText().toString(), product2.getText().toString(), company2.getText().toString(), latitude, longitude)) {
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

                            }} else {

                            Location nwLocation = appLocationService
                                    .getLocation(LocationManager.NETWORK_PROVIDER);


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
                            if (mydb.updatebackend(result2.getText().toString(), product2.getText().toString(), company2.getText().toString(), latitude, longitude)) {
                                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Saveddata.class);
                                //intent.putExtras(dataBundle);

                                startActivity(intent);
                            }
                        }
                    }

                });
            }
        };
    }


}

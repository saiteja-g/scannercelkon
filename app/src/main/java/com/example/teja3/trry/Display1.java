package com.example.teja3.trry;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Display1 extends ActionBarActivity {



    private DBHelper mydb ;

    TextView result2;
    TextView product2;
    TextView company2;
    TextView latlong2;
    ProgressBar Pbar;

    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        result2 = (TextView) findViewById(R.id.textView5);
        product2 = (TextView) findViewById(R.id.editTextProduct);
        company2 = (TextView) findViewById(R.id.editTextCompany);
        latlong2 = (TextView) findViewById(R.id.textView7);


        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
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
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                Pbar = (ProgressBar)findViewById(R.id.progressBar1);
                Pbar.setVisibility(View.INVISIBLE);

                result2.setText((CharSequence) result3);
                result2.setTextIsSelectable(true);
                //result2.setFocusable(false);
                //result2.setClickable(false);

                product2.setText((CharSequence) product3);
                product2.setTextIsSelectable(true);

                //product2.setEnabled(false);
                //product2.setFocusable(false);
                //product2.setClickable(false);

                company2.setText((CharSequence) company3);
                company2.setTextIsSelectable(true);

                //company2.setEnabled(true);
                //company2.setFocusable(false);
                //company2.setClickable(false);

                latlong2.setText(latlong3);
                latlong2.setTextIsSelectable(true);
                //latlong2.setFocusable(false);
                //latlong2.setClickable(false);




            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int ide = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (ide == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.deleteContact)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mydb.clear();
                            //mydb.deleteContact(id_To_Update);
                            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            AlertDialog d = builder.create();
            d.setTitle("Are you sure");
            d.show();

            return true;        }*/




        return super.onOptionsItemSelected(item);
    }
}

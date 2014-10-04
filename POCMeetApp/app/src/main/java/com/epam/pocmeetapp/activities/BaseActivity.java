package com.epam.pocmeetapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


public class BaseActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(getApplicationContext(), getString(R.string.device_is_not_conected_error_message), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_schedule:
                if (!(this instanceof MainScheduleActivity)) {
                    Intent intent = new Intent(this, MainScheduleActivity.class);
                    startActivityForResult(intent, 0);
                    finish();
                }
                break;

            case R.id.action_my_schedule:

                break;

            case R.id.action_maps:
                if (!(this instanceof PlaceActivity)) {
                    Intent intent = new Intent(this, PlaceActivity.class);
                    startActivityForResult(intent, 0);
                    finish();
                }
                break;

            case R.id.action_logout:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
      }


        return super.onOptionsItemSelected(item);
    }
}

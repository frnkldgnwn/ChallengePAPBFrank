package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        SharedPreferences.Editor sharedPreferencesEdit = getSharedPreferences(getApplication().toString(), MODE_PRIVATE).edit();

        if (getSharedPreferences(getApplication().toString(), MODE_PRIVATE).getInt("INTERVAL", -1) == -1) {
            sharedPreferencesEdit.putInt("INTERVAL", 5);
        }

        if (getSharedPreferences(getApplication().toString(), MODE_PRIVATE).getInt("JACKPOT_NUM", -1) == -1) {
            sharedPreferencesEdit.putInt("JACKPOT_NUM", 7);
        }

        sharedPreferencesEdit.apply();

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.main_navigation, R.id.settings_fragment)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        navController.navigate(R.id.settings_fragment);
                        return true;
                    }
                });
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}

package view.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cpbr11.campuseromobile.R;
import presenter.IntermediateAuthRequests;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    protected void onCreate(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R
                .string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem agenda = menu.add("Agenda");
        agenda.setIcon(R.drawable.ic_schedule_black_24dp);

        MenuItem personalAgenda = menu.add("Agenda Pessoal");
        personalAgenda.setIcon(R.drawable.ic_today_black_24dp);

        if(IntermediateAuthRequests.isUserLoggedIn(this)){
            MenuItem profile = menu.add("Meu Perfil");
            profile.setIcon(R.drawable.ic_person_black_24dp);
        }

        navigationView.setNavigationItemSelectedListener(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Agenda")) {

        }
        else if(item.getTitle().equals("Agenda Pessoal")){

        }
        else if(item.getTitle().equals("Meu Perfil")){
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
package com.example.trivia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.app.AlertDialog;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//This activity contains everything and uses fragment manager to swap screens.
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    ExecutorService executor;

    //All database actions occur through this database.
    public static final String DATABASE_NAME = "MyGameDatabase";
    private List<Game> games;

    private GameDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating threads - for database actions.
        this.executor = Executors.newFixedThreadPool(4);

        //Builds database
        this.db = Room.databaseBuilder(
                getApplicationContext(),
                GameDatabase.class,
                DATABASE_NAME
        ).build();


        //Adding our own custom toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Creating drawer layout - incorporates navigation
        drawerLayout = findViewById(R.id.nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.nav_open,
                R.string.nav_closed
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Adding navigation items
        NavigationView navigationView = findViewById(R.id.nav_panel);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().performIdentifierAction(R.id.home_menu, 0);

        //Gets all games from the database
        retrieveGames();


    }

    //Change fragment function - simplifies code and abstracts alot of detail
    public void changeFragment(Fragment fragment, String tag, String backstackTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(backstackTag)
                .commit();
    }

    //Overwritting backpressed so we can do some custom actions.
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("Game");

        //If drawer is open - shut it.
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        }

        //If previous fragment is game we don't want to return to that screen so we return home.
        else if (getSupportFragmentManager().getBackStackEntryCount() != 0 &&
                getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName().equals("Game")) {
            changeFragment(new Home(), "Home", "Game");
        }

        //If current fragment is game - we want to give a warning.
        else if (currentFragment != null && currentFragment.isVisible()) {
            AlertDialog.Builder exitBuilder = new AlertDialog.Builder(this);
            exitBuilder.setCancelable(true);
            exitBuilder.setTitle("Exit Game");
            exitBuilder.setMessage("Are you sure you want to exit the game?\nAny unsaved progress may be lost");
            exitBuilder.setPositiveButton("Confirm",
                    (dialog, which) -> MainActivity.super.onBackPressed());
            exitBuilder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            });
            AlertDialog dialog = exitBuilder.create();
            dialog.setOnCancelListener(dialog1 -> dialog1.dismiss());

            dialog.show();
        }

        //Else do what it would normally do
        else {
            super.onBackPressed();
        }
    }

    //Setting the navigation drawers buttons.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Getting menu item id
        int id = item.getItemId();


        Fragment fragment = getSupportFragmentManager().findFragmentByTag("Game"); //Checks if game is in play
        //If the game is being played and quit menu isnt selected (as quit menu already has a popup)
        //then we want to provide a popup checking if they want to leave the current game
        if (fragment != null && fragment.isVisible() && id != R.id.quit_menu) {
            AlertDialog.Builder exitBuilder = new AlertDialog.Builder(this);
            exitBuilder.setCancelable(true);
            exitBuilder.setTitle("Exit Game");
            exitBuilder.setMessage("Are you sure you want to exit the game?\nAny unsaved progress may be lost");
            exitBuilder.setPositiveButton("Confirm",
                    (dialog, which) -> {
                        if (id == R.id.home_menu) {
                            changeFragment(new Home(), "Home", "Game");
                        } else if (id == R.id.play_menu) {
                            changeFragment(new GameSelectionFragment(), "Select", "Game");
                        } else if (id == R.id.recent_menu) {
                            changeFragment(new FragmentRecentGames(), "Recent", "Game");
                        }
                    });
            exitBuilder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            });
            AlertDialog dialog = exitBuilder.create();
            dialog.setOnCancelListener(dialog1 -> dialog1.dismiss());
            dialog.show();


        } else {
            if (id == R.id.home_menu) {
                changeFragment(new Home(), "Home", "Nav");
            } else if (id == R.id.play_menu) {
                changeFragment(new GameSelectionFragment(), "Select", "Nav");
            } else if (id == R.id.quit_menu) {
                //Quit game confirmation popup
                AlertDialog.Builder quitBuilder = new AlertDialog.Builder(this);
                quitBuilder.setCancelable(true);
                quitBuilder.setTitle("Quit Game");
                quitBuilder.setMessage("Are you sure you want to quit the application?");
                quitBuilder.setPositiveButton("Confirm",
                        (dialog, which) -> {
                            finish();
                            System.exit(0);
                        });
                quitBuilder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                });
                AlertDialog dialog = quitBuilder.create();
                dialog.setOnCancelListener(dialog1 -> dialog1.dismiss());
                dialog.show();

            } else if (id == R.id.recent_menu) {
                changeFragment(new FragmentRecentGames(), "Recent", "Game");
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    //Inserts game and updates game list
    public void insertGame(Game game) {
        executor.execute(() -> {
            db.gameDAO().insertGame(game);
            games = db.gameDAO().getAllGames();
        });
    }

    public void retrieveGames() {
        executor.execute(() -> {
            games = db.gameDAO().getAllGames();
            //Uncomment below and refresh app for a fresh start. Then comment out again (lazy way)
            //this.db.gameDAO().deleteAllGames();
        });
    }

    public List<Game> getGames() {
        return games;
    }

}
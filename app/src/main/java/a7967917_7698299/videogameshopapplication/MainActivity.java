package a7967917_7698299.videogameshopapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import a7967917_7698299.videogameshopapplication.fragments.AccountFragment;
import a7967917_7698299.videogameshopapplication.fragments.CartFragment;
import a7967917_7698299.videogameshopapplication.fragments.HelpFragment;
import a7967917_7698299.videogameshopapplication.fragments.HomeFragment;
import a7967917_7698299.videogameshopapplication.fragments.OrdersFragment;
import a7967917_7698299.videogameshopapplication.fragments.ResultsFragment;
import a7967917_7698299.videogameshopapplication.fragments.SettingsFragment;
import a7967917_7698299.videogameshopapplication.fragments.WishlistFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    // components
    private DrawerLayout drawer;
    private NavigationView navigationView;

    // logic variables
    private boolean viewIsAtHome;
    private boolean showingMainDrawerMenu;

    // fragments
    private AccountFragment accountFragment;
    private HelpFragment helpFragment;
    private HomeFragment homeFragment;
    private OrdersFragment ordersFragment;
    private ResultsFragment resultsFragment;
    private SettingsFragment settingsFragment;
    private WishlistFragment wishlistFragment;
    private CartFragment cartFragment;

    // variables to keep track of the current and previous fragments
    private Fragment currentFragment;
    private Fragment previousFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // init drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // reset the drawer menu when close
                resetMainDrawerMenu();
            }
        };
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        // init navigation view
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // reset the drawer menu
        resetMainDrawerMenu();

        // init fragments
        accountFragment = new AccountFragment();
        helpFragment = new HelpFragment();
        homeFragment = new HomeFragment();
        ordersFragment = new OrdersFragment();
        resultsFragment = new ResultsFragment();
        settingsFragment = new SettingsFragment();
        wishlistFragment = new WishlistFragment();
        cartFragment = new CartFragment();

        // init other
        viewIsAtHome = false;
        showingMainDrawerMenu = true;

        // set first fragment to home
        displayFragment(R.id.nav_home);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            if (!showingMainDrawerMenu) {
                resetMainDrawerMenu();
            } else {
                drawer.closeDrawer(GravityCompat.START);
            }
        } else {

            // return the view to home
            if (!viewIsAtHome) {
                displayFragment(R.id.nav_home);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        // open cart fragment
        if (id == R.id.action_cart) {
            displayFragment(R.id.action_cart);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        final int itemId = item.getItemId();
        if (itemId == R.id.nav_shop_console || itemId == R.id.nav_shop_game_by_category ||
                itemId == R.id.nav_shop_game_by_console) {
            changeDrawerMenu(itemId);
        } else {
            drawer.closeDrawer(GravityCompat.START);
            // make the navigation to another fragment smoother
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayFragment(itemId);
                }
            }, 150);

            // close the drawer
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void setDrawerHeaderBackButtonVisibility(boolean visible) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView headerTextView = (TextView) hView.findViewById(R.id.drawer_header_textview);
        Button headerBackButton = (Button) hView.findViewById(R.id.drawer_header_button);

        if (visible) {
            headerTextView.setVisibility(View.GONE);
            headerBackButton.setVisibility(View.VISIBLE);

        } else {
            headerTextView.setVisibility(View.VISIBLE);
            headerBackButton.setVisibility(View.GONE);
        }


    }


    public void displayFragment(int itemId) {

        previousFragment = currentFragment;
        currentFragment = null;
        String title = getString(R.string.app_name);
        String subtitle = "";

        // set the current fragment depending of the item's id
        switch (itemId) {
            case R.id.nav_home:
                currentFragment = homeFragment;
                title = "Home";
                break;
            // consoles
            case R.id.nav_switch:
                currentFragment = resultsFragment;
                title = "Results";
                break;
            case R.id.nav_3ds:
                currentFragment = resultsFragment;
                title = "Results";
                break;
            case R.id.nav_ps4:
                currentFragment = resultsFragment;
                title = "Results";
                break;
            case R.id.nav_xbox:
                currentFragment = resultsFragment;
                title = "Results";
                break;
            // games
            case R.id.nav_shop_game_by_category:
                currentFragment = resultsFragment;
                title = "Results";
                break;
            case R.id.nav_shop_game_by_console:
                currentFragment = resultsFragment;
                title = "Results";
                break;
            case R.id.nav_account:
                currentFragment = accountFragment;
                title = "Account";
                break;
            case R.id.nav_wishlist:
                currentFragment = wishlistFragment;
                title = "Wishlist";
                break;
            case R.id.nav_orders:
                currentFragment = ordersFragment;
                title = "Orders";
                break;
            case R.id.nav_settings:
                currentFragment = settingsFragment;
                title = "Settings";
                break;
            case R.id.nav_feedback:
                // email intent
                launchFeedbackEmailIntent();
                break;
            case R.id.nav_help:
                currentFragment = helpFragment;
                title = "Help";
                break;
            case R.id.action_cart:
                currentFragment = cartFragment;
                title = "Cart";
                break;
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setSubtitle(subtitle);
        }

        // animate the fragment navigation
        if (currentFragment != null) {
            if (!currentFragment.isVisible()) {
                replaceFragmentWithAnimation(currentFragment, "" + currentFragment);
            }
        }

        // set hardware back button logic
        if (itemId == R.id.nav_home) {
            viewIsAtHome = true;
        } else {
            viewIsAtHome = false;
        }

    }


    // animation of fragments - makes the navigation flow smoother
    public void replaceFragmentWithAnimation(Fragment fragment, String tag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragment.equals(cartFragment)) {
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        } else if (previousFragment != null && previousFragment.equals(cartFragment)) {
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        } else {
            transaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
        }

        transaction.replace(R.id.content_frame, fragment, tag);
        transaction.commit();
    }


    //
    private void resetMainDrawerMenu() {
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.main_drawer_menu);
        showingMainDrawerMenu = true;
        setDrawerHeaderBackButtonVisibility(false);
    }


    // change the drawer menu to a specific one
    private void changeDrawerMenu(int itemId) {

        navigationView.getMenu().clear();


        if (itemId == R.id.nav_shop_console)
            navigationView.inflateMenu(R.menu.console_drawer_menu);
        if (itemId == R.id.nav_shop_game_by_category)
            navigationView.inflateMenu(R.menu.game_category_drawer_menu);
        if (itemId == R.id.nav_shop_game_by_console)
            navigationView.inflateMenu(R.menu.console_drawer_menu);
        showingMainDrawerMenu = false;
        setDrawerHeaderBackButtonVisibility(true);
    }


    // creates an email intent to send feedback
    private void launchFeedbackEmailIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/email");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Alexandre.Stelzig@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "App Feedback");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void drawerHeaderButtonClicked(View view) {
        resetMainDrawerMenu();
    }
}

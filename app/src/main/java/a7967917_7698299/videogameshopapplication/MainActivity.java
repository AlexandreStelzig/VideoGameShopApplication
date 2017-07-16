package a7967917_7698299.videogameshopapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import a7967917_7698299.videogameshopapplication.database.DatabaseHardCodedValues;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.fragments.AccountFragment;
import a7967917_7698299.videogameshopapplication.fragments.AccountInfoFragment;
import a7967917_7698299.videogameshopapplication.fragments.AddressInfoFragment;
import a7967917_7698299.videogameshopapplication.fragments.AddressListFragment;
import a7967917_7698299.videogameshopapplication.fragments.CartFragment;
import a7967917_7698299.videogameshopapplication.fragments.CheckoutFragment;
import a7967917_7698299.videogameshopapplication.fragments.HelpFragment;
import a7967917_7698299.videogameshopapplication.fragments.HomeFragment;
import a7967917_7698299.videogameshopapplication.fragments.ItemInfoFragment;
import a7967917_7698299.videogameshopapplication.fragments.OrderInfoFragment;
import a7967917_7698299.videogameshopapplication.fragments.OrderListFragment;
import a7967917_7698299.videogameshopapplication.fragments.PaymentInfoFragment;
import a7967917_7698299.videogameshopapplication.fragments.PaymentListFragment;
import a7967917_7698299.videogameshopapplication.fragments.ResultsFragment;
import a7967917_7698299.videogameshopapplication.fragments.SettingsFragment;
import a7967917_7698299.videogameshopapplication.fragments.SignInFragment;
import a7967917_7698299.videogameshopapplication.fragments.SignUpFragment;
import a7967917_7698299.videogameshopapplication.fragments.WishlistFragment;
import a7967917_7698299.videogameshopapplication.model.PaymentInformation;
import a7967917_7698299.videogameshopapplication.model.UserAddress;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;
import a7967917_7698299.videogameshopapplication.variables.OrderVariables;
import a7967917_7698299.videogameshopapplication.variables.VideoGameVariables;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    // TODO IMPORTANT!!! TURN OFF FOR RELEASE
    private boolean DELETE_DATABASE_EVERY_REBUILD = true;

    // components
    private DrawerLayout drawer;
    private NavigationView navigationView;

    // database
    private DatabaseManager databaseManager;


    // logic variables
    private boolean viewIsAtHome;
    private boolean showingMainDrawerMenu;
    private PaymentInformation editingPayment = null;
    private UserAddress editingAddress = null;

    // fragments
    private AccountFragment accountFragment;
    private HelpFragment helpFragment;
    private HomeFragment homeFragment;
    private OrderListFragment orderListFragment;
    private ResultsFragment resultsFragment;
    private SettingsFragment settingsFragment;
    private WishlistFragment wishlistFragment;
    private CartFragment cartFragment;
    private ItemInfoFragment itemInfoFragment;
    private SignInFragment signInFragment;
    private CheckoutFragment checkoutFragment;
    private AddressListFragment addressListFragment;
    private AddressInfoFragment addressInfoFragment;
    private OrderInfoFragment orderInfoFragment;
    private PaymentInfoFragment paymentInfoFragment;
    private PaymentListFragment paymentListFragment;
    private SignUpFragment signUpFragment;
    private AccountInfoFragment accountInfoFragment;

    // variables to keep track of the current and previous fragments
    private Fragment currentFragment;
    private int previousFragmentTag = -1;
    private int currentFragmentTag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseManager.initDatabase(this, DELETE_DATABASE_EVERY_REBUILD);
        databaseManager = DatabaseManager.getInstance();

        // if database if empty, populate data
        if (databaseManager.isDatabaseEmpty()) {
            DatabaseHardCodedValues.getInstance().initDatabase();
        }

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
        orderListFragment = new OrderListFragment();
        resultsFragment = new ResultsFragment();
        settingsFragment = new SettingsFragment();
        wishlistFragment = new WishlistFragment();
        cartFragment = new CartFragment();
        itemInfoFragment = new ItemInfoFragment();
        signInFragment = new SignInFragment();
        checkoutFragment = new CheckoutFragment();
        addressListFragment = new AddressListFragment();
        addressInfoFragment = new AddressInfoFragment();
        orderInfoFragment = new OrderInfoFragment();
        paymentInfoFragment = new PaymentInfoFragment();
        paymentListFragment = new PaymentListFragment();
        signUpFragment = new SignUpFragment();
        accountInfoFragment = new AccountInfoFragment();

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

            if (previousFragmentTag != -1 && previousFragmentTag != currentFragmentTag) {
                resultsFragment.setRefreshData(false);
                displayFragment(previousFragmentTag);

                if(!currentFragment.equals(paymentListFragment) && !currentFragment.equals(addressListFragment) && !currentFragment.equals(accountInfoFragment))
                    previousFragmentTag = -1;
//                resultsFragment.setRefreshData(true);
            }
            // return the view to home
            else if (!viewIsAtHome) {
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

        MenuItem cartItem = menu.findItem(R.id.action_cart);
        if (signInFragment.isVisible() || signUpFragment.isVisible()) {
            cartItem.setVisible(false);
        } else {
            cartItem.setVisible(true);
        }

        if (databaseManager.getCurrentActiveUser() != null) {
            int nbItemsInCart = databaseManager.getNbItemsInCart();

            if (nbItemsInCart == 0)
                cartItem.setIcon(R.mipmap.ic_cart_0);
            else if (nbItemsInCart == 1)
                cartItem.setIcon(R.mipmap.ic_cart_1);
            else if (nbItemsInCart == 2)
                cartItem.setIcon(R.mipmap.ic_cart_2);
            else if (nbItemsInCart == 3)
                cartItem.setIcon(R.mipmap.ic_cart_3);
            else if (nbItemsInCart == 4)
                cartItem.setIcon(R.mipmap.ic_cart_4);
            else
                cartItem.setIcon(R.mipmap.ic_cart_5plus);
        } else {
            cartItem.setIcon(R.mipmap.ic_cart_0);
        }

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


        //  itemId != R.id.search_view_results fixes a bug where you couldn't search twice
        if (itemId == currentFragmentTag && itemId != R.id.search_view_results)
            return;


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
                setFilterByConsole(ItemVariables.CONSOLES.SWITCH);
                break;
            case R.id.nav_3ds:
                currentFragment = resultsFragment;
                setFilterByConsole(ItemVariables.CONSOLES.THREE_DS);
                title = "Results";
                break;
            case R.id.nav_ps4:
                currentFragment = resultsFragment;
                setFilterByConsole(ItemVariables.CONSOLES.PS4);
                title = "Results";
                break;
            case R.id.nav_xbox:
                currentFragment = resultsFragment;
                setFilterByConsole(ItemVariables.CONSOLES.XBOXONE);
                title = "Results";
                break;
            // games by category
            case R.id.nav_game_action:
                currentFragment = resultsFragment;
                setFilterGamesByCategory(VideoGameVariables.CATEGORY.ACTION);
                title = "Results";
                break;
            case R.id.nav_game_adventure:
                currentFragment = resultsFragment;
                setFilterGamesByCategory(VideoGameVariables.CATEGORY.ADVENTURE);
                title = "Results";
                break;
            case R.id.nav_game_rpg:
                currentFragment = resultsFragment;
                setFilterGamesByCategory(VideoGameVariables.CATEGORY.RPG);
                title = "Results";
                break;
            case R.id.nav_game_sport:
                currentFragment = resultsFragment;
                setFilterGamesByCategory(VideoGameVariables.CATEGORY.SPORTS);
                title = "Results";
                break;
            // games by console
            case R.id.nav_game_switch:
                currentFragment = resultsFragment;
                setFilterGamesByConsole(ItemVariables.CONSOLES.SWITCH);
                title = "Results";
                break;
            case R.id.nav_game_3ds:
                currentFragment = resultsFragment;
                setFilterGamesByConsole(ItemVariables.CONSOLES.THREE_DS);
                title = "Results";
                break;
            case R.id.nav_game_ps4:
                currentFragment = resultsFragment;
                setFilterGamesByConsole(ItemVariables.CONSOLES.PS4);
                title = "Results";
                break;
            case R.id.nav_game_xbox:
                currentFragment = resultsFragment;
                setFilterGamesByConsole(ItemVariables.CONSOLES.XBOXONE);
                title = "Results";
                break;

            // other nav
            case R.id.nav_account:
                if (isUserConnectedWithMessage()) {
                    currentFragment = accountFragment;
                    title = "Account";
                } else {
                    currentFragment = signInFragment;
                    title = "Sign in";
                }
                break;
            case R.id.nav_wishlist:
                if (isUserConnectedWithMessage()) {
                    currentFragment = wishlistFragment;
                    title = "Wishlist";
                } else {
                    currentFragment = signInFragment;
                    title = "Sign in";
                }
                break;
            case R.id.nav_orders:
                if (isUserConnectedWithMessage()) {
                    currentFragment = orderListFragment;
                    title = "Orders";
                } else {
                    currentFragment = signInFragment;
                    title = "Sign in";
                }
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
                if (isUserConnectedWithMessage()) {
                    currentFragment = cartFragment;
                    title = "Cart";
                } else {
                    currentFragment = signInFragment;
                    title = "Sign in";
                }
                break;
            case R.id.search_view_results:
                currentFragment = resultsFragment;
                title = "Results";
                break;
            case R.layout.fragment_item_info:
                currentFragment = itemInfoFragment;
                title = "Item info";
                break;
            case R.layout.fragment_checkout:
                currentFragment = checkoutFragment;
                title = "Checkout";
                break;
            case R.id.nav_sign_in_out:
                if (databaseManager.getCurrentActiveUser() == null) {
                    // sign in
                    currentFragment = signInFragment;

                    title = "Sign in";
                } else {
                    // sign out
                    showSignOutDialog();
                }
                break;
            case R.layout.fragment_address_list:
                currentFragment = addressListFragment;
                title = "Address List";
                break;
            case R.layout.fragment_address_info:
                currentFragment = this.addressInfoFragment;
                title = "Address Info";
                break;
            case R.layout.fragment_order_info:
                currentFragment = orderInfoFragment;
                title = "Order Info";
                break;
            case R.layout.fragment_payment_info:
                currentFragment = paymentInfoFragment;
                title = "Payment Info";
                break;
            case R.layout.fragment_payment_list:
                currentFragment = paymentListFragment;
                title = "Payment List";
                break;
            case R.id.nav_sign_up:
                currentFragment = signUpFragment;
                title = "Create Account";
                break;
            case R.layout.fragment_account_info:
                currentFragment = accountInfoFragment;
                title = "Account Info";
                break;
            default:
                currentFragment = homeFragment;
                title = "Home";
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
                if (currentFragment.equals(paymentListFragment) || currentFragment.equals(addressListFragment) || currentFragment.equals(accountInfoFragment)) {
                    previousFragmentTag = R.id.nav_account;
                } else if (currentFragmentTag != R.id.nav_sign_in_out) {
                    previousFragmentTag = currentFragmentTag;
                }
                currentFragmentTag = itemId;

                replaceFragmentWithAnimation(currentFragment, "" + currentFragmentTag);
            } else {
                if (currentFragment.equals(resultsFragment)) {
                    previousFragmentTag = currentFragmentTag = itemId;
                    // repopulate list view with the new query if it's the fragment is already displayed
                    resultsFragment.populateListView();
                }
            }
        }

        // set hardware back button logic
        if (currentFragment != null && currentFragment.equals(homeFragment)) {
            viewIsAtHome = true;
        } else {
            viewIsAtHome = false;
        }

        invalidateOptionsMenu();

    }


    // animation of fragments - makes the navigation flow smoother
    public void replaceFragmentWithAnimation(Fragment fragment, String tag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragment.equals(cartFragment) && previousFragmentTag != R.layout.fragment_checkout || fragment.equals(checkoutFragment)) {
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        } else if (previousFragmentTag == R.id.action_cart || (previousFragmentTag == R.layout.fragment_checkout)) {
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        } else {
            transaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
        }

        transaction.replace(R.id.content_frame, fragment, tag);
        transaction.commit();
    }

    //
    public void resetMainDrawerMenu() {
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.main_drawer_menu);
        showingMainDrawerMenu = true;
        setDrawerHeaderBackButtonVisibility(false);

        Menu menuNav = navigationView.getMenu();
        MenuItem navAccount = menuNav.findItem(R.id.nav_account);
        MenuItem navWishList = menuNav.findItem(R.id.nav_wishlist);
        MenuItem navOrders = menuNav.findItem(R.id.nav_orders);
        MenuItem signInOut = menuNav.findItem(R.id.nav_sign_in_out);
        MenuItem signup = menuNav.findItem(R.id.nav_sign_up);

        boolean userActive = databaseManager.getCurrentActiveUser() != null;


        navAccount.setEnabled(userActive);
        navWishList.setEnabled(userActive);
        navOrders.setEnabled(userActive);
        signup.setVisible(!userActive);

        if (userActive)
            signInOut.setTitle("Sign out");

        else
            signInOut.setTitle("Sign in");


    }


    // change the drawer menu to a specific one
    private void changeDrawerMenu(int itemId) {

        navigationView.getMenu().clear();

        if (itemId == R.id.nav_shop_console)
            navigationView.inflateMenu(R.menu.console_drawer_menu);
        if (itemId == R.id.nav_shop_game_by_category)
            navigationView.inflateMenu(R.menu.game_category_drawer_menu);
        if (itemId == R.id.nav_shop_game_by_console)
            navigationView.inflateMenu(R.menu.game_by_console_drawer_menu);
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

    public void addItemToCart(long itemId, ItemVariables.TYPE itemType) {
        if (isUserConnectedWithMessage()) {
            databaseManager.addItemToCart(itemType, itemId);
            Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
            invalidateOptionsMenu();
        } else {
            displayFragment(R.id.nav_sign_in_out);
        }
    }

    public void toggleWishlistAdd(long itemId, ItemVariables.TYPE itemType) {

        if (isUserConnectedWithMessage()) {
            boolean itemAlreadyInWishlist = databaseManager.isItemAlreadyInWishlist(itemId, itemType);
            if (itemAlreadyInWishlist) {
                databaseManager.deleteWishlist(itemId, itemType);
                Toast.makeText(this, "Item removed from wishlist", Toast.LENGTH_SHORT).show();
            } else {
                databaseManager.createWishList(itemType, itemId);
                Toast.makeText(this, "Item added to wishlist", Toast.LENGTH_SHORT).show();
            }
        } else {
            displayFragment(R.id.nav_sign_in_out);
        }
    }

    public void createOrderFromCartItems(String deliverTo, String dateOrdered, String dateArriving, OrderVariables.STATUS status, long cardNumber, String nameOnCard, int expirationMonth, int expirationYear, String street, String country, String state, String city, String postalCode, boolean extraShpping) {
        if (isUserConnectedWithMessage()) {
            databaseManager.createOrderFromItemsInCart(deliverTo, dateOrdered, dateArriving, status, cardNumber, nameOnCard, expirationMonth, expirationYear, street, country, state, city, postalCode, extraShpping);
            Toast.makeText(this, "Order Created", Toast.LENGTH_SHORT).show();
            invalidateOptionsMenu();
        } else {
            // should never come here
            displayFragment(R.id.nav_sign_in_out);
        }
    }

    public boolean isUserConnectedWithMessage() {
        if (databaseManager.getCurrentActiveUser() == null) {
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void drawerHeaderButtonClicked(View view) {
        resetMainDrawerMenu();
    }

    public void setSearchQuery(String query) {
        resultsFragment.setSearchViewQuery(query);
    }

    public void setFilterGamesByConsole(ItemVariables.CONSOLES consoleToFilterBy) {
        resultsFragment.setFilterGamesByConsole(consoleToFilterBy);
    }

    public void setFilterByConsole(ItemVariables.CONSOLES consoleToFilterBy) {
        resultsFragment.setFilterByConsole(consoleToFilterBy);
    }

    public void setFilterGamesByCategory(VideoGameVariables.CATEGORY categoryToFilterBy) {
        resultsFragment.setFilterGamesByCategory(categoryToFilterBy);
    }

    public void setItemIdToOpenAtInfoLaunch(long itemId, ItemVariables.TYPE itemType) {
        itemInfoFragment.setItemIdToOpenAtLaunch(itemId, itemType);
    }

    public void setOrderIdToOpenAtOrderInfoLaunch(long orderId) {
        orderInfoFragment.setOrderIdToOpenAtOrderInfoLaunch(orderId);
    }

    public void continueShoppingClicked(View view) {
        setSearchQuery("");
        displayFragment(R.id.search_view_results);
    }

    public UserAddress getEditingAddress() {
        return editingAddress;
    }

    public void setEditingAddress(UserAddress address) {
        editingAddress = address;
    }

    public PaymentInformation getEditingPayment() {
        return editingPayment;
    }

    public void setEditingPayment(PaymentInformation paymentInformation) {
        editingPayment = paymentInformation;
    }

    private void showSignOutDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        databaseManager.setCurrentActiveUser(-1);

                        if (homeFragment.isVisible())
                            homeFragment.setHomeSignInComponents();
                        displayFragment(R.id.nav_home);
                        resetMainDrawerMenu();
                        viewIsAtHome = true;
                        previousFragmentTag = currentFragmentTag = R.id.nav_home;
                        currentFragment = homeFragment;
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to sign out?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


}

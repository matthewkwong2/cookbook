package com.hkucs.cookbook;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private FloatingSearchView searchView;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setComponentRef();
        appBarAddOffsetListener();
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        setToolBarElevationStyle();
    }

    private void setComponentRef() {
        searchView = findViewById(R.id.floating_search_view);
        appBarLayout = findViewById(R.id.appBarLayout);
    }

    private void appBarAddOffsetListener() {
        appBarLayout.addOnOffsetChangedListener(this);
    }

    private void setToolBar() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    private void setToolBarElevationStyle() {
        final NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);
        final AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        final int animationDelay = 100;
        final int animationDuration = 250;
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (view, newScrollX, newScrollY, oldScrollX, oldScrollY) -> {
            if(newScrollY > 0 && oldScrollY == 0) {
                appBarLayout.setTranslationZ(4 * getResources().getDisplayMetrics().density);
            } else if(newScrollY == 0){
                appBarLayout.animate()
                        .translationZ(0)
                        .setDuration(animationDuration)
                        .setStartDelay(animationDelay)
                        .start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        searchView.setTranslationY(verticalOffset);
    }
}

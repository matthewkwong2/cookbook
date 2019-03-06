package com.hkucs.cookbook.activities.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.appbar.AppBarLayout;
import com.hkucs.cookbook.R;
import com.hkucs.cookbook.activities.CookbookActivity;
import com.hkucs.cookbook.activities.main.adapters.RecipeCategoryAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends CookbookActivity implements AppBarLayout.OnOffsetChangedListener {
    private FloatingSearchView searchView;
    private AppBarLayout appBarLayout;
    private RecyclerView recipeCategoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setComponentRef();
        setSearchViewStyle();
        appBarAddOffsetListener();
        initRecipeCategoryRecyclerView();
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setToolBarElevationStyle();
    }

    private void setComponentRef() {
        searchView = findViewById(R.id.main_floating_search_view);
        appBarLayout = findViewById(R.id.main_app_bar_layout);
        recipeCategoryRecyclerView = findViewById(R.id.main_recipe_category_recycler_view);
    }

    private void setSearchViewStyle() {
        searchView.findViewById(R.id.search_query_section).setBackgroundColor(Color.TRANSPARENT);
    }

    private void appBarAddOffsetListener() {
        appBarLayout.addOnOffsetChangedListener(this);
    }

    private void initRecipeCategoryRecyclerView() {
        recipeCategoryRecyclerView.setAdapter(new RecipeCategoryAdapter(this));
        setRecipeCategoryRecyclerViewLayoutManager();
    }

    private void setRecipeCategoryRecyclerViewLayoutManager() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
        layoutManager.setAlignItems(AlignItems.CENTER);
        recipeCategoryRecyclerView.setLayoutManager(layoutManager);
    }

    private void setToolBar() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }


    private void setToolBarElevationStyle() {
        recipeCategoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                System.out.println(dy);
                if (dy > 0) {
                    appBarLayout.setTranslationZ(4 * getResources().getDisplayMetrics().density);
                } else {
                    appBarLayout.animate()
                            .translationZ(0)
                            .setDuration(100)
                            .setStartDelay(250)
                            .start();
                }
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
//        searchView.setTranslationY(verticalOffset);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchBarFocused()) {
            searchView.clearSearchFocus();
        } else {
            super.onBackPressed();
        }
    }
}

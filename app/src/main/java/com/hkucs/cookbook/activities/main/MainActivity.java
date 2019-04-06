package com.hkucs.cookbook.activities.main;

import android.graphics.Color;
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

    private RecipeCategoryAdapter recipeCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void initComponents() {
        setSearchView();
        setRecipeCategoryRecyclerView();
        setAppBarLayout();
    }

    private void setSearchView() {
        searchView = findViewById(R.id.main_floating_search_view);
        setSearchViewStyle();
        setSearchViewSearchListener();
    }

    private void setAppBarLayout() {
        appBarLayout = findViewById(R.id.main_app_bar_layout);
        setAppBarAddOffsetListener();
    }

    private void setRecipeCategoryRecyclerView() {
        recipeCategoryRecyclerView = findViewById(R.id.main_recipe_category_recycler_view);
        setRecipeCategoryRecyclerViewAdapter();
        setRecipeCategoryRecyclerViewLayoutManager();
    }

    private void setSearchViewStyle() {
        searchView.findViewById(R.id.search_query_section).setBackgroundColor(Color.TRANSPARENT);
    }

    private void setSearchViewSearchListener() {
        searchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            recipeCategoryAdapter.getFilter().filter(newQuery);
        });
    }

    private void setAppBarAddOffsetListener() {
        appBarLayout.addOnOffsetChangedListener(this);
    }

    private void setRecipeCategoryRecyclerViewAdapter() {
        recipeCategoryAdapter = new RecipeCategoryAdapter(this);
        recipeCategoryRecyclerView.setAdapter(recipeCategoryAdapter);
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

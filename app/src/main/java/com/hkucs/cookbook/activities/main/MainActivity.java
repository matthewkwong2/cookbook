package com.hkucs.cookbook.activities.main;

import android.os.Bundle;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.hkucs.cookbook.R;
import com.hkucs.cookbook.activities.CookbookActivity;
import com.hkucs.cookbook.activities.main.adapters.RecipeCategoryAdapter;

public class MainActivity extends CookbookActivity implements AppBarLayout.OnOffsetChangedListener {
    private FloatingSearchView searchView;
    private AppBarLayout appBarLayout;
    private RecyclerView recipeCategoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setComponentRef();
        appBarAddOffsetListener();
        initRecipeCategoryRecyclerView();
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        setToolBarElevationStyle();
    }

    private void setComponentRef() {
        searchView = findViewById(R.id.main_floating_search_view);
        appBarLayout = findViewById(R.id.main_app_bar_layout);
        recipeCategoryRecyclerView = findViewById(R.id.main_recipe_category_recycler_view);
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
        layoutManager.setAlignItems(AlignItems.STRETCH);
        recipeCategoryRecyclerView.setLayoutManager(layoutManager);
    }

    private void setToolBar() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

//    private void setToolBarElevationStyle() {
//        final NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);
//        final AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
//        final int animationDelay = 100;
//        final int animationDuration = 250;
//        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (view, newScrollX, newScrollY, oldScrollX, oldScrollY) -> {
//            if(newScrollY > 0 && oldScrollY == 0) {
//                appBarLayout.setTranslationZ(4 * getResources().getDisplayMetrics().density);
//            } else if(newScrollY == 0){
//                appBarLayout.animate()
//                        .translationZ(0)
//                        .setDuration(animationDuration)
//                        .setStartDelay(animationDelay)
//                        .start();
//            }
//        });
//    }

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

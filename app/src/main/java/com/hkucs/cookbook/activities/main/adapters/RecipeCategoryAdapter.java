package com.hkucs.cookbook.activities.main.adapters;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.hkucs.cookbook.R;
import com.hkucs.cookbook.activities.testActivity.TestActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeCategoryAdapter extends RecyclerView.Adapter<RecipeCategoryAdapter.ViewHolder> {
    private final Context context;
    private final List<RecipeCategory> recipeCategories;
    private final int[] drawableId = new int[]{
            R.drawable.cat_1,
            R.drawable.cat_2,
            R.drawable.cat_3,
            R.drawable.cat_4,
            R.drawable.cat_5,
            R.drawable.cat_6,
            R.drawable.cat_7,
            R.drawable.cat_8,
            R.drawable.cat_9,
            R.drawable.cat_10,
            R.drawable.cat_11,
            R.drawable.cat_12,
            R.drawable.cat_13,
            R.drawable.cat_14,
            R.drawable.cat_15,
            R.drawable.cat_16,
            R.drawable.cat_17,
            R.drawable.cat_18,
            R.drawable.cat_19
    };

    public RecipeCategoryAdapter(Context context) {
        this.context = context;
        recipeCategories = initCategories();
    }

    private List<RecipeCategory> initCategories() {
        final List<RecipeCategory> recipeCategories = new ArrayList<>();
        final String[] recipeCategoryNames = context.getResources().getStringArray(R.array.recipe_categories);
        for (int i = 0; i < recipeCategoryNames.length; i++) {
            recipeCategories.add(new RecipeCategory(recipeCategoryNames[i], drawableId[i % drawableId.length]));
        }
        return recipeCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.main_recipe_category_item,
                viewGroup,
                false
        );
        return new RecipeCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int idx) {
        viewHolder.bindTo(recipeCategories.get(idx));
    }

    @Override
    public int getItemCount() {
        return recipeCategories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.main_recipe_category_item_image_button);
            button.setOnClickListener(this);
        }

        public void bindTo(RecipeCategory recipeCategory) {
            setContent(recipeCategory);
            setLayoutParams();
        }

        private void setContent(@NonNull RecipeCategory recipeCategory) {
            button.setText(recipeCategory.name);

            Drawable backgroundImage = context.getDrawable(recipeCategory.drawableId);
            assert backgroundImage != null;
            backgroundImage.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            button.setBackground(backgroundImage);
        }

        private void setLayoutParams() {
            ViewGroup.LayoutParams containerLayoutParams = button.getLayoutParams();
            if (containerLayoutParams instanceof FlexboxLayoutManager.LayoutParams) {
                FlexboxLayoutManager.LayoutParams flexboxLayoutParams = (FlexboxLayoutManager.LayoutParams) containerLayoutParams;
                flexboxLayoutParams.setFlexGrow(1.0f);
            }
        }

        @Override
        public void onClick(View view) {
//            Log.d("Item click", "clicked");
            Intent intent = new Intent(context, TestActivity.class);
            context.startActivity(intent);
        }
    }

    class RecipeCategory {
        private final String name;
        private final int drawableId;

        RecipeCategory(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}

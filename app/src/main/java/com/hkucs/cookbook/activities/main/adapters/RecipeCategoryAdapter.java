package com.hkucs.cookbook.activities.main.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkucs.cookbook.R;
import com.hkucs.cookbook.activities.recipeMenuActivity.RecipeMenu;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeCategoryAdapter extends RecyclerView.Adapter<RecipeCategoryAdapter.ViewHolder> implements Filterable {
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
    private List<RecipeCategory> filteredRecipeCategories;

    public RecipeCategoryAdapter(Context context) {
        this.context = context;
        recipeCategories = initCategories();
        filteredRecipeCategories = initCategories();
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
                R.layout.recipe_category_item,
                viewGroup,
                false
        );
        return new RecipeCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int idx) {
        viewHolder.bindTo(filteredRecipeCategories.get(idx));
        viewHolder.cardId = idx+1;
    }

    @Override
    public int getItemCount() {
        return filteredRecipeCategories.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keywords = constraint.toString();
                if (keywords.isEmpty()) {
                    filteredRecipeCategories = recipeCategories;
                } else {
                    List<RecipeCategory> filteredList = new ArrayList<>();
                    for (RecipeCategory recipeCategory : recipeCategories) {
                        if (recipeCategory.name.toLowerCase().contains(keywords.toLowerCase())) {
                            filteredList.add(recipeCategory);
                        }
                    }

                    filteredRecipeCategories = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredRecipeCategories;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredRecipeCategories = (List<RecipeCategory>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private TextView textView;
        private ImageView imageView;

        public Integer cardId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            textView = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.image_view);
            cardView.setOnClickListener(this);

        }

        public void bindTo(RecipeCategory recipeCategory) {
            setContent(recipeCategory);
        }

        private void setContent(@NonNull RecipeCategory recipeCategory) {
            textView.setText(recipeCategory.name);
            imageView.setImageDrawable(context.getDrawable(recipeCategory.drawableId));
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, RecipeMenu.class);
            Log.d("CLICKED", "onClick: "+this.cardId);
            intent.putExtra("categoryId",Integer.valueOf(this.cardId));
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

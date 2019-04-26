package com.hkucs.cookbook.activities.recipeMenuActivity.adapters;

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
import com.hkucs.cookbook.activities.recipeMenuActivity.RecipeItem;
import com.hkucs.cookbook.activities.testActivity.TestActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<RecipeItem> recipeItemList;
    private ArrayList<RecipeItem> filteredRecipeItemList;
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keywords = constraint.toString();
                if (keywords.isEmpty()) {
                    filteredRecipeItemList = recipeItemList;
                } else {
                    List<RecipeItem> filteredList = new ArrayList<>();
                    for (RecipeItem recipeItem : recipeItemList) {
                        if (recipeItem.getName().toLowerCase().contains(keywords.toLowerCase())) {
                            filteredList.add(recipeItem);
                        }
                    }

                    filteredRecipeItemList = (ArrayList<RecipeItem>)filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredRecipeItemList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredRecipeItemList = (ArrayList<RecipeItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public RecipeItemAdapter(Context context,ArrayList<RecipeItem> recipeItemList) {
        this.context = context;
        this.recipeItemList = recipeItemList;
        this.filteredRecipeItemList = recipeItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_menu_recyclerview,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeItem item = filteredRecipeItemList.get(position);
        holder.titleView.setText(item.getName());
        holder.scoreView.setText(String.valueOf(item.getScore()));
        holder.timeView .setText(String.valueOf(item.getTime()));
        Picasso.get().load(item.getDrawableId()).into(holder.imageView);
        holder.recipeId = item.getItemId();


    }

    @Override
    public int getItemCount() {
        return filteredRecipeItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private TextView titleView, scoreView, timeView;
        private ImageView imageView;
        public Integer recipeId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            titleView = itemView.findViewById(R.id.title);
            scoreView = itemView.findViewById(R.id.score);
            timeView = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.image_view);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Log.d("CLICK EVENT!!", "onClick: "+this.recipeId);
            Intent intent = new Intent(context, TestActivity.class);
            intent.putExtra("recipeId",Integer.valueOf(this.recipeId));
            context.startActivity(intent);
        }
    }


}


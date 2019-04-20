package com.hkucs.cookbook.activities.testActivity.adapter;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.hkucs.cookbook.R;
import com.hkucs.cookbook.activities.testActivity.Ingredient;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class IngredientListAdapter extends ArrayAdapter<Ingredient> {

    private static final String TAG = "IngredientListAdapter";

    private Context context;

    int resource;

    public IngredientListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Ingredient> ingredients) {
        super(context, resource, ingredients);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String ingredient = getItem(position).getIngredient();
        boolean checked = getItem(position).isChecked();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);
        CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.ingredientCheckBox);
        checkBox.setText(ingredient);
        checkBox.setChecked(checked);
        if(Build.VERSION.SDK_INT >= 26) {
            checkBox.setTypeface(ResourcesCompat.getFont(context, R.font.cormorant));
        }
        checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (getItem(position).isChecked()){
                    getItem(position).setChecked(false);
                }
                else {
                    getItem(position).setChecked(true);
                }
//                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}

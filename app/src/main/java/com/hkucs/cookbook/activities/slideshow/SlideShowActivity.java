package com.hkucs.cookbook.activities.slideshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hkucs.cookbook.R;
import com.hkucs.cookbook.activities.DatabaseHelper;
import com.hkucs.cookbook.activities.GlobalVariable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SlideShowActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    /**
     * The {@link androidx.viewpager.widget.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * androidx.fragment.app.FragmentStatePagerAdapter.
     */
    private String TAG = "SlideShow";
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private String JSON_FILE = "procedure.json";
    private ArrayList<Procedure> pArrayList = new ArrayList<>();
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        databaseHelper = new DatabaseHelper(this);


        Intent intent = getIntent();
        Integer repId;
        Integer repTwoId;
        GlobalVariable gv = new GlobalVariable();
        if (databaseHelper.isDatabaseEmpty()) {
            System.out.println("insert data");
            insertData();
        }

        if (gv.getNumOfRecipe() == 0) {
            repId = intent.getIntExtra("recipeId", 1);
        } else {
            if (gv.getNumOfRecipe() == 1) {
                repId = gv.getRecipeOne();
            } else {
                repId = gv.getRecipeOne();
                repTwoId = gv.getRecipeTwo();
                loadData(repTwoId);
            }
        }

        loadData(repId);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_slide_show, menu);
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

    /*
     * read jsonfile and insert them to the database
     * */
    private JSONArray readJson() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this));
            Log.d("hi", "readJson:OK");
            return obj.getJSONArray("procedure");
        } catch (JSONException e) {
            Log.d(TAG, "readJson: ERROR cannot read json string as object");
            e.printStackTrace();
        }
        return null;
    }

    private String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(JSON_FILE);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private ArrayList<Procedure> getDataFromJson() {
        ArrayList<Procedure> proceduresList = new ArrayList<Procedure>();
        JSONArray arr = readJson();
        try {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Integer id = Integer.valueOf(obj.getString("recipeId"));
                Integer step = Integer.valueOf(obj.getString("step"));
                String imgName = obj.getString("image_path");
                String description = obj.getString("description");
                Procedure item = new Procedure(id, step, imgName, description);
                Log.d(TAG, description);
                proceduresList.add(item);
            }
        } catch (JSONException e) {
            Log.d("hi", "getDataFromJson: ERROR in getting json object ");
            e.printStackTrace();
        }
        return proceduresList;
    }

    private void insertData() {
//       get json and read json
        ArrayList<Procedure> proceduresList = getDataFromJson();
        databaseHelper.cleanTable2();
        boolean result = databaseHelper.insertMultipleProcedure(proceduresList);
        if (result) {
            Toast.makeText(this, "Sucessfully insert data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error in insert data", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadData(Integer rep_Id) {
        Log.d("RecipeMenu", "load data");
        ArrayList<Procedure> tempArrayList = databaseHelper.getProcedureById(rep_Id);
        System.out.println("size: " + tempArrayList.size());
        pArrayList.addAll(tempArrayList);
        Collections.sort(pArrayList, new Comparator<Procedure>() {
            @Override
            public int compare(Procedure o1, Procedure o2) {
                return o1.getStep() - o2.getStep();
            }
        });
        Log.d("RecipeMenu", "recipeItemArrayList: " + pArrayList);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_IMGPATH = "imgPath";
        private static final String ARG_DESCRIPTION = "description";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String imgPath, String description) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_IMGPATH, imgPath);
            args.putString(ARG_DESCRIPTION, description);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_slide_show, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.stepNumber);
            textView.setText("Step " + getArguments().getInt(ARG_SECTION_NUMBER));
            TextView descript = (TextView) rootView.findViewById(R.id.description);
            descript.setText(getArguments().getString(ARG_DESCRIPTION));
            ImageView stepImg = (ImageView) rootView.findViewById(R.id.stepImg);
            String name = getArguments().getString(ARG_IMGPATH);
            int img = getResources().getIdentifier(name, "drawable", "com.hkucs.cookbook");
            stepImg.setImageResource(img);
            TextView time = rootView.findViewById(R.id.timer);
            Button start = (Button) rootView.findViewById(R.id.start_button);
            String[] check = getArguments().getString(ARG_DESCRIPTION).split(":");
            if (check[0].equals("Timer")) {
                descript.setText("Countdown for: " + Integer.valueOf(check[1]) + " sec");
                int t = Integer.valueOf(check[1]) * 1000;
                CountDownTimer timerObj = new CountDownTimer(t, 1000) {
                    public void onTick(long millisUntilFinished) {
                        int min = Math.round(millisUntilFinished / 60000);
                        String display = "";
                        if (min > 10) {
                            if ((millisUntilFinished / 1000) % 60 >= 10) {
                                display += "0" + min + ":" + (millisUntilFinished / 1000) % 60;
                            } else {
                                display += "0" + min + ":0" + (millisUntilFinished / 1000) % 60;
                            }
                        } else {
                            if ((millisUntilFinished / 1000) % 60 >= 10) {
                                display += min + ":" + (millisUntilFinished / 1000) % 60;
                            } else {
                                display += min + ":0" + (millisUntilFinished / 1000) % 60;
                            }
                        }
                        time.setText(display);
                    }

                    public void onFinish() {
                        time.setText("Done!");
                        Vibrator vb = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                        vb.vibrate(1000);
                    }
                };
                start.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        timerObj.start();
                    }
                });
            } else {
                time.setVisibility(View.GONE);
                start.setVisibility(View.GONE);
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, pArrayList.get(position).getImgPath(), pArrayList.get(position).getDescription());
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return pArrayList.size();
        }
    }

}


package kumeshw.poke.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kumeshw.poke.R;
import kumeshw.poke.model.Ability;
import kumeshw.poke.model.Move;
import kumeshw.poke.model.PokeResponse;
import kumeshw.poke.model.Stat;
import kumeshw.poke.model.Type;

/**
 * Pokedex for Android - Fetches and displays data on Generation 1 Pokemon from the Kanto Region.
 * <p>
 * Created by kumeshw on 17/10/2016.
 */

public class DetailedPokemonView extends AppCompatActivity {

    public ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_pokemon_view);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Pokedex: Detailed View");

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        final PokeResponse savedPokeResponseObject = (PokeResponse) bundle.getSerializable("bundledPokeObject");

        /**
         * Setting values to the Detailed Pokemon View XML items
         */

        //Pokemon Name
        ((TextView) findViewById(R.id.textview_name)).setText(savedPokeResponseObject.name.toUpperCase());

        //Front Image
        Picasso.with(this)
                .load(savedPokeResponseObject.sprites.getFrontDefault())
                .resize(96, 96)
                .into(((ImageView) findViewById(R.id.imageview_front_default)));

        //Back Image
        Picasso.with(this)
                .load(savedPokeResponseObject.sprites.getBackDefault())
                .resize(96, 96)
                .into(((ImageView) findViewById(R.id.imageview_back_default)));

        //National Number
        String nationalNumber = ((Integer) savedPokeResponseObject.id).toString();
        ((TextView) findViewById(R.id.text_field_national_number)).setText(nationalNumber);

        //Type
        List<String> typesTempArray = new ArrayList<String>();
        for (Type type : savedPokeResponseObject.types) {
            typesTempArray.add(type.toString());
        }
        ((TextView) findViewById(R.id.text_field_type)).setText(typesTempArray.toString());


        //Height in meters with 1 d.p fix
        Double height = (double) savedPokeResponseObject.height;
        height = (height / 10);
        String correctHeight = height.toString() + " m";
        ((TextView) findViewById(R.id.text_field_height)).setText(correctHeight);


        //Weight in kilograms with 1 d.p fix
        Double weight = (double) savedPokeResponseObject.weight;
        Double weightDiv = (weight / 10);
        String correctWeight = weightDiv.toString()+ " kg";
        ((TextView) findViewById(R.id.text_field_weight)).setText(correctWeight);


        //Base Experience
        String baseEXP = ((Integer) savedPokeResponseObject.baseExperience).toString();
        ((TextView) findViewById(R.id.text_field_base_experience)).setText(baseEXP);


        //Abilities
        List<String> abilitiesTempArray = new ArrayList<String>();
        for (Ability ability : savedPokeResponseObject.abilities) {
            abilitiesTempArray.add(ability.toString());
        }
        ListView abilitiesListView = ((ListView) findViewById(R.id.list_field_abilities));

        listAdapter = new ArrayAdapter<String>(this, R.layout.generic_list_view_row, abilitiesTempArray);
        abilitiesListView.setAdapter(listAdapter);
        setListViewHeightBasedOnItems(abilitiesListView);

        
        
        //Base Stats - Name

        List<String> statNameTempArray = new ArrayList<String>();
//        for (Stat stat: savedPokeResponseObject.stats) {
//            statNameTempArray.add(stat.getStat().getName().toString());
//        }
        statNameTempArray.add("Speed");
        statNameTempArray.add("Sp. Defence");
        statNameTempArray.add("Sp. Attack");
        statNameTempArray.add("Defence");
        statNameTempArray.add("Attack");
        statNameTempArray.add("HP");

        ListView statNameListView = ((ListView) findViewById(R.id.list_field_bs_name));

        listAdapter = new ArrayAdapter<String>(this, R.layout.generic_list_view_row, statNameTempArray);
        statNameListView.setAdapter(listAdapter);
        setListViewHeightBasedOnItems(statNameListView);
        
        
        //Base Stats - Base Value

        List<String> statValueTempArray = new ArrayList<String>();
        for (Stat stat: savedPokeResponseObject.stats) {
            statValueTempArray.add(stat.getBaseStat().toString());
        }
        ListView statValueListView = ((ListView) findViewById(R.id.list_field_bs_value));

        listAdapter = new ArrayAdapter<String>(this, R.layout.generic_list_view_row, statValueTempArray);
        statValueListView.setAdapter(listAdapter);
        setListViewHeightBasedOnItems(statValueListView);



        //Base Stats - Effort

        List<String> statEffortTempArray = new ArrayList<String>();
        for (Stat stat: savedPokeResponseObject.stats) {
            statEffortTempArray.add(stat.getEffort().toString());
        }
        ListView statEffortListView = ((ListView) findViewById(R.id.list_field_bs_effort));

        listAdapter = new ArrayAdapter<String>(this, R.layout.generic_list_view_row, statEffortTempArray);
        statEffortListView.setAdapter(listAdapter);
        setListViewHeightBasedOnItems(statEffortListView);



        //Moves
        List<String> movesTempArray = new ArrayList<String>();
        for (Move move: savedPokeResponseObject.moves) {
            movesTempArray.add(move.toString());
        }
        ListView movesListView = ((ListView) findViewById(R.id.list_field_moves));

        listAdapter = new ArrayAdapter<String>(this, R.layout.generic_list_view_row, movesTempArray);
        movesListView.setAdapter(listAdapter);
        setListViewHeightBasedOnItems(movesListView);



    }

    /**
     * Sets ListView height dynamically based on the height of the items.
     */
    public static void setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();


        }

    }

}
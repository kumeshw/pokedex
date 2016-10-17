package kumeshw.poke.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kumeshw.poke.R;
import kumeshw.poke.adapter.LinearRecyclerAdapter;
import kumeshw.poke.services.Notifications;
import kumeshw.poke.model.PokeResponse;
import kumeshw.poke.services.APIEndpointInterface;
import kumeshw.poke.services.OnClickActivityInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Pokedex for Android - Fetches and displays data on Generation 1 Pokemon from the Kanto Region.
 * <p>
 *     Main Activity which includes methods for retrieving PokeAPI service data, saving it to Shared
 *     Preferences and retrieving it and parsing pokemon object data to relevant Activity elements.
 * Created by kumeshw on 10/10/2016.
 */


public class MainActivity extends AppCompatActivity implements OnClickActivityInterface {

    public List<PokeResponse> retrievedPokeResponseData = null;
    private List<PokeResponse> savedPokeResponseData = null;
    private Retrofit retrofit;
    public static ProgressDialog progress;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public LinearRecyclerAdapter adapter;

    protected SharedPreferences globalPokePref;
    final String BASE_URL = "http://pokeapi.co/api/v2/";


    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //initialise views
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        SharedPreferences globalPokePref = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isFirstRun = globalPokePref.getBoolean("IS_FIRST_POKE_RUN", true);
        if (isFirstRun) {

            System.out.println("***********************");
            System.out.println("** POKEDEX First Run **");
            System.out.println("***********************");

            try {

                retrievePokeAPIContent();

            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("Oopsie!...");
            }

            SharedPreferences.Editor editor = globalPokePref.edit();
            editor.putBoolean("IS_FIRST_POKE_RUN", false);
            editor.apply();

        } else {

            System.out.println("*************************************");
            System.out.println("** Running from Shared Preferences **");
            System.out.println("*************************************");

            Notifications.showProgressDialog(this);

            try {

                getPokemonFromSharedPreference();
                setAdapter();

            }catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("Oopsie!...");
            }
        }
        System.out.println("On Create Build Complete...");

    }

    /**
     * Sets the content to the recyclerView adapter
     */
    private void setAdapter() {

        adapter = new LinearRecyclerAdapter(savedPokeResponseData, this, this);
        recyclerView.setAdapter(adapter);

    }


    /**
     * Retrieves Pokemon data from PokeAPI service utilising an instance of Retrofit
     */
    private void retrievePokeAPIContent() {

        APIEndpointInterface apiService = retrofit.create(APIEndpointInterface.class);

        Notifications.showProgressDialog(this);

        final int startingPokemon = 1;
        final int endingPokemon = 151 + 1;

        for (int id = startingPokemon; id < endingPokemon; id++) {

            retrievedPokeResponseData = new ArrayList<>();

            Call<PokeResponse> call = apiService.getDataFromInterface(Integer.toString(id));
            call.enqueue(new Callback<PokeResponse>() {

                             @Override
                             public void onResponse(Call<PokeResponse> call, Response<PokeResponse> response) {

                                 retrievedPokeResponseData.add(response.body());
                                 int size = retrievedPokeResponseData.size();
                                 System.out.println("************ " + size + " Pokemon retrieved ************");

                                 if (size >= endingPokemon-startingPokemon) {

                                     saveToSharedPreference();
                                     System.out.println("******* POKEDEX DATA RETRIEVAL SUCCESS *******");

                                     getPokemonFromSharedPreference();
                                     setAdapter();
                                 }
                             }

                             @Override
                             public void onFailure(Call<PokeResponse> call, Throwable t) {

                                 Notifications.hideProgressDialog();
                                 Toast.makeText(getApplicationContext(), "Pokedex could not communicate" +
                                         " with the PokeAPI Service", Toast.LENGTH_LONG).show();
                                 System.out.println("**** # POKEDEX DATA RETRIEVAL FAILED # ****");
                                 System.out.println(t);
                             }
                         }
            );
        }
    }


    /**
     * Stores the pokemon ArrayList into device memory using the Shared Preferences option
     */
    private void saveToSharedPreference() {

        System.out.println("***** Saving Pokemon Data to Shared Preferences *****");
        globalPokePref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = globalPokePref.edit();

        System.out.println("*** Converting data into Primitive Json String ***");
        Gson gson = new Gson();
        editor.putString("SHARED_PREFERENCES_POKEMON_STRING", gson.toJson(retrievedPokeResponseData));
        editor.commit();
        System.out.println("*** Pokemon Data Successfully Saved ***");

        Notifications.hideProgressDialog();

    }

    /**
     * Reads the pokemon data already stored in device memory, after first run or refresh
     */
    private void getPokemonFromSharedPreference() {

        System.out.println("***** Retrieving Shared Preferences Pokemon Data *****");
        globalPokePref = PreferenceManager.getDefaultSharedPreferences(this);
        String pokemonString = globalPokePref.getString("SHARED_PREFERENCES_POKEMON_STRING", null);

        System.out.println("*** Converting Json String into ArrayList object ***");
        Gson gson = new Gson();
        PokeResponse[] convertedResponses = gson.fromJson(pokemonString, PokeResponse[].class);
        savedPokeResponseData = Arrays.asList(convertedResponses);
        Collections.sort(savedPokeResponseData);

        System.out.println("*** String Retrieved Contents: ***  \n" + savedPokeResponseData);
        System.out.println("*** Pokemon Data Successfully Loaded ***");

        Notifications.hideProgressDialog();

    }

    /**
     * On click, parses the selected Pokemon's data as a bundle to the DetailedPokemonView Activity
     */
    @Override
    public void onPokemonCardClicked(PokeResponse savedPokeResponse) {
        Intent intent = new Intent(this, DetailedPokemonView.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bundledPokeObject", savedPokeResponse);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles the pokemon data update through the app bar menu option
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh_pokemon) {

            retrievePokeAPIContent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

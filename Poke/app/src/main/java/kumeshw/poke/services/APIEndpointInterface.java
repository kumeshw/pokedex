package kumeshw.poke.services;

import kumeshw.poke.model.PokeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Pokedex for Android - Fetches and displays data on Generation 1 Pokemon from the Kanto Region.
 * <p>
 *     Interface used with Retrofit2 for information retrieval from the PokeAPI Restful Service
 * Created by kumeshw on 13/10/2016.
 */

public interface APIEndpointInterface {

    @GET("pokemon/{id}")
    Call<PokeResponse> getDataFromInterface(@Path("id") String id);

}

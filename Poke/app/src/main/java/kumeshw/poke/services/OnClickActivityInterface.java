package kumeshw.poke.services;

import kumeshw.poke.model.PokeResponse;

/**
 * Pokedex for Android - Fetches and displays data on Generation 1 Pokemon from the Kanto Region.
 * <p>
 *     Interface for linking the two main activities on mouse click and allowing a parseable/serializable ArrayList object
 * Created by kumeshw on 18/10/2016.
 */
public interface OnClickActivityInterface {
    void onPokemonCardClicked (PokeResponse savedPokeResponse);
}

package kumeshw.poke.services;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Pokedex for Android - Fetches and displays data on Generation 1 Pokemon from the Kanto Region.
 * <p>
 *     Class methods for handling notifications throughout the app
 * Created by kumeshw on 16/10/2016.
 */
public class Notifications {

    static ProgressDialog progress;

    /**
     * Displays progress dialog while Pokedex data is being loaded
     */
    public static void showProgressDialog(Context context) {
        if (progress == null) {
            progress = ProgressDialog.show(context, "Loading Your Pokedex",
                    "This may take a minute. Please be patient while we catch 'em all.", true);
        }
    }

    /**
     * Method to hides a previously active progress dialog
     */
    public static void hideProgressDialog() {
        if (progress != null && progress.isShowing()) {
            progress.hide();
            progress = null;
        }
    }

}

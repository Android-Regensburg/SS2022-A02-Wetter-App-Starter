package de.ur.mi.android.wetter.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * Instanzen dieser Klasse repräsentieren eine einzelne Anfrage an den Weather-API-Server, um
 * eines der Wetter-Icon-Bilder zu erhalten. Der Aufbau entspricht weitestgehend dem des
 * WeatherDataRequest. Statt JSON-formatierter Wetterdaten wird hier aber direkt auf ein, auf dem
 * Server gespeichertes, Bild zugegriffen, dass wir herunterladen und als Bitmap in der Anwendung
 * verwenden wollen.
 */
public class WeatherIconRequest {

    private final WeatherData data;
    private final Context context;

    public WeatherIconRequest(WeatherData data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void run(RequestListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        /*
         * Über die ImageRequest-Klasse aus dem VolleyFramework können wir Grafiken, die über eine
         * eindeutige URL identifiziert werden können, in unsere App herunterladen.
         */
        ImageRequest imageRequest = new ImageRequest(data.weatherIconUrl, response -> {
            // Das als Bitmap heruntergeladene Bild wird an den Listener weitergegeben
            listener.onResult(response);
        }, 0, 0, ImageView.ScaleType.CENTER, null, error -> {
            // Während des Versuchs, das Bild herunterzuladen,können viele Fehler auftreten, die hier verarbeitet werden müssen!
            // @TODO Fehler beim Ausführen der Anfrage verarbeiten
        });
        queue.add(imageRequest);
    }

    public interface RequestListener {

        void onResult(Bitmap icon);

    }
}

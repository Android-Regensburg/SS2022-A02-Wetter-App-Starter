package de.ur.mi.android.wetter.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Instanzen dieser Klasse repräsentieren eine einzelne Anfrage an die Weater API mit dem Ziel,
 * die aktuellen Wetterinformationen für eine bekannte Stadt zu erfragen.
 */
public class WeatherDataRequest {

    // Vorlage für HTTP-Anfrage für Wetterinformationen einer bestimmten Stadt
    private static final String REQUEST_URL = "https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}&units=metric";
    // Vorlage für HTTP-Anfrage zu einem bestimmten Wetter-ICON
    private static final String ICON_URL = "https://openweathermap.org/img/wn/{icon id}@2x.png";
    // API-Key, der bei jeder Anfrage zur Authentfizierung gegenüber der API verwendet wird
    // @TODO API-Key hinzufügen (openweathermap.org)
    private static final String API_KEY = "";

    // Name der Stadt, deren Wetterinformationen abgerufen werden sollen
    private final String city;
    // Kontext der aufrufenden Activity/App für Verwendung im Volley-Framework
    private final Context context;

    public WeatherDataRequest(String city, Context context) {
        this.city = city;
        this.context = context;
    }

    /**
     * Die Methode führt den eigentlichen API-Request durch und liefert die so erhaltenen Informationen
     * als WeatherData-Objekt an den hier übergebenen Listener zurück. Das ist notwendig, da die Anfrage
     * parallelisiert erfolgt und die aufrufende Stelle asynchron über das Ergebnis informiert werden muss.
     */
    public void run(RequestListener listener) {
        // Wir verwende Volley, um die JSON-formatierten Wetter-Daten zu erhalten (Vgl. "Mensa-App")
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = REQUEST_URL.replace("{city name}", city).replace("{API key}", API_KEY);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    /*
                     * Aus den erhaltenen Daten, die als JSONObject hier eingehen, erstellen wir
                     * WeatherData-Objekte und geben diese an die Listener weiter. So wird das
                     * Ergebnis der Anfrage an die aufrufende Stelle, hier die MainActivity, weiter
                     * gegeben.
                     */
                    listener.onResult(getWeatherDataFromResponse(response));
                }, error -> {
                    // Während der Anfrage können viele Fehler auftreten, die hier verarbeitet werden müssen!
                    // @TODO Fehler beim Ausführen der Anfrage verarbeiten
                });
        queue.add(jsonObjectRequest);
    }

    /**
     * Die Methode sucht aus dem JSONObject, das als Ergebnis der API-Anfrage erhalten wurde, die
     * relevanten Informationen heraus und gibt diese, verpackt als WeatherData-Objekt, zurück (Vgl "Mensa-App").
     */
    private WeatherData getWeatherDataFromResponse(JSONObject response) {
        try {
            String city = response.getString("name");
            // Der Name des aktuelle gültigen Wetter-Icons versteckt sich auf einer tieferen Ebene des JSONObject
            String icon = response.getJSONArray("weather").getJSONObject(0).getString("icon");
            // Aus dem ausgelesenen Icon-Namen und der URL-Vorlage wird die direkte URL zur passenden Grafik zusammengebaut
            String iconUrl = ICON_URL.replace("{icon id}", icon);
            return new WeatherData(city, iconUrl);
        } catch (JSONException exception) {
            return null;
        }
    }

    /**
     * Mit diesem Interface geben wir vor, wie Objekte aufgebaut sein müssen, die als Empfänger der
     * Request-Ergebnisse (Observer) fungieren. Ein entsprechendes Objekt muss der "run"-Methode
     * des Request übergeben werden.
     */
    public interface RequestListener {

        void onResult(WeatherData data);

    }
}



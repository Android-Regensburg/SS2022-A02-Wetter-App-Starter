package de.ur.mi.android.wetter;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.ur.mi.android.wetter.api.WeatherDataRequest;
import de.ur.mi.android.wetter.api.WeatherIconRequest;

/**
 * In diesem Starterpaket finden Sie Vorschläge und Hinweise zum Umgang mit der Weather API sowie
 * dem Zugriff auf diese mit Hilfe des Volley-Frameworks. Sie können diese Strukturen übernehmen und
 * in Ihren eigenen Lösungsvorschlag einbauen. Mit hoher Wahrscheinlichkeit müssen Sie dabei aber
 * substantielle Änderungen vornehmen. Überlegen Sie sich, ob die vorgeschlagene Vorgehensweise
 * kompatible mit Ihrer eigenen Herangehensweise ist. Suchen Sie im Zweifel nach anderen Ansätzen.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Vor dem Testen des Request (auskommentieren der Methode) müssen Sie Ihren API-Key in der WwatherDataRequest-Klasse eintragen
        // @TODO API-Key in WeatherDataRequest eintragen
        // runDummyRequest();
    }

    /**
     * Diese Methode führt eine Testanfrage an die Weater API durch, um die aktuellen
     * Wetterinformationen für Regensburg zu erhalten. Wenn die Anfrage erfolgreich abgeschlossen
     * wurde, wird der Name der angefragten Stadt im UI angezeigt. Im Anschluss wird die passende
     * Wettergrafik heruntergeladen und, unter dem Namen der Stadt, in einem ImageView im UI eingetragen.
     */
    private void runDummyRequest() {
        // Erstellen des Requests für die Wetterinformationen
        WeatherDataRequest dataRequest = new WeatherDataRequest("Regensburg", getApplicationContext());
        // Ausführen des Requests
        dataRequest.run(data -> {
            // Verarbeiten der eingegangenen Wetterinformationen
            TextView cityNameView = findViewById(R.id.city_name_view);
            cityNameView.setText(data.city); // Anzeige des Stadtnamens im UI
            // Erstellen des Requests für die Wettergrafik
            WeatherIconRequest iconRequest = new WeatherIconRequest(data, getApplicationContext());
            // Ausführen des Requests
            iconRequest.run(icon -> {
                // Verarbeiten der als Bitmap heruntergeladenen Grafik
                ImageView cityWeatherIconView = findViewById(R.id.city_weather_icon_view);
                cityWeatherIconView.setImageBitmap(icon); // Anzeigen der Grafik im UI
            });
        });
    }
}
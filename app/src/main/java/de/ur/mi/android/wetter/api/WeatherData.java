package de.ur.mi.android.wetter.api;

/**
 * Mit dieser Klasse werden konkrete Wetterinformationen repräsentiert, die über die HTTP-Anfrage
 * an die Weather API erhalten wurden.
 */
public class WeatherData {

    public final String city; // Die Stadt, für die diese Wetterdaten gelten
    public final String weatherIconUrl; // Die URL zum Icon, das die aktuelle Wetterlage visualisiert

    public WeatherData(String city, String weatherIconUrl) {
        this.city = city;
        this.weatherIconUrl = weatherIconUrl;
    }

}
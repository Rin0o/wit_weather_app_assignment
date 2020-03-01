package sr_rino.witweatherapp.weather

import android.location.Location
import sr_rino.witweatherapp.data.objects.WeatherLocation

interface WeatherContract {

    interface View {

        fun onGetWeatherSuccessful(list: List<WeatherLocation>)
        fun onGetWeatherFailure(readon: String)

    }

    interface Controller {

        fun getWeather()
        fun getWeatherLocationsByGroup(ids: String)
        fun getWeatherByCoordinates(lat: String, long: String)
    }

}
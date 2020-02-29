package sr_rino.witweatherapp.weather

import android.location.Location
import sr_rino.witweatherapp.data.objects.WeatherLocation

interface WeatherContract {

    interface View {

        fun onGetWeatherLocationsByGroupSuccessful(list: List<WeatherLocation>)
        fun onGetWeatherLocationsByGroupFailure(readon: String)

        fun onGetWeatherLocationByCoordinatesSuccessful(x: WeatherLocation)
        fun onGetWeatherLocationByCoordinatesFailure(readon: String)

        fun onGetCurrentLocationSuccessful(location: Location)

    }

    interface Controller {

        fun getCurrentLocation()
        fun getWeatherLocationsByGroup(ids: String)
        fun getWeatherByCoordinates(lat: String, long: String)
    }

}
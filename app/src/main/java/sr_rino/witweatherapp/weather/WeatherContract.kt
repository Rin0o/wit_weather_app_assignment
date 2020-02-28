package sr_rino.witweatherapp.weather

import android.location.Location

interface WeatherContract {

    interface View {

        fun onGetWeatherLocationSuccessful()
        fun onGetWeatherFailure(readon: String)

        fun onGetCurrentLocationSuccessful(location: Location)

    }

    interface Controller {

        fun getCurrentLocation()
        fun getWeatherLocations()
    }

}
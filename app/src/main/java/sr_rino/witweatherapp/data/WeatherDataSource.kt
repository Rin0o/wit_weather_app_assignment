package sr_rino.witweatherapp.data

import sr_rino.witweatherapp.data.objects.WeatherLocation
interface WeatherDataSource {

    interface GetWeatherLocationByCoordinatesCallback {
        fun onGetWeatherLocationSuccessful(x: WeatherLocation)
        fun onGetWeatherFailure(reason: String)
    }

    interface GetWeatherLocationsByGroupCallback {
        fun onGetWeatherLocationSuccessful(list: List<WeatherLocation>)
        fun onGetWeatherFailure(reason: String)
    }


    fun getWeatherLocationByCoordinates(lat: String, long: String, callback: GetWeatherLocationByCoordinatesCallback)
    fun getWeatherLocationsByGroup(ids: String, callback: GetWeatherLocationsByGroupCallback)
}
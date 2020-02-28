package sr_rino.witweatherapp.data

interface WeatherDataSource {

    interface GetWeatherLocationsCallback {
        fun onGetWeatherLocationSuccessful()
        fun onGetWeatherFailure(readon: String)
    }

    fun getWeatherLocations(callback: GetWeatherLocationsCallback)
}
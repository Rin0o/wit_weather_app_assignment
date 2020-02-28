package sr_rino.witweatherapp.data.remote

import sr_rino.witweatherapp.data.WeatherDataSource

class WeatherRemoteDataSource : WeatherDataSource{

    private var INSTANCE: WeatherRemoteDataSource? = null

    fun getInstance(): WeatherRemoteDataSource {
        if (INSTANCE == null) {
            INSTANCE = WeatherRemoteDataSource()
        }
        return INSTANCE as WeatherRemoteDataSource
    }

    override fun getWeatherLocations(callback: WeatherDataSource.GetWeatherLocationsCallback) {

    }

}
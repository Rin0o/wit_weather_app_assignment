package sr_rino.witweatherapp.data

import sr_rino.witweatherapp.data.remote.WeatherRemoteDataSource

class WeatherRepository (private val weatherRDSData: WeatherDataSource) : WeatherDataSource{

    override fun getWeatherLocations(callback: WeatherDataSource.GetWeatherLocationsCallback) {
        weatherRDSData.getWeatherLocations(callback)
    }

}
package sr_rino.witweatherapp.data

import sr_rino.witweatherapp.data.remote.WeatherRemoteDataSource

class WeatherRepository (private val weatherRDSData: WeatherDataSource) : WeatherDataSource{


    companion object {
        private var INSTANCE: WeatherRepository? = null

        fun getInstance(weatherRemoteDataSource: WeatherDataSource): WeatherRepository {
            if (INSTANCE == null) {
                INSTANCE = WeatherRepository(weatherRemoteDataSource)
            }
            return INSTANCE as WeatherRepository
        }
    }

    override fun getWeatherLocationByCoordinates(
        lat: String,
        long: String,
        callback: WeatherDataSource.GetWeatherLocationByCoordinatesCallback
    ) {
        weatherRDSData.getWeatherLocationByCoordinates(lat, long, callback)
    }

    override fun getWeatherLocationsByGroup(
        ids: String,
        callback: WeatherDataSource.GetWeatherLocationsByGroupCallback
    ) {
        weatherRDSData.getWeatherLocationsByGroup(ids, callback)
    }

}
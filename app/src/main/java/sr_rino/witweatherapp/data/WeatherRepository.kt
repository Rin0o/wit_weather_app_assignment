package sr_rino.witweatherapp.data

import sr_rino.witweatherapp.data.remote.WeatherRemoteDataSource

class WeatherRepository (private val weatherRDSData: WeatherDataSource) : WeatherDataSource{

    /**
     * The class would be were i would put the data persistence using some kind of item box/SQL lite DB
     * Would only get Data from API if persisted data was empty or outdated by some time margin
     */

    companion object {
        private var INSTANCE: WeatherRepository? = null

        fun getInstance(weatherRemoteDataSource: WeatherDataSource): WeatherRepository {
            if (INSTANCE == null) {
                INSTANCE = WeatherRepository(weatherRemoteDataSource)
            }
            return INSTANCE as WeatherRepository
        }
    }

    /**
     * Call to API to get Current location Weather Details
     */
    override fun getWeatherLocationByCoordinates(
        lat: String,
        long: String,
        callback: WeatherDataSource.GetWeatherLocationByCoordinatesCallback
    ) {
        weatherRDSData.getWeatherLocationByCoordinates(lat, long, callback)
    }

    /**
     * Call to API to get Capitals locations Weather Details
     */
    override fun getWeatherLocationsByGroup(
        ids: String,
        callback: WeatherDataSource.GetWeatherLocationsByGroupCallback
    ) {
        weatherRDSData.getWeatherLocationsByGroup(ids, callback)
    }

}
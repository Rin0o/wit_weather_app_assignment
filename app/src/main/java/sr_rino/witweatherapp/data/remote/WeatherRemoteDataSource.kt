package sr_rino.witweatherapp.data.remote

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import sr_rino.witweatherapp.data.WeatherDataSource
import sr_rino.witweatherapp.data.WeatherRestClient
import sr_rino.witweatherapp.data.objects.WeatherGroupResponse
import sr_rino.witweatherapp.data.objects.WeatherLocation

class WeatherRemoteDataSource : WeatherDataSource{


    companion object {
        private var INSTANCE: WeatherRemoteDataSource? = null

        fun getInstance(): WeatherRemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = WeatherRemoteDataSource()
            }
            return INSTANCE as WeatherRemoteDataSource
        }
    }

    @SuppressLint("CheckResult")
    override fun getWeatherLocationByCoordinates(
        lat: String,
        long: String,
        callback: WeatherDataSource.GetWeatherLocationByCoordinatesCallback
    ) {

        val weatherAPI = WeatherRestClient().createService(WeatherAPI::class.java)
        val weatherFetch = weatherAPI.getCurrentWeatherByCoordinates(lat, long)

        weatherFetch.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ weather ->
                callback.onGetWeatherLocationSuccessful(weather)
            }, { error ->
                callback.onGetWeatherFailure(error.message.toString())
            })

    }


    @SuppressLint("CheckResult")
    override fun getWeatherLocationsByGroup(
        ids: String,
        callback: WeatherDataSource.GetWeatherLocationsByGroupCallback
    ) {

        val weatherAPI = WeatherRestClient().createService(WeatherAPI::class.java)
        val weatherFetch = weatherAPI.getCurrentWeatherByGroupId(ids)

        weatherFetch.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ weather ->
                callback.onGetWeatherLocationSuccessful(weather.list)
            }, { error ->
                callback.onGetWeatherFailure(error.message.toString())
            })

    }

}
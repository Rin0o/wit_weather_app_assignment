package sr_rino.witweatherapp.weather

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import sr_rino.witweatherapp.data.WeatherDataSource
import sr_rino.witweatherapp.data.WeatherRepository
import sr_rino.witweatherapp.data.objects.WeatherLocation

class WeatherController (private val activity: Activity,
                         private val mWeatherRepository: WeatherRepository,
                         val mWeatherView: WeatherContract.View) : WeatherContract.Controller{

    var mFusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)

    override fun getWeatherLocationsByGroup(ids: String) {
        mWeatherRepository.getWeatherLocationsByGroup(ids, object : WeatherDataSource.GetWeatherLocationsByGroupCallback{
            override fun onGetWeatherLocationSuccessful(list: List<WeatherLocation>) {
                mWeatherView.onGetWeatherLocationsByGroupSuccessful(list)
            }

            override fun onGetWeatherFailure(reason: String) {
                mWeatherView.onGetWeatherLocationsByGroupFailure(reason)
            }

        })
    }

    override fun getWeatherByCoordinates(lat: String, long: String) {
        mWeatherRepository.getWeatherLocationByCoordinates(lat, long, object : WeatherDataSource.GetWeatherLocationByCoordinatesCallback{
            override fun onGetWeatherLocationSuccessful(x: WeatherLocation) {
                mWeatherView.onGetWeatherLocationByCoordinatesSuccessful(x)
            }

            override fun onGetWeatherFailure(reason: String) {
                mWeatherView.onGetWeatherLocationByCoordinatesFailure(reason)
            }

        })
    }

    /**
     * https://www.androdocs.com/kotlin/getting-current-location-latitude-longitude-in-android-using-kotlin.html
     */
    override fun getCurrentLocation() {

        mFusedLocationClient.lastLocation.addOnCompleteListener(activity!!) { task ->
            var location: Location? = task.result
            if (location == null) {
                requestNewLocationData()
            } else {
                mWeatherView.onGetCurrentLocationSuccessful(location)
            }
        }
    }

    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            mWeatherView.onGetCurrentLocationSuccessful(locationResult.lastLocation)

        }
    }
}
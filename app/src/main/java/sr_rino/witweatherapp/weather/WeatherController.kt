package sr_rino.witweatherapp.weather

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import sr_rino.witweatherapp.data.WeatherDataSource
import sr_rino.witweatherapp.data.WeatherRepository
import sr_rino.witweatherapp.data.objects.Locations
import sr_rino.witweatherapp.data.objects.WeatherLocation

class WeatherController (private val activity: Activity,
                         private val mWeatherRepository: WeatherRepository,
                         val mWeatherView: WeatherContract.View) : WeatherContract.Controller{

    var mFusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)

    //Weather Locations List to be returned to the view
    private var mWeatherList = mutableListOf<WeatherLocation>()

    /**
     * Call to dataSource to get capitals weather by their API id
     */
    override fun getWeatherLocationsByGroup(ids: String) {
        mWeatherRepository.getWeatherLocationsByGroup(ids, object : WeatherDataSource.GetWeatherLocationsByGroupCallback{
            override fun onGetWeatherLocationSuccessful(list: List<WeatherLocation>) {
                mWeatherList.addAll(list)
                mWeatherView.onGetWeatherSuccessful(mWeatherList)
            }

            override fun onGetWeatherFailure(reason: String) {
                mWeatherView.onGetWeatherFailure(reason)
            }

        })
    }

    /**
     * Call to dataSource to get location by geo coordinates
     * Used to get current location Weather
     */
    override fun getWeatherByCoordinates(lat: String, long: String) {
        mWeatherRepository.getWeatherLocationByCoordinates(lat, long, object : WeatherDataSource.GetWeatherLocationByCoordinatesCallback{
            override fun onGetWeatherLocationSuccessful(weather: WeatherLocation) {
                mWeatherList.add(weather)
                val ids = Locations.values().joinToString (separator = ",") { it.cityId.toString() }
                getWeatherLocationsByGroup(ids)
            }

            override fun onGetWeatherFailure(reason: String) {
                mWeatherView.onGetWeatherFailure(reason)
            }

        })
    }

    /**
     * Call to get Weather list
     */
    override fun getWeather() {
        mWeatherList.clear()
        getCurrentLocation()
    }

    /**
     * https://www.androdocs.com/kotlin/getting-current-location-latitude-longitude-in-android-using-kotlin.html
     * Get current GPS geo coordinates
     */
    private fun getCurrentLocation(){
        mFusedLocationClient.lastLocation.addOnCompleteListener(activity!!) { task ->
            var location: Location? = task.result
            if (location == null) {
                requestNewLocationData()
            } else {
                getWeatherByCoordinates(location.latitude.toString(), location.longitude.toString())
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
            var location = locationResult.lastLocation
            getWeatherByCoordinates(location.latitude.toString(), location.longitude.toString())
        }
    }
}
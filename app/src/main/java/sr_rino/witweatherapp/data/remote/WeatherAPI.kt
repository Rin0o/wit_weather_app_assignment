package sr_rino.witweatherapp.data.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import sr_rino.witweatherapp.data.objects.WeatherGroupResponse
import sr_rino.witweatherapp.data.objects.WeatherLocation

//const val API_KEY = "a8afe571bd36335e47df69deaef32a66"
//const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

interface WeatherAPI {

    @GET("weather")
    fun getCurrentWeatherByCoordinates(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("lang") lang: String = "en",
        @Query("units") units: String = "metric"
    ): Observable<WeatherLocation>

    @GET("group")
    fun getCurrentWeatherByGroupId(
        @Query("id") ids: String,
        @Query("lang") lang: String = "en",
        @Query("units") units: String = "metric"
    ): Observable<WeatherGroupResponse>

}
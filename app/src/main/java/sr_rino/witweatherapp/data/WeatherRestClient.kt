package sr_rino.witweatherapp.data

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

class WeatherRestClient {

    val CONNECT_TIMEOUT_CALL = 25
    val READ_TIMEOUT_CALL = 25
    val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val API_KEY = "a8afe571bd36335e47df69deaef32a66"


    /**
     * Generic method to create rest requests
     */
    @Synchronized
    fun <S> createService(serviceClass: Class<S>): S {
        val httpClient = OkHttpClient.Builder().proxy(Proxy.NO_PROXY)

        httpClient.connectTimeout(CONNECT_TIMEOUT_CALL.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(READ_TIMEOUT_CALL.toLong(), TimeUnit.SECONDS)

        val clientInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("appid", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }

        httpClient.addInterceptor(clientInterceptor)

        val client = httpClient.build()

        val gsonBuilder = GsonBuilder()
        //gsonBuilder.excludeFieldsWithoutExposeAnnotation()
        gsonBuilder.serializeNulls()
        val gson = gsonBuilder.create()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(serviceClass)
    }

}
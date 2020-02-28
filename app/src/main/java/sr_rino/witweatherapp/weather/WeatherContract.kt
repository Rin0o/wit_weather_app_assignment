package sr_rino.witweatherapp.weather

interface WeatherContract {

    interface View {

        fun onGetWeatherLocationSuccessful()
        fun onGetWeatherFailure(readon: String)

    }

    interface Controller {

        fun getWeatherLocations()
    }

}
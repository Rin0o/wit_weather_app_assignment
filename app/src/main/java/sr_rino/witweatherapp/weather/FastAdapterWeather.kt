package sr_rino.witweatherapp.weather

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.ui.utils.FastAdapterUIUtils
import kotlinx.android.synthetic.main.fast_adapter_weather.view.*
import sr_rino.witweatherapp.R
import sr_rino.witweatherapp.data.objects.WeatherLocation

class FastAdapterWeather(val mCtx: Context, val weather: WeatherLocation) : AbstractItem<FastAdapterWeather.ViewHolder>() {

    override val layoutRes: Int
        get() = R.layout.fast_adapter_weather
    override val type: Int
        get() = R.id.fast_adapter_weather

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<FastAdapterWeather>(view) {

        private var mainContainer: LinearLayout = itemView.findViewById(R.id.main_container)
        private val weatherIconMap = mapOf("Rain" to R.drawable.ic_weather_rain,
                                    "Clear" to R.drawable.ic_weather_clear,
                                    "Drizzle" to R.drawable.ic_weather_drizzle,
                                    "Clouds" to R.drawable.ic_weather_coulds,
                                    "Snow" to R.drawable.ic_weather_snow,
                                    "Thunderstorm" to R.drawable.ic_weather_thunderstorm,
                                    "Mist" to R.drawable.ic_weather_haze,
                                    "Smoke" to R.drawable.ic_weather_haze,
                                    "Haze" to R.drawable.ic_weather_haze,
                                    "Dust" to R.drawable.ic_weather_haze,
                                    "Fog" to R.drawable.ic_weather_haze,
                                    "Sand" to R.drawable.ic_weather_haze,
                                    "Dust" to R.drawable.ic_weather_haze,
                                    "Ash" to R.drawable.ic_weather_haze,
                                    "Squall" to R.drawable.ic_weather_haze,
                                    "Tornado" to R.drawable.ic_weather_tornado)

        @SuppressLint("SetTextI18n")
        override fun bindView(item: FastAdapterWeather, payloads: MutableList<Any>) {

            val weather = item.weather
            mainContainer.apply {
                fast_adapter_weather_country.text = weather.name
                fast_adapter_weather_humidity.text = weather.main.humidity.toString() + "%"
                fast_adapter_weather_temp.text = weather.main.temp.toString() + "º"
                fast_adapter_weather_max_temp.text = weather.main.temp_max.toString() + "º"
                fast_adapter_weather_min_temp.text = weather.main.temp_min.toString() + "º"
                fast_adapter_weather_desc.text = weather.weather.first().description.capitalize()
                fast_adapter_weather_icon.setImageResource(weatherIconMap.getValue(weather.weather.first().main))
            }
        }

        override fun unbindView(item: FastAdapterWeather) {
            mainContainer.apply {
                fast_adapter_weather_country.text = context.getString(R.string.string_na)
                fast_adapter_weather_humidity.text = context.getString(R.string.string_na)
                fast_adapter_weather_temp.text = context.getString(R.string.string_na)
                fast_adapter_weather_max_temp.text = context.getString(R.string.string_na)
                fast_adapter_weather_min_temp.text = context.getString(R.string.string_na)
                fast_adapter_weather_desc.text = context.getString(R.string.string_na)
                fast_adapter_weather_icon.setImageResource(R.drawable.ic_weather_clear)
            }
        }
    }

}
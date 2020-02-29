package sr_rino.witweatherapp.weather

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
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

        val viewHolder = view
        private var mainContainer: RelativeLayout = itemView.findViewById(R.id.main_container)

        override fun bindView(item: FastAdapterWeather, payloads: MutableList<Any>) {

            val weather = item.weather
            mainContainer.apply {
                fast_adapter_weather_country.text = weather.name
                fast_adapter_weather_pressure.text = weather.main.pressure.toString()
                fast_adapter_weather_humidity.text = weather.main.humidity.toString() + "%"
                fast_adapter_weather_temp.text = weather.main.temp.toString() + "º"
                fast_adapter_weather_max_temp.text = weather.main.temp_max.toString() + "º"
                fast_adapter_weather_min_temp.text = weather.main.temp_min.toString() + "º"
                fast_adapter_weather_desc.text = weather.weather.first().main
            }
        }

        override fun unbindView(item: FastAdapterWeather) {
            mainContainer.apply {
                fast_adapter_weather_country.text = context.getString(R.string.string_na)
                fast_adapter_weather_pressure.text = context.getString(R.string.string_na)
                fast_adapter_weather_humidity.text = context.getString(R.string.string_na)
                fast_adapter_weather_temp.text = context.getString(R.string.string_na)
                fast_adapter_weather_max_temp.text = context.getString(R.string.string_na)
                fast_adapter_weather_min_temp.text = context.getString(R.string.string_na)
                fast_adapter_weather_desc.text = context.getString(R.string.string_na)
            }
        }
    }

}
package sr_rino.witweatherapp.data.objects


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherGroupResponse(
    @SerializedName("list")
    @Expose
    val list: List<WeatherLocation>
)
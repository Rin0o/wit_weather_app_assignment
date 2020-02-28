package sr_rino.witweatherapp.weather

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_weather.view.*
import sr_rino.witweatherapp.R
import sr_rino.witweatherapp.data.WeatherRepository
import sr_rino.witweatherapp.data.remote.WeatherRemoteDataSource

class WeatherFragment : Fragment(), WeatherContract.View {


    companion object {

        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }

    //lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mController: WeatherController
    private lateinit var mRootLayout: View

    private lateinit var mLat: String
    private lateinit var mLong: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mController = WeatherController(activity!!, WeatherRepository.getInstance(WeatherRemoteDataSource.getInstance()), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRootLayout = view
        setHasOptionsMenu(true)

        if (isAdded){
            //mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

            mController.getCurrentLocation()


        }

    }



//    fun drawLoadingView() {
//        adapter.clear()
//        view.apply {
//            this.fragment_generic_progress_bar_view.visibility = View.VISIBLE
//            this.fragment_generic_empty_view.visibility = View.GONE
//            this.fragment_list_view.visibility = View.INVISIBLE
//        }
//    }
//
//    fun drawLoadedView() {
//        view.apply {
//            this.fragment_generic_progress_bar_view.visibility = View.GONE
//            this.fragment_generic_empty_view.visibility = View.GONE
//            this.fragment_list_view.visibility = View.VISIBLE
//        }
//    }
//
//    fun drawLoadFailView() {
//        view.apply {
//            this.fragment_generic_progress_bar_view.visibility = View.GONE
//            this.fragment_generic_empty_view.visibility = View.VISIBLE
//            this.fragment_list_view.visibility = View.INVISIBLE
//        }
//    }

    override fun onGetCurrentLocationSuccessful(location: Location) {
        mRootLayout.fragment_generic_progress_bar_view.visibility = View.GONE
        mRootLayout.fragment_generic_empty_view.visibility = View.VISIBLE
        mRootLayout.fragment_generic_empty_view.text = location.latitude.toString() + " " + location.longitude.toString()
    }

    override fun onGetWeatherLocationSuccessful() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetWeatherFailure(readon: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
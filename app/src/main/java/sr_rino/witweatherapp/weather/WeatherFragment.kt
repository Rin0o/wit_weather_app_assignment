package sr_rino.witweatherapp.weather

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.select.getSelectExtension
import kotlinx.android.synthetic.main.fragment_weather.view.*
import sr_rino.witweatherapp.R
import sr_rino.witweatherapp.data.WeatherRepository
import sr_rino.witweatherapp.data.objects.Locations
import sr_rino.witweatherapp.data.objects.WeatherLocation
import sr_rino.witweatherapp.data.remote.WeatherRemoteDataSource

class WeatherFragment : Fragment(), WeatherContract.View {

    companion object {

        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }

    private lateinit var mSwipeRefresh: SwipeRefreshLayout
    private lateinit var mItemAdapter: ItemAdapter<FastAdapterWeather>
    private lateinit var mFastAdapter: FastAdapter<FastAdapterWeather>
    private lateinit var mController: WeatherController
    private lateinit var mRootLayout: View
    //private lateinit var mActionModeHelper: ActionModeHelper<FastAdapterPallets>

    private lateinit var mCurrentLocation: Location
    private var mWeatherList = mutableListOf<WeatherLocation>()

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
            initializeFastAdapter()
            mSwipeRefresh = mRootLayout.fragment_injection_recicler_swipe
            mSwipeRefresh.isEnabled = true
            mSwipeRefresh.setOnRefreshListener {
                fetchData()
            }

            fetchData()
        }

    }

    private fun fetchData() {
        drawLoadingView()
        mController.getCurrentLocation()
    }

    /**
     * Initialize the FastAdapter
     */
    private fun initializeFastAdapter() {
        val recyclerView = mRootLayout.findViewById<RecyclerView>(R.id.fragment_list_view)
        val layoutManager = LinearLayoutManager(activity!!.applicationContext, RecyclerView.VERTICAL, false)

        val itemAdapter = ItemAdapter<FastAdapterWeather>()
        val fastAdapter = FastAdapter.with(itemAdapter)

        mItemAdapter = itemAdapter
        mFastAdapter = fastAdapter

        recyclerView.adapter = fastAdapter

        recyclerView.apply {
            this.setHasFixedSize(false)
            this.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            this.layoutManager = layoutManager
            this.adapter = fastAdapter
        }


        val selectExtension = fastAdapter.getSelectExtension()
        selectExtension.isSelectable = false
        selectExtension.multiSelect = false
        selectExtension.selectOnLongClick = false

    }

    private fun populateRecyclerView() {
        if (mWeatherList.isNotEmpty()){
            mWeatherList.forEach { weather ->
                mItemAdapter.add(FastAdapterWeather(requireContext(), weather))
            }

        }
    }

    private fun drawLoadingView() {
        mItemAdapter.clear()
        mRootLayout.apply {
            this.fragment_generic_progress_bar_view.visibility = View.VISIBLE
            this.fragment_generic_empty_view.visibility = View.GONE
            this.fragment_list_view.visibility = View.INVISIBLE
        }
    }

    private fun drawLoadedView() {
        mRootLayout.apply {
            this.fragment_generic_progress_bar_view.visibility = View.GONE
            this.fragment_generic_empty_view.visibility = View.GONE
            this.fragment_list_view.visibility = View.VISIBLE
        }
        if (mSwipeRefresh.isRefreshing) {
            mSwipeRefresh.isRefreshing = false
        }
    }

    private fun drawLoadFailView() {
        mRootLayout.apply {
            this.fragment_generic_progress_bar_view.visibility = View.GONE
            this.fragment_generic_empty_view.visibility = View.VISIBLE
            this.fragment_list_view.visibility = View.INVISIBLE
        }
        if (mSwipeRefresh.isRefreshing) {
            mSwipeRefresh.isRefreshing = false
        }
    }

    override fun onGetCurrentLocationSuccessful(location: Location) {
        mCurrentLocation = location
        mController.getWeatherByCoordinates(location.latitude.toString(), location.longitude.toString())
    }

    override fun onGetWeatherLocationByCoordinatesSuccessful(weather: WeatherLocation) {
        mWeatherList.add(weather)
        val ids = Locations.values().joinToString (separator = ",") { it.cityId.toString() }
        mController.getWeatherLocationsByGroup(ids)
    }

    override fun onGetWeatherLocationByCoordinatesFailure(reason: String) {
        Toast.makeText(requireContext(), reason, Toast.LENGTH_LONG).show()
        drawLoadFailView()
    }


    override fun onGetWeatherLocationsByGroupSuccessful(list: List<WeatherLocation>) {
        mWeatherList.addAll(list)

        populateRecyclerView()
        drawLoadedView()
    }

    override fun onGetWeatherLocationsByGroupFailure(reason: String) {
        Toast.makeText(requireContext(), reason, Toast.LENGTH_LONG).show()
        drawLoadFailView()
    }

}
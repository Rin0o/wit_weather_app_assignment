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

            initializeFastAdapter()
            initializeSwipeRefresh()

            fetchData()
        }

    }

    /**
     * Call to get WeatherLocations Data
     */
    private fun fetchData() {
        drawLoadingView()
        mController.getWeather()
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

    /**
     * Initialize the Swipe to Load View component
     */
    private fun initializeSwipeRefresh() {
        mSwipeRefresh = mRootLayout.fragment_injection_recicler_swipe
        mSwipeRefresh.isEnabled = true
        mSwipeRefresh.setOnRefreshListener {
            fetchData()
        }
    }

    /**
     * Insert new list in View
     */
    private fun populateRecyclerView(list: List<WeatherLocation>) {
        if (list.isNotEmpty()){
            mItemAdapter.clear()
            mFastAdapter.notifyAdapterDataSetChanged()
            var adapterList = mutableListOf<FastAdapterWeather>()
            list.forEach { weather ->
                adapterList.add(FastAdapterWeather(requireContext(), weather))
            }
            mItemAdapter.setNewList(adapterList)
        }
    }

    /**
     * Set View components to LoadOk State
     */
    private fun drawLoadingView() {
        mItemAdapter.clear()
        mRootLayout.apply {
            this.fragment_generic_progress_bar_view.visibility = View.VISIBLE
            this.fragment_generic_empty_view.visibility = View.GONE
            this.fragment_list_view.visibility = View.INVISIBLE
        }
    }

    /**
     * Set View components to Loading state
     */
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

    /**
     * Set View components to Failure State
     */
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

    /**
     * Get Weather Locations list from DataSource
     */
    override fun onGetWeatherSuccessful(list: List<WeatherLocation>) {
        populateRecyclerView(list)
        drawLoadedView()
    }

    /**
     * Show Toast in Case of error or failure
     */
    override fun onGetWeatherFailure(reason: String) {
        Toast.makeText(requireContext(), reason, Toast.LENGTH_LONG).show()
        drawLoadFailView()
    }

}
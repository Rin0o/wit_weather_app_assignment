package sr_rino.witweatherapp.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sr_rino.witweatherapp.R

class WeatherFragment : Fragment(), WeatherContract.View {

    companion object {

        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }


    private lateinit var mPresenter: WeatherController
    private lateinit var mRootLayout: View

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

    override fun onGetWeatherLocationSuccessful() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetWeatherFailure(readon: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
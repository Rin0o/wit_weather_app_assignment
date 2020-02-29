package sr_rino.witweatherapp

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import sr_rino.witweatherapp.weather.WeatherFragment
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET
            )
            Permissions.check(
                this,
                permissions,
                null,
                null,
                object : PermissionHandler() {
                    override fun onGranted() {
                        changeFragment(savedInstanceState)
                    }

                    override fun onDenied(context: Context, deniedPermissions: ArrayList<String>) {
                        Toast.makeText(context, "É necessaria permissão sobre a sua localização", Toast.LENGTH_LONG).show()
                    }
                })
        } else {
            changeFragment(savedInstanceState)
        }

    }

    private fun changeFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_generic_fragment_container, WeatherFragment.newInstance())
                .commit()
        }
    }
}

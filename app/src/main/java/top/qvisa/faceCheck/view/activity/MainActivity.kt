package top.qvisa.faceCheck.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gyf.immersionbar.ktx.immersionBar
import top.qvisa.faceCheck.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        immersionBar {
            autoDarkModeEnable(true)
            fitsSystemWindows(true)
            barColor(R.color.colorStatusBar)
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigatiomView)
        val navController = Navigation.findNavController(this, R.id.fragment_host_main)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

}

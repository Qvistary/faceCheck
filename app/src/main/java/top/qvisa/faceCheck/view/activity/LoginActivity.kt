package top.qvisa.faceCheck.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gyf.immersionbar.ktx.immersionBar
import top.qvisa.faceCheck.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        immersionBar {
            autoDarkModeEnable(true)
            fitsSystemWindows(true)
            barColor(R.color.colorStatusBar)
        }
    }
}

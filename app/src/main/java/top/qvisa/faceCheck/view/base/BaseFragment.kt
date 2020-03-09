package top.qvisa.faceCheck.view.base

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment




open class BaseFragment : Fragment() {

    private lateinit var mPermissionListen: PermissionListener

    fun requestPermission(permissionListener: PermissionListener){
        mPermissionListen = permissionListener
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            mPermissionListen.onGranted()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionListen.onGranted()
            } else {
                Toast.makeText(
                    context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    interface PermissionListener{
        fun onGranted()
    }

   companion object {
        private const val PERMISSIONS_REQUEST_CODE = 10
    }
}
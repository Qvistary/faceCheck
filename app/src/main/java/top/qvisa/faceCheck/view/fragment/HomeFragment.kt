package top.qvisa.faceCheck.view.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baidu.location.BDLocation
import kotlinx.android.synthetic.main.fragment_home.*
import top.qvisa.faceCheck.R
import top.qvisa.faceCheck.utils.DateUtils
import top.qvisa.faceCheck.utils.LocationUtils
import top.qvisa.faceCheck.view.base.BaseFragment
import top.qvisa.faceCheck.viewModel.HomeViewModel
import top.qvisa.faceCheck.viewModel.SignViewModel


class HomeFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        tV_home_date.text = viewModel.data
        tV_home_weak.text = viewModel.weakHours

        viewModel.location.observe(viewLifecycleOwner, Observer {
            tV_home_location.text = it
        })

        tV_home_location.setOnClickListener {
            requestPermission(object : PermissionListener {
                override fun onGranted() {
                    viewModel.getLocation(context)
                }
            })
        }
    }
}




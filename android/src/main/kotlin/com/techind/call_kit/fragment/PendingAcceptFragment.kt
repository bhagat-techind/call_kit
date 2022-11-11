package com.techind.call_kit.fragment



import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Button
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.techind.call_kit.MyApplication
import com.techind.call_kit.R
import com.techind.call_kit.databinding.FragmentPendingAcceptBinding
import com.techind.call_kit.model.EventModel
import com.techind.call_kit.model.PendingAcceptViewModel
import com.techind.call_kit.model.Ride
import com.techind.call_kit.my_interface.MapFragmentInterface
import com.techind.call_kit.utils.ViewUtils


/**
 *  Author - Bhagat Singh
 *  Contact - https://www.linkedin.com/in/bhagat-singh-79496a14b/
 *  Created On - 10/10/2022
 *
 * A [PendingAcceptFragment] is [Fragment] subclass witch will show accept ui of new ride .
 * Use the [PendingAcceptFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PendingAcceptFragment : Fragment() {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param [event] will store event detail data
         * @return A new instance of fragment PendingAcceptFragment.
         */
        @JvmStatic
        fun newInstance(event: EventModel,durationInSec:Int = 5) =
            PendingAcceptFragment().apply {
                arguments = Bundle().apply {
                    putString("payload", Gson().toJson(event))
                    putInt("durationInSec", durationInSec)
                }
            }
    }

    private var TAG = javaClass.name
    private lateinit var binding: FragmentPendingAcceptBinding
    private var eventModel: EventModel? = null
    private var rideModel: Ride? = null
    var durationInSec : Int = 5
    private var acceptButtonClickedListener: MapFragmentInterface.OnButtonClickListener? = null

//    private val acceptCounter: Observable<Long>? = null

    private var mapPaddingListener: MapFragmentInterface.MapPaddingListener? = null
    private var onGlobalLayoutListener: OnGlobalLayoutListener? = null

    var cTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val payload = it.getString("payload")
            Log.w(TAG,"payload ==>> $payload")
            durationInSec = it.getInt("durationInSec",8)
            eventModel = Gson().fromJson(payload, EventModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPendingAcceptBinding.inflate(inflater)
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pending_accept, container, false)
        /// Initial view model and set it on UI
        val acceptedMode = PendingAcceptViewModel()
        acceptedMode.setRide(rideModel)
        binding.viewModel = acceptedMode
        var options = RequestOptions().centerCrop()
        if(rideModel?.rider?.user?.photoUrl!=null) {
            Glide.with(MyApplication.getInstance().applicationContext)
                .asBitmap()
                .load(rideModel!!.rider.user.photoUrl)
                .apply(options)
                .into(binding.riderAvatar)
        }
        binding.acceptSubtitle.visibility = if(rideModel!!.isFemaleDriverRequest()) View.VISIBLE else View.GONE

        onGlobalLayoutListener  = OnGlobalLayoutListener {  updateMapPadding() }
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)

        initializeCounter()
        return binding.root
    }

    fun initializeCounter(){
        cTimer = object : CountDownTimer((durationInSec * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.counterText.text = "${(millisUntilFinished / 1000).toInt()}"
            }

            override fun onFinish() {
                activity?.finish()
            }
        }.start()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.accept.setOnClickListener {
            cTimer?.cancel()
            binding.accept.setOnClickListener(null)
            acceptButtonClickedListener!!.onClicked(1)
        }
        updateMapPadding()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun updateMapPadding() {
        if (mapPaddingListener != null) {
            mapPaddingListener!!.onBottomPaddingUpdated(
                binding.root.height + ViewUtils.dpToPixels(
                    10f
                ).toInt()
            )
        }
    }

    fun setRide(rideModel: Ride) {
        this.rideModel = rideModel
    }

    fun setDuration(durationInSec: Int = 8) {
        this.durationInSec = durationInSec
    }

    fun setButtonClickedListener(buttonClickedListener: MapFragmentInterface.OnButtonClickListener) {
        this.acceptButtonClickedListener = buttonClickedListener
    }

    fun setMapPaddingListener(mapPaddingListener: MapFragmentInterface.MapPaddingListener) {
        this.mapPaddingListener = mapPaddingListener
    }

    override fun onStart() {
        onGlobalLayoutListener?.onGlobalLayout()
        super.onStart()
    }

    override fun onDestroyView() {
        cTimer?.cancel()
        binding.root.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
        super.onDestroyView()
    }

}
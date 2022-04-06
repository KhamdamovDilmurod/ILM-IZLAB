package com.example.ilm_izlab.screen.edudetail

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.ilm_izlab.R
import com.example.ilm_izlab.adapters.PagerAdapter
import com.example.ilm_izlab.adapters.TabAdapter
import com.example.ilm_izlab.databinding.ActivityEduDetailBinding
import com.example.ilm_izlab.model.TrainingCentersModel
import com.example.ilm_izlab.screen.evaluation.EvaluationActivity
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.screen.show_fully.ShowFullyCommentFragment
import com.example.ilm_izlab.screen.sign.LoginActivity
import com.example.ilm_izlab.utils.Constants
import com.example.ilm_izlab.utils.PrefUtils

class EduDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityEduDetailBinding

    lateinit var viewModel: MainViewModel

    lateinit var item: TrainingCentersModel
    
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var indicatorContainer: LinearLayout


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEduDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setSupportActionBar(binding.toolbarLayout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarLayout.setNavigationOnClickListener {
            finish()
        }

        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as TrainingCentersModel

        //-------------Pager Adapter-------------//
        val images = item.images
        images.add(0, item.main_image)
        pagerAdapter = PagerAdapter(images,item)
        binding.imagePager.adapter = pagerAdapter
        binding.imagePager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setUpActiveIndicator(position)
            }
        })
        //------------------setIndicator--------------------//
        indicatorContainer = binding.indicatorContainer
        val indicators = arrayOfNulls<ImageView>(pagerAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices){
            indicators[i] = ImageView(this)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.active_indicator)
            )
            indicators[i]?.layoutParams = layoutParams
            binding.indicatorContainer.addView(indicators[i])
        }
        setUpActiveIndicator(0)
// <<<< -------------------
        binding.viewPager.adapter = TabAdapter(supportFragmentManager,item)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        // ->
        //Glide.with(this).load(Constants.HOST_IMAGE + item.main_image).into(binding.image)
        binding.toolbarLayout.title = item.name
        binding.name.text = item.name
        val rating = String.format("%.1f", item.rating)
        binding.grade.text = "${item.rating_count.toString()} ta-$rating/o'rtacha"
        binding.subscribeCount.text = item.subsribers_count.toString()
        binding.monthlyMiddleAmount.text = "${item.monthly_payment_min} dan ${item.monthly_payment_max} gacha"
        binding.address.text = item.address
        val phone = item.phone
        binding.phone.text = phone
        val comment = item.comment
        binding.comment.text = comment

        showDistance()

        binding.share.setOnClickListener {
            share(item.name, rating)
        }

        binding.evaluation.setOnClickListener {
            val intent = Intent(this,EvaluationActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA,item)
            startActivity(intent)
        }

        viewModel.progress.observe(this, Observer {
            if(it){
                binding.flProgress.visibility = View.VISIBLE
                binding.subscribe.visibility = View.GONE
            } else {
                binding.flProgress.visibility = View.GONE
                binding.subscribe.visibility = View.VISIBLE
            }
        })

        viewModel.checkSubscriberData.observe(this, Observer {
            if (it==true){
                binding.subscribe.text="Obunani tugatish"

                binding.subscribe.setTextColor(resources.getColor(R.color.gray))
            }else {
                binding.subscribe.text="Obuna bo'lish"
                binding.subscribe.setTextColor(resources.getColor(R.color.gold))
            }
        })
        viewModel.setSubscriberData.observe(this, Observer {
            viewModel.checkSubscriber(item.id)
        })

        viewModel.checkSubscriber(item.id)

        binding.subscribe.setOnClickListener {
            if(PrefUtils.getToken().isNullOrEmpty()){
                startActivity(Intent(this, LoginActivity::class.java))
            }else{
                viewModel.setSubscriber(item.id)
            }
        }

        binding.seeInMap.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=${item.latitude},${item.longitude} (" + item.name + ")")
            )
            startActivity(intent)
        }

        binding.callCenter.setOnClickListener {
                val phoneNumber = item.phone.trim()
                val phoneIntent = Intent(Intent.ACTION_DIAL)
                phoneIntent.data = Uri.parse("tel:" + "${phoneNumber}")
                startActivity(phoneIntent)
        }

        // <-
        binding.seeFullyComment.setOnClickListener {

            val showFullyCommentFragment = ShowFullyCommentFragment()

            // -> send data to fragment
            val bundle = Bundle()
            bundle.putString("comment",comment)
            showFullyCommentFragment.arguments = bundle // <- //

            // -> show fragment
            showFullyCommentFragment.show(supportFragmentManager,showFullyCommentFragment.tag) //

        }

    }

    private fun setUpActiveIndicator(position: Int){
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount){
            val indicator = binding.indicatorContainer.getChildAt(i) as ImageView
            if(i==position){
                indicator.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_indicator))
            }
            else{
                indicator.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.deactive_indicator))
            }
        }
    }

    fun share(name: String, rating:String){
        val str = "\uD83C\uDFE2 Markaz nomi: ${name} \n" +
                "⭐ Qo'yilgan baholar: ${rating} \n" +
                "\uD83D\uDC65 Obunachilar soni: ${item.subsribers_count} \n" +
                "\uD83D\uDCB0 O'rtacha oylik to'lov: ${item.monthly_payment_min} - ${item.monthly_payment_max} \n" +
                "\uD83D\uDCDE Telefon raqami: ${item.phone} \n" +
                "\uD83D\uDCCD Manzil: ${item.address} \n" +
                "\uD83D\uDCC4 Batafsil: http://api.ilm-izlab.uz/center/${item.id} \n" +
                "© ILM-IZLAB \n" +
                "\uD83D\uDCF2 Ilovani yuklab olish: http://ilm-izlab.uz"

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, str)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    fun showDistance(){

        if(PrefUtils.getLocation()!=null){

            val currentLocation = PrefUtils.getLocation()

            val locationA = Location("gps")
            locationA.latitude = currentLocation!!.latitude
            locationA.longitude = currentLocation!!.longitude

            val locationB = Location("gps")
            locationB.latitude = item.latitude
            locationB.longitude = item.longitude

            val distance: Float = locationA.distanceTo(locationB)
            binding.btnDistance.text = String.format("%.1f", distance/1000) + " km"
        }

    }

}
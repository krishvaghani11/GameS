package com.example.games.Activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart
import com.example.games.Adapter.CartAdapter
import com.example.games.R
import com.example.games.databinding.ActivityCartBinding

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private var tax:Double=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart=ManagmentCart(this)

        setVariable()
        initCartList()
        calculatorCart()

    }

    private fun initCartList() {
        binding.viewCart.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.viewCart.adapter=
            CartAdapter(managmentCart.getListCart(), this, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculatorCart()
                }
            })
        with(binding) {
            emptyTxt.visibility=
                if (managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView3.visibility=
                if (managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun setVariable() {
        binding.apply {
            backBtn.setOnClickListener {
                finish()
            }

            methode1.setOnClickListener {
                methode1.setBackgroundResource(R.drawable.purple_bg_selected)
                methodeIc1.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(this@CartActivity,R.color.purple))
                methodeTitle1.setTextColor(getResources().getColor(R.color.purple))
                methodeSubtitle1.setTextColor(getResources().getColor(R.color.purple))

                methode2.setBackgroundResource(R.drawable.gray_bg_selected)
                methodeIc2.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(this@CartActivity,R.color.black))
                methodeTitle2.setTextColor(getResources().getColor(R.color.black))
                methodeSubtitle2.setTextColor(getResources().getColor(R.color.black))
            }
            methode2.setOnClickListener {
                methode2.setBackgroundResource(R.drawable.purple_bg_selected)
                methodeIc2.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(this@CartActivity,R.color.purple))
                methodeTitle2.setTextColor(getResources().getColor(R.color.purple))
                methodeSubtitle2.setTextColor(getResources().getColor(R.color.purple))

                methode1.setBackgroundResource(R.drawable.gray_bg_selected)
                methodeIc1.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(this@CartActivity,R.color.black))
                methodeTitle1.setTextColor(getResources().getColor(R.color.black))
                methodeSubtitle1.setTextColor(getResources().getColor(R.color.black))
            }
        }
    }
    private fun calculatorCart(){
        val percentage=0.02
        val delivery=10.0
        tax=Math.round((managmentCart.getTotalFee()*percentage)*100)/100.0
        val total=Math.round((managmentCart.getTotalFee()+tax+delivery)*100)/100
        val itemTotal=Math.round(managmentCart.getTotalFee()*100)/100

        with(binding){
            totalFreeTxt.text="$$itemTotal"
            taxTxt.text="$$tax"
            deliveryTxt.text="$$delivery"
            totalTxt.text="$$total"
        }
    }
}
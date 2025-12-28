package com.example.games.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.games.Adapter.PicAdapter
import com.example.games.Adapter.SelectModelAdapter
import com.example.games.Model.ItemsModel
import com.example.games.databinding.ActivityDetailBinding
import com.example.project1762.Helper.ManagmentCart

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var item: ItemsModel? = null // Changed to nullable to prevent initialization crash
    private var numberOrder = 1
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        getBundle()
    }

    private fun getBundle() {
        // Safe way to get Parcelable for both old and new Android versions
        item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("object", ItemsModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("object")
        }

        // Check if the item is null before proceeding
        val currentItem = item
        if (currentItem != null) {
            setupViews(currentItem)
            initList(currentItem)
        } else {
            // Log the error and close activity so the app doesn't stay on a blank screen
            Log.e("DetailActivity", "Error: No ItemsModel found in Intent extras.")
            Toast.makeText(this, "Failed to load item details", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupViews(item: ItemsModel) {
        binding.titleTxt.text = item.title
        binding.descriptionTxt.text = item.description
        binding.priceTxt.text = "$" + item.price
        binding.ratingTxt.text = "${item.rating} Rating"

        binding.addToCartBtn.setOnClickListener {
            item.numberInCart = numberOrder
            managmentCart.insertItem(item)
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
        }

        binding.backBtn.setOnClickListener { finish() }
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }
    }

    private fun initList(item: ItemsModel) {
        // Handle Model List
        val modeList = ArrayList<String>()
        for (models in item.model) {
            modeList.add(models)
        }
        binding.modelList.adapter = SelectModelAdapter(modeList)
        binding.modelList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Handle Picture List
        val picList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            picList.add(imageUrl)
        }

        // Set initial image
        if (picList.isNotEmpty()) {
            Glide.with(this)
                .load(picList[0])
                .into(binding.img)
        }

        binding.picList.adapter = PicAdapter(picList) { selectedImageUrl ->
            Glide.with(this)
                .load(selectedImageUrl)
                .into(binding.img)
        }

        binding.picList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}
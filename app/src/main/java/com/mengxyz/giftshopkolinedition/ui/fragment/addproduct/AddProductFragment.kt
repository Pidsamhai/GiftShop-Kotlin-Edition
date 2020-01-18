package com.mengxyz.giftshopkolinedition.ui.fragment.addproduct

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.extentions.await
import com.mengxyz.giftshopkolinedition.ui.scope.FragmentScope
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.log

@SuppressLint("SetTextI18n")
class AddProductFragment : FragmentScope(), View.OnClickListener, ViewPager.OnPageChangeListener {

    private val storage = FirebaseStorage.getInstance("gs://gift-shop-kotlin.appspot.com")
    private val storageRef = storage.reference
    private lateinit var productRef: StorageReference
    private var imgs: MutableList<Uri> = mutableListOf()
    private lateinit var adapter: PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.appbar_title).text = " Add Product "
        open_file.setOnClickListener(this)
        upload_file.setOnClickListener(this)
        add_img.setOnClickListener(this)
        remove_img.setOnClickListener(this)
        initViewPager()
    }


    private fun initViewPager() {
        adapter = context?.let { PagerAdapter(it, imgs) }!!
        pager.addOnPageChangeListener(this)
        pager.adapter = adapter
    }

    private fun pickImage() {
        ImagePicker.with(this)
            .galleryOnly()
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )  //Final image resolution will be less than 1080 x 1080(Optional)
            .start { resultCode, data ->
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val fileUri = data?.data
                        if (fileUri != null) {
                            adapter.addItem(fileUri)
                            adapter.notifyDataSetChanged()
                            pager.currentItem = adapter.getAllItem().size
                            remove_img.show()
                        }
                    }
                    ImagePicker.RESULT_ERROR -> {
                        Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun upload() = launch(Dispatchers.Main) {
        try {
            loading.visibility = View.VISIBLE
            val size = adapter.getItemSize()
            adapter.getAllItem().forEachIndexed {idx,img ->
                val uuid = UUID.randomUUID().toString()
                upload_status.text = "upload ${idx + 1} / $size ..."
                productRef = storageRef.child("product/$uuid.png")
                try {
                    productRef.putFile(img).await()
                    val url = productRef.downloadUrl.await()
                    //Toast.makeText(context, url.toString(), Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e("ProductRef error", "${e.message}")
                }
            }
            loading.visibility = View.GONE
            Toast.makeText(context, "Uploaded $size / $size", Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            Log.e("Upload file", "Error ",e)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            open_file -> {
                pickImage()
            }
            add_img -> {
                pickImage()
            }
            upload_file -> {
                if (adapter.getItemSize() > 0) {
                    upload()
                }
            }
            remove_img -> {
                removeImage()
            }
        }
    }

    private fun removeImage() {
        if (adapter.getItemSize() > 0) {
            adapter.removeItem(pager.currentItem)
            adapter.notifyDataSetChanged()
            updateIndicator(pager.currentItem, adapter.getItemSize())
        }
    }

    private fun updateIndicator(position: Int = 0, size: Int = 0) {
        if (size == 0) remove_img.hide()
        text_indicator.text = "${if (size > 0) position + 1 else 0} / $size"
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        updateIndicator(position, adapter.getItemSize())
    }

    override fun onPageSelected(position: Int) {}


}
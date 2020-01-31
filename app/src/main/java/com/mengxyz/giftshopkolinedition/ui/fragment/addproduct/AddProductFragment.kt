package com.mengxyz.giftshopkolinedition.ui.fragment.addproduct

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.firestore.FieldValue
import com.mengxyz.giftshopkolinedition.R
import com.mengxyz.giftshopkolinedition.db.model.ProductModel2
import com.mengxyz.giftshopkolinedition.ui.scope.FragmentScope
import com.mengxyz.giftshopkolinedition.utils.Result
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.product_input.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("SetTextI18n")
class AddProductFragment : FragmentScope(), View.OnClickListener, ViewPager.OnPageChangeListener {
    private val viewModel by viewModel<AddProductFragmentViewModel>()
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
        (activity as AppCompatActivity).findViewById<TextView>(R.id.appbar_title).text =
            " Add Product "
        upload_file.setOnClickListener(this)
        add_img.setOnClickListener(this)
        remove_img.setOnClickListener(this)
        initViewPager()
        viewModel.getProduct("d85d4ac3-dca0-4f8b-8de9-3f4641508dee").observe(this, Observer {
            if (it == null)
                return@Observer
            Log.e("response", it.toString())
        })
    }


    private fun initViewPager() {
        adapter = context?.let { PagerAdapter(it, imgs) }!!
        pager.addOnPageChangeListener(this)
        pager.adapter = adapter
    }

    private fun pickImage() {
        ImagePicker.with(this)
            .galleryOnly()
            .compress(512)
            .maxResultSize(
                1080,
                1080
            )
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
        val imageUrlList = mutableListOf<String>()
        val imageNameList = mutableListOf<String>()
        val size = adapter.getItemSize()
        try {
            loading.visibility = View.VISIBLE
            adapter.getAllItem().forEachIndexed { idx, img ->
                upload_status.text = "upload ${idx + 1} / $size ..."
                val result = viewModel.uploadImg(img)
                if (result != Result.Error) {
                    val (name: String, url: String) = result as Pair<String, String>
                    imageNameList.add(name)
                    imageUrlList.add(url)
                } else {
                    Log.e("Upload image Error", "upload:$idx ")
                }
            }
            if (imageNameList.size > 0 && imageUrlList.size > 0) {
                addDataBase(mapValue(imageNameList, imageUrlList))
                loading.visibility = View.GONE
                showSnackBar("Uploaded")
            } else {
                loading.visibility = View.GONE
                Log.e("No file selected", "Error")
            }
        } catch (e: Exception) {
            Log.e("Upload file", "Error ", e)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            add_img -> {
                pickImage()
            }
            upload_file -> {
                if (adapter.getItemSize() > 0) {
                    upload()
                } else {
                    showSnackBar("Please add Image")
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

    private fun mapValue(
        imageNameList: MutableList<String>,
        imageUrlList: MutableList<String>
    ): ProductModel2 {
        val productModel2 = ProductModel2(
            name = e_product_name.text.toString(),
            price = e_product_price.text.toString().toDouble(),
            picture = imageNameList,
            picture_url = imageUrlList,
            description = e_product_detail.text.toString(),
            tel = e_tel.text.toString(),
            product_id = null,
            facebook_name = e_facebook_name.text.toString(),
            facebook_url = e_facebook_url.text.toString(),
            line_id = e_line_id.text.toString(),
            line_url = e_line_url.text.toString(),
            lat = null,
            lon = null,
            timestamps = FieldValue.serverTimestamp(),
            u_id = null
        )
        return productModel2
    }

    private fun addDataBase(mapValue: ProductModel2) = launch(Dispatchers.Main) {
        val result = viewModel.addProduct(mapValue)
        if (result == Result.OK)
            showSnackBar("Upload Database Success")
        else
            showSnackBar("Upload Database Error")
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



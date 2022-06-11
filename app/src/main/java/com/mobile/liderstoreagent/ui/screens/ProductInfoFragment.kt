package com.mobile.liderstoreagent.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.InfoClass
import com.mobile.liderstoreagent.data.models.getproduct.ProductDetail
import com.mobile.liderstoreagent.ui.viewmodels.ProductDetailViewModel
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.product_info_fragment.*


class ProductInfoFragment : Fragment(R.layout.product_info_fragment) {

    private val viewModel: ProductDetailViewModel by viewModels()
    var myProductId = 0
    private lateinit var product: InfoClass


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            myProductId = it.getInt("int")
            product = it.getSerializable("class") as InfoClass
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productName.text = product.name
        productQuantity.text = product.quantity
        productStatus.text = product.status
        price.text = product.price
        setUpProductDetail()
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            productInfoProgressBar.visibility = View.VISIBLE
        } else {
            productInfoProgressBar.visibility = View.GONE
        }
    }


    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }


    private val successProductDetailObserver = Observer<ProductDetail> { productDetail ->
        setUpProductData(productDetail)

    }

    private fun setUpProductData(productDetail: ProductDetail?) {

        productName.text = productDetail!!.product.name
        productQuantity.text = (productDetail.product.quantity.toString() + " " + productDetail.product.unit)
        productStatus.text = productDetail.product.product_type

        //     productCategory.text = productDetail.product.category.name

        providerName.text = productDetail.product.provider.name
//        providerAddress.text = productDetail.product.provider.address
//        providerPhone1.text = productDetail.product.provider.phone_number1
//        providerPhone2.text = productDetail.product.provider.phone_number2
//        providerDirector.text = productDetail.product.provider.director
//        providerResponsiblePerson.text = productDetail.product.provider.responsible_agent

        if(productDetail.product.image!=null){
            Glide.with(productImage.context).load("${productDetail.product.image}")
                .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                .into(productImage)
        }else productImage.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
    }
    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpProductDetail() {
        viewModel.progressProductLiveData.observe(this, progressObserver)
        viewModel.connectionErrorProductLiveData.observe(this, connectionErrorObserver)
        viewModel.successProductLiveData.observe(this, successProductDetailObserver)
    }
}
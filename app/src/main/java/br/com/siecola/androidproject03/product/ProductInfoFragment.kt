package br.com.siecola.androidproject03.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.siecola.androidproject03.databinding.FragmentProductInfoBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ProductInfoFragment : Fragment() {
    private val productInfoViewModel: ProductInfoViewModel by lazy {
        ViewModelProvider(this).get(ProductInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.productInfoViewModel = productInfoViewModel

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                productInfoViewModel.fcmRegistrationId.value = task.result
                return@OnCompleteListener
            }
        })

        this.arguments?.getString("productInfo")?.let { productInfoArgument ->
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter: JsonAdapter<Product> =
                moshi.adapter<Product>(Product::class.java)
            jsonAdapter.fromJson(productInfoArgument).let { product ->
                productInfoViewModel.product.value = product
            }
        }


        return binding.root
    }

}
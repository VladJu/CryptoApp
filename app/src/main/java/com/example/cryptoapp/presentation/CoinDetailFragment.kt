package com.example.cryptoapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.FragmentDetailInfoAboutCoinBinding
import com.squareup.picasso.Picasso

class CoinDetailFragment : Fragment() {
    private lateinit var viewModel: CoinViewModel
 //3
    private var _binding: FragmentDetailInfoAboutCoinBinding? = null
    private val binding: FragmentDetailInfoAboutCoinBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailInfoAboutCoinBinding is null")

//4
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailInfoAboutCoinBinding.inflate(inflater, container, false)
        return binding.root
    }
//5
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fromSymbol = getSymbol()
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) {
            with(binding) {
                tvPrice.text = it.price.toString()
                tvMinPrice.text = it.lowDay.toString()
                tvMaxPrice.text = it.highDay.toString()
                tvLastMarket.text = it.lastMarket
                tvUpdate.text = it.lastUpdate
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        }
    }


    // 2)получаем из агрументов строку
    private fun getSymbol(): String {
        return requireArguments().getString(EXTRA_FROM_SYMBOL, EMPTY_SYMBOL)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // 1)
    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""
        fun newInstance(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }
}
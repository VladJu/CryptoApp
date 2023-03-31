package com.example.cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptoapp.domain.CoinInfoEntity

class CoinInfoDiffCallback : DiffUtil.ItemCallback<CoinInfoEntity>() {

    //1 сравниваем что это 1 и тот же объект
    override fun areItemsTheSame(oldItem: CoinInfoEntity, newItem: CoinInfoEntity): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }
    //2 проверяет не изменилось ли содержимое объекта, если изменилось то перерисовываем
    override fun areContentsTheSame(oldItem: CoinInfoEntity, newItem: CoinInfoEntity): Boolean {
        return oldItem == newItem
    }
}
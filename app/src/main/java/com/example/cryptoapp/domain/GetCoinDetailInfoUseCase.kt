package com.example.cryptoapp.domain

import javax.inject.Inject

class GetCoinDetailInfoUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(fromSymbols: String) = repository.getCoinDetailInfo(fromSymbols)
}
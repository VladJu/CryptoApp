package com.example.cryptoapp.domain

//3)
class GetCoinInfoListUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke()=repository.getCoinInfoList()
}
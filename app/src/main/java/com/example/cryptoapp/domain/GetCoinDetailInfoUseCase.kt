package com.example.cryptoapp.domain
//3)
class GetCoinDetailInfoUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke(fromSymbols: String) = repository.getCoinDetailInfo(fromSymbols)
}
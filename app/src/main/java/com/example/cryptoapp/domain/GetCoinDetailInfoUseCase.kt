package com.example.cryptoapp.domain
class GetCoinDetailInfoUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke(fromSymbols: String) = repository.getCoinDetailInfo(fromSymbols)
}
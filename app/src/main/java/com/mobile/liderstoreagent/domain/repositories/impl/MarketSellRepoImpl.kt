package com.mobile.liderstoreagent.domain.repositories.impl

import com.mobile.liderstoreagent.data.models.SellToClientOwnProduct
import com.mobile.liderstoreagent.data.models.sellmodel.MarketSellData
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.MarketSellApi
import com.mobile.liderstoreagent.domain.repositories.repo.MarketSellRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarketSellRepoImpl : MarketSellRepository {

    private val api = ApiClient.retrofit.create(MarketSellApi::class.java)

    override suspend fun marketProducts(list: List<MarketSellData>): Flow<Result<Pair<Int, Any?>>> =
        flow {
            try {
                val response = api.marketSell(
                    "application/json",
                    list
                )
                if (response.code() == 201) {
                    emit(Result.success(Pair(201, response.body())))
                } else if (response.code() == 400) {
                    emit(Result.success(Pair(400, null)))
                }
            } catch (e: Exception) {

            }
        }

    override suspend fun marketOwnProducts(list: List<SellToClientOwnProduct>): Flow<Result<Pair<Int, Any?>>> =
        flow {
            try {
                val response = api.marketOwnSell(
                    "application/json",
                    "token ${TokenSaver.token}",
                    list
                )
                if (response.code() == 201) {
                    emit(Result.success(Pair(201, response.body())))
                } else if (response.code() == 400) {
                    emit(Result.success(Pair(400, null)))
                }
            } catch (e: Exception) {

            }
        }
}
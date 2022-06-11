package com.mobile.liderstoreagent.domain.repositories.repo

import com.mobile.liderstoreagent.data.models.clientmodel.clientproducts.ClientProducts
import kotlinx.coroutines.flow.Flow

interface ClientProductsRepository {
    suspend fun getClientProducts(clientId: String): Flow<Result<List<ClientProducts>?>>
}
package com.mobile.liderstoreagent.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.liderstoreagent.data.models.Profit
import com.mobile.liderstoreagent.domain.usecases.ProfitUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfitViewModel : ViewModel() {
    var profitUseCase = ProfitUseCase()

    private val _order = MutableStateFlow<Profit?>(null)
    val order: StateFlow<Profit?>
        get() = _order

    fun getProfit(id: String, start_date: String, to_date: String) {
        viewModelScope.launch {
            profitUseCase.getSelectors(id, start_date, to_date)
                .catch {

                }.collect {
                    _order.emit(it)
                }
        }
    }
}
package org.azkary.variousazkarlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.azkary.data.model.Zikr
import javax.inject.Inject

@HiltViewModel
class VariousAzkarViewModel @Inject constructor(
    private val repository: VariousAzkarRepository
) : ViewModel() {
    private val _variousAzkarStateFlow = MutableStateFlow<List<Zikr>>(emptyList())

    val variousAzkarStateFlow: StateFlow<List<Zikr>> = _variousAzkarStateFlow

    fun getVariousAzkar() =
        viewModelScope.launch {
             _variousAzkarStateFlow.value = repository.getVariousAzkar()
        }
}
package com.curiousapps.apimyapi.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curiousapps.apimyapi.domain.EmojiListItem
import com.curiousapps.apimyapi.domain.EmojiListRepository
import com.curiousapps.apimyapi.util.IO_DISPATCHER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmojiListViewModel @Inject constructor(
    private val emojiRepo: EmojiListRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EmojiListState())
    val state = _state
        .onStart {
            fetchAllData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(4000L),
            EmojiListState()
        )

    private fun fetchAllData(){
        viewModelScope.launch(IO_DISPATCHER) {
            val result = emojiRepo.fetchAllData()
            when{
                result.isSuccess -> {
                    _state.update { it.copy(
                        emojiList = result.getOrNull()!!,
                        isLoading = false
                    ) }
                }
                result.isFailure -> {
                    _state.update {
                        it.copy(
                            emojiList = emptyList(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun fetchOneData(slug: String){
        viewModelScope.launch(IO_DISPATCHER) {
            val result = emojiRepo.fetchOneData(slug = slug)
            when{
                result.isSuccess -> {
                    _state.update { it.copy(
                        selectedEmoji = result.getOrNull()!!
                    ) }
                }
                result.isFailure -> {
                    _state.update { it.copy(
                        selectedEmoji = emptyList()

                        ) }
                }
            }
        }
    }

    fun dismissDialog(){
        _state.update { it.copy(
            selectedEmoji = emptyList()
        ) }
    }

    data class EmojiListState(
        val emojiList: List<EmojiListItem> = emptyList(),
        val isLoading : Boolean = true,
        val selectedEmoji : List<EmojiListItem> = emptyList()
    )
}
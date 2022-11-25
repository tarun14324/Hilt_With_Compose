package com.example.hiltwithcompose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltwithcompose.room.TableItems
import com.example.hiltwithcompose.room.UserDataBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UserRepository, private val dataBase: UserDataBase
) : ViewModel() {
    var charactersFlow: MutableStateFlow<List<Result>> = MutableStateFlow(emptyList())
    var charactersFlowFromDb: MutableStateFlow<List<TableItems>> = MutableStateFlow(emptyList())
    var isConnected = mutableStateOf(false)

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    fun getUsers() {
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            charactersFlow.value = repository.users().body()!!.results
            charactersFlow.value.forEach { item ->
                dataBase.userDao().addNote(
                    TableItems(
                        id = item.id,
                        name = item.name,
                    )
                )
            }
        }
    }

    fun getUsersFromDb() {
        viewModelScope.launch {
            charactersFlowFromDb.value = dataBase.userDao().getNotes()
        }
    }
}


package com.android.timesheetchecker.ui.membersdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.timesheetchecker.data.network.models.Entry
import com.android.timesheetchecker.domain.*
import com.android.timesheetchecker.utils.Event
import com.android.timesheetchecker.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class MemberDetailViewModel @Inject constructor(
    memberDetailUseCase: MemberDetailUseCase,
    private val updateExcludedStatusUseCase: UpdateExcludedStatusUseCase,
    private val fetchEntriesUseCase: FetchEntriesUseCase,
    private var markCompletedUseCase: MarkCompletedUseCase,
    private var markAsDevUseCase: MarkAsDevUseCase
) : ViewModel() {

    private val selectedUserIdLiveData = MutableLiveData<Int>()

    var entriesReceivedEvent = MutableLiveData<Event<List<Entry>>>()
    var errorEvent = MutableLiveData<Event<Result.Error>>()
    var selectedUser = Transformations.switchMap(selectedUserIdLiveData) {
        memberDetailUseCase(it)
    }

    fun setSelectedUserId(selectedUserId: Int) {
        selectedUserIdLiveData.postValue(selectedUserId)
    }

    fun updateUserExcludedStatus(checked: Boolean) {
        selectedUser.value?.let {
            viewModelScope.launch {
                updateExcludedStatusUseCase(it.id, checked)
            }
        }
    }

    fun updateUserDevTeamStatus(checked: Boolean) {
        selectedUser.value?.let {
            viewModelScope.launch {
                markAsDevUseCase(it.id, checked)
            }
        }
    }

    fun fetchEntries() {
        selectedUser.value?.let {
            viewModelScope.launch {
                fetchEntriesUseCase(it.id).let {
                    when (it) {
                        is Result.Success -> {
                            entriesReceivedEvent.postValue(Event(convertEntriesToPerDayEntries(it.data)))
                        }
                        is Result.Error -> {
                            errorEvent.postValue(Event(it))
                        }
                    }
                }
            }
        }
    }

    private fun convertEntriesToPerDayEntries(receivedEntries: List<Entry>): List<Entry> {
        val entries = ArrayList<Entry>()

        for (entry in receivedEntries) {
            val matchingEntry = entries.find { it.date == entry.date }

            if (matchingEntry == null) {
                entries.add(entry)
            } else {
                matchingEntry.hours += entry.hours
            }
        }

        return entries.sortedBy {
            it.date
        }
    }

    fun markTimesheetCompleted() {
        selectedUser.value?.let {
            viewModelScope.launch {
                markCompletedUseCase(it.id)
            }
        }
    }
}
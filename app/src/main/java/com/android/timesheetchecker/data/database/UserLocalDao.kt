package com.android.timesheetchecker.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.timesheetchecker.data.database.model.UserLocal

@Dao
interface UserLocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(localUsers: List<UserLocal>)

    @Query("SELECT id FROM UserLocal WHERE excluded = 1")
    suspend fun getExcludedUsersId(): List<Int>

    @Query("SELECT id FROM UserLocal")
    suspend fun getAllUserIds(): List<Int>

    @Query("SELECT * FROM UserLocal ORDER BY name")
    fun getAllUsersLiveData(): LiveData<List<UserLocal>>

    @Query("SELECT * FROM UserLocal WHERE isDev = 1 ORDER BY name")
    fun getDevUsersLiveData(): LiveData<List<UserLocal>>

    @Query("SELECT slackId FROM UserLocal WHERE email = :email")
    suspend fun getSlackUserIdByEmail(email: String): String

    @Query("UPDATE UserLocal SET excluded = :excluded WHERE id = :id")
    suspend fun updateUserExcludedStatus(id: Int, excluded: Boolean)

    @Query("SELECT * FROM UserLocal WHERE id = :id")
    fun getUserById(id: Int): LiveData<UserLocal>

    @Query("UPDATE UserLocal SET completed = 1 WHERE id = :id")
    suspend fun updateUserCompletedStatus(id: Int)

    @Query("UPDATE UserLocal SET isDev = :isDev WHERE id = :id")
    suspend fun updateUserDevTeamStatus(id: Int, isDev: Boolean)

    @Query("UPDATE UserLocal SET completed = 0")
    suspend fun resetCompletedStatus()

    @Query("SELECT * FROM UserLocal ORDER BY name")
    suspend fun getAllUsersOnce(): List<UserLocal>

    @Query("SELECT * FROM UserLocal WHERE isDev = 1 ORDER BY name")
    suspend fun getDevUsersOnce(): List<UserLocal>

    @Query("UPDATE UserLocal SET completedForDay = 1 WHERE id = :id")
    fun updateUserCompletedForDayStatus(id: Int)
}
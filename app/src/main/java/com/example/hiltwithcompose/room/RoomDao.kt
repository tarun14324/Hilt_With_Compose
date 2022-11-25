package com.example.hiltwithcompose.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(user: TableItems)

    @Query("SELECT * FROM users ORDER BY name DESC")
    fun getNotes(): List<TableItems>

    @Update
    suspend fun updateNote(note: TableItems)

    @Delete
    suspend fun deleteNote(note: TableItems)
}
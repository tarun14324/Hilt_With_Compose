package com.example.hiltwithcompose.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class TableItems(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?=null,
    @ColumnInfo(name = "name")
    val name: String?=null
)

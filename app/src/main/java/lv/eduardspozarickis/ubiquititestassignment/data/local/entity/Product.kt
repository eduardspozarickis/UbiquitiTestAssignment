package lv.eduardspozarickis.ubiquititestassignment.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val id: String,
    @ColumnInfo val deviceType: String,
    @ColumnInfo val line: String,
    @ColumnInfo val name: String,
    @ColumnInfo val imageId: String,
)
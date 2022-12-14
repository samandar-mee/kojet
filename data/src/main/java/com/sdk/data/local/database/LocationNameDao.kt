package com.sdk.data.local.database

import androidx.room.*
import com.sdk.data.local.entity.LocationNameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationNameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocationName(name: LocationNameEntity)

    @Delete
    suspend fun deleteLocationName(name: LocationNameEntity)

    @Query("UPDATE LocationName SET isLocationSaved = :isSaved WHERE id = :id")
    suspend fun updateFavLocation(id: Int, isSaved: Boolean)

    @Query("SELECT * FROM LocationName ORDER BY id DESC")
    fun getAllLocationNames(): Flow<List<LocationNameEntity>>
}
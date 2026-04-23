package com.nwsweather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedLocationDao {
    @Query("SELECT * FROM saved_locations ORDER BY label ASC")
    fun observeAll(): Flow<List<SavedLocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SavedLocationEntity)
}

@Dao
interface PointCacheDao {
    @Query("SELECT * FROM point_cache WHERE `key` = :key LIMIT 1")
    suspend fun get(key: String): PointCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PointCacheEntity)
}

@Dao
interface WeatherSnapshotDao {
    @Query("SELECT * FROM weather_snapshot WHERE id = 0 LIMIT 1")
    suspend fun getLatest(): WeatherSnapshotEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: WeatherSnapshotEntity)
}

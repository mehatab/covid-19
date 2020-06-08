package info.covid.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import info.covid.data.models.global.Country

@Dao
interface GlobalDao {
    @Query("SELECT * FROM Country")
    suspend fun getAllCountryData(): List<Country>

    @Insert
    suspend fun insert(list : List<Country>)

    @Query("DELETE FROM Country")
    suspend fun deleteAll()
}
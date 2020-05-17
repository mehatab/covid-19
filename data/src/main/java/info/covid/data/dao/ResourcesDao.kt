package info.covid.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import info.covid.data.enities.Resources


@Dao
interface ResourcesDao {
    @Query("SELECT * FROM resources")
    fun getResources(): LiveData<List<Resources>>

    @Query("SELECT * FROM resources where state IN (:filters) AND city IN (:filters) AND category IN (:filters)")
    fun getResources(filters: ArrayList<String>): LiveData<List<Resources>>

    @Query("SELECT * FROM resources where state IN (:state) AND (:city IS NULL OR city IN (:city)) AND (category LIKE :categories)")
    fun getResources(state: String?, city: String?, categories: String): LiveData<List<Resources>>

    @Query("SELECT * FROM resources where state IN (:state) AND (:city IS NULL OR city IN (:city)) AND category IN (:categories)")
    fun getResources(
        state: String?,
        city: String?,
        categories: List<String>?
    ): LiveData<List<Resources>>

    @Query("SELECT * FROM resources where state IN (:state) AND (:city IS NULL OR city IN (:city))")
    fun getResources(state: String?, city: String?): LiveData<List<Resources>>

    @Insert
    suspend fun insert(list: List<Resources>)

    @Query("DELETE FROM resources")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM resources")
    fun getCount(): Int
}
package info.covid.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import info.covid.data.enities.Tag

@Dao
interface FilterDao {


    @Query("SELECT DISTINCT r.state as name FROM resources r")
    fun getStatesAndUT(): LiveData<List<Tag>>

    @Query("SELECT DISTINCT r.category as name FROM resources r WHERE r.city=:city")
    fun getCategories(city: String): LiveData<List<Tag>>

    @Query("SELECT DISTINCT r.city as name FROM resources r WHERE r.state =:state")
    fun getCities(state: String): LiveData<List<Tag>>

}
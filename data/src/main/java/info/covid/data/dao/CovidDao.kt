package info.covid.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.covid.data.enities.CovidDayInfo
import info.covid.data.enities.KeyValues
import info.covid.data.enities.State

@Dao
interface CovidDao {

  @Query("SELECT * FROM COVID_INFO_DAY")
  fun getInfo() : LiveData<List<CovidDayInfo>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(result: List<CovidDayInfo>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entry: CovidDayInfo)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entry: KeyValues)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertStateWise(result: List<State>)

  @Query("SELECT * FROM STATE_WISE where state = 'Total'")
  fun getTotalCount() : LiveData<List<State>>

  @Query("SELECT * FROM STATE_WISE where state != 'Total'")
  fun getStates() : LiveData<List<State>>

  @Query("SELECT * FROM STATE_WISE")
  fun getStatesWithTotal() : LiveData<List<State>>

  @Query("SELECT * FROM STATE_WISE where state = 'Total'")
  fun getToday() : LiveData<List<State>>

    @Query("SELECT * FROM STATE_WISE where state = 'Total'")
    fun getTotal(): LiveData<State>

    @Query("SELECT * FROM STATE_WISE where state =:state")
    fun getState(state: String): LiveData<State>
}
package info.covid.data.repositories

import info.covid.data.dao.FilterDao
import info.covid.data.dao.ResourcesDao
import info.covid.data.enities.Resources
import info.covid.data.network.EssentialsApiService
import info.covid.data.network.RetrofitClient


class EssentialsRepository(
    private val resourceDao: ResourcesDao,
    private val filterDao: FilterDao,
    private val apiService: EssentialsApiService
) {

    suspend fun <T> getResources(success: (List<Resources>?) -> T, error: (String) -> T) {
        try {
            val resp = apiService.getResources()
            if (resp.isSuccessful) {
                success(resp.body()?.resources ?: emptyList())
            } else error("Oops, Something went wrong")
        } catch (e: Exception) {
            error("Oops, Something went wrong.")
        }
    }

    fun getStatesAndUT() = filterDao.getStatesAndUT()
    fun getCities(state: String) = filterDao.getCities(state)
    fun getCategories(city: String) = filterDao.getCategories(city)

    fun getLocalResources(state: String?, city: String?) = resourceDao.getResources(state, city)
    fun getLocalResources(state: String?, city: String?, categories: List<String>?) =
        resourceDao.getResources(state, city, categories)

    fun getCount() = resourceDao.getCount()
    suspend fun clearAll() {
        resourceDao.clearAll()
    }

    suspend fun insert(it: List<Resources>) {
        resourceDao.insert(it)
    }
}
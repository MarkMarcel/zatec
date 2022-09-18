package com.freelancemarcel.zatec.houses.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.freelancemarcel.zatec.houses.models.HouseListItem

class HouseDataSource(private val houseRepository: HouseRepository) :
    PagingSource<String, HouseListItem>() {

    companion object{
        fun nextPage(currentPage:String): String {
            val regex = """page=(\d++)""".toRegex()
            val page = regex.find(currentPage)!!.groupValues[0].split("=")[1]
            val nextPage = "page=${(page.toInt() + 1)}"
            return currentPage.replace(regex,nextPage)
        }

        fun prevPage(currentPage:String): String {
            val regex = """page=(\d++)""".toRegex()
            val page = regex.find(currentPage)!!.groupValues[0].split("=")[1]
            val nextPage = "page=${(page.toInt() - 1)}"
            return currentPage.replace(regex,nextPage)
        }
    }

    private val firstPageLink = "https://www.anapioficeandfire.com/api/houses?page=1&pageSize=20"

    override fun getRefreshKey(state: PagingState<String, HouseListItem>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            return if(anchorPage?.prevKey != null)
                nextPage(anchorPage.prevKey!!)
            else prevPage(anchorPage?.nextKey!!)
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, HouseListItem> {
        val pageUrl = params.key ?: firstPageLink
        return houseRepository.getHouses(pageUrl).fold(
            { error ->
                LoadResult.Error(error)
            },
            { houseData ->
                LoadResult.Page(
                    data = houseData.items,
                    nextKey = houseData.next,
                    prevKey = houseData.prev
                )
            },
        )
    }
}
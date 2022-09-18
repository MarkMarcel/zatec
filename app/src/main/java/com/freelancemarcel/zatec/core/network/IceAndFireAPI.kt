package com.freelancemarcel.zatec.core.network

import com.freelancemarcel.zatec.houses.HouseListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface IceAndFireAPI {
    data class Pagination(val next: String? = null, val prev: String? = null)

    companion object {
        fun parsePaginationHeader(headers: okhttp3.Headers): Pagination {
            var pagination = Pagination()
            val paginationLinks = headers["link"]
            paginationLinks?.let {
                val regex = """<(.*)>; *rel="(next|prev)"""".toRegex()
                val matchedResults = regex.findAll(it)
                matchedResults.forEach { matchResult ->
                    val (link,type) = matchResult.destructured
                    if(type.lowercase() == "next"){
                        pagination = pagination.copy(next = link)
                    }
                    if(type.lowercase() == "prev"){
                        pagination = pagination.copy(prev = link)
                    }
                }
            }
            return pagination
        }
    }

    @GET
    suspend fun getHouses(@Url page: String): Response<List<HouseListItem>>
}
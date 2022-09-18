package com.freelancemarcel.zatec.houses

import com.freelancemarcel.zatec.core.models.Either
import com.freelancemarcel.zatec.core.models.PaginatedList
import com.freelancemarcel.zatec.core.network.IceAndFireAPI

class HouseRepository(private val iceAndFireAPI: IceAndFireAPI) {
    suspend fun getHouses(pageUrl: String): Either<Error, PaginatedList<String, HouseListItem>> {
        val apiResponse = iceAndFireAPI.getHouses(pageUrl)
        return if (apiResponse.isSuccessful) {
            val pagination = IceAndFireAPI.parsePaginationHeader(apiResponse.headers())
            val result = PaginatedList(
                items = apiResponse.body()!!,
                next = pagination.next,
                prev = pagination.prev
            )
            Either.Right(result)
        } else {
            Either.Left(Error(ErrorType.LOAD_FAILURE_HOUSES))
        }
    }
}
package com.freelancemarcel.gotown.houses.data

import com.freelancemarcel.gotown.core.models.Either
import com.freelancemarcel.gotown.core.models.Error
import com.freelancemarcel.gotown.core.models.ErrorType
import com.freelancemarcel.gotown.core.models.PaginatedList
import com.freelancemarcel.gotown.core.network.IceAndFireAPI
import com.freelancemarcel.gotown.houses.models.House
import com.freelancemarcel.gotown.houses.models.HouseListItem
import java.lang.Exception

class HouseRepository(private val iceAndFireAPI: IceAndFireAPI) {
    suspend fun getHouse(houseUrl:String):Either<Error,House>{
        return try {
            val apiResponse = iceAndFireAPI.getHouse(houseUrl)
            if(apiResponse.isSuccessful){
                Either.Right(apiResponse.body()!!)
            }else{
                Either.Left(Error(ErrorType.LOAD_FAILURE_HOUSE))
            }
        }catch (e:Exception){
            Either.Left(Error(ErrorType.LOAD_FAILURE_NETWORK))
        }
    }

    suspend fun getHouses(pageUrl: String): Either<Error, PaginatedList<String, HouseListItem>> {
        return try {
            val apiResponse = iceAndFireAPI.getHouses(pageUrl)
            if (apiResponse.isSuccessful) {
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
        }catch (e:Exception){
            Either.Left(Error(ErrorType.LOAD_FAILURE_NETWORK))
        }

    }
}
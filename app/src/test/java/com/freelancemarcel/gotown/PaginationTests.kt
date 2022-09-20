package com.freelancemarcel.gotown

import com.freelancemarcel.gotown.houses.data.HouseDataSource
import org.junit.Assert
import org.junit.Test

class PaginationTests {
    @Test
    fun nextPage_isCorrect() {
        val currentPage = "https://www.anapioficeandfire.com/api/houses?page=2&pageSize=10"
        val expected = "https://www.anapioficeandfire.com/api/houses?page=3&pageSize=10"
        Assert.assertEquals(expected, HouseDataSource.nextPage(currentPage))
    }
    @Test
    fun prevPage_isCorrect() {
        val currentPage = "https://www.anapioficeandfire.com/api/houses?page=2&pageSize=10"
        val expected = "https://www.anapioficeandfire.com/api/houses?page=1&pageSize=10"
        Assert.assertEquals(expected, HouseDataSource.prevPage(currentPage))
    }
}
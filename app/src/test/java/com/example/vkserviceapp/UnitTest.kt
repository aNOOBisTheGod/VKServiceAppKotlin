package com.example.vkserviceapp

import api.Api
import org.junit.Assert.assertEquals
import org.junit.Test
import pojo.Request
import pojo.Service


class UnitTest {
    @Test
    fun api_test() {
        val api = Api()
        val request = api.fetchData()
        assertEquals(Request::class.simpleName, request::class.simpleName)
        assert(request.items.isNotEmpty()) { "No items in request" }
        assertEquals(Service::class.simpleName, request.items[0]::class.simpleName)
    }
}
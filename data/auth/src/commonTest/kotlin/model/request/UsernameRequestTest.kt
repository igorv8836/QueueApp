package model.request

import com.example.auth.model.request.UsernameRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UsernameRequestTest {
    @Test
    fun `test serialization to JSON`() {
        val request = UsernameRequest(username = "testuser")
        val json = Json.encodeToString(request)
        val expectedJson = """{"username":"testuser"}"""
        assertEquals(expectedJson, json)
    }

    @Test
    fun `test deserialization from JSON`() {
        val json = """{"username":"testuser"}"""
        val request = Json.decodeFromString<UsernameRequest>(json)
        assertEquals("testuser", request.username)
    }
}
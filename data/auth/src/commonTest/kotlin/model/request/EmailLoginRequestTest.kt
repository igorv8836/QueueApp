package model.request

import com.example.auth.model.request.EmailLoginRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class EmailLoginRequestTest {

    @Test
    fun `test serialization to JSON`() {
        val request = EmailLoginRequest(email = "test@example.com", password = "securePassword")
        val json = Json.encodeToString(request)
        val expectedJson = """{"email":"test@example.com","password":"securePassword"}"""

        assertEquals(expectedJson, json)
    }

    @Test
    fun `test deserialization from JSON`() {
        val json = """{"email":"test@example.com","password":"securePassword"}"""
        val request = Json.decodeFromString<EmailLoginRequest>(json)

        assertEquals("test@example.com", request.email)
        assertEquals("securePassword", request.password)
    }
}

package model.request

import com.example.auth.model.request.EmailRegisterRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class EmailRegisterRequestTest {
    @Test
    fun `test serialization to JSON`() {
        val request = EmailRegisterRequest(
            email = "newuser@example.com",
            password = "newPassword",
            username = "test"
        )
        val json = Json.encodeToString(request)
        val expectedJson =
            """{"email":"newuser@example.com","password":"newPassword","username":"test"}"""
        assertEquals(expectedJson, json)
    }

    @Test
    fun `test deserialization from JSON`() {
        val json = """{"email":"newuser@example.com","password":"newPassword", "username":"test"}"""
        val request = Json.decodeFromString<EmailRegisterRequest>(json)
        assertEquals("newuser@example.com", request.email)
        assertEquals("newPassword", request.password)
        assertEquals("test", request.username)
    }
}
package model.request

import com.example.auth.model.request.PasswordResetRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PasswordResetRequestTest {
    @Test
    fun `test serialization to JSON`() {
        val request = PasswordResetRequest(
            email = "reset@example.com",
            code = 123456,
            newPassword = "newPass"
        )
        val json = Json.encodeToString(request)
        val expectedJson = """{"email":"reset@example.com","code":123456,"newPassword":"newPass"}"""
        assertEquals(expectedJson, json)
    }

    @Test
    fun `test deserialization from JSON`() {
        val json = """{"email":"reset@example.com","code":123456,"newPassword":"newPass"}"""
        val request = Json.decodeFromString<PasswordResetRequest>(json)
        assertEquals("reset@example.com", request.email)
        assertEquals(123456, request.code)
        assertEquals("newPass", request.newPassword)
    }
}
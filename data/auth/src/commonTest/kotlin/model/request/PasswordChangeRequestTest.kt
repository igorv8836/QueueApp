package model.request

import com.example.auth.model.request.PasswordChangeRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PasswordChangeRequestTest {
    @Test
    fun `test serialization to JSON`() {
        val request = PasswordChangeRequest(
            email = "user@example.com",
            oldPassword = "oldPass",
            newPassword = "newPass"
        )
        val json = Json.encodeToString(request)
        val expectedJson =
            """{"email":"user@example.com","oldPassword":"oldPass","newPassword":"newPass"}"""
        assertEquals(expectedJson, json)
    }

    @Test
    fun `test deserialization from JSON`() {
        val json =
            """{"email":"user@example.com","oldPassword":"oldPass","newPassword":"newPass"}"""
        val request = Json.decodeFromString<PasswordChangeRequest>(json)
        assertEquals("user@example.com", request.email)
        assertEquals("oldPass", request.oldPassword)
        assertEquals("newPass", request.newPassword)
    }
}
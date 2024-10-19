package model.request

import com.example.auth.model.request.SendingResetCodeRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class SendingResetCodeRequestTest {
    @Test
    fun `test serialization to JSON`() {
        val request = SendingResetCodeRequest(email = "code@example.com")
        val json = Json.encodeToString(request)
        val expectedJson = """{"email":"code@example.com"}"""
        assertEquals(expectedJson, json)
    }

    @Test
    fun `test deserialization from JSON`() {
        val json = """{"email":"code@example.com"}"""
        val request = Json.decodeFromString<SendingResetCodeRequest>(json)
        assertEquals("code@example.com", request.email)
    }
}
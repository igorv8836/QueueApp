package network

import com.example.auth.model.request.EmailLoginRequest
import com.example.auth.model.request.EmailRegisterRequest
import com.example.auth.model.request.PasswordChangeRequest
import com.example.auth.model.request.PasswordResetRequest
import com.example.auth.model.request.SendingResetCodeRequest
import com.example.auth.model.request.UsernameRequest
import com.example.auth.network.AuthApiService
import com.example.common.MyResult
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandler
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AuthApiServiceTest {

    private fun createMockClient(handler: MockRequestHandler): HttpClient {
        return HttpClient(MockEngine(handler)) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    explicitNulls = false
                })
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                url {
                    protocol = URLProtocol.HTTPS
                }
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens("test token", "")
                    }
                }
            }
        }
    }

    private suspend fun testApiCall(
        endpoint: String,
        requestBody: Any?,
        responseJson: String,
        status: HttpStatusCode,
        expectedResult: Boolean,
        expectedMessage: String
    ) {
        val mockClient = createMockClient { request ->
            assertEquals(endpoint, request.url.encodedPath)
            respond(
                responseJson,
                status,
                headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val service = AuthApiService(mockClient)
        val response = when (requestBody) {
            is PasswordChangeRequest -> service.changePassword(requestBody)
            is EmailRegisterRequest -> service.signUp(requestBody)
            is EmailLoginRequest -> service.login(requestBody)
            is PasswordResetRequest -> service.resetPassword(requestBody)
            is SendingResetCodeRequest -> service.sendEmailResetCode(requestBody)
            is UsernameRequest -> service.isUsernameExisted(requestBody)
            null -> service.getUserInfo()
            else -> throw IllegalArgumentException("Unsupported request type")
        }

        if (expectedResult) {
            assertTrue(response is MyResult.Success)
        } else {
            assertTrue(response is MyResult.Error)
            val errorMessage = response.exception.message
            assertEquals(expectedMessage, errorMessage)
        }
    }

    @Test
    fun `test changePassword`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/change_password",
            requestBody = PasswordChangeRequest("user@example.com", "oldPassword", "newPassword"),
            responseJson = """{"success":true, "message":"Password changed successfully"}""",
            status = HttpStatusCode.OK,
            expectedResult = true,
            expectedMessage = "Password changed successfully"
        )
    }

    @Test
    fun `test changePassword 400 http`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/change_password",
            requestBody = PasswordChangeRequest("user@example.com", "oldPassword", "newPassword"),
            responseJson = """{"success":false, "message":"error"}""",
            status = HttpStatusCode.BadRequest,
            expectedResult = false,
            expectedMessage = "BadRequest: error"
        )
    }

    @Test
    fun `test signUp`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/email_signup",
            requestBody = EmailRegisterRequest("newuser@example.com", "password123", "name"),
            responseJson = """{"success":true, "message":"Account created successfully"}""",
            status = HttpStatusCode.OK,
            expectedResult = true,
            expectedMessage = "Account created successfully"
        )
    }

    @Test
    fun `test signUp 409 http`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/email_signup",
            requestBody = EmailRegisterRequest("newuser@example.com", "password123", "name"),
            responseJson = """{"success":false, "message":"error"}""",
            status = HttpStatusCode.Conflict,
            expectedResult = false,
            expectedMessage = "Conflict: error"
        )
    }

    @Test
    fun `test getUserInfo`() = runBlocking {
        val mockClient = createMockClient { request ->
            assertEquals("/api/v1/auth/get-user-info", request.url.encodedPath)
            val authHeader = request.headers[HttpHeaders.Authorization]
            assertEquals("Bearer test token", authHeader)
            respond(
                """{"email":"user@example.com", "username":"user123", "photoUrl":"", "notificationEnabled":true, "isActive":true, "banReason":null}""",
                HttpStatusCode.OK,
                headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val service = AuthApiService(mockClient)
        val response = service.getUserInfo()

        assertTrue(response is MyResult.Success)
        val userInfo = response.data
        assertEquals("user@example.com", userInfo.email)
        assertEquals("user123", userInfo.username)
    }

    @Test
    fun `test getUserInfo 400 http`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/get-user-info",
            requestBody = null,
            responseJson = """{"success":false, "message":"error"}""",
            status = HttpStatusCode.BadRequest,
            expectedResult = false,
            expectedMessage = "BadRequest: error"
        )
    }

    @Test
    fun `test isUsernameExisted`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/is_username_existed",
            requestBody = UsernameRequest("user123"),
            responseJson = """{"success":true, "isExisted":true}""",
            status = HttpStatusCode.OK,
            expectedResult = true,
            expectedMessage = "true"
        )
    }

    @Test
    fun `test login`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/login",
            requestBody = EmailLoginRequest("user@example.com", "password"),
            responseJson = """{"success":true, "message":"token"}""",
            status = HttpStatusCode.OK,
            expectedResult = true,
            expectedMessage = "token"
        )
    }

    @Test
    fun `test login 400 http`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/login",
            requestBody = EmailLoginRequest("user@example.com", "password"),
            responseJson = """{"success":false, "message":"error"}""",
            status = HttpStatusCode.BadRequest,
            expectedResult = false,
            expectedMessage = "BadRequest: error"
        )
    }

    @Test
    fun `test resetPassword`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/password_reset",
            requestBody = PasswordResetRequest("user@example.com", 212, "newPassword"),
            responseJson = """{"success":true, "message":"Password reset successful"}""",
            status = HttpStatusCode.OK,
            expectedResult = true,
            expectedMessage = "Password reset successful"
        )
    }

    @Test
    fun `test resetPassword 400 http`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/password_reset",
            requestBody = PasswordResetRequest("user@example.com", 212, "newPassword"),
            responseJson = """{"success":false, "message":"error"}""",
            status = HttpStatusCode.BadRequest,
            expectedResult = false,
            expectedMessage = "BadRequest: error"
        )
    }

    @Test
    fun `test sendEmailResetCode`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/send_password_reset_code",
            requestBody = SendingResetCodeRequest("user@example.com"),
            responseJson = """{"success":true, "message":"Reset code sent"}""",
            status = HttpStatusCode.OK,
            expectedResult = true,
            expectedMessage = "Reset code sent"
        )
    }

    @Test
    fun `test sendEmailResetCode 400 http`() = runBlocking {
        testApiCall(
            endpoint = "/api/v1/auth/send_password_reset_code",
            requestBody = SendingResetCodeRequest("user@example.com"),
            responseJson = """{"success":false, "message":"error"}""",
            status = HttpStatusCode.BadRequest,
            expectedResult = false,
            expectedMessage = "BadRequest: error"
        )
    }
}

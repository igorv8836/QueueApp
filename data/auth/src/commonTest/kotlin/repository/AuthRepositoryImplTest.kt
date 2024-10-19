package repository

import com.example.auth.network.RemoteDataSource
import com.example.auth.repository.AuthRepositoryImpl
import com.example.common.MyResult
import com.example.datastore.TokenManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import network.AuthApiServiceMock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AuthRepositoryImplTest {

    private lateinit var authRepository: AuthRepositoryImpl
    private val api: RemoteDataSource = AuthApiServiceMock()
    private val tokenManager: TokenManager = mockk()

    @BeforeTest
    fun setUp() {
        authRepository = AuthRepositoryImpl(api, tokenManager)
    }

    @Test
    fun `signUp should return success and save token when API call is successful`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val username = "testuser"

        coEvery { tokenManager.saveToken(any()) } returns Unit

        val result = authRepository.signUp(email, password, username)

        assertTrue(result is MyResult.Success)
        coVerify { tokenManager.saveToken(any()) }
    }

    @Test
    fun `login should return success and save token when API call is successful`() = runBlocking {
        val email = "test@example.com"
        val password = "password"

        coEvery { tokenManager.saveToken(any()) } returns Unit

        val result = authRepository.login(email, password)

        assertTrue(result is MyResult.Success)
        coVerify { tokenManager.saveToken(any()) }
    }

    @Test
    fun `getToken should return token from TokenManager`() = runBlocking {
        val token = "sampleToken"
        every { tokenManager.getToken() } returns flowOf(token)

        val result = authRepository.getToken()

        result.collect { assertEquals(token, it) }
        verify { tokenManager.getToken() }
    }

    @Test
    fun `logout should remove token and return success`() = runBlocking {
        coEvery { tokenManager.removeToken() } returns Unit

        val result = authRepository.logout()

        assertTrue(result is MyResult.Success)
        coVerify { tokenManager.removeToken() }
    }

    @Test
    fun `sendResetCode should return success when API call is successful`() = runBlocking {
        val email = "test@example.com"

        val result = authRepository.sendResetCode(email)

        assertTrue(result is MyResult.Success)
        assertEquals("success", result.data)
    }

    @Test
    fun `resetPassword should return success when API call is successful`() = runBlocking {
        val email = "test@example.com"
        val resetCode = 1234
        val newPassword = "newPassword"

        val result = authRepository.resetPassword(email, resetCode, newPassword)

        assertTrue(result is MyResult.Success)
        assertEquals("success", result.data)
    }

    @Test
    fun `changePassword should return success when API call is successful`() = runBlocking {
        val email = "test@example.com"
        val oldPassword = "oldPassword"
        val newPassword = "newPassword"

        val result = authRepository.changePassword(email, oldPassword, newPassword)

        assertTrue(result is MyResult.Success)
        assertEquals("success", result.data)
    }
}

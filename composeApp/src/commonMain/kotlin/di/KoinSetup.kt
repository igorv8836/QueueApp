package di

import com.example.auth.authDataModule
import com.example.auth.di.authFeatureModule
import com.example.common.commonModule
import com.example.database.databaseModule
import com.example.database.platformDatabaseBuilderModule
import com.example.datastore.createDataStoreModule
import com.example.datastore.datastoreModule
import com.example.network.di.networkModule
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            commonModule(),
            platformDatabaseBuilderModule(),
            databaseModule(),
            networkModule(),
            createDataStoreModule(),
            datastoreModule(),
            authDataModule(),
            authFeatureModule()
        )

    }

object KoinFactory {
    private var di: Koin? = null

    fun setupKoin(appDeclaration: KoinAppDeclaration = {}) {
        if (di == null) {
            di = initKoin(appDeclaration).koin
        }
    }

    fun getDI(): Koin {
        return di ?: run {
            setupKoin()
            di ?: throw IllegalStateException("Koin is not initialized")
        }
    }
}
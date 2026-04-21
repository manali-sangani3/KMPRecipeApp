import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


object HttpClientProvider {

    val client = HttpClient {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    logDebug(message)
                }
            }
            level = LogLevel.ALL
        }
//        install(HttpSend) {
//            intercept { request ->
//                SessionManager.accessToken?.let { token ->
//                    request.headers.append("Authorization", "Bearer $token")
//                }
//                execute(request)
//            }
//        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
        }
    }
}

expect fun logDebug(message: String)
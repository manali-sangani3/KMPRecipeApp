import com.russhwolf.settings.Settings

object SessionManager {

    private val settings = Settings()

    var accessToken: String? = null

    fun isLoggedIn(): Boolean {
        return !accessToken.isNullOrEmpty()
    }

    fun saveToken(token: String) {
        accessToken = token
        settings.putString("TOKEN", token)
    }

    fun loadToken() {
        accessToken = settings.getStringOrNull("TOKEN")
    }

    fun logout() {
        accessToken = null
        settings.remove("TOKEN")
    }
}
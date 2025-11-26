package worksout.com.moduleapi.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    var secret: String = "",
    var accessTokenExpiration: Long = 3600000, // 1시간
    var refreshTokenExpiration: Long = 604800000 // 1주일

) {
    val secretKey: String get() = secret
}
package worksout.com.moduleapi.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date
import java.util.Properties
import javax.crypto.SecretKey
import javax.swing.text.StyledEditorKit.BoldAction

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
){
    private fun getSigningKey(): SecretKey{
        return Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8))
    }

    //Access Token 생성
    fun generateAccessToken(userId: Long, email: String? = null, authorities: Collection<String> = listOf("ROLE_USER")): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtProperties.accessTokenExpiration)

        return Jwts.builder()
            .subject(userId.toString())
            .claim("authorities", authorities.joinToString(","))
            .claim("tokenType", "ACCESS")
            .apply {
                email?.let { claim("email", it) }
            }
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getSigningKey())
            .compact()
    }

    //Refresh Token 생성
    fun generateRefreshToken(userId: Long): String{
        val now = Date()
        val expiryDate = Date(now.time + jwtProperties.refreshTokenExpiration)

        return Jwts.builder()
            .subject(userId.toString())
            .claim("tokenType", "REFRESH")
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getSigningKey())
            .compact()
    }

    //헤더에서 토큰 추출
    fun extractToken(request: HttpServletRequest): String?{
        val bearerToken = request.getHeader("Authorization")
        return if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            bearerToken.substring(7)
        }else{
            null
        }
    }

    //JWT 토큰에서 userId 추출
    fun getUserIsdFromToken(token: String): String?{
        return try {
            val claims = getClaims(token)
            claims.subject
        }catch (e: Exception){
            null
        }
    }

    //Refresh Token 유효성 검증
    fun validateRefreshToken(token: String): Boolean{
        return try{
            val claims = getClaims(token)
            val tokenType = claims.get("tokenTpye", String::class.java)
            tokenType == "REFRESH" && claims.expiration.after(Date())
        }catch (e: Exception) {
            false
        }
    }

    //Access Token 유효성 검증
    fun validateAccessToken(token: String): Boolean{
        return try{
            val claims = getClaims(token)
            val tokenType = claims.get("tokenType", String::class.java)
            tokenType == "ACCESS" && claims.expiration.after(Date())
        }catch (e: Exception){
            false
        }
    }

    private fun getClaims(token: String): Claims{
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

}
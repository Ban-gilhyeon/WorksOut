package worksout.com.moduleapi.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken(
    private val userId: Long,
    authorities: Collection<GrantedAuthority>
): AbstractAuthenticationToken(authorities) {
    private val credentials: String? = null

    init{
        super.setAuthenticated(true)
    }

    override fun getCredentials(): Any = userId

    override fun getPrincipal(): Any?  = credentials
}
package worksout.com.moduleauth.exception

import worksout.com.modulecommon.exception.BaseException
import worksout.com.modulecommon.exception.ErrorCode

class AuthException(
    errorCode: ErrorCode,
    detail: Map<String, Any?>? = null,
    message: String? = null
):BaseException(
    errorCode = errorCode,
    detail = detail,
    message = message ?: errorCode.message
)
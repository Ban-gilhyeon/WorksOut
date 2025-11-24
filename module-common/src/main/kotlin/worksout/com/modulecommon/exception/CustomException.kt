package worksout.com.modulecommon.exception

import javax.annotation.processing.Messager

abstract class CustomException(
    val errorType: BaseErrorType,
    customMessage: String? = null
): RuntimeException(customMessage ?: errorType.message) {
    abstract fun readResolve(): Any

    val status: Int
        get() = errorType.status
}
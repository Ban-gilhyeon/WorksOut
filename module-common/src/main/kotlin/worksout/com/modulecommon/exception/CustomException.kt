package worksout.com.modulecommon.exception

abstract class CustomException(
    val errorCode: BaseErrorCode,
    private val sourceLayer: String? = null,
):RuntimeException() {

    val status: Int get() = errorCode.errorReason.status
}
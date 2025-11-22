package worksout.com.modulecommon.exception

enum class GlobalException(
    private val status: Int,
    private val errorCode: String,
    private val message: String,
): BaseErrorCode {

}
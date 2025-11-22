package worksout.com.modulecommon.exception

data class ErrorReason(
    val status: Int,
    val errorCode: String,
    val message: String,
) {
    companion object{
        operator fun invoke(status: Int, errorCode: String, message: String): ErrorReason {
            return ErrorReason(status, errorCode, message)
        }
    }
}
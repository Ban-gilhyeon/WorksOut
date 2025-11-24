package worksout.com.modulecommon.exception

interface BaseErrorType {
    val status: Int
    val errorCode: String
    val message: String
}
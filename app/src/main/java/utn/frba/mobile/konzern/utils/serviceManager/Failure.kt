package utn.frba.mobile.konzern.utils.serviceManager

data class Failure(val intStringIdMessage: Int? = null,
                   val intStringIdTitle: Int? = null,
                   val stringTitle: String? = null,
                   val stringMessage: String? = null) {

    lateinit var failureType: FailureType
    var failureContinuation: (() -> Any)? = null
}


enum class FailureType(val code: String) {
    NO_FAILURE("0"),
    CONNECTION("1"),
    WARNING("2"),
    SERVER_ERROR("SERVER_ERROR"),
    UNKNOWN("")
}
package utn.frba.mobile.konzern.posts.viewModel

data class Failure(val intStringIdMessage: Int? = null,
                   val intStringIdTitle: Int? = null,
                   val stringTitle: String? = null,
                   val stringMessage: String? = null) {

    lateinit var failureType: FailureType
    var failureContinuation: (() -> Any)? = null

}


enum class FailureType(val code: String) {
    NO_FAILURE("0"),
    RES_1("1"),
    RES_2("2"),
    RES_3("3"),
    RES_4("4"),
    RES_5("5"),
    RES_6("6"),
    RES_7("7"),
    RES_8("8"),
    RES_9("9"),
    RES_10("10"),
    RES_11("11"),
    RES_12("12"),
    WARNING("-1"),
    SERVER_ERROR("SERVER_ERROR"),
    UNKNOWN(""),
    CHECK_VERSION_FAILURE("")
}
package utn.frba.mobile.konzern.utils.serviceManager

open class ControlledException(val failureType: FailureType,
                               val titleStringResource: Int? = null,
                               val messageStringResource: Int? = null,
                               val titleString: String? = null,
                               val messageString: String? = null,
                               ex: Exception? = null) : RuntimeException(ex) {

    fun isWarning(): Boolean = this.failureType == FailureType.WARNING

}

fun ControlledException.toFailure(): Failure {
    val failure = Failure(
        this.messageStringResource,
        this.titleStringResource,
        this.titleString,
        this.messageString
    )
    failure.failureType = this.failureType
    return failure
}
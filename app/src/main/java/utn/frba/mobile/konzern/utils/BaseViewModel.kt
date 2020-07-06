package utn.frba.mobile.konzern.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.utils.serviceManager.*

open class BaseViewModel: ViewModel() {
    private var coRoutinesJob = Job()
    private val coRoutinesScope = CoroutineScope(Dispatchers.Main + this.coRoutinesJob)

    private val _failure =
        SingleLiveEvent<Failure>()
    val failure: LiveData<Failure> get() = _failure

    private val showProgress = MutableLiveData<Boolean>()
    val showProgressIndicator: LiveData<Boolean> get() = showProgress

    fun launchControlledInBg(
        mainOperation: suspend ViewModel.() -> Unit,
        continuation: () -> Any = {},
        customHandlingProgress: Boolean = false
    ) {
        this.coRoutinesScope.launch {
            try {
                startProgressBar()
                mainOperation()
                continuation()
            } catch (t: ControlledException) {
                handleControlledException(t, continuation)
            } catch (e: Exception) {
                handleException(e)
            }
            finally {
                if(!customHandlingProgress)
                    hideProgressBar()
            }
        }
    }

    protected fun startProgressBar(){
        showProgress.value = true
    }

    protected fun hideProgressBar(){
        showProgress.value = false
    }

    private fun handleException(e: Exception) {
        val failure =
            Failure(intStringIdTitle = R.string.error)
        failure.failureType = FailureType.UNKNOWN
        this._failure.value = failure

        e.fillInStackTrace()
    }

    private fun handleControlledException(exception: ControlledException, continuation: () -> Any) {
        val failure = exception.toFailure()
        if(exception.isWarning()) {
            failure.failureContinuation = continuation
        }
        this._failure.value = failure
    }
}
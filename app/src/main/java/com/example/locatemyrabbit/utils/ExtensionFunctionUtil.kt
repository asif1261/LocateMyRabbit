package com.example.locatemyrabbit.utils

import android.graphics.Color
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.locatemyrabbit.R
import com.example.locatemyrabbit.network.Resources
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*

//Function for Show and Hide Items
fun View.visibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

//Function For Enable or Disable Items
fun View.enable(enable: Boolean) {
    isEnabled = enable
    alpha = if (enable) 1f else 0.5f
}

/*Preventing Double clicked debouncing problem*/
fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

//Delay searchView TextView EditText
fun TextInputEditText.afterTextChangedDebounce(delayMillis: Long, input: (String) -> Unit) {
    var lastInput = ""
    var debounceJob: Job? = null
    val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            if (editable != null) {
                val newtInput = editable.toString()
                debounceJob?.cancel()
                if (lastInput != newtInput) {
                    lastInput = newtInput
                    debounceJob = uiScope.launch {
                        delay(delayMillis)
                        if (lastInput == newtInput) {
                            input(newtInput)
                        }
                    }
                }
            }
        }

        override fun beforeTextChanged(cs: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {}
    })}

//Function for Showing Snackbar Toast message
fun View.snackBar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }

    val colorSnack = ContextCompat.getColor(context, R.color.colorError)

    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(colorSnack)
    val textView = snackBarView.findViewById(
        com.google.android.material.R.id.snackbar_text
    ) as TextView

    textView.setTextColor(Color.WHITE)
    textView.textSize = 14f

    snackBar.setActionTextColor(ContextCompat.getColor(context, R.color.white))
    snackBar.show()
}

//Function for Handling Api Error counter when fetching Network Data
fun Fragment.handleApiError(
    failure: Resources.Failure,
    errorMessage: String? = null,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackBar(
            CHECK_YOUR_CONNECTION
        )

        failure.errorCode == 302 -> {
            requireView().snackBar(
                ERROR_CODE_302_MESSAGE,
                retry
            )
        }

        failure.errorCode == 200 -> {
            requireView().snackBar(
                SUCCESSFUL
            )
        }

        failure.errorCode == 401 -> {
            requireView().snackBar(
                ERROR_CODE_401_MESSAGE,
                retry
            )
        }

        failure.errorCode == 406 -> {
            requireView().snackBar(
                ERROR_CODE_406_MESSAGE,
                retry
            )
        }

        failure.errorCode == 400 -> {
            requireView().snackBar(
                ERROR_CODE_400_MESSAGE,
                retry
            )
        }

        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackBar(error, retry)
        }
    }
}
package com.naufal.mal.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun EditText.debounceSearch(lifecycle: Lifecycle, onDebouncingQueryTextChange: (String) -> Unit) {
    val debouncePeriod: Long = 500

    val coroutineScope = lifecycle.coroutineScope

    var searchJob: Job? = null

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            searchJob?.cancel()
            searchJob = coroutineScope.launch {
                s.toString().let {
                    delay(debouncePeriod)
                    onDebouncingQueryTextChange(s.toString())
                }
            }
        }

        override fun afterTextChanged(editable: Editable?) {
        }
    })
}

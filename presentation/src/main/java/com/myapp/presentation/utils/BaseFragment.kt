package com.myapp.presentation.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import timber.log.Timber

/**
 * Fragment基盤
 */
abstract class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        Timber.tag(this.javaClass.simpleName)
            .d("▼▼▼▼▼▼▼▼▼▼▼▼lifecycle▼▼▼▼▼▼▼▼▼▼▼▼▼")
        Timber.tag(this.javaClass.simpleName)
            .d(">>>>[lifecycle] onAttach is called")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(this.javaClass.simpleName)
            .d(">>>>>>>>[lifecycle] onCreate is called")
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        Timber.tag(this.javaClass.simpleName)
            .d(">>>>>>>>>>>>[lifecycle] onViewCreated is called")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        Timber.tag(this.javaClass.simpleName)
            .d(">>>>>>>>>>>>>>>> [lifecycle] onStart is called")
        super.onStart()
    }

    override fun onResume() {
        Timber.tag(this.javaClass.simpleName)
            .d(">>>>>>>>>>>>>>>>>>>>[lifecycle] onResume is called")
        super.onResume()
    }

    override fun onPause() {
        Timber.tag(this.javaClass.simpleName)
            .d("<<<<<<<<<<<<<<<<<<<<[lifecycle] onPause is called")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.tag(this.javaClass.simpleName)
            .d("<<<<<<<<<<<<<<<<[lifecycle] onSaveInstanceState is called")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Timber.tag(this.javaClass.simpleName)
            .d("<<<<<<<<<<<<<<<<[lifecycle] onStop is called")
        super.onStop()
    }

    override fun onDestroyView() {
        Timber.tag(this.javaClass.simpleName)
            .d("<<<<<<<<<<<<[lifecycle] onDestroyView is called")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.tag(this.javaClass.simpleName)
            .d("<<<<<<<<[lifecycle] onDestroy is called")
        super.onDestroy()
    }

    override fun onDetach() {
        Timber.tag(this.javaClass.simpleName)
            .d("<<<<[lifecycle] onDetach is called")
        Timber.tag(this.javaClass.simpleName)
            .d("▲▲▲▲▲▲▲▲▲▲▲▲lifecycle▲▲▲▲▲▲▲▲▲▲▲▲▲")
        super.onDetach()
    }
}

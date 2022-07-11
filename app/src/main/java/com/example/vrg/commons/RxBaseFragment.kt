package com.example.vrg.commons

import android.support.v4.app.Fragment
import rx.subscriptions.CompositeSubscription

open class RxBaseFragment: Fragment() {
    protected var subcriptions = CompositeSubscription()

    override fun onResume() {
        super.onResume()
        subcriptions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        if (!subcriptions.isUnsubscribed) {
            subcriptions.unsubscribe()
        }
        subcriptions.clear()
    }

}
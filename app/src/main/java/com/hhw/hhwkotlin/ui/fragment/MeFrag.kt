package com.hhw.hhwkotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hhw.hhwkotlin.R
import com.hhw.hhwkotlin.ui.base.BaseFrag

/**
 * Created by Joe.
 */
class MeFrag : BaseFrag() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_me, container, false)
    }
}
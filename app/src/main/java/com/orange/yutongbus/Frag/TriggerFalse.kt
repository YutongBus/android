package com.orange.yutongbus.Frag


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orange.blelibrary.blelibrary.RootFragement

import com.orange.yutongbus.R
import kotlinx.android.synthetic.main.trigger_success.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TriggerFalse : RootFragement() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       rootview=inflater.inflate(R.layout.fragment_trigger_false, container, false)
        rootview.exit.setOnClickListener {
act.supportFragmentManager.popBackStack(null,1)
        }
        rootview.keepgoing.setOnClickListener {
            act.GoBack()
        }
        return rootview
    }


}

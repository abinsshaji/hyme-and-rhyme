package yep.com.handr.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_rhyme.*
import kotlinx.android.synthetic.main.fragment_rhyme.view.*

import yep.com.handr.R
import yep.com.handr.utils.Rxbus
import yep.com.handr.viemodels.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RhymeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RhymeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RhymeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var viewModel: MainViewModel? = null
    private val disposable= CompositeDisposable()
    private var i = 1
    private var rxbus=Rxbus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_rhyme, container, false)
        setData(1, 2)
        v.b_next_r.setOnClickListener({
            setData(++i, 1)
            Log.e("message","in rhyme next")
        })
        v.b_prev_r.setOnClickListener {
            setData(--i, 0)
        }
        v.swipe_refresh_r.setOnRefreshListener {
            swipe_refresh_r.isRefreshing=true
            setData(tv_number_r.text.toString().toInt(),2)
        }


        lsiteners()
        // Inflate the layout for this fragment
        return v

    }
    fun setData(pos: Int, type: Int) {

        disposable.add(viewModel?.getSingleRhyme(pos)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
        !!.subscribe({ t ->
            tv_content_r.text = t.content
            tv_number_r.text = t.number.toString()
            swipe_refresh_r.isRefreshing=false
        }, {
            swipe_refresh_r.isRefreshing=false

            when (type) {
                1 -> i--
                0 -> i++


            }


        }
        )

        )
    }

    fun lsiteners()
    {
        disposable.add(rxbus.listen()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it is String)
                    {
                        if (it.contentEquals("search"))
                        {
                            Log.e("mesasage","search")

                        }
                    }
                }))
    }




    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RhymeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                RhymeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}

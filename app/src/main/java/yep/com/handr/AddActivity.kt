package yep.com.handr

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import yep.com.handr.room.Hyme
import yep.com.handr.room.Rhyme
import yep.com.handr.viemodels.MainViewModel
import java.util.concurrent.Callable

class AddActivity : AppCompatActivity() {


    var disposable = CompositeDisposable()
    var viewModel: MainViewModel? = null
    var selected_type:String?=null
    var  is_rhyme:Boolean=false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        fab.setOnClickListener { view ->
            savetoDb(view);

        }
        setupToolbar()
        setupSpinner()
        getInsertedData()

        find.setOnClickListener {
            if (!number.text.isEmpty())
            {
                content.text.clear()
                getSingleContent(number.text.toString().toInt())


            }
        }


    }
    private fun getSingleContent(numb:Int)
    {
        if (is_rhyme)
        {
            disposable.add(viewModel?.getSingleRhyme(numb)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.map { t: Rhyme -> t }
                    !!.subscribe({ t ->
                        content.setText(t.content)

                    },{

            }))
        }
        else
        {
            disposable.add(viewModel?.getSingleHyme(numb)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.map { t: Hyme -> t }
            !!.subscribe({ t ->
                content.setText(t.content)

            },{

            }))

        }
    }

    private fun savetoDb(view:View) {
        if (!number.text.isEmpty() && !content.text.isEmpty()) {
            if (is_rhyme)
            {
                val rhyme = Rhyme(number = number.text.toString().toInt(), content = content.text.toString())

                disposable.add(Single.fromCallable({
                    viewModel?.insertRhymeDb(rhyme)
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(Consumer {
                            Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG)
                                    .setAction("Ok", {  }).show()

                        }))

            }
            else
            {
                val hyme = Hyme(number = number.text.toString().toInt(), content = content.text.toString())

                disposable.add(Single.fromCallable({
                    viewModel?.insertHymeDb(hyme)
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(Consumer {
                            Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG)
                                    .setAction("Ok", {  }).show()

                        }))

            }



        }

    }

    private fun getInsertedData() {
        disposable.add(viewModel?.getAllRhymeDb()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.map { t -> t }
        !!.subscribe{
            for (rhyme in it) {
                Log.e("messgae", rhyme.content)
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }


    fun setupToolbar() {
        toolbar.title = "Add Content"
        toolbar.navigationIcon = ActivityCompat.getDrawable(this, R.drawable.ic_arrow_back_24dp)
        setSupportActionBar(toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun setupSpinner() {
        val types = arrayOf("Hymes", "Rhymes")


        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types)
        spinner.adapter = arrayAdapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {


            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                 selected_type = p0?.getItemAtPosition(p2).toString()
                is_rhyme = selected_type.equals("Rhymes")
                Log.e("message", selected_type)

                number.text.clear()
                content.text.clear()

            }
        }


    }




}

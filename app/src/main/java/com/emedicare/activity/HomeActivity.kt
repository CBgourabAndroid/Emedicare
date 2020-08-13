package com.emedicare.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.MainActivity
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.adapter.MainCatAdapter
import com.emedicare.responceModel.Record
import com.emedicare.responceModel.SpecialityResponce
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class HomeActivity : MainActivity() {

    lateinit var recyclerViewCategory: RecyclerView
    lateinit var categoryAdapter: MainCatAdapter
    lateinit var progressDialog : ProgressDialog
    //lateinit var searchtollbar:Toolbar
   // lateinit var search_menu: Menu
    //lateinit var item_search: MenuItem
    lateinit var search_view:SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.home_activity, container)

        progressDialog= ProgressDialog(this)
        titleTv.text="Specialities"

        recyclerViewCategory=findViewById(R.id.category_view)
        search_view=findViewById(R.id.searchView)


        if(!PostInterface.isConnected(this@HomeActivity)){

            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
        else{

            progressDialog.setMessage("Please wait ...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            callDataApi()

        }
        setSearchtollbar()
    }

    override fun onResume() {
        super.onResume()
        val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(search_view.windowToken, 0)
        search_view.clearFocus()
    }

    override fun onStart() {
        super.onStart()
        val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(search_view.windowToken, 0)
        search_view.clearFocus()
    }

    override fun onStop() {
        super.onStop()
        val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(search_view.windowToken, 0)
        search_view.clearFocus()
    }

    private fun setSearchtollbar() {

        // search hint
        search_view.setQueryHint("Search Specialities")
        search_view.onActionViewExpanded()
        search_view.clearFocus()

        //search_view.setHin
        //search_view.setTextColor(Color.DKGRAY)

        val id = search_view.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        val textView =  search_view.findViewById<TextView>(id)
        textView.setTextColor(Color.DKGRAY);

        val btnid = search_view.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        val btnV =  search_view.findViewById<ImageView>(btnid)
        btnV.setOnClickListener {

            val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(search_view.windowToken, 0)
            search_view.setQuery("",false)
            search_view.onActionViewExpanded()
            search_view.clearFocus()
        }

        //val searchText = search_view.findViewById<EditText>(R.id.search_src_text)
        //searchText.hint = "Search Specialities"
       // searchText.setHintTextColor(Color.DKGRAY)
        //searchText.setTextColor(Color.DKGRAY)

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (newText.length==0){
                    val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(search_view.windowToken, 0)
                    search_view.clearFocus()
                }
                categoryAdapter.filter(newText)
                return true
            }
        })

        val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(search_view.windowToken, 0)



    }


    fun initSearchView() {


        // Enable/Disable Submit button in the keyboard
        search_view.isSubmitButtonEnabled = false
        // Change search close button image
        val closeButton =
            search_view.findViewById(R.id.search_close_btn) as ImageView
        closeButton.setImageResource(R.drawable.ic_clear_black_24dp)
        // set hint and the text colors
        val txtSearch =
            search_view.findViewById(R.id.search_src_text) as EditText
        txtSearch.hint = "Search Specialities"
        txtSearch.setHintTextColor(Color.DKGRAY)
        txtSearch.setTextColor(Color.DKGRAY)
        // set the cursor
        val searchTextView =
            search_view.findViewById(R.id.search_src_text) as AutoCompleteTextView
        try {
            val mCursorDrawableRes =
                TextView::class.java.getDeclaredField("mCursorDrawableRes")
            mCursorDrawableRes.isAccessible = true
            mCursorDrawableRes[searchTextView] =
                R.drawable.search_cursor //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (e: Exception) {
            e.printStackTrace()
        }
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                categoryAdapter.filter(newText)
                return true
            }
        })
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun circleReveal(
        viewID: Int,
        posFromRight: Int,
        containsOverflow: Boolean,
        isShow: Boolean
    ) {
        val myView: View = findViewById(viewID)
        var width = myView.width
        if (posFromRight > 0) width -= posFromRight * resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) - resources.getDimensionPixelSize(
            R.dimen.abc_action_button_min_width_material
        ) / 2
        if (containsOverflow) width -= resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material)
        val cx = width
        val cy = myView.height / 2
        val anim: Animator
        anim = if (isShow) ViewAnimationUtils.createCircularReveal(
            myView,
            cx,
            cy,
            0f,
            width.toFloat()
        ) else ViewAnimationUtils.createCircularReveal(myView, cx, cy, width.toFloat(), 0f)
        anim.duration = 330.toLong()
        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (!isShow) {
                    super.onAnimationEnd(animation)
                   // myView.visibility = View.INVISIBLE
                }
            }
        })
        // make the view visible and start the animation
        if (isShow) myView.visibility = View.VISIBLE
        // start the animation
        anim.start()
    }

    private fun callDataApi() {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.getSpeciality().enqueue(object :
            Callback<SpecialityResponce> {
            override fun onResponse(call: Call<SpecialityResponce>, response: Response<SpecialityResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {


                    progressDialog.dismiss()
                    categoryAdapter = MainCatAdapter(this@HomeActivity)
                    recyclerViewCategory.layoutManager = GridLayoutManager(applicationContext,2) as RecyclerView.LayoutManager?
                    recyclerViewCategory.adapter = categoryAdapter
                    categoryAdapter.setDataListItems(this@HomeActivity,
                        response.body()!!.records as ArrayList<Record>
                    )




                } else {
                    progressDialog.dismiss()
                    Log.i(
                        "onEmptyResponse",
                        "Returned empty response"
                    );//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    Toast.makeText(applicationContext, "No Data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SpecialityResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })


    }
}
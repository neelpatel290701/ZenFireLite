package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForFireInspectorList
import com.example.zenfirelite.adapters.AdapterForInspectionForm
import com.example.zenfirelite.adapters.PageAdapterForInspectionInfo
import com.example.zenfirelite.databinding.FragmentInspectionInfoBinding
import com.example.zenfirelite.datamodels.InspectionInfoFormModel
import com.example.zenfirelite.datamodels.InspectionInfoModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Locale

@Suppress("DEPRECATION")
class InspectionInfo : Fragment() {

    private lateinit var binding : FragmentInspectionInfoBinding
    private lateinit var navController: NavController
    val args : InspectionInfoArgs by navArgs()
    private val tabTitles = arrayListOf("Forms","Deficiency")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inspectionInfo : InspectionInfoModel = args.inspectionInfo
        // Set the title in the toolbar
        requireActivity().title = inspectionInfo.InspectionNumber
        binding = FragmentInspectionInfoBinding.inflate(inflater,container,false)

        binding.customerName.text = inspectionInfo.CustomerName.toString()
        binding.InsStartDateValue.text = inspectionInfo.InsStartDate
        binding.InsStartTimeValue.text = inspectionInfo.InsStartTime

        binding.location.setOnClickListener {
            val uri :  String = "http://maps.google.com/maps?saddr="
            val intent : Intent  = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        val fireInspectorList = ArrayList<String>()
        for (i in 1..10) {
            fireInspectorList.add("Neel Patel")
            fireInspectorList.add("Smit Patel")
            fireInspectorList.add("Kuldeep Tripathi")
        }

        clickOnFireInspectorDropDownMenu(binding.fireInspector , fireInspectorList)

        binding.insInfoViewPager.adapter = PageAdapterForInspectionInfo(this)
        TabLayoutMediator(binding.insInfoTabLayout , binding.insInfoViewPager){tab,position->
//              tab.text = tabTitles[position]
              val customView = LayoutInflater.from(binding.insInfoTabLayout.context).
              inflate(R.layout.inspectioninfo_tabtitle, null) as TextView
              customView.text = tabTitles[position]
              tab.customView = customView
        }.attach()

//        for(i in 0..2){
//            val textView = LayoutInflater.from(requireContext()).inflate(R.layout.inspectioninfo_tabtitle,null) as TextView
//            binding.insInfoTabLayout.getTabAt(i)?.customView = textView
//        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.topLayout.visibility = View.VISIBLE
    }

    private fun clickOnFireInspectorDropDownMenu(fireInspector : TextView , fireInspectorList:ArrayList<String>) {
        fireInspector.setOnClickListener{

            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.fireinspector_dialogspinner)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

            val dialogWidth = WindowManager.LayoutParams.MATCH_PARENT
            val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(650,800)

            val location = IntArray(2)
            binding.fireInspector.getLocationOnScreen(location)
            val x = location[0]-20
            val y = location[1] + binding.fireInspector.height - 140

            dialog.window?.setGravity(Gravity.TOP or Gravity.START)
            dialog.window?.attributes?.x = x
            dialog.window?.attributes?.y = y

            val fireInspectorRecyclerView = dialog.findViewById<RecyclerView>(R.id.fireInspectorRecycleView)
            val fireInspectorSearchView = dialog.findViewById<EditText>(R.id.searchview)
            fireInspectorRecyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = AdapterForFireInspectorList(fireInspectorList,binding.fireInspector,dialog)
            fireInspectorRecyclerView.adapter = adapter

            dialog.show()


            fireInspectorSearchView.addTextChangedListener(object: TextWatcher {
                @SuppressLint("NotifyDataSetChanged")
                override fun afterTextChanged(s: Editable?) {

                    if(!s.toString().isEmpty()) {
                        val tempArrayList = ArrayList<String>()
                        for (value in fireInspectorList) {
                            Log.d("neel", s.toString())
                            if (value.toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT).trim())) tempArrayList.add(value)
                        }
                        val newAdapter = AdapterForFireInspectorList(tempArrayList,binding.fireInspector,dialog)
                        fireInspectorRecyclerView.adapter = newAdapter
                        newAdapter.notifyDataSetChanged()
                    }else{
                        val fireInspectoradapter = AdapterForFireInspectorList(fireInspectorList,binding.fireInspector,dialog)
                        fireInspectorRecyclerView.adapter = fireInspectoradapter
                        adapter.notifyDataSetChanged()
                    }

                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

        }
    }

}
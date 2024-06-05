package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForFireInspectorList
import com.example.zenfirelite.adapters.AdapterForFormTemplatesList
import com.example.zenfirelite.adapters.AdapterForInspectionForm
import com.example.zenfirelite.adapters.AdapterForPreviousFormList
import com.example.zenfirelite.databinding.FragmentInspectionInfoFormListBinding
import com.example.zenfirelite.datamodels.InspectionInfoFormModel
import com.example.zenfirelite.interfaces.OnItemClickListenerForFormTemplateItem
import com.google.android.material.internal.ViewUtils.showKeyboard
import java.util.Locale


class InspectionInfoFormList : Fragment() , OnItemClickListenerForFormTemplateItem {
    private lateinit var binding : FragmentInspectionInfoFormListBinding
    private lateinit var view : View
    private lateinit var parentTopLinearLayout : LinearLayout
    private var parentFragment: Fragment? = null
//    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    @SuppressLint("ClickableViewAccessibility", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInspectionInfoFormListBinding.inflate(inflater,container,false)

        val formInspectionList = ArrayList<InspectionInfoFormModel>()
        for (i in 1..10) {
            formInspectionList.add(
                InspectionInfoFormModel(
                "Inspection of IND Fire Suppression System",
                "Neel Patel",
                "07/05/2024",
                "12:00AM")
            )
            formInspectionList.add(
                InspectionInfoFormModel(
                "Off Road Vehicle Sysytem Inspection",
                "Kuldeep Tripathi",
                "07/05/2024",
                "12:00PM")
            )
        }

        val adapter = AdapterForInspectionForm(formInspectionList)
        binding.formsRecycleView.layoutManager = LinearLayoutManager(context)
        binding.formsRecycleView.adapter = adapter


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        navController = Navigation.findNavController(view)
        this.view = view

        binding.addForm.setOnClickListener{
            OpenFormList()
        }

        parentFragment  = requireParentFragment()
        when (parentFragment) {
            is InspectionInfo -> {
                val parentView = (parentFragment as InspectionInfo).requireView()
                parentTopLinearLayout = parentView.findViewById<LinearLayout>(R.id.insInfo_TopLayout)
            }
            is CustomerDetails -> {
                binding.addForm.visibility =View.GONE
                val parentView = (parentFragment as CustomerDetails).requireView()
                parentTopLinearLayout = parentView.findViewById<LinearLayout>(R.id.cusDetail_TopLayout)
            }
        }

        binding.searchForm.setOnClickListener {
            parentTopLinearLayout.visibility = View.GONE
        }


        binding.searchForm.addTextChangedListener(object: TextWatcher {
            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                parentTopLinearLayout.visibility = View.GONE
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    private fun OpenFormList() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_addform_list)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialogeboxbackground)

        val dialogWidth = WindowManager.LayoutParams.MATCH_PARENT
        val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(900, 1300)

        dialog.window?.setGravity(Gravity.CENTER)

        val formTemplatesList = ArrayList<String>()
        for (i in 1..20) {
            formTemplatesList.add("1-Fire Sprinkler inspection report(combo)")
            formTemplatesList.add("Alarm Inspection & Testing Form ")
            formTemplatesList.add("Backflow Assembly test form")
        }

        val formTemplatesRecycleView = dialog.findViewById<RecyclerView>(R.id.formTemplatesRecycleView)
        val previousFormsRecycleView = dialog.findViewById<RecyclerView>(R.id.previousFormsRecycleView)
        val FormTemplate = dialog.findViewById<TextView>(R.id.formtemplates)
        val PreviousForm = dialog.findViewById<TextView>(R.id.previousForm)
        var FormTemplateflag = true
        FormTemplate.setOnClickListener{
            if(!FormTemplateflag) {
                formTemplatesRecycleView.visibility = View.VISIBLE
            }else{
                formTemplatesRecycleView.visibility = View.GONE
            }
            FormTemplateflag = !FormTemplateflag
        }

        var PreviousFormflag = true
        PreviousForm.setOnClickListener{
            if(!PreviousFormflag) {
                previousFormsRecycleView.visibility = View.VISIBLE
            }else{
                previousFormsRecycleView.visibility = View.GONE
            }
            PreviousFormflag = !PreviousFormflag
        }
        formTemplatesRecycleView .layoutManager = LinearLayoutManager(requireContext())
        previousFormsRecycleView .layoutManager = LinearLayoutManager(requireContext())
        val formTemplatesAdapter = AdapterForFormTemplatesList(formTemplatesList , this , dialog)
        val previousFormAdapter = AdapterForPreviousFormList(formTemplatesList)
        formTemplatesRecycleView.adapter = formTemplatesAdapter
        previousFormsRecycleView.adapter = previousFormAdapter
        dialog.show()
    }

    override fun onFormTemplateClick(item: String) {
        val action = InspectionInfoDirections.actionInspectionInfoToFormDetails2(item,0)
        val navController = Navigation.findNavController(requireParentFragment().requireView())
        navController.navigate(action)

    }


}


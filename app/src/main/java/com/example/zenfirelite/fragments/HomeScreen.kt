package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForCustomerList
import com.example.zenfirelite.adapters.AdapterForInspectionList
import com.example.zenfirelite.databinding.FragmentHomeScreenBinding
import com.example.zenfirelite.adapters.AdapterForFormTemplatesList
import com.example.zenfirelite.datamodels.InspectionListModel
import com.example.zenfirelite.utils.ZTUtils
import com.example.zenfirelite.utils.ZTUtils.getDateInMillis
import com.example.zenfirelite.viewmodels.CustomerListViewModel
import com.example.zenfirelite.viewmodels.FormTemplatesListViewModel
import com.example.zenfirelite.viewmodels.HomeViewModel


@Suppress("DEPRECATION")
class HomeScreen : Fragment(){

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var navController: NavController

    private var screenWidth: Int = 0

    private val viewModel: HomeViewModel by viewModels()
    private val customerListviewModel: CustomerListViewModel by viewModels()
    private val formTemplatesListViewModel: FormTemplatesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        // Get screen width
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        Log.d("neel", "onCreate()-HomeScreen")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.startDate.setOnClickListener {
            ZTUtils.openCalenderPicker(
                binding.startDate,
                null,
                binding.toDate.getDateInMillis(),
                requireContext()
            )
        }
        binding.toDate.setOnClickListener {
            ZTUtils.openCalenderPicker(
                binding.toDate,
                binding.startDate.getDateInMillis(),
                null,
                context = requireContext()
            )
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        requireActivity().title = ""
        setHasOptionsMenu(true)

        binding.inspectionrecyclerview.layoutManager = LinearLayoutManager(context)

        viewModel.inspectionList.observe(viewLifecycleOwner, Observer { inspectionDetails ->
            if (inspectionDetails != null) {
                setRecyclerView(inspectionDetails)
            }else{
                Toast.makeText(requireContext(), "InspectionList-Null", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    private fun setRecyclerView(inspectionDetails: List<InspectionListModel>) {
        val adapter =
            context?.let {
                AdapterForInspectionList(it, screenWidth, inspectionDetails) { InspectionModel ->
                    val action = HomeScreenDirections.actionHomeScreenToInspectionInfo(
                        InspectionModel
                    )
                    navController.navigate(action)
                }
            }
        binding.inspectionrecyclerview.adapter = adapter
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbaritems, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItemAddCustomer = menu.findItem(R.id.addCustomerOnHomepage)
        val menuItemReload = menu.findItem(R.id.reload)
        menuItemAddCustomer.isVisible = true
        menuItemReload.isVisible = true
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addCustomerOnHomepage -> {
                clickOnAddCustomerOnHomePage()
            }
            else -> false
        }
    }

    private fun clickOnAddCustomerOnHomePage(): Boolean {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_customer_list)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialogeboxbackground)

        val dialogWidth = WindowManager.LayoutParams.MATCH_PARENT
        val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(900, 1300)

        dialog.window?.setGravity(Gravity.CENTER)


        val customerRecycleView = dialog.findViewById<RecyclerView>(R.id.customerRecycleView)
        customerRecycleView.layoutManager = LinearLayoutManager(requireContext())

        customerListviewModel.customerList.observe(viewLifecycleOwner, Observer { customerList ->
            if (customerList != null) {
                val customeradapter = AdapterForCustomerList(customerList, requireContext(), true)
                { customerDetail ->
                    dialog.dismiss()
                    onclickCustomerNameOpenFormTemplatesList()
                }
                customerRecycleView.adapter = customeradapter
            }else{
                Toast.makeText(requireContext(), "CustomerList-Null", Toast.LENGTH_SHORT).show()
            }
        })

        val addCustomer = dialog.findViewById<TextView>(R.id.addCustomer)

        addCustomer.setOnClickListener {
            navController.navigate(R.id.addCustomerDetails)
            dialog.dismiss()
        }

        dialog.show()
        return true
    }

    private fun onclickCustomerNameOpenFormTemplatesList() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.formtemplates_athomepage)
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

        val formTemplatesRecycleView =
            dialog.findViewById<RecyclerView>(R.id.formTemplatesRecycleView_homepage)
        val cancelDialogBox = dialog.findViewById<TextView>(R.id.cancelDialogBox)
        formTemplatesRecycleView.layoutManager = LinearLayoutManager(requireContext())


//        val formTemplatesAdapter = AdapterForFormTemplatesList(formTemplatesList, this, dialog)
//        formTemplatesRecycleView.adapter = formTemplatesAdapter

        formTemplatesListViewModel.formTemplatesList.observe(viewLifecycleOwner, Observer { formsList ->
            if ( formsList!= null) {
                val formTemplatesAdapter = AdapterForFormTemplatesList(formsList,dialog){
                    formDetails,formName ->
                    val action = HomeScreenDirections.actionHomeScreenToFormDetails(formDetails,formName, 0)
                    navController.navigate(action)
                }
                formTemplatesRecycleView.adapter = formTemplatesAdapter
            }else{
                Toast.makeText(requireContext(), "FormTemplatesList-Null", Toast.LENGTH_SHORT).show()
            }
        })

        cancelDialogBox.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}
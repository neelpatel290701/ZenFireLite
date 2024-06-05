package com.example.zenfirelite.fragments

import android.app.DatePickerDialog
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
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.activities.MainActivity
import com.example.zenfirelite.adapters.AdapterForCustomerList
import com.example.zenfirelite.adapters.AdapterForInspectionList
import com.example.zenfirelite.databinding.FragmentHomeScreenBinding
import com.example.zenfirelite.datamodels.InspectionInfoModel
import com.example.zenfirelite.interfaces.OnItemClickListenerForInspectionItem
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.appcompat.widget.Toolbar
import com.example.zenfirelite.adapters.AdapterForFormTemplatesList
import com.example.zenfirelite.adapters.AdapterForPreviousFormList
import com.example.zenfirelite.interfaces.OnItemClickListenerForFormTemplateItem


@Suppress("DEPRECATION")
class HomeScreen : Fragment() , OnItemClickListenerForInspectionItem ,
    OnItemClickListenerForFormTemplateItem {

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var navController: NavController

//    val viewBinding = requireActivity().findViewById<Toolbar>(R.id.toolbar)

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
        // Inflate the layout for this fragment
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        requireActivity().title = ""
        setHasOptionsMenu(true)

        // Get screen width
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        binding.startDate.setOnClickListener {
            openCalenderPicker(binding.startDate, null, binding.toDate.getDateInMillis())
        }
        binding.toDate.setOnClickListener {
            openCalenderPicker(binding.toDate, binding.startDate.getDateInMillis())
        }

        binding.inspectionrecyclerview.layoutManager = LinearLayoutManager(context)
        val inspectionList = ArrayList<InspectionInfoModel>()
        for (i in 1..5) {
            inspectionList.add(InspectionInfoModel(
                "#00123",
                "Neel Patel" ,
                "Completed",
                "0",
                "2",
                "05/02/2024",
                "05/03/2024",
                "12:00PM",
                "12:00PM",
                "Kuldeep Tripathi"
            ))
            inspectionList.add(InspectionInfoModel(
                "#00124",
                "Kuldeep Tripathi" ,
                "In Process",
                "1",
                "3",
                "05/02/2024",
                "05/10/2024",
                "12:00PM",
                "12:00PM",
                "Neel Patel"
            ))
            inspectionList.add(InspectionInfoModel(
                "#00125",
                "Dhruv Pathak" ,
                "Pending",
                "2",
                "3",
                "05/02/2024",
                "05/10/2024",
                "12:00PM",
                "12:00PM",
                "Neel Patel"
            ))
        }
        val adapter = context?.let { AdapterForInspectionList(it, screenWidth , inspectionList ,this) }
        binding.inspectionrecyclerview.adapter = adapter

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbaritems, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        // Hide or show menu items as needed
        val menuItem_addCustomer= menu.findItem(R.id.addCustomerOnHomepage)
        val menuItem_reload= menu.findItem(R.id.reload)
        menuItem_addCustomer.isVisible = true // or false, depending on your condition
        menuItem_reload.isVisible = true
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when (item.itemId) {
            R.id.addCustomerOnHomepage -> {
                OnItemClickListenerForAddCustomerOnHomePage()
            }
            else -> false
        }
    }


    private fun openCalenderPicker(
        dateValue: EditText,
        minDate: Long? = null,
        maxDate: Long? = null
    ) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog: DatePickerDialog? = null
        datePickerDialog = context?.let { it1 ->
            DatePickerDialog(
                it1,
                R.style.MyDatePickerDialogStyle,
                { view, year, monthOfYear, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, monthOfYear, dayOfMonth)
                    val selectedDate = calendar.time
                    val monthName = DateFormatSymbols().shortMonths[monthOfYear]
                    val date = "$dayOfMonth-$monthName-$year"
                    dateValue.setText(date)

                    if (dateValue.id == R.id.toDate) {
                        if (minDate != null) {
                            datePickerDialog?.datePicker?.minDate = minDate
                        }
                    } else if (dateValue.id == R.id.startDate) {
                        if (maxDate != null) {
                            datePickerDialog?.datePicker?.maxDate = maxDate
                        }
                    }
                },
                year,
                month,
                day
            )
        }
        if (minDate != null && datePickerDialog != null) {
            datePickerDialog.datePicker.minDate = minDate
        }
        if (maxDate != null && datePickerDialog != null) {
            datePickerDialog.datePicker.maxDate = maxDate
        }
        datePickerDialog?.show()
    }
    fun EditText.getDateInMillis(): Long? {
        val text = this.text.toString()
        if (text.isNotEmpty()) {
            val sdf = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            val cal = Calendar.getInstance()
            cal.time = sdf.parse(text)
            return cal.timeInMillis
        }
        return null
    }

    override fun onItemClick(item: InspectionInfoModel) {
            val action = HomeScreenDirections.actionHomeScreenToInspectionInfo(item)
            navController.navigate(action)
    }

    private fun OnItemClickListenerForAddCustomerOnHomePage() : Boolean {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_customer_list)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialogeboxbackground)

        val dialogWidth = WindowManager.LayoutParams.MATCH_PARENT
        val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(900, 1300)

        dialog.window?.setGravity(Gravity.CENTER)

        val customerList = ArrayList<String>()
        for (i in 1..10) {
            customerList.add("Neel Patel (1234)")
            customerList.add("Smit Patel (5678)")
            customerList.add("Kuldeep Tripathi (9012)")
            customerList.add("Dhruv Pathak (34567)")
        }

        val customerRecycleView = dialog.findViewById<RecyclerView>(R.id.customerRecycleView)
        customerRecycleView .layoutManager = LinearLayoutManager(requireContext())
        val adapter = AdapterForCustomerList(customerList,requireContext(),true) { customerName, customerId ->
            dialog.dismiss()
            onclickCustomerNameOpenFormTemplatesList(customerName, customerId)
        }

        customerRecycleView .adapter = adapter

        val addCustomer = dialog.findViewById<TextView>(R.id.addCustomer)

        addCustomer.setOnClickListener{
            navController.navigate(R.id.addCustomerDetails)
            dialog.dismiss()
        }

        dialog.show()
        return true
    }

    private fun onclickCustomerNameOpenFormTemplatesList(customerName: String, customerId: String) {
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

        val formTemplatesRecycleView = dialog.findViewById<RecyclerView>(R.id.formTemplatesRecycleView_homepage)
        val cancelDialogBox = dialog.findViewById<TextView>(R.id.cancelDialogBox)
        formTemplatesRecycleView .layoutManager = LinearLayoutManager(requireContext())
        val formTemplatesAdapter = AdapterForFormTemplatesList(formTemplatesList, this , dialog)
        formTemplatesRecycleView.adapter = formTemplatesAdapter

        cancelDialogBox.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onFormTemplateClick(item: String) {
        val action = HomeScreenDirections.actionHomeScreenToFormDetails(item,0)
        navController.navigate(action)
    }


}
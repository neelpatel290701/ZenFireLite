package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
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
import androidx.annotation.RequiresApi
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
import com.example.zenfirelite.datamodels.InspectionListModel
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.zenfirelite.adapters.AdapterForFormTemplatesList
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.CustomerListRequestBody
import com.example.zenfirelite.apis.datamodels.CustomerListResponse
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_RequestBody
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_Response
import com.example.zenfirelite.apis.datamodels.InspectionListRequestBody
import com.example.zenfirelite.apis.datamodels.InspectionListResponse
import com.example.zenfirelite.apis.datamodels.SortBy
import com.example.zenfirelite.datamodels.CustomerListModel
import com.example.zenfirelite.interfaces.OnItemClickListenerForFormTemplateItem
import com.example.zenfirelite.prefs
import com.example.zenfirelite.utils.ZTUtils
import com.example.zenfirelite.viewmodels.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Suppress("DEPRECATION")
class HomeScreen : Fragment(),OnItemClickListenerForFormTemplateItem {

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var navController: NavController
    private var customerList = ArrayList<CustomerListModel>()
    private var screenWidth : Int = 0

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        // Get screen width
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        Log.d("neel","onCreate()-HomeScreen")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.startDate.setOnClickListener {
            openCalenderPicker(binding.startDate, null, binding.toDate.getDateInMillis())
        }
        binding.toDate.setOnClickListener {
            openCalenderPicker(binding.toDate, binding.startDate.getDateInMillis())
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

        Log.d("neel","onCreateView()-HomeScreen")
        binding.inspectionrecyclerview.layoutManager = LinearLayoutManager(context)

        viewModel.inspectionList.observe(viewLifecycleOwner, Observer { items ->
            if (items != null) {
                val adapter =
                context?.let { AdapterForInspectionList(it, screenWidth, items, ){ InspectionModel ->
                    val action = HomeScreenDirections.actionHomeScreenToInspectionInfo(InspectionModel)
                    navController.navigate(action)
                } }
                binding.inspectionrecyclerview.adapter = adapter
            }
        })

        return binding.root
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

//    override fun onItemClick(item: InspectionListModel) {
//        val action = HomeScreenDirections.actionHomeScreenToInspectionInfo(item)
//        navController.navigate(action)
//    }

    private fun OnItemClickListenerForAddCustomerOnHomePage(): Boolean {
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

        if(customerList.isEmpty()){
            fetchCustomerList(customerRecycleView,dialog)
        }else{
            val customeradapter = AdapterForCustomerList(
                customerList,
                requireContext(),
                true
            ) { CustomerDetail->
                dialog.dismiss()
                onclickCustomerNameOpenFormTemplatesList()
            }
            customerRecycleView.adapter = customeradapter
        }


        val addCustomer = dialog.findViewById<TextView>(R.id.addCustomer)

        addCustomer.setOnClickListener {
            navController.navigate(R.id.addCustomerDetails)
            dialog.dismiss()
        }

        dialog.show()
        return true
    }

    private fun fetchCustomerList(customerRecycleView:RecyclerView,dialog:Dialog) {

        val customerListRequestModel2 = CustomerList_ServiceBilling_RequestBody(
            0,50,"","",false,false,true
        )

        APIManager.apiInterface.getCustomerListWithBillingService(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
            customerListRequestModel2
        ).enqueue(object : Callback<CustomerList_ServiceBilling_Response>{
            override fun onResponse(
                call: Call<CustomerList_ServiceBilling_Response>,
                response: Response<CustomerList_ServiceBilling_Response>
            ) {
                val customerListResponse = response.body()
                val custList = customerListResponse?.result?.data?.hits?.flatMap{hit->
                    hit.serviceAddresses.map{ serviceAddress ->
                        CustomerListModel(
                            firstName = serviceAddress.firstname,
                            lastName = serviceAddress.lastname,
                            customerUniqueId = serviceAddress.customerUniqueId,
                            addressLine1 = serviceAddress.addressLine1,
                            addressLine2 = serviceAddress.addressLine2 ?: "", // Handle nullable field
                            city = serviceAddress.city,
                            state = serviceAddress.state,
                            zipCode = serviceAddress.zipcode,
                            email = serviceAddress.email,
                            cellPhone = serviceAddress.cellphone,
                            landline = serviceAddress.landline
                        )
                    }
                }?.toCollection(ArrayList())

                if(custList!=null){
                    customerList = custList
                }

                val customeradapter = AdapterForCustomerList(
                    customerList,
                    requireContext(),
                    true
                ) { CustomerDetail->
                    dialog.dismiss()
                    onclickCustomerNameOpenFormTemplatesList()
                }
                customerRecycleView.adapter = customeradapter

            }

            override fun onFailure(call: Call<CustomerList_ServiceBilling_Response>, t: Throwable) {
                Log.d("neel", "CustomerList_HomePage-onFailure")
                t.printStackTrace()
            }
        })



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
        val formTemplatesAdapter = AdapterForFormTemplatesList(formTemplatesList, this, dialog)
        formTemplatesRecycleView.adapter = formTemplatesAdapter

        cancelDialogBox.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onFormTemplateClick(item: String) {
        val action = HomeScreenDirections.actionHomeScreenToFormDetails(item, 0)
        navController.navigate(action)
    }


}
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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForCustomerList
import com.example.zenfirelite.adapters.AdapterForInspectionList
import com.example.zenfirelite.databinding.FragmentHomeScreenBinding
import com.example.zenfirelite.datamodels.InspectionInfoModel
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.zenfirelite.adapters.AdapterForFormTemplatesList
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.InspectionListRequestBody
import com.example.zenfirelite.apis.datamodels.InspectionListResponse
import com.example.zenfirelite.apis.datamodels.SortBy
import com.example.zenfirelite.interfaces.OnItemClickListenerForFormTemplateItem
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
    private var inspectionList = ArrayList<InspectionInfoModel>()
    private var screenWidth : Int = 0

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

//        val adapter =
//            context?.let { AdapterForInspectionList(it, screenWidth, inspectionList, ){
//                Log.d("neel",it.toString())
//                val action = HomeScreenDirections.actionHomeScreenToInspectionInfo(it)
//                navController.navigate(action)
//            } }
//
//        binding.inspectionrecyclerview.adapter = adapter
//        binding.inspectionrecyclerview.adapter?.notifyDataSetChanged()
        if(inspectionList.isEmpty()) {
            fetchInspectionList()
            Log.d("neel", "InspectionList-Empty")
        }else{
            val adapter =
                context?.let { AdapterForInspectionList(it, screenWidth, inspectionList, ){ it ->
                    val action = HomeScreenDirections.actionHomeScreenToInspectionInfo(it)
                    navController.navigate(action)
                } }

            binding.inspectionrecyclerview.adapter = adapter
            binding.inspectionrecyclerview.adapter?.notifyDataSetChanged()

            Log.d("neel", "InspectionList-Not Empty")
        }

        return binding.root
    }

    private fun fetchInspectionList() {
        val inspectionListRequestModel = InspectionListRequestBody(SortBy("desc"))
        val accessToken: String =
            "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsInRva2VuVHlwZSI6ImFjY2Vzcy10b2tlbiJ9.eyJ1c2VySWQiOjcyMTksImNvbXBhbnlJZCI6NDczLCJyb2xlSWQiOjI0MDAsInJvbGVOYW1lIjoiVGVjaG5pY2lhbiIsInByb2ZpbGVOYW1lIjoiVGVjaG5pY2lhbiIsInBsYXRmb3JtQXBwIjoiWkVORklSRV9MSVRFIiwiaWF0IjoxNzE3NzU0NTAxLCJleHAiOjE3MTc4NDA5MDF9.F2xSqU0uEnP85YgNZYOLqdMKPvkAQFPrg61hQafJbjR6Ta2VUSaRlPQ1ALPePOJFmLoxPE5-2mp83D9txj9shRG-4DL3BQhq6uedE_rCOvPX2xEowT8YFeRTfy2dw2LSFB9nDFs949pjucV9VKS9dBPnfacdtEXn4vBNe-U7ej8"
        APIManager.apiInterface.inspectionList(
            "7219",
            accessToken,
            "473",
            inspectionListRequestModel
        )
            .enqueue(object : Callback<InspectionListResponse> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(
                    call: Call<InspectionListResponse>,
                    response: Response<InspectionListResponse>
                ) {
                    Log.d("neel", "Inspection List : ${response.body()}")

                    val InspectionResponse = response.body()
                    Log.d("neel","InspectionInfo Response")
                    val insList = InspectionResponse?.result?.map{ result->

                        InspectionInfoModel("#"+result.ticketNumber,
                            result.ticketId.customerId.firstname +" "+result.ticketId.customerId.lastname,
                            result.status,
                            result.defficiencyReportedCount.toString(),
                            result.recommendationsCount.toString(),
                            convertTimestampToFormattedDate(result.ticketStartDate),
                            convertTimestampToFormattedDate(result.ticketEndDate),
                        )
                    }?.toCollection(ArrayList())
//                    Log.d("neel",insList.toString())
                    inspectionList = insList!!
                    val adapter =
                        context?.let { AdapterForInspectionList(it, screenWidth, inspectionList, ){ it ->
                            val action = HomeScreenDirections.actionHomeScreenToInspectionInfo(it)
                            navController.navigate(action)
                        } }

                    binding.inspectionrecyclerview.adapter = adapter
                }

                override fun onFailure(call: Call<InspectionListResponse>, t: Throwable) {
                    Log.d("neel", "InspectionList-onFailure")
                    t.printStackTrace()
                }

            })
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

//    override fun onItemClick(item: InspectionInfoModel) {
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

        val customerList = ArrayList<String>()
        for (i in 1..10) {
            customerList.add("Neel Patel (1234)")
            customerList.add("Smit Patel (5678)")
            customerList.add("Kuldeep Tripathi (9012)")
            customerList.add("Dhruv Pathak (34567)")
        }

        val customerRecycleView = dialog.findViewById<RecyclerView>(R.id.customerRecycleView)
        customerRecycleView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = AdapterForCustomerList(
            customerList,
            requireContext(),
            true
        ) { customerName, customerId ->
            dialog.dismiss()
            onclickCustomerNameOpenFormTemplatesList(customerName, customerId)
        }

        customerRecycleView.adapter = adapter

        val addCustomer = dialog.findViewById<TextView>(R.id.addCustomer)

        addCustomer.setOnClickListener {
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTimestampToFormattedDate(timestamp: Long): String {
        // Convert the timestamp to LocalDateTime
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())
        // Define the formatter with the desired pattern
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy   hh:mma")
        // Format the LocalDateTime to the desired string format
        return dateTime.format(formatter)
    }

}
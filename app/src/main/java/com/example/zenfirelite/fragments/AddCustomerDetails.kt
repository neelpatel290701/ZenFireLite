package com.example.zenfirelite.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForCustomerList
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.AddCustomerRequestBody
import com.example.zenfirelite.apis.datamodels.AddCustomerResponse
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_RequestBody
import com.example.zenfirelite.apis.datamodels.CustomerList_ServiceBilling_Response
import com.example.zenfirelite.apis.datamodels.PaymentTerm
import com.example.zenfirelite.apis.datamodels.PaymentTerm2
import com.example.zenfirelite.apis.datamodels.SalesTaxRate
import com.example.zenfirelite.apis.datamodels.SalesTaxRate2
import com.example.zenfirelite.apis.datamodels.TaxRateBreakUp
import com.example.zenfirelite.apis.datamodels.TaxRateBreakUp2
import com.example.zenfirelite.apis.datamodels.TaxZone
import com.example.zenfirelite.apis.datamodels.TaxZone2
import com.example.zenfirelite.apis.datamodels.addCustomerBillingAddress
import com.example.zenfirelite.apis.datamodels.addCustomerNotes
import com.example.zenfirelite.apis.datamodels.addCustomerNotes2
import com.example.zenfirelite.apis.datamodels.addCustomerServiceAddress
import com.example.zenfirelite.databinding.FragmentAddCustomerDetailsBinding
import com.example.zenfirelite.databinding.FragmentCustomerDetailsBinding
import com.example.zenfirelite.databinding.FragmentHomeScreenBinding
import com.example.zenfirelite.datamodels.CustomerListModel
import com.example.zenfirelite.prefs
import com.example.zenfirelite.utils.ZTUtils
import com.example.zenfirelite.utils.ZTUtils.isValidUserName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("DEPRECATION")
class AddCustomerDetails : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentAddCustomerDetailsBinding
    private lateinit var navController: NavController
    var propertyTypes = arrayOf<String?>("Residential", "Commercial")

    private lateinit var companyName: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var additionalName: String
    private lateinit var addressLine1: String
    private lateinit var addressLine2: String
    private lateinit var city: String
    private lateinit var state: String
    private lateinit var zipCode: String
    private lateinit var country: String
    private lateinit var email: String
    private lateinit var landline: String
    private lateinit var extension: String
    private lateinit var cellPhone: String
    private lateinit var additionalDetails: String
    private lateinit var propertyTypeValue: String

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
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddCustomerDetailsBinding.inflate(inflater, container, false)

        binding.spinner.onItemSelectedListener = this
        setHasOptionsMenu(true)

        val propertyAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item,
            propertyTypes
        )

        propertyAdapter.setDropDownViewResource(
            R.layout.status_spinner_dropdown_item
        )

        binding.spinner.adapter = propertyAdapter

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbaritems, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItem_save = menu.findItem(R.id.save_addCustomer)
        menuItem_save.isVisible = true //ion
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_addCustomer -> {
                initializeCustomerDetailValues()
                if(ZTUtils.validateCustomerDetails(
                        companyName,
                        firstName,
                        addressLine1,
                        city,
                        state,
                        zipCode,
                        country,
                        email,
                        binding
                    )
                ) {
                    apiRequestForAddCustomer()
                    Toast.makeText(requireContext(), "Customer Added", Toast.LENGTH_SHORT).show()
                }
                true
            }

            else -> false
        }
    }

    private fun initializeCustomerDetailValues() {
        companyName = binding.companyName.text.toString().trim()
        firstName = binding.firstName.text.toString().trim()
        lastName = binding.lastName.text.toString().trim()
        addressLine1 = binding.addressLine1.toString().trim()
        city = binding.city.text.toString().trim()
        state = binding.state.text.toString().trim()
        zipCode = binding.zipCode.text.toString().trim()
        country = binding.country.text.toString().trim()
        email = binding.email.text.toString().trim()
        landline = binding.landLine.text.toString().trim()
        cellPhone = binding.cellPhone.text.toString().trim()
    }

    private fun apiRequestForAddCustomer() {

        val additionalContacts = ArrayList<String>()
        val salTaxRates = ArrayList<SalesTaxRate>()
        val salTaxRates2 = ArrayList<SalesTaxRate2>()

        salTaxRates.add(
            SalesTaxRate(
                "w4FaocEJxx",
                3,
                "SANTA CLARA, 722 LOS GATOS",
                TaxRateBreakUp(3.25, 6),
                9.25,
                "SANTA CLARA, 722 LOS GATOS, CA 95030",
                "LOS GATOS",
                "US",
                true,
                false,
                false,
                "viIlhF5Cfe",
                "2023-08-07T09:24:31.209Z",
                "2023-08-07T09:24:31.209Z",
                3,
                3
            )
        )

        salTaxRates2.add(
            SalesTaxRate2(
                "w4FaocEJxx",
                3,
                "SANTA CLARA, 722 LOS GATOS",
                TaxRateBreakUp2(3.25, 6),
                9.25,
                "SANTA CLARA, 722 LOS GATOS, CA 95030",
                "LOS GATOS",
                "US",
                true,
                false,
                false,
                "viIlhF5Cfe",
                "2023-08-07T09:24:31.209Z",
                "2023-08-07T09:24:31.209Z",
                3,
                3
            )
        )

        val taxZone = TaxZone(
            "NVPvyn5xrK",
            "NVPvyn5xrK",
            3,
            "SANTA CLARA, 722 LOS GATOS, CA 95030",
            true,
            false,
            false,
            "2023-08-07T09:24:31.217Z",
            "2023-08-07T09:24:31.217Z",
            3,
            3,
            salTaxRates,
            9.25
        )

        val taxZon2 = TaxZone2(
            "NVPvyn5xrK",
            "NVPvyn5xrK",
            3,
            "SANTA CLARA, 722 LOS GATOS, CA 95030",
            true,
            false,
            false,
            "2023-08-07T09:24:31.217Z",
            "2023-08-07T09:24:31.217Z",
            3,
            3,
            salTaxRates2,
            9.25
        )

        val billingAddress = addCustomerBillingAddress(
            "300 College Avenue",
            "",
            "Los Gatos",
            "CA",
            "US",
            "95030",
            "",
            propertyTypeValue,
            addCustomerNotes(""),
            PaymentTerm(""),
            additionalContacts,
            false,
            taxZone
        )
        val serviceAddress = addCustomerServiceAddress(
            "300 College Avenue",
            "",
            "Los Gatos",
            "CA",
            "US",
            "95030",
            "",
            propertyTypeValue,
            addCustomerNotes2(""),
            PaymentTerm2(""),
            additionalContacts,
            false,
            taxZon2
        )

        val customerDetails = AddCustomerRequestBody(
            firstName,
            lastName,
            "",
            "Subinoy, Ghosh Wedge (6919865)",
            email,
            landline,
            cellPhone,
            "",
            companyName,
            null,
            null,
            false,
            billingAddress,
            serviceAddress
        )

        APIManager.apiInterface.addCustomer(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
            customerDetails
        ).enqueue(object : Callback<AddCustomerResponse> {
            override fun onResponse(
                call: Call<AddCustomerResponse>,
                response: Response<AddCustomerResponse>
            ) {
                if (response.isSuccessful) {
                    val result = Bundle().apply {
                        putBoolean(CustomerList.CUSTOMER_ADDED_STATUS, true)
                    }
                    parentFragmentManager.setFragmentResult(
                        CustomerList.CUSTOMERDETAILS_KEY,
                        result
                    )
                    parentFragmentManager.popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Customer Added - Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<AddCustomerResponse>, t: Throwable) {
                Log.d("neel", "CustomerAdded-onFailure")
                t.printStackTrace()
            }

        })
    }



    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        propertyTypeValue = propertyTypes[position].toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}
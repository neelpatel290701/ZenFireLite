package com.example.zenfirelite.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zenfirelite.apis.APIManager
import com.example.zenfirelite.apis.datamodels.CheckboxOption
import com.example.zenfirelite.apis.datamodels.Column
import com.example.zenfirelite.apis.datamodels.ColumnOptions
import com.example.zenfirelite.apis.datamodels.DropdownOption
import com.example.zenfirelite.apis.datamodels.DropdownOption2
import com.example.zenfirelite.apis.datamodels.Field
import com.example.zenfirelite.apis.datamodels.FieldOptions
import com.example.zenfirelite.apis.datamodels.FormTemplatesListResponse
import com.example.zenfirelite.apis.datamodels.FormTemplatesResult
import com.example.zenfirelite.apis.datamodels.Section
import com.example.zenfirelite.apis.datamodels.Layout
import com.example.zenfirelite.apis.datamodels.RadioOption
import com.example.zenfirelite.apis.datamodels.Value
import com.example.zenfirelite.datamodels.CheckboxOptionModel
import com.example.zenfirelite.datamodels.ColumnModel
import com.example.zenfirelite.datamodels.ColumnOptionsModel
import com.example.zenfirelite.datamodels.DropdownOption2Model
import com.example.zenfirelite.datamodels.DropdownOptionModel
import com.example.zenfirelite.datamodels.FieldModel
import com.example.zenfirelite.datamodels.FieldOptionsModel
import com.example.zenfirelite.datamodels.FormTemplatesListModel
import com.example.zenfirelite.datamodels.LayoutModel
import com.example.zenfirelite.datamodels.RadioOptionModel
import com.example.zenfirelite.datamodels.SectionData
import com.example.zenfirelite.datamodels.ValueModel
import com.example.zenfirelite.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class FormTemplatesListViewModel : ViewModel() {

    private var _formTemplatesList = MutableLiveData<List<FormTemplatesListModel?>>()
    val formTemplatesList: LiveData<List<FormTemplatesListModel?>> get() = _formTemplatesList

    init {
        fetchFormTemplatesList()
    }

    private fun fetchFormTemplatesList(){

        APIManager.apiInterface.getFormTemplates(
            prefs.userID.toString(),
            prefs.accessToken.toString(),
            prefs.companyID.toString(),
        ).enqueue(object : Callback<FormTemplatesListResponse> {
            override fun onResponse(
                call: Call<FormTemplatesListResponse>,
                response: Response<FormTemplatesListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _formTemplatesList.value = it.result.toFormTemplatesListModel()
                    }
                }
            }

            override fun onFailure(call: Call<FormTemplatesListResponse>, t: Throwable) {
                Log.d("neel", "FormTemplatesList-onFailure : $call")
                if (t is IOException) {
                    Log.e("neel-RetrofitFailure", "Network error", t)
                } else {
                    Log.e("neel-RetrofitFailure", "Conversion error", t)
                }
                t.printStackTrace()
            }
        })
    }


    // Convert FormTemplatesListResponse to FormTemplatesListModel
    // Convert FormTemplatesListResponse to FormTemplatesListModel
    fun List<FormTemplatesResult>.toFormTemplatesListModel(): List<FormTemplatesListModel> {
        return this.map { formTemplatesResult ->
            FormTemplatesListModel(
                id = formTemplatesResult.id,
                name = formTemplatesResult.name,
                displayName = formTemplatesResult.displayName,
                description = formTemplatesResult.description,
                template = formTemplatesResult.template,
                isActive = formTemplatesResult.isActive,
                isDeleted = formTemplatesResult.isDeleted,
                companyId = formTemplatesResult.companyId,
                createdUserId = formTemplatesResult.createdUserId,
                updatedUserId = formTemplatesResult.updatedUserId,
                createdAt = formTemplatesResult.createdAt,
                updatedAt = formTemplatesResult.updatedAt,
                sections = formTemplatesResult.sections.map { it.toSectionData() }
            )
        }
    }
    // Convert Section to SectionData
    fun Section.toSectionData(): SectionData {
        return SectionData(
            id = this.id,
            name = this.name,
            displayName = this.displayName,
            showDisplayName = this.showDisplayName,
            showSummary = this.showSummary,
            moduleId = this.moduleId,
            moduleEntityId = this.moduleEntityId,
            companyId = this.companyId,
            sortPosition = this.sortPosition,
            isActive = this.isActive,
            isDeleted = this.isDeleted,
            createdUserId = this.createdUserId,
            updatedUserId = this.updatedUserId,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            fields = this.fields?.map { it.toFieldType() },
            description = this.description
        )
    }

    // Convert Field to FieldType
    fun Field.toFieldType(): FieldModel {
        return FieldModel(
            id = this.id,
            name = this.name,
            displayName = this.displayName,
            uiType = this.uiType,
            dataType = this.dataType,
            defaultValue = this.defaultValue,
            mandatory = this.mandatory,
            readonly = this.readonly,
            sortPosition = this.sortPosition,
            sectionId = this.sectionId,
            layout = this.layout?.toLayout(),
            isActive = this.isActive,
            isDeleted = this.isDeleted,
            createdUserId = this.createdUserId,
            updatedUserId = this.updatedUserId,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            options = this.options?.toFieldOptions(),
        )
    }

    // Convert Layout to Layout
    fun Layout.toLayout(): LayoutModel {
        return LayoutModel(
            x = this.x,
            y = this.y,
            w = this.w,
            h = this.h,
            i = this.i,
            fullWidth = this.fullWidth
        )
    }

    // Convert FieldOptions to FieldOptions
    fun FieldOptions.toFieldOptions(): FieldOptionsModel {
        return FieldOptionsModel(
            radioOptions = this.radioOptions?.map { it.toRadioOption() },
            dropdownOptions = this.dropdownOptions?.map { it.toDropdownOption() },
            checkboxOptions = this.checkboxOptions?.map { it.toCheckboxOption() },
            value = this.value?.toValue(),
            label = this.label,
            entity = this.entity,
            entityType = this.entityType,
            columns = this.columns?.map { it.toColumn() }
        )
    }

    // Convert RadioOption to RadioOption
    fun RadioOption.toRadioOption(): RadioOptionModel {
        return RadioOptionModel(
            key = this.key,
            value = this.value,
            label = this.label
        )
    }

    // Convert DropdownOption to DropdownOption
    fun DropdownOption.toDropdownOption(): DropdownOptionModel {
        return DropdownOptionModel(
            key = this.key,
            value = this.value,
            label = this.label
        )
    }

    // Convert CheckboxOption to CheckboxOption
    fun CheckboxOption.toCheckboxOption(): CheckboxOptionModel {
        return CheckboxOptionModel(
            key = this.key,
            value = this.value,
            label = this.label
        )
    }

    // Convert Value to Value
    fun Value.toValue(): ValueModel {
        return ValueModel(
            entity = this.entity,
            entityType = this.entityType
        )
    }

    // Convert Column to Column
    fun Column.toColumn(): ColumnModel {
        return ColumnModel(
            name = this.name,
            displayName = this.displayName,
            uiType = this.uiType,
            dataType = this.dataType,
            columnOptions = this.columnOptions.toColumnOptions()
        )
    }

    // Convert ColumnOptions to ColumnOptions
    fun ColumnOptions.toColumnOptions(): ColumnOptionsModel {
        return ColumnOptionsModel(
            dropdownOptions = this.dropdownOptions?.map { it.toDropdownOption2() }
        )
    }

    // Convert DropdownOption2 to DropdownOption2
    fun DropdownOption2.toDropdownOption2(): DropdownOption2Model {
        return DropdownOption2Model(
            key = this.key,
            value = this.value,
            label = this.label
        )
    }

}
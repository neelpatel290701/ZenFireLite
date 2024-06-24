package com.example.zenfirelite.apis

import com.example.zenfirelite.apis.datamodels.FieldDetails
import com.example.zenfirelite.apis.datamodels.FieldOptionsDetails
import com.example.zenfirelite.apis.datamodels.LayoutDetails
import com.example.zenfirelite.apis.datamodels.ReasonDetails
import com.example.zenfirelite.apis.datamodels.ValueDetailsOfForm
import com.google.gson.JsonDeserializer
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

// Not Used now
class FieldDetailsDeserializer : JsonDeserializer<FieldDetails> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): FieldDetails {
        val jsonObject = json?.asJsonObject

        val id = jsonObject?.get("id")?.asString
        val name = jsonObject?.get("name")?.asString
        val displayName = jsonObject?.get("displayName")?.asString
        val value: ValueDetailsOfForm? = when {
            jsonObject?.get("value")?.isJsonArray == true -> {
//                val listValue = context?.deserialize<List<String>>(
//                    jsonObject.get("value"),
//                    object : TypeToken<List<String>>() {}.type
//                )
//                ValueDetailsOfForm.ListValue(listValue!!)
                ValueDetailsOfForm.StringValue("")

            }
            jsonObject?.get("value")?.isJsonPrimitive == true -> {
                val stringValue = jsonObject.get("value").asString
                ValueDetailsOfForm.StringValue(stringValue)
            }
            else -> null
        }
        val uiType = jsonObject?.get("uiType")?.asString
        val dataType = jsonObject?.get("dataType")?.asString
        val defaultValue = jsonObject?.get("defaultValue")?.asString
        val mandatory = jsonObject?.get("mandatory")?.asBoolean
        val readonly = jsonObject?.get("readonly")?.asBoolean
        val sortPosition = jsonObject?.get("sortPosition")?.asString
        val sectionId = jsonObject?.get("sectionId")?.asString
        val layout = context?.deserialize<LayoutDetails?>(jsonObject?.get("layout"), LayoutDetails::class.java)
        val isActive = jsonObject?.get("isActive")?.asBoolean
        val isDeleted = jsonObject?.get("isDeleted")?.asBoolean
        val createdUserId = jsonObject?.get("createdUserId")?.asLong
        val updatedUserId = jsonObject?.get("updatedUserId")?.asLong
        val createdAt = jsonObject?.get("createdAt")?.asString
        val updatedAt = jsonObject?.get("updatedAt")?.asString
        val reasons = context?.deserialize<List<ReasonDetails>>(jsonObject?.get("reasons"), object : TypeToken<List<ReasonDetails>>() {}.type)
        val options = context?.deserialize<FieldOptionsDetails?>(jsonObject?.get("options"), FieldOptionsDetails::class.java)

        return FieldDetails(
            id!!, name!!, displayName!!, value, uiType!!, dataType!!, defaultValue,
            mandatory!!, readonly!!, sortPosition!!, sectionId!!, layout, isActive!!, isDeleted!!,
            createdUserId!!, updatedUserId!!, createdAt!!, updatedAt!!, reasons!!, options
        )
    }

}

import com.example.zenfirelite.apis.datamodels.CheckboxOption
import com.example.zenfirelite.apis.datamodels.Column
import com.example.zenfirelite.apis.datamodels.DropdownOption
import com.example.zenfirelite.apis.datamodels.FieldOptions
import com.example.zenfirelite.apis.datamodels.RadioOption
import com.example.zenfirelite.apis.datamodels.Value
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class FieldOptionsDeserializer : JsonDeserializer<FieldOptions?> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): FieldOptions? {
        if (json.isJsonObject) {
            // Handle the case where options is an object
            val jsonObject = json.asJsonObject
            val radioOptions = if (jsonObject.has("radioOptions")) {
                context?.deserialize<List<RadioOption>>(
                    jsonObject.getAsJsonArray("radioOptions"),
                    object : TypeToken<List<RadioOption>>() {}.type
                )
            } else null

            val dropdownOptions = if (jsonObject.has("dropdownOptions")) {
                context?.deserialize<List<DropdownOption>>(
                    jsonObject.getAsJsonArray("dropdownOptions"),
                    object : TypeToken<List<DropdownOption>>() {}.type
                )
            } else null

            val checkboxOptions = if (jsonObject.has("checkboxOptions")) {
                context?.deserialize<List<CheckboxOption>>(
                    jsonObject.getAsJsonArray("checkboxOptions"),
                    object : TypeToken<List<CheckboxOption>>() {}.type
                )
            } else null

            val value = if (jsonObject.has("value") && jsonObject.has("entityType")) {
                Value(
                    jsonObject.get("entity").asString,
                    jsonObject.get("entityType").asString
                )
            } else null

            val label = if (jsonObject.has("label")) {
                jsonObject.get("label").asString
            } else null

            val entity = if (jsonObject.has("entity")) {
                jsonObject.get("entity").asString
            } else null

            val entityType = if (jsonObject.has("entityType")) {
                jsonObject.get("entityType").asString
            } else null

            val columns = if (jsonObject.has("columns")) {
                context?.deserialize<List<Column>>(
                    jsonObject.getAsJsonArray("columns"),
                    object : TypeToken<List<Column>>() {}.type
                )
            } else null

            return FieldOptions(radioOptions, dropdownOptions, checkboxOptions, value, label, entity, entityType, columns)
        } else if (json.isJsonArray) {
            // Handle the case where options is an array
            val jsonArray = json.asJsonArray
            if (jsonArray.size() == 1 && jsonArray[0].isJsonObject && jsonArray[0].asJsonObject.entrySet().isEmpty()) {
                // Handle the case where the array is exactly one empty object
                return null
            } else {
                // Handle other cases for array as per your application logic
                return null
            }
        }
        return null
    }
}

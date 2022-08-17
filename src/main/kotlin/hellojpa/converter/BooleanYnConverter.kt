package hellojpa.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class BooleanYnConverter : AttributeConverter<Boolean, String> {
    override fun convertToDatabaseColumn(attribute: Boolean?): String = if (attribute == true) "Y" else "N"

    override

    fun convertToEntityAttribute(dbData: String?): Boolean = dbData == "Y"
}
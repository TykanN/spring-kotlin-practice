package hellojpa.converter

import hellojpa.RoleType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class RoleTypeConverter : AttributeConverter<RoleType, String> {
    override fun convertToDatabaseColumn(attribute: RoleType?): String = attribute?.code ?: ""


    override fun convertToEntityAttribute(dbData: String?): RoleType {
        return when (dbData) {
            RoleType.USER.code -> RoleType.USER
            RoleType.ADMIN.code -> RoleType.ADMIN
            else -> RoleType.USER
        }
    }

}
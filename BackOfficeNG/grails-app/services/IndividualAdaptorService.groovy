import com.google.gson.JsonObject

import org.libredemat.business.users.Child
import org.libredemat.business.users.SexType
import org.libredemat.business.users.TitleType
import org.libredemat.service.users.IUserService
import org.libredemat.service.users.IUserSearchService
import org.libredemat.service.users.IUserWorkflowService
import org.libredemat.util.translation.ITranslationService
import org.libredemat.exception.CvqValidationException

class IndividualAdaptorService {

    ITranslationService translationService
    IUserService userService
    IUserSearchService userSearchService
    IUserWorkflowService userWorkflowService

    public getIndividualDescription(individual) {
        def result = ["firstName" : "", "lastName" : "", "title" : ""]
        if (!individual) return result
        result.firstName = individual.firstName
        result.lastName = individual.lastName
        if (individual.class.simpleName == "Child") {
            switch (individual.sex) {
                case SexType.MALE :
                    result.title = TitleType.MISTER
                    break;
                case SexType.FEMALE :
                    result.title = TitleType.MADAM
                    break;
                default :
                    result.title = TitleType.UNKNOWN
            }
        } else if (individual.class.simpleName == "Adult") {
            result.title = individual.title
        }
        return result
    }

    public adaptSubjects(subjects) {
        def result = [:]
        subjects.each {
            def subject = userSearchService.getById(it)
            result[it] = subject instanceof Child && !subject.born ? translationService.translate("request.subject.childNoBorn", subject.fullName) : subject.fullName
        }
        return result
    }

    public adaptMeansOfContact(meansOfContact) {
        def result = []
        meansOfContact.each {
            result.add([
                key : it.type,
                label : translationService.translate("meansOfContact." + StringUtils.pascalToCamelCase(StringUtils.toPascalCase(it.type.toString())))
            ])
        }
        return result.sort {it.label}
    }

    public historize(individual, bean, dto, name, fields, boolean validate = true) throws CvqValidationException {
        def atom = new JsonObject()
        atom.addProperty("name", name)
        def diff = new JsonObject()
        atom.add("fields", diff)
        fields.each {
            if (dto[it] != bean[it]) {
                def field = new JsonObject()
                field.addProperty("from", bean[it].toString())
                field.addProperty("to", dto[it].toString())
                diff.add(it, field)
                bean[it] = dto[it]
            }
        }
        if (validate) {
            def invalidFields = userService.validate(individual)
            if (!invalidFields.isEmpty()) throw new CvqValidationException(invalidFields)
        }
        if (diff.entrySet().size() > 0) userWorkflowService.modify(individual, atom)
    }
}

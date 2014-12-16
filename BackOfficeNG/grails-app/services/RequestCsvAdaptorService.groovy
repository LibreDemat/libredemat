import java.lang.reflect.Method;

import org.libredemat.business.request.Request;
import au.com.bytecode.opencsv.CSVWriter;
import static au.com.bytecode.opencsv.CSVParser.*;
import java.lang.reflect.Method
import java.util.List;

import org.libredemat.business.request.LocalReferentialData;
import org.libredemat.service.users.IUserSearchService;

class RequestCsvAdaptorService {

    IUserSearchService userSearchService

    public def exportRequestAsCsv(List<Request> requests, OutputStreamWriter out) {
        def csvWriter = new CSVWriter(out, (char)';', DEFAULT_QUOTE_CHARACTER , DEFAULT_ESCAPE_CHARACTER, "\n")
        // Write header
        if (!requests.empty) {
            List<String> headers = requestGenericData.clone()
            headers.add("")
            headers.addAll(Arrays.asList("email", "telephones"))
            headers.addAll(Arrays.asList("code rivoli", "voie", "complément 1", "complément 2", "code postal", "localité", "pays"))
            headers.add("")
            headers.addAll(serializeField(requests.get(0).getSpecificData(), true))
            println(headers)
            csvWriter.writeNext((String[])headers.toArray())
        }
        // Write data
        requests.each { req ->
            List<String> data = extractGenericData(req)
            data.add("")
            data.addAll(extractRequesterData(req))
            data.add("")
            data.addAll(serializeField(req.getSpecificData()))
            csvWriter.writeNext((String[])data.toArray())
        }
        csvWriter.flush()
    }

    // Common request data
    private def requestGenericData = [
        "Id", "HomeFolderId", "SubjectId", "SubjectLastName", "SubjectFirstName",
        "RequesterId", "RequesterLastName", "RequesterFirstName", "RequestSeason", "State",
        "DataState", "Step", "CreationDate", "ValidationDate", "LastModificationDate",
        "LastInterveningUserId", "OrangeAlert", "RedAlert"]

    // Extract generic data about the request (common to all requests)
    private def extractGenericData(Request request) {
        return requestGenericData.collect { col ->
            request.getClass().getMethod("get"+col).invoke(request)
        }
    }

    // Manually extract data about the requester (email, phones, address)
    private def extractRequesterData(Request request) {
        def requesterId = request.getRequesterId()
        def requester = userSearchService.getById(requesterId)
        def adult = userSearchService.getAdultById(requesterId)
        def address = requester.address

        List<String> data = new ArrayList<String>();
        data.add(adult.email)
        data.add(orEmpty(adult.homePhone)+"\n"+orEmpty(adult.mobilePhone)+"\n"+orEmpty(adult.officePhone))

        data.add(orEmpty(address.streetRivoliCode))
        data.add(orEmpty(address.streetNumber) + " " + orEmpty(address.streetName) + " " + orEmpty(address.streetMatriculation))
        data.add(orEmpty(address.additionalDeliveryInformation))
        data.add(orEmpty(address.additionalGeographicalInformation))
        data.add(address.postalCode)
        data.add(address.city + (orEmpty(address.placeNameOrService).isEmpty() ? "" : ", " + address.placeNameOrService))
        data.add(orEmpty(address.countryName))
        return data
    }

    private def orEmpty(String str) { (str == null) ? "" : str }

    // Extract all getters of this class and display blank value
    private def serializeNullField(Method getter, boolean header = false) {
        Class c = getter.getReturnType()
        if (isStringifiable(c)) header ? getter.name : ""
        else if (Collection.class.isAssignableFrom(c)) header ? getter.name : ""
        else extractGetters(c).collect { serializeNullField(it, header) }
    }

    // Extract all getters from this object
    private def serializeField(Object obj, boolean header = false) {
        extractGetters(obj.class).collect { getter ->
            def res = getter.invoke(obj)

            // This field is null: extract all getters as blank field
            if (res == null) {
                serializeNullField(getter, header)

            // Simple field: display it
            } else if (isStringifiable(res.class)) {
                header ? renameHeader(getter) : res.toString().replace("\n", "\r")

            // Collection: extract all data in one cell
            } else if (Collection.class.isAssignableFrom(res.class)) {
                if (header) {
                    def heads = res.collect {
                        if (it instanceof LocalReferentialData) getter.name
                        else renameHeader(getter) + serializeField(it, header)
                    }
                    (heads.empty) ? "" : heads.get(0).toString()
                } else {
                    res.collect {
                        if (it instanceof LocalReferentialData) it.toString()
                        else serializeField(it, header)
                    }.grep { it != null }.join("\n")
                }

            // Complex object: serialize it recursively
            } else {
                serializeField(res, header)
            }

        }.flatten()
    }

    // Class displayable with a toString
    private def isStringifiable(Class c) {
       return (c.isPrimitive()
            || c.isEnum()
            || c == Byte.class
            || c == Short.class
            || c == Integer.class
            || c == Long.class
            || c == Float.class
            || c == Double.class
            || c == Boolean.class
            || c == Character.class
            || c == String.class
            || c == LocalReferentialData.class
            || c.name.startsWith("java")
            || c.name.startsWith("org.joda.time"))
    }

    // Extract all bean-getters from a class
    private def extractGetters(Class c) {
        return c.methods.findAll { m ->
            (m.name.startsWith("get") || m.name.startsWith("is")) && !m.name.equals("getClass")
        }
    }

    // Helper to display a method name
    private def renameHeader(Method method) {
        return method.name.replaceAll("^(get|is)", "")
    }
}
package fr.cg95.cvq.util.development;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateMidnight;

import fr.cg95.cvq.business.request.Category;
import fr.cg95.cvq.business.request.LocalReferentialData;
import fr.cg95.cvq.business.authority.LocalAuthority;
import fr.cg95.cvq.business.authority.RecreationCenter;
import fr.cg95.cvq.business.authority.School;
import fr.cg95.cvq.business.document.DepositOrigin;
import fr.cg95.cvq.business.document.DepositType;
import fr.cg95.cvq.business.document.Document;
import fr.cg95.cvq.business.document.DocumentState;
import fr.cg95.cvq.business.document.DocumentType;
import fr.cg95.cvq.business.request.RequestSeason;
import fr.cg95.cvq.business.users.Address;
import fr.cg95.cvq.business.users.Adult;
import fr.cg95.cvq.business.users.Child;
import fr.cg95.cvq.business.users.FamilyStatusType;
import fr.cg95.cvq.business.users.SexType;
import fr.cg95.cvq.business.users.TitleType;
import fr.cg95.cvq.business.users.UserState;

/**
 * A factory to create necessary referential objects.
 *
 * @author bor@zenexity.fr
 */
public class BusinessObjectsFactory {

    public static LocalAuthority gimmeLocalAuthority(String name) {
        LocalAuthority localAuthority = new LocalAuthority();
        localAuthority.setName(name);
        return localAuthority;
    }

    public static Category gimmeCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }

    public static School gimmeSchool(String name) {
        School school = new School();
        school.setName(name);
        school.setAddress("Rue du centre");
        school.setActive(true);
        return school;
    }

    public static RecreationCenter gimmeRecreationCenter(String name) {
        RecreationCenter recreationCenter = new RecreationCenter();
        recreationCenter.setName(name);
        recreationCenter.setAddress("Rue du centre");
        recreationCenter.setActive(true);
        return recreationCenter;
    }

    public static Adult gimmeAdult(TitleType title, String lastName, String firstName,
        Address address, FamilyStatusType fs) {
        Adult adult = new Adult();
        adult.setState(UserState.VALID);
        adult.setTitle(title);
        adult.setLastName(lastName);
        adult.setFirstName(firstName);
        adult.setFirstName2("Firstname2");
        adult.setFirstName3("Firstname3");
        adult.setNameOfUse("NAMEOFUSE");
        adult.setFamilyStatus(fs);
        adult.setHomePhone("0101010101");
        adult.setMobilePhone("0606060606");
        adult.setOfficePhone("0202020202");
        adult.setEmail("capdemat-dev@zenexity.fr");
        adult.setCfbn("5050505E");
        adult.setProfession("Profession");
        if (address != null)
            adult.setAddress(address);
        adult.setBirthCity("PARIS");
        adult.setBirthCountry("FRANCE");
        return adult;
    }

    public static Child gimmeChild(String lastName, String firstName) {
        Child child = new Child();
        child.setLastName(lastName);
        child.setFirstName(firstName);
        child.setSex(SexType.FEMALE);
        child.setBirthCity("Paris");
        child.setBirthPostalCode("75012");
        Calendar calendar = new GregorianCalendar();
        Date currentDate = new Date();
        calendar.setTime(currentDate);
        child.setBirthDate(calendar.getTime());
        return child;
    }

    public static Address gimmeAddress(String streetNumber, String streetName, String city,
        String postalCode) {
        Address address = new Address();
        address.setAdditionalDeliveryInformation("Chez Zenxity");
        address.setAdditionalGeographicalInformation("Bat. ParisCyber Village");
        address.setStreetNumber(streetNumber);
        address.setStreetName(streetName);
        address.setCity(city);
        address.setPostalCode(postalCode);
        return address;
    }

    public static Document gimmeDocument(String note, DepositOrigin depOrig, DepositType depType,
        DocumentType docType) {
        Document doc = new Document(null, note, docType, DocumentState.PENDING);
        doc.setDepositOrigin(depOrig);
        doc.setDepositType(depType);
        return doc;
    }

    public static LocalReferentialData gimmeLocalReferentialData() {

        LocalReferentialData lrd = new LocalReferentialData();
        lrd.setPriority(Integer.valueOf("1"));
        lrd.setName("God");
        lrd.setAdditionalInformationLabel("AddLabel");
        lrd.setAdditionalInformationValue("AddValue");

        LocalReferentialData lrdChild1 = new LocalReferentialData();
        lrdChild1.setPriority(Integer.valueOf("1"));
        lrdChild1.setName("Child1");
        lrdChild1.setAdditionalInformationLabel("AddLabel");
        lrdChild1.setAdditionalInformationValue("AddValue");
        if (lrd.getChildren() == null) {
            Set<LocalReferentialData> data = new HashSet<LocalReferentialData>();
            data.add(lrdChild1);
            lrd.setChildren(data);
        } else {
            lrd.getChildren().add(lrdChild1);
        }
        lrdChild1.setParent(lrd);

        LocalReferentialData lrdChild2 = new LocalReferentialData();
        lrdChild2.setPriority(Integer.valueOf("2"));
        lrdChild2.setName("Child2");
        lrdChild2.setAdditionalInformationLabel("AddLabel");
        lrdChild2.setAdditionalInformationValue("AddValue");
        if (lrd.getChildren() == null) {
            Set<LocalReferentialData> data = new HashSet<LocalReferentialData>();
            data.add(lrdChild2);
            lrd.setChildren(data);
        } else {
            lrd.getChildren().add(lrdChild2);
        }
        lrdChild2.setParent(lrd);

        return lrd;
    }

    public static RequestSeason gimmeRequestSeason(final String label,
        final int registrationStartOffset, final int registrationEndOffset,
        final int effectStartOffset, final int effectEndOffset) {
        RequestSeason requestSeason = new RequestSeason();
        requestSeason.setLabel(label);
        requestSeason.setRegistrationStart(new DateMidnight().plusMonths(registrationStartOffset));
        requestSeason.setRegistrationEnd(new DateTime().plusMonths(registrationEndOffset));
        requestSeason.setValidationAuthorizationStart(new DateMidnight(requestSeason.getRegistrationEnd()));
        requestSeason.setEffectStart(new DateMidnight().plusMonths(effectStartOffset));
        requestSeason.setEffectEnd(new DateTime().plusMonths(effectEndOffset));
        return requestSeason;
    }
}

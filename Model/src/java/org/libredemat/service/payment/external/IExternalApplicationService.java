package org.libredemat.service.payment.external;

import java.util.List;
import java.util.Map;

import org.libredemat.business.payment.ExternalDepositAccountItem;
import org.libredemat.business.payment.ExternalInvoiceItem;
import org.libredemat.business.payment.ExternalTicketingContractItem;
import org.libredemat.business.payment.external.ExternalApplication;
import org.libredemat.business.payment.external.ExternalHomeFolder;
import org.libredemat.business.users.Adult;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.exception.CvqObjectNotFoundException;


public interface IExternalApplicationService {

    final static String EXTERNAL_APPLICATION_LABEL = "LIBREDEMAT";

    ExternalApplication getExternalApplicationById(final Long id) throws CvqObjectNotFoundException;

    List<ExternalApplication> allExternalApplications();

    void createExternalApplication(ExternalApplication externalApplication) throws CvqModelException;

    void modifyExternalApplication(ExternalApplication externalApplication) throws CvqModelException;

    void deleteExternalApplication(final Long id) throws CvqModelException, CvqObjectNotFoundException;

    Map<String,Integer> importHomeFolders(Long externalApplicationId, byte[] csvDatas)
            throws CvqModelException, CvqObjectNotFoundException;

    Map<String,Integer> importInvoices(Long externalApplicationId, String broker, byte[] csvDatas)
            throws CvqModelException, CvqObjectNotFoundException;

    Map<String,Integer> importInvoicesDetails(Long externalApplicationId, byte[] csvDatas)
            throws CvqModelException, CvqObjectNotFoundException;

    Map<String,Integer> importDepositAccounts(Long externalApplicationId, String broker, byte[] csvDatas)
            throws CvqModelException, CvqObjectNotFoundException;

    Map<String,Integer> importDepositAccountsDetails(Long externalApplicationId, byte[] csvDatas)
            throws CvqModelException, CvqObjectNotFoundException;

    Map<String,Integer> importTicketingContracts(Long externalApplicationId, String broker, byte[] csvDatas)
            throws CvqModelException, CvqObjectNotFoundException;

    List<ExternalHomeFolder> getHomeFolders(Integer offset, Integer max) throws CvqException;

    ExternalHomeFolder getHomeFolder(Long id) throws CvqException;

    ExternalHomeFolder getHomeFolder(Long externalApplicationId, String externalId) throws CvqException;

    List<ExternalHomeFolder> getHomeFolders(Long externalApplicationId, Integer offset, Integer max) throws CvqException;

    void modifyHomeFolder(ExternalHomeFolder eh) throws CvqException;

    List<Adult> matchAdults(Long externalHomeFolderId);

    List<ExternalInvoiceItem> getExternalInvoiceItems(String externalApplicationId);

    List<ExternalDepositAccountItem> getExternalDepositAccountItems(String externalApplicationId);

    List<ExternalTicketingContractItem> getExternalTicketingContractItems(
            String externalApplicationId);
}

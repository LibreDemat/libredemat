package org.libredemat.util.logging.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.libredemat.business.payment.Payment;
import org.libredemat.business.users.Adult;
import org.libredemat.security.SecurityContext;
import org.libredemat.service.authority.ILocalAuthorityLifecycleAware;
import org.libredemat.service.authority.ILocalAuthorityRegistry;
import org.libredemat.util.logging.ILog;

import au.com.bytecode.opencsv.CSVWriter;


public class Log implements ILog, ILocalAuthorityLifecycleAware {

    private static ILocalAuthorityRegistry localAuthorityRegistry;
    
    private static String assetBase;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private void initLogger(String localAuthorityName) {
        Logger logger = Logger.getLogger(localAuthorityName);
        String logFileName = assetBase + SecurityContext.getCurrentSite().getName()
                + "/log/business.log";
        try {
            logger.addAppender(new FileAppender(new PatternLayout("%d %-5p - %m%n"), logFileName));
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger logger (String localAuthorityName) {
        return Logger.getLogger(localAuthorityName);
    }

    @Override
    public void addLocalAuthority(String localAuthorityName) {
        if (assetBase == null)
            assetBase = localAuthorityRegistry.getAssetsBase();
        initLogger(localAuthorityName);
    }

    public static void adultToCsV (Adult adult) {
        try {
            SecurityContext.getCurrentSite().getName();
            String homeFolderFile =  assetBase + SecurityContext.getCurrentSite().getName()
                    + "/log/homeFolder-" + dateFormat.format(new Date()) + ".csv";

            CSVWriter writer = new CSVWriter(new FileWriter(homeFolderFile, true));

            List<String> line = new ArrayList<String>();
            line.add(adult.getTitle().name());
            line.add(adult.getLastName());
            line.add(adult.getFirstName());
            line.add(adult.getEmail());
            line.add(adult.getHomePhone());
            line.add(adult.getMobilePhone());
            line.add(adult.getOfficePhone());
            line.add(adult.getAddress().getAdditionalDeliveryInformation());
            line.add(adult.getAddress().getAdditionalGeographicalInformation());
            line.add(adult.getAddress().getStreetNumber());
            line.add(adult.getAddress().getStreetName());
            line.add(adult.getAddress().getPlaceNameOrService());
            line.add(adult.getAddress().getPostalCode());
            line.add(adult.getAddress().getCity());
            line.add(adult.getAddress().getCountryName());
            line.add(adult.getQuestion());
            line.add(adult.getAnswer());

            writer.writeNext(line.toArray(new String[]{}));
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importedHomeFolderToCsv(Adult adult, String password, String externalId) {
        try {
            String importedHomeFolderFile = localAuthorityRegistry.getLocalAuthorityImportedHomeFoldersFile(0);
            CSVWriter writer = new CSVWriter(new FileWriter(importedHomeFolderFile, true));

            File file = new File(importedHomeFolderFile);
            if (!file.exists() || file.length() == 0) {
                List<String> line = new ArrayList<String>();
                line.add("Identifiant de compte");
                line.add("Identifiant métier");
                line.add("Prénom");
                line.add("Nom de famille");
                line.add("Identifiant");
                line.add("Mot de passe");
                line.add("Courriel");
                line.add("Adresse");
                writer.writeNext(line.toArray(new String[]{}));
            }

            List<String> line = new ArrayList<String>();
            line.add(adult.getHomeFolder().getId().toString());
            line.add(externalId);
            line.add(adult.getFirstName());
            line.add(adult.getLastName());
            line.add(adult.getLogin());
            line.add(password);
            line.add(adult.getEmail());
            line.add(adult.getAddress().getStreetNumber() == null ?
                    String.format("%s %s %s", adult.getAddress().getStreetName(),
                            adult.getAddress().getPostalCode(), adult.getAddress().getCity()) :
                    String.format("%s %s %s %s", adult.getAddress().getStreetNumber(),
                            adult.getAddress().getStreetName(), adult.getAddress().getPostalCode(), adult.getAddress().getCity()));

            writer.writeNext(line.toArray(new String[]{}));
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void paymentToCsv(Payment payment) {
        try {
            SecurityContext.getCurrentSite().getName();
            String paymentFile =  assetBase + SecurityContext.getCurrentSite().getName()
                    + "/log/payment-" + dateFormat.format(new Date()) + ".csv";
            CSVWriter writer = new CSVWriter(new FileWriter(paymentFile, true));

            File file = new File(paymentFile);
            if (!file.exists()) {
                List<String> line = new ArrayList<String>();
                line.add("BankReference");
                line.add("CvqReference");
                line.add("FormatedAmount");
                line.add("RequesterFirstName");
                line.add("RequesterLastName");
                line.add("HomeFolderId");
                line.add("InitializationDate");
                line.add("RequesterId");
                writer.writeNext(line.toArray(new String[]{}));
            }

            List<String> line = new ArrayList<String>();
            line.add(payment.getBankReference());
            line.add(payment.getCvqReference());
            line.add(payment.getFormatedAmount());
            line.add(payment.getRequesterFirstName());
            line.add(payment.getRequesterLastName());
            line.add(payment.getHomeFolderId().toString());
            line.add(payment.getInitializationDate().toString());
            line.add(payment.getRequesterId().toString());

            writer.writeNext(line.toArray(new String[]{}));
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeLocalAuthority(String localAuthorityName) {
    }

    public void setLocalAuthorityRegistry(ILocalAuthorityRegistry localAuthorityRegistry) {
        this.localAuthorityRegistry = localAuthorityRegistry;
    }
}

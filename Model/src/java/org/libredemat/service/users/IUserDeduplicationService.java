package org.libredemat.service.users;

import java.util.List;
import java.util.Map;

import org.libredemat.business.users.Adult;
import org.libredemat.business.users.HomeFolder;
import org.libredemat.exception.CvqInvalidTransitionException;
import org.libredemat.exception.CvqModelException;
import org.libredemat.security.annotation.IsUser;


public interface IUserDeduplicationService {

    /**
     * Return a summary of duplicates in homeFolderId for duplicateHomeFolderId
     * 
     * Key is either adults or children and value is the number of matches
     */
    Map<String, Long> countHomeFolderDuplicates(@IsUser Long homeFolderId, 
            Long duplicateHomeFolderId);

    /**
     * Return a summary (full name) of duplicates in homeFolderId for duplicateHomeFolderId
     */
    List<String> getHomeFolderDuplicates(Long homeFolderId, Long duplicateHomeFolderId);

    /**
     * Remove duplicate entry on targetHomeFolderId for homeFolderId
     */
    void removeDuplicate(@IsUser Long homeFolderId, Long targetHomeFolderId);
    
    /**
     * Merge homeFolderId into targetHomeFolderId
     */
    void mergeDuplicate(@IsUser Long homeFolderId, Long targetHomeFolderId)
        throws CvqModelException, CvqInvalidTransitionException;

    /**
     * find a duplicate adult for this adult
     */
    void findAdultDuplicates(Adult adult);

    void createMapping(HomeFolder homeFolderTarget);
}

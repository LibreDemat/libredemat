package org.libredemat.business.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.libredemat.business.document.DocumentType;


@SuppressWarnings("serial")
@Entity
@Table(name="global_home_folder_configuration")
/**
 * Global settings related to home folders.
 */
public class GlobalHomeFolderConfiguration implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    /**
     * Whether or not a home folder can be created without starting a request.
     */
    @Column(nullable=false)
    private Boolean independentCreation = false;

    /**
     * Block new account if it's a duplicate
     */
    @Column(name="block_duplicate_creation", nullable=false)
    private Boolean blockDuplicateCreation = false;

    /**
     * After how many days a non-validated account is deleted
     */
    @Column(name="pending_users_live_duration", nullable=false)
    private Integer pendingUsersLiveDuration = 10;

    /**
     * After how many days a notification is sent for non-validated account
     */
    @Column(name="pending_users_notification_purge", nullable=false)
    private Integer pendingUserNotificationPurge = 5;

    /**
     * Document types wished at home folder creation time.
     */
    @OneToMany
    @JoinTable(
        name="home_folder_wished_document_types",
        joinColumns={ @JoinColumn(name="global_home_folder_configuration_id") },
        inverseJoinColumns={ @JoinColumn(name="document_type_id") }
    )
    private Set<DocumentType> wishedDocumentTypes = new HashSet<DocumentType>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIndependentCreation() {
        return independentCreation;
    }

    public void setIndependentCreation(Boolean independent) {
        this.independentCreation = independent;
    }

    public Set<DocumentType> getWishedDocumentTypes() {
        return wishedDocumentTypes;
    }

    public void setWishedDocumentTypes(Set<DocumentType> documentTypes) {
        this.wishedDocumentTypes = documentTypes;
    }

    public Integer getPendingUserNotificationPurge() {
        return pendingUserNotificationPurge;
    }

    public Integer getPendingUsersLiveDuration() {
        return pendingUsersLiveDuration;
    }

    public void setPendingUserNotificationPurge(Integer pendingUserNotificationPurge) {
        this.pendingUserNotificationPurge = pendingUserNotificationPurge;
    }

    public void setPendingUsersLiveDuration(Integer pendingUsersLiveDuration) {
        this.pendingUsersLiveDuration = pendingUsersLiveDuration;
    }

    public Boolean getBlockDuplicateCreation() {
        return blockDuplicateCreation;
    }

    public void setBlockDuplicateCreation(Boolean blockDuplicateCreation) {
        this.blockDuplicateCreation = blockDuplicateCreation;
    }
}

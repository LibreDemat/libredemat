package org.libredemat.business.request;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="request_note")
public class RequestNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(length=32)
    private RequestNoteType type;

    @Column(length=1024)
    private String note;

    private Date date;

    @Column(name="attachment_name")
    private String attachmentName;

    @Column(name="attachment")
    private byte[] attachment;

    @Column(name="parent_id")
    private Long parentId;

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @OrderBy("date desc")
    @JoinColumn(name="parent_id")
    private Set<RequestNote> children;

    /** default constructor */
    public RequestNote() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RequestNoteType getType() {
        return this.type;
    }

    public void setType(RequestNoteType type) {
        this.type = type;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Set<RequestNote> getChildren() {
        return children;
    }

    public void setChildren(Set<RequestNote> children) {
        this.children = children;
    }

}

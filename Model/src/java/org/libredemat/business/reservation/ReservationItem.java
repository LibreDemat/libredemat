package org.libredemat.business.reservation;

import org.libredemat.business.users.Child;
import org.libredemat.business.users.HomeFolder;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author seb
 * Pojo of reservation item to stock reservations before validate them
 */
@Entity
@Table(name="reservation")
public class ReservationItem implements Serializable{
    
     private static final long serialVersionUID = 1L;
     
     @Id
     @GeneratedValue(strategy=GenerationType.SEQUENCE)
     private Long id;
     
     @Column(name="session")
     private String session;
     
     @Column(name="home_folder_id")
     private Long homeFolderId;
     
     @Column(name="child_id")
     private Long childId;
             
     @Column(name="activity_code")
     private String activityCode;
     
     @Column(name="activity_service_code")
     private String activityServiceCode;
     
     @Column(name="ativity_service_label")
     private String activityServiceLabel;
     
     @Column(name="date")     
     private Date day;
     
     @Column(name="day_type")
     private String dayType;
     
     @Column(name="color")
     private String color;

    public ReservationItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Long getHomeFolderId() {
        return homeFolderId;
    }

    public void setHomeFolderId(Long homeFolderId) {
        this.homeFolderId = homeFolderId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }
   
    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityServiceCode() {
        return activityServiceCode;
    }

    public void setActivityServiceCode(String activityServiceCode) {
        this.activityServiceCode = activityServiceCode;
    }
    
    public String getActivityServiceLabel() {
        return activityServiceLabel;
    }
    
    public void setActivityServiceLabel(String activityServiceLabel) {
        this.activityServiceLabel = activityServiceLabel;
    }

    public Date getDay() {
        return day;
    }
    
    public void setDay(Date day) {
        this.day = day;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
        
}

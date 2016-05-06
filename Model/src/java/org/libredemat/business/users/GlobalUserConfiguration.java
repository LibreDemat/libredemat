package org.libredemat.business.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="global_user_configuration")
public class GlobalUserConfiguration {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(name="instruction_alert_delay",nullable=false, columnDefinition = "int4 default 3")
    private int instructionAlertDelay = 3;

    @Column(name="instruction_max_delay",nullable=false, columnDefinition = "int4 default 10")
    private int instructionMaxDelay = 10;

    @Column(name="instruction_alerts_enabled",nullable=false, columnDefinition = "bool default false")
    private boolean instructionAlertsEnabled = false;

    @Column(name="instruction_alerts_detailed",nullable=false, columnDefinition = "bool default false")
    private boolean instructionAlertsDetailed = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getInstructionAlertDelay() {
        return instructionAlertDelay;
    }

    public void setInstructionAlertDelay(int instructionAlertDelay) {
        this.instructionAlertDelay = instructionAlertDelay;
    }

    public int getInstructionMaxDelay() {
        return instructionMaxDelay;
    }

    public void setInstructionMaxDelay(int instructionMaxDelay) {
        this.instructionMaxDelay = instructionMaxDelay;
    }

    public boolean isInstructionAlertsEnabled() {
        return instructionAlertsEnabled;
    }

    public void setInstructionAlertsEnabled(boolean instructionAlertsEnabled) {
        this.instructionAlertsEnabled = instructionAlertsEnabled;
    }

    public boolean isInstructionAlertsDetailed() {
        return instructionAlertsDetailed;
    }

    public void setInstructionAlertsDetailed(boolean instructionAlertsDetailed) {
        this.instructionAlertsDetailed = instructionAlertsDetailed;
    }

}

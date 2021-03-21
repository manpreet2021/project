package project.entity;

import project.dto.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import java.util.Date;

@Entity(name = "patient")
public class PatientEntity extends BaseModel {
    private String patientId;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "phonenumber",nullable = false,unique = true)
    private String phoneNumber;
    @Column(name = "address",nullable = false)
    private String address;
    @Column(name = "email",nullable = false,unique = true)
    @Email(message = "Invalid Email")
    private String email;
    @Column(name = "password",nullable = false)
    private String encryptedPassword;
    @Column(name = "appointmentdate")
    private Date appointmentdate;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Date getAppointmentdate() {
        return appointmentdate;
    }

    public void setAppointmentdate(Date appointmentdate) {
        this.appointmentdate = appointmentdate;
    }
}

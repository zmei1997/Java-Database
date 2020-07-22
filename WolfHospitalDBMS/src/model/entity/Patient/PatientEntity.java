package model.entity.Patient;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * object class of patient entity
 */
@Entity
@Table ( name = "patient", schema = "health" )
public class PatientEntity {
    private int PatientId;
    private Integer SSN;
    private String Name;
    private Date DateOfBirth;
    private String Gender;
    private int Age;
    private String PhoneNumber;
    private String Address;
    private String Status;

    /**
     * constructor of patient entity
     * @param patientId
     * @param SSN
     * @param name
     * @param dateOfBirth
     * @param gender
     * @param age
     * @param phoneNumber
     * @param address
     * @param status
     */
    public PatientEntity (final int patientId, final Integer SSN, final String name, final Date dateOfBirth,
                          final String gender, final int age, final String phoneNumber, final String address, final String status ) {

        this.PatientId = patientId;
        this.SSN = SSN;
        this.Name = name;
        this.DateOfBirth = dateOfBirth;
        this.Gender = gender;
        this.Age = age;
        this.PhoneNumber = phoneNumber;
        this.Address = address;
        this.Status = status;
    }

    /**
     * patient entity
     */
    public PatientEntity() {

    }

    @Id
    @Column ( name = "PatientId" )
    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(final int patientId ) {
        this.PatientId = patientId;
    }

    @Basic
    @Column ( name = "SSN" )
    public Integer getSSN() {
        return SSN;
    }

    public void setSSN(final Integer ssn ) {
        this.SSN = ssn;
    }

    @Basic
    @Column ( name = "Name" )
    public String getName() {
        return Name;
    }

    public void setName(final String name ) {
        this.Name = name;
    }

    @Basic
    @Column ( name = "DateOfBirth" )
    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(final Date dateOfBirth ) {
        this.DateOfBirth = dateOfBirth;
    }

    @Basic
    @Column ( name = "Gender" )
    public String getGender() {
        return Gender;
    }

    public void setGender(final String gender ) {
        this.Gender = gender;
    }

    @Basic
    @Column ( name = "Age" )
    public int getAge() {
        return Age;
    }

    public void setAge(final int age ) {
        this.Age = age;
    }

    @Basic
    @Column ( name = "PhoneNumber" )
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber ) {
        this.PhoneNumber = phoneNumber;
    }

    @Basic
    @Column ( name = "Address" )
    public String getAddress() {
        return Address;
    }

    public void setAddress(final String address ) {
        this.Address = address;
    }

    @Basic
    @Column ( name = "Status" )
    public String getStatus() {
        return Status;
    }

    public void setStatus(final String status ) {
        this.Status = status;
    }

    @Override
    public boolean equals ( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        final PatientEntity that = (PatientEntity) o;
        return PatientId == that.PatientId && Age == that.Age && Objects.equals(SSN, that.SSN)
                && Objects.equals(Name, that.Name) && Objects.equals(DateOfBirth, that.DateOfBirth)
                && Objects.equals(Gender, that.Gender) && Objects.equals(PhoneNumber, that.PhoneNumber)
                && Objects.equals(Address, that.Address) && Objects.equals(Status, that.Status);
    }

    @Override
    public int hashCode () {
        return Objects.hash(PatientId, SSN, Name, DateOfBirth, Gender, Age, PhoneNumber, Address, Status);
    }
}

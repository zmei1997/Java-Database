package model.entity.Hospital;

import javax.persistence.*;
import java.util.Objects;

/**
 * Table staff. Used to create staff table
 */
@Entity
@Table ( name = "staff", schema = "health" )
public class StaffEntity {
    private int StaffId;
    private int HospitalId;
    private String Name;
    private int Age;
    private String Gender;
    private String JobTitle;
    private String ProfessionalTitle;
    private String Department;
    private String Phone;
    private String EmailAddress;
    private String Address;

    /**
     * constructor of staff
     * @param staffId staffId
     * @param hospitalId hospitalId
     * @param name name
     * @param age age
     * @param gender gender
     * @param jobTitle jobTitle
     * @param professionalTitle professionalTitle
     * @param department department
     * @param phone phone
     * @param emailAddress emailAddress
     * @param address address
     */
    public StaffEntity ( final int staffId, final int hospitalId, final String name, final int age, final String gender,
            final String jobTitle, final String professionalTitle, final String department, final String phone,
            final String emailAddress, final String address ) {

        this.StaffId = staffId;
        this.HospitalId = hospitalId;
        this.Name = name;
        this.Age = age;
        this.Gender = gender;
        this.JobTitle = jobTitle;
        this.ProfessionalTitle = professionalTitle;
        this.Department = department;
        this.Phone = phone;
        this.EmailAddress = emailAddress;
        this.Address = address;
    }

    /**
     * default constructor
     */
    public StaffEntity() {
        // default
    }

    @Id
    @Column ( name = "StaffId" )
    public int getStaffId() {
        return StaffId;
    }

    public void setStaffId(final int staffId ) {
        this.StaffId = staffId;
    }

    @Basic
    @Column ( name = "HospitalId" )
    public int getHospitalId() {
        return HospitalId;
    }

    public void setHospitalId(final int hospitalId ) {
        this.HospitalId = hospitalId;
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
    @Column ( name = "Age" )
    public int getAge() {
        return Age;
    }

    public void setAge(final int age ) {
        this.Age = age;
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
    @Column ( name = "JobTitle" )
    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(final String jobTitle ) {
        this.JobTitle = jobTitle;
    }

    @Basic
    @Column ( name = "ProfessionalTitle" )
    public String getProfessionalTitle() {
        return ProfessionalTitle;
    }

    public void setProfessionalTitle(final String professionalTitle ) {
        this.ProfessionalTitle = professionalTitle;
    }

    @Basic
    @Column ( name = "Department" )
    public String getDepartment() {
        return Department;
    }

    public void setDepartment(final String department ) {
        this.Department = department;
    }

    @Basic
    @Column ( name = "Phone" )
    public String getPhone() {
        return Phone;
    }

    public void setPhone(final String phone ) {
        this.Phone = phone;
    }

    @Basic
    @Column ( name = "EmailAddress" )
    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(final String emailAddress ) {
        this.EmailAddress = emailAddress;
    }

    @Basic
    @Column ( name = "Address" )
    public String getAddress() {
        return Address;
    }

    public void setAddress(final String address ) {
        this.Address = address;
    }

    @Override
    public boolean equals ( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        final StaffEntity that = (StaffEntity) o;
        return StaffId == that.StaffId && HospitalId == that.HospitalId && Age == that.Age
                && Objects.equals(Name, that.Name) && Objects.equals(Gender, that.Gender)
                && Objects.equals(JobTitle, that.JobTitle)
                && Objects.equals(ProfessionalTitle, that.ProfessionalTitle)
                && Objects.equals(Department, that.Department) && Objects.equals(Phone, that.Phone)
                && Objects.equals(EmailAddress, that.EmailAddress) && Objects.equals(Address, that.Address);
    }

    @Override
    public int hashCode () {
        return Objects.hash(StaffId, HospitalId, Name, Age, Gender, JobTitle, ProfessionalTitle, Department, Phone,
                EmailAddress, Address);
    }
}

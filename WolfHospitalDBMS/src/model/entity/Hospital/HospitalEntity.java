package model.entity.Hospital;

import javax.persistence.*;
import java.util.Objects;

/**
 * Table hospital, used to create hospital table
 */
@Entity
@Table ( name = "hospital", schema = "health" )
public class HospitalEntity {
    private int HospitalId;
    private int AdministratorId;
    private String Address;
    private String Phone;
    private String Specialization1;
    private int ChargesPerDay1;
    private String Specialization2;
    private int ChargesPerDay2;
    private int Capacity;

    /**
     * constructor of hospital
     * @param hospitalId hospitalId
     * @param administratorId administratorId
     * @param address address
     * @param phone phone
     * @param specialization1 specialization1
     * @param chargesPerDay1 chargesPerDay1
     * @param specialization2 specialization2
     * @param chargesPerDay2 chargesPerDay2
     * @param capacity capacity
     */
    public HospitalEntity ( final int hospitalId, final int administratorId, final String address, final String phone,
                            final String specialization1, final int chargesPerDay1, final String specialization2,
                            final int chargesPerDay2, final int capacity ) {

        this.HospitalId = hospitalId;
        this.AdministratorId = administratorId;
        this.Address = address;
        this.Phone = phone;
        this.Specialization1 = specialization1;
        this.ChargesPerDay1 = chargesPerDay1;
        this.Specialization2 = specialization2;
        this.ChargesPerDay2 = chargesPerDay2;
        this.Capacity = capacity;
    }

    /**
     * default constructor
     */
    public HospitalEntity() {
        // default
    }

    @Id
    @Column ( name = "HospitalId" )
    public int getHospitalId() {
        return HospitalId;
    }

    public void setHospitalId(final int hospitalId ) {
        this.HospitalId = hospitalId;
    }

    @Basic
    @Column ( name = "AdministratorId" )
    public int getAdministratorId() {
        return AdministratorId;
    }

    public void setAdministratorId(final int administratorId ) {
        this.AdministratorId = administratorId;
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
    @Column ( name = "Phone" )
    public String getPhone() {
        return Phone;
    }

    public void setPhone(final String phone ) {
        this.Phone = phone;
    }

    @Basic
    @Column ( name = "Specialization1" )
    public String getSpecialization1() {
        return Specialization1;
    }

    public void setSpecialization1(final String specialization1 ) {
        this.Specialization1 = specialization1;
    }

    @Basic
    @Column ( name = "ChargesPerDay1" )
    public int getChargesPerDay1() {
        return ChargesPerDay1;
    }

    public void setChargesPerDay1(final int chargesPerDay1 ) {
        this.ChargesPerDay1 = chargesPerDay1;
    }

    @Basic
    @Column ( name = "Specialization2" )
    public String getSpecialization2() {
        return Specialization2;
    }

    public void setSpecialization2(final String specialization2 ) {
        this.Specialization2 = specialization2;
    }

    @Basic
    @Column ( name = "ChargesPerDay2" )
    public int getChargesPerDay2() {
        return ChargesPerDay2;
    }

    public void setChargesPerDay2(final int chargesPerDay2 ) {
        this.ChargesPerDay2 = chargesPerDay2;
    }

    @Basic
    @Column ( name = "Capacity" )
    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(final int capacity ) {
        this.Capacity = capacity;
    }

    @Override
    public boolean equals ( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        final HospitalEntity that = (HospitalEntity) o;
        return HospitalId == that.HospitalId && AdministratorId == that.AdministratorId
                && ChargesPerDay1 == that.ChargesPerDay1 && ChargesPerDay2 == that.ChargesPerDay2
                && Capacity == that.Capacity && Objects.equals(Address, that.Address)
                && Objects.equals(Phone, that.Phone) && Objects.equals(Specialization1, that.Specialization1)
                && Objects.equals(Specialization2, that.Specialization2);
    }

    @Override
    public int hashCode () {
        return Objects.hash(HospitalId, AdministratorId, Address, Phone, Specialization1, ChargesPerDay1,
                Specialization2, ChargesPerDay2, Capacity);
    }
}
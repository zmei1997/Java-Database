package model.entity.Hospital;

import javax.persistence.*;
import java.util.Objects;

/**
 * table bed, create a bed table
 */
@Entity
@Table(name = "bed", schema = "health")
@IdClass(BedEntityPK.class)
public class BedEntity {
    // BedNumber
    private int BedNumber;
    // HospitalId
    private int HospitalId;
    // PatientSSN
    private Integer PatientSSN;
    // Specialization
    private String Specialization;
    // ChargesPerDay
    private int ChargesPerDay;
    // NurseId
    private int NurseId;

    /**
     * constructor of BedEntity
     * @param bedNumber bedNumber
     * @param hospitalId hospitalId
     * @param patientSSN patientSSN
     * @param specialization specialization
     * @param chargesPerDay chargesPerDay
     * @param nurseId nurseId
     */
    public BedEntity(int bedNumber, int hospitalId, Integer patientSSN, String specialization, int chargesPerDay, int nurseId) {
        this.BedNumber = bedNumber;
        this.HospitalId = hospitalId;
        this.PatientSSN = patientSSN;
        this.Specialization = specialization;
        this.ChargesPerDay = chargesPerDay;
        this.NurseId = nurseId;
    }

    // default constructor
    public BedEntity() {
        // default
    }

    /**
     * getter of BedNumber
     * @return BedNumber
     */
    @Id
    @Column(name = "BedNumber")
    public int getBedNumber() {
        return BedNumber;
    }

    /**
     * setter of bedNumber
     * @param bedNumber bedNumber
     */
    public void setBedNumber(int bedNumber) {
        this.BedNumber = bedNumber;
    }

    /**
     * getter of HospitalId
     * @return HospitalId
     */
    @Id
    @Column(name = "HospitalId")
    public int getHospitalId() {
        return HospitalId;
    }

    /**
     * setter of hospitalId
     * @param hospitalId hospitalId
     */
    public void setHospitalId(int hospitalId) {
        this.HospitalId = hospitalId;
    }

    /**
     * getter of PatientSSN
     * @return PatientSSN
     */
    @Basic
    @Column(name = "PatientSSN")
    public Integer getPatientSSN() {
        return PatientSSN;
    }

    /**
     * setter of patientSSN
     * @param patientSSN patientSSN
     */
    public void setPatientSSN(Integer patientSSN) {
        this.PatientSSN = patientSSN;
    }

    /**
     * getter of Specialization
     * @return Specialization
     */
    @Basic
    @Column(name = "Specialization")
    public String getSpecialization() {
        return Specialization;
    }

    /**
     * setter of Specialization
     * @param specialization Specialization
     */
    public void setSpecialization(String specialization) {
        this.Specialization = specialization;
    }

    /**
     * getter of ChargesPerDay
     * @return ChargesPerDay
     */
    @Basic
    @Column(name = "ChargesPerDay")
    public int getChargesPerDay() {
        return ChargesPerDay;
    }

    /**
     * setter of ChargesPerDay
     * @param chargesPerDay ChargesPerDay
     */
    public void setChargesPerDay(int chargesPerDay) {
        this.ChargesPerDay = chargesPerDay;
    }

    /**
     * getter of NurseId
     * @return NurseId
     */
    @Basic
    @Column(name = "NurseId")
    public int getNurseId() {
        return NurseId;
    }

    /**
     * setter of nurseId
     * @param nurseId
     */
    public void setNurseId(int nurseId) {
        this.NurseId = nurseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BedEntity bedEntity = (BedEntity) o;
        return BedNumber == bedEntity.BedNumber &&
                HospitalId == bedEntity.HospitalId &&
                ChargesPerDay == bedEntity.ChargesPerDay &&
                NurseId == bedEntity.NurseId &&
                Objects.equals(PatientSSN, bedEntity.PatientSSN) &&
                Objects.equals(Specialization, bedEntity.Specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(BedNumber, HospitalId, PatientSSN, Specialization, ChargesPerDay, NurseId);
    }
}

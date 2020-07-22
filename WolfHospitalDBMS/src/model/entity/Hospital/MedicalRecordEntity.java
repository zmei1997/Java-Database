package model.entity.Hospital;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Table medicalrecord. Used to create medicalrecord table
 */
@Entity
@Table ( name = "medicalrecord", schema = "health" )
public class MedicalRecordEntity {
    private int MedicalRecordId;
    private int PatientId;
    private int HospitalId;
    private Date StartDate;
    private Date EndDate;
    private int DoctorId;
    private String Prescription;
    private String Diagnosic;
    private String Test;
    private String Result;
    private String Treatment;
    private int ConsultationFee;
    private int TestFee;
    private int TreatmentFee;

    /**
     * constructor of medicalrecord
     * @param medicalRecordId medicalRecordId
     * @param patientId patientId
     * @param hospitalId hospitalId
     * @param startDate startDate
     * @param endDate endDate
     * @param doctorId doctorId
     * @param prescription prescription
     * @param diagnosic diagnosic
     * @param test test
     * @param result result
     * @param treatment treatment
     * @param consultationFee consultationFee
     * @param testFee testFee
     * @param treatmentFee treatmentFee
     */
    public MedicalRecordEntity ( final int medicalRecordId, final int patientId, final int hospitalId,
            final Date startDate, final Date endDate, final int doctorId, final String prescription,
            final String diagnosic, final String test, final String result, final String treatment,
            final int consultationFee, final int testFee, final int treatmentFee ) {
        this.MedicalRecordId = medicalRecordId;
        this.PatientId = patientId;
        this.HospitalId = hospitalId;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.DoctorId = doctorId;
        this.Prescription = prescription;
        this.Diagnosic = diagnosic;
        this.Test = test;
        this.Result = result;
        this.Treatment = treatment;
        this.ConsultationFee = consultationFee;
        this.TestFee = testFee;
        this.TreatmentFee = treatmentFee;
    }

    public MedicalRecordEntity () {

    }

    @Id
    @Column ( name = "MedicalRecordId" )
    public int getMedicalRecordId() {
        return MedicalRecordId;
    }

    public void setMedicalRecordId(final int medicalRecordId ) {
        this.MedicalRecordId = medicalRecordId;
    }

    @Basic
    @Column ( name = "PatientId" )
    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(final int patientId ) {
        this.PatientId = patientId;
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
    @Column ( name = "StartDate" )
    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(final Date startDate ) {
        this.StartDate = startDate;
    }

    @Basic
    @Column ( name = "EndDate" )
    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(final Date endDate ) {
        this.EndDate = endDate;
    }

    @Basic
    @Column ( name = "DoctorId" )
    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(final int doctorId ) {
        this.DoctorId = doctorId;
    }

    @Basic
    @Column ( name = "Prescription" )
    public String getPrescription() {
        return Prescription;
    }

    public void setPrescription(final String prescription ) {
        this.Prescription = prescription;
    }

    @Basic
    @Column ( name = "Diagnosis" )
    public String getDiagnosis () {
        return Diagnosic;
    }

    public void setDiagnosis ( final String diagnosis ) {
        this.Diagnosic = Diagnosic;
    }

    @Basic
    @Column ( name = "Test" )
    public String getTest() {
        return Test;
    }

    public void setTest(final String test ) {
        this.Test = test;
    }

    @Basic
    @Column ( name = "Result" )
    public String getResult() {
        return Result;
    }

    public void setResult(final String result ) {
        this.Result = result;
    }

    @Basic
    @Column ( name = "Treatment" )
    public String getTreatment() {
        return Treatment;
    }

    public void setTreatment(final String treatment ) {
        this.Treatment = treatment;
    }

    @Basic
    @Column ( name = "ConsultationFee" )
    public int getConsultationFee() {
        return ConsultationFee;
    }

    public void setConsultationFee(final int consultationFee ) {
        this.ConsultationFee = consultationFee;
    }

    @Basic
    @Column ( name = "TestFee" )
    public int getTestFee() {
        return TestFee;
    }

    public void setTestFee(final int testFee ) {
        this.TestFee = testFee;
    }

    @Basic
    @Column ( name = "TreatmentFee" )
    public int getTreatmentFee() {
        return TreatmentFee;
    }

    public void setTreatmentFee(final int treatmentFee ) {
        this.TreatmentFee = treatmentFee;
    }

    @Override
    public boolean equals ( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        final MedicalRecordEntity that = (MedicalRecordEntity) o;
        return MedicalRecordId == that.MedicalRecordId && PatientId == that.PatientId && HospitalId == that.HospitalId
                && DoctorId == that.DoctorId && ConsultationFee == that.ConsultationFee && TestFee == that.TestFee
                && TreatmentFee == that.TreatmentFee && Objects.equals(StartDate, that.StartDate)
                && Objects.equals(EndDate, that.EndDate) && Objects.equals(Prescription, that.Prescription)
                && Objects.equals(Diagnosic, that.Diagnosic) && Objects.equals(Test, that.Test)
                && Objects.equals(Result, that.Result) && Objects.equals(Treatment, that.Treatment);
    }

    @Override
    public int hashCode () {
        return Objects.hash(MedicalRecordId, PatientId, HospitalId, StartDate, EndDate, DoctorId, Prescription,
                Diagnosic, Test, Result, Treatment, ConsultationFee, TestFee, TreatmentFee);
    }
}

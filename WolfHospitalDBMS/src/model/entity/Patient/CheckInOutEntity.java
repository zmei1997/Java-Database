package model.entity.Patient;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * object class of check in/out entity
 */
@Entity
@Table ( name = "checkinout", schema = "health" )
@IdClass ( CheckInOutEntityPK.class )
public class CheckInOutEntity {
    private int PatientId;
    private int HospitalId;
    private int DoctorId;
    private Integer BedNumber;
    private Date StartDate;
    private Date EndDate;
    private double RegistrationFee;
    private String DiagnosisDetail;

    /**
     * constructor of the check in/out entity
     * @param patientId
     * @param hospitalId
     * @param doctorId
     * @param bedNumber
     * @param startDate
     * @param endDate
     * @param registrationFee
     * @param diagnosisDetail
     */
    public CheckInOutEntity ( final int patientId, final int hospitalId, final int doctorId, final Integer bedNumber,
            final Date startDate, final Date endDate, final double registrationFee, final String diagnosisDetail ) {
        this.PatientId = patientId;
        this.HospitalId = hospitalId;
        this.DoctorId = doctorId;
        this.BedNumber = bedNumber;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.RegistrationFee = registrationFee;
        this.DiagnosisDetail = diagnosisDetail;
    }

    /**
     * empty constructor of check in/out entity
     */
    public CheckInOutEntity () {

    }

    /**
     * get patient id
     * @return
     */
    @Id
    @Column ( name = "PatientId" )
    public int getPatientId() {
        return PatientId;
    }

    /**
     * set patient id
     * @param patientId
     */
    public void setPatientId(final int patientId ) {
        this.PatientId = patientId;
    }

    /**
     * get hospital id
     * @return
     */
    @Id
    @Column ( name = "HospitalId" )
    public int getHospitalId() {
        return HospitalId;
    }

    /**
     * set hospital id
     * @param hospitalId
     */
    public void setHospitalId(final int hospitalId ) {
        this.HospitalId = hospitalId;
    }

    /**
     * get doctor id
     * @return
     */
    @Id
    @Column ( name = "DoctorId" )
    public int getDoctorId() {
        return DoctorId;
    }

    /**
     * set doctor id
     * @param doctorId
     */
    public void setDoctorId(final int doctorId ) {
        this.DoctorId = doctorId;
    }

    /**
     * get bed number
     * @return
     */
    @Basic
    @Column ( name = "BedNumber" )
    public Integer getBedNumber() {
        return BedNumber;
    }

    /**
     * set bed number
     * @param bedNumber
     */
    public void setBedNumber(final Integer bedNumber ) {
        this.BedNumber = bedNumber;
    }

    /**
     * get start date
     * @return
     */
    @Id
    @Column ( name = "StartDate" )
    public Date getStartDate() {
        return StartDate;
    }

    /**
     * set start date
     * @param startDate
     */
    public void setStartDate(final Date startDate ) {
        this.StartDate = startDate;
    }

    /**
     * get end date
     * @return
     */
    @Basic
    @Column ( name = "EndDate" )
    public Date getEndDate() {
        return EndDate;
    }

    /**
     * set end date
     * @param endDate
     */
    public void setEndDate(final Date endDate ) {
        this.EndDate = endDate;
    }

    /**
     * get registeration fee
     * @return
     */
    @Basic
    @Column ( name = "RegistrationFee" )
    public double getRegistrationFee() {
        return RegistrationFee;
    }

    /**
     * set registration fee
     * @param registrationFee
     */
    public void setRegistrationFee(final double registrationFee ) {
        this.RegistrationFee = registrationFee;
    }

    /**
     * get diagnosis detail
     * @return
     */
    @Basic
    @Column ( name = "DiagnosisDetail" )
    public String getDiagnosisDetail() {
        return DiagnosisDetail;
    }

    public void setDiagnosisDetail(final String diagnosisDetail ) {
        this.DiagnosisDetail = diagnosisDetail;
    }

    @Override
    public boolean equals ( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        final CheckInOutEntity that = (CheckInOutEntity) o;
        return PatientId == that.PatientId && HospitalId == that.HospitalId && DoctorId == that.DoctorId
                && Double.compare( that.RegistrationFee, RegistrationFee) == 0
                && Objects.equals(BedNumber, that.BedNumber) && Objects.equals(StartDate, that.StartDate)
                && Objects.equals(EndDate, that.EndDate) && Objects.equals(DiagnosisDetail, that.DiagnosisDetail);
    }

    @Override
    public int hashCode () {
        return Objects.hash(PatientId, HospitalId, DoctorId, BedNumber, StartDate, EndDate, RegistrationFee,
                DiagnosisDetail);
    }
}

package model.entity.Patient;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * object class of check in/out entity pk
 */
public class CheckInOutEntityPK implements Serializable {
    private int patientId;
    private int hospitalId;
    private int doctorId;
    private Date startDate;

    @Column(name = "PatientId")
    @Id
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Column(name = "HospitalId")
    @Id
    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Column(name = "DoctorId")
    @Id
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    @Column(name = "StartDate")
    @Id
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckInOutEntityPK that = (CheckInOutEntityPK) o;
        return patientId == that.patientId &&
                hospitalId == that.hospitalId &&
                doctorId == that.doctorId &&
                Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, hospitalId, doctorId, startDate);
    }
}

package model.dao.impl;

import model.dao.BedDao;
import model.entity.Hospital.BedEntity;
import model.entity.Patient.CheckInOutEntity;
import utils.DBUtils;

import java.util.Date;
import java.util.List;

/**
 * The implementation of BedDao containing the functions for the methods in BedDao
 */
public class BedDaoImpl implements BedDao {
    /**
     * scan all the beds in given hospital with given specialty,
     * then use the bed No. to scan in check-in-out records,
     * return the number of beds - the count of those beds with NULL end date
     *
     * @param hospitalId given hospital id
     * @param specialty  given specialty
     * @return the count of those beds with NOT NULL end date
     */
    @Override
    public int getAvailableBedsInHospitalGivenSpecialty(int hospitalId, String specialty) {
        // use transaction version
        return DBUtils.transactionGetAvailableBedsInHospitalGivenSpecialty(hospitalId, specialty);
    }

    /**
     * first call getAvailableBedsInHospitalGivenSpecialty to check if available,
     * if return number == 0, return.
     * else, we have available beds,
     * find a available bed, get its number,
     * add new check in tuple with given number (Insert)
     * MODIFY PATIENT STATUS HERE
     *
     * @param patientId  given patient
     * @param hospitalId given hospital
     * @param doctorId   assigned doctor
     * @param specialty  given specialty
     * @param startDate  start date
     * @return false if can't be reserved, true if successfully reserved
     */
    @Override
    public boolean reserveBed(int patientId, int hospitalId, int doctorId, String specialty, Date startDate) {
        // check availability first
        if (getAvailableBedsInHospitalGivenSpecialty(hospitalId, specialty) == 0) {
            return false;
        }

        // get list of all beds
        String sql = "SELECT * FROM Bed WHERE HospitalId = ? AND Specialization = ?";
        List<BedEntity> beds = DBUtils.getList(BedEntity.class, sql, hospitalId, specialty);
        // find an available bed number
        int bedNumber = -1;
        for (BedEntity bed : beds) {
            String sqlAvailable = "SELECT * FROM CheckInOut WHERE EndDate IS NULL AND BedNumber = ?";
            if (DBUtils.get(CheckInOutEntity.class, sqlAvailable, bed.getBedNumber()) == null) {
                // no occupying, bed available
                bedNumber = bed.getBedNumber();
                break;
            }
        }

        // insert new check in record
        CheckInOutEntity checkInOut = new CheckInOutEntity(
                patientId, hospitalId, doctorId, bedNumber, startDate, null, 20, "");
        String sqlCheck = "INSERT INTO CheckInOut (PatientId, HospitalId, DoctorId, BedNumber, StartDate, EndDate, RegistrationFee, DiagnosisDetail) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        DBUtils.update(sqlCheck, checkInOut.getPatientId(), checkInOut.getHospitalId(), checkInOut.getDoctorId(), checkInOut.getBedNumber(),
                checkInOut.getStartDate(), checkInOut.getEndDate(), checkInOut.getRegistrationFee(), checkInOut.getDiagnosisDetail());

        // update patient status
        String sqlPatient = "UPDATE Patient SET Status = ? WHERE PatientId = ?";
        DBUtils.update(sqlPatient, "Reserved", patientId);

        return true;
    }

    /**
     * use given patient, hospital, bed to find a check in record with NULL end date,
     * update its end date with given end date
     * MODIFY PATIENT STATUS HERE
     *
     * @param patientId  given patient
     * @param hospitalId given hospital
     * @param bedNumber  given bed number
     * @param endDate    given end date
     */
    @Override
    public void releaseBed(int patientId, int hospitalId, int bedNumber, Date endDate) {
        // USE TRANSACTION VERSION
        DBUtils.transactionReleaseBed(patientId, hospitalId, bedNumber, endDate);

//        // update check out record
//        String sqlCheck = "UPDATE CheckInOut SET endDate = ? WHERE PatientId = ? AND HospitalId = ? AND BedNumber = ?";
//        DBUtils.update(sqlCheck, endDate, patientId, hospitalId, bedNumber);
//
//        // update patient status
//        String sqlPatient = "UPDATE Patient SET Status = ? WHERE PatientId = ?";
//        DBUtils.update(sqlPatient, "Treatment complete", patientId);
    }
}

package model.dao.impl;

import model.dao.BedDao;
import model.dao.CheckInOutDao;
import model.entity.Patient.CheckInOutEntity;
import utils.DBUtils;

import java.util.Date;

/**
 * Implements functions of methods in CheckInOutDao class
 * @author Dunzhi Zhou
 */
public class CheckInOutDaoImpl implements CheckInOutDao {
    private BedDao bedDao = new BedDaoImpl();

    /**
     * query the checkinout entity with the given primary keys
     * @param patientId of the patient in the check in/out
     * @param hospitalId of the hospital in the check in/out
     * @param startDate of the check in/out
     * @return entity queried
     */
    @Override
    public CheckInOutEntity query(int patientId, int hospitalId, Date startDate) {
        String sql = "SELECT * FROM CheckInOut WHERE PatientId = ? AND HospitalId = ? AND StartDate = ?";
        return DBUtils.get(CheckInOutEntity.class, sql, patientId, hospitalId, startDate);
    }

    /**
     * first call getAvailableBedsInHospitalGivenSpecialty to check if available,
     * if return number = 0, return false
     * else, we have available beds,
     * find a available bed, get its number,
     * add new check in tuple with given number
     * MODIFY PATIENT STATUS HERE
     *
     * @param patientId  given patient
     * @param hospitalId given hospital
     * @param doctorId given doctor
     * @param specialty  given specialty
     * @param startDate  given startDate
     * @return false if unavailable, true if available
     */
    @Override
    public boolean assignPatientIfAvailable(int patientId, int hospitalId, int doctorId, String specialty, Date startDate) {
        // do reserve bed, which equal to assign patient, but the status is wrong
        if (!bedDao.reserveBed(patientId, hospitalId, doctorId, specialty, startDate))
            return false;

        // if succeeded,  update patient status
        String sqlPatient = "UPDATE Patient SET Status = ? WHERE PatientId = ?";
        DBUtils.update(sqlPatient, "In treatment", patientId);

        return true;
    }

    /**
     * first, find the old check in record with patientId, oldHospitalId, oldStartDate
     * update its end date with new start date
     * second, insert new check in record with patientId, newHospitalId, newStartDate
     *  @param patientId     given patient
     * @param oldHospitalId hospital from
     * @param newHospitalId hospital to
     * @param newDoctorId   new doctor
     * @param newBedNumber new bed number
     * @param oldStartDate  old start date
     * @param newStartDate  old end date, new start date
     */
    @Override
    public void managePatientTransfer(int patientId, int oldHospitalId, int newHospitalId, int newDoctorId,
                                      int newBedNumber, Date oldStartDate, Date newStartDate) {
        // checkout first
        String sqlOut = "UPDATE CheckInOut SET EndDate = ? WHERE PatientId = ? AND HospitalId = ? AND StartDate = ?";
        DBUtils.update(sqlOut, newStartDate, patientId, oldHospitalId, oldStartDate);

        // checkin then
        insert(new CheckInOutEntity(patientId, newHospitalId, newDoctorId,
                newBedNumber, newStartDate, null, 20, null));
    }

    /**
     * insert a new check in/out to the database
     * @param checkInOut entity to be inserted
     */
    @Override
    public void insert(CheckInOutEntity checkInOut) {
        /*8 KEY*/
        String sql = "INSERT INTO CheckInOut (PatientId, HospitalId, DoctorId, BedNumber, StartDate, EndDate, RegistrationFee, DiagnosisDetail) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        DBUtils.update(sql, checkInOut.getPatientId(), checkInOut.getHospitalId(), checkInOut.getDoctorId(), checkInOut.getBedNumber(),
                checkInOut.getStartDate(), checkInOut.getEndDate(), checkInOut.getRegistrationFee(), checkInOut.getDiagnosisDetail());
    }

    /**
     * update an existing check in/out entity with the same primary keys
     * @param checkInOut to update in the database
     */
    @Override
    public void update(CheckInOutEntity checkInOut) {
        /*8 KEY*/
        String sql = "UPDATE CheckInOut SET PatientId = ?, HospitalId = ?, DoctorId = ?, BedNumber = ?, " +
                "StartDate = ?, EndDate = ?, RegistrationFee = ?, DiagnosisDetail = ? " +
                "WHERE PatientId = ? AND HospitalId = ? AND DoctorId = ? AND StartDate = ?";
        DBUtils.update(sql, checkInOut.getPatientId(), checkInOut.getHospitalId(), checkInOut.getDoctorId(), checkInOut.getBedNumber(),
                checkInOut.getStartDate(), checkInOut.getEndDate(), checkInOut.getRegistrationFee(), checkInOut.getDiagnosisDetail(),
                checkInOut.getPatientId(), checkInOut.getHospitalId(), checkInOut.getDoctorId(), checkInOut.getStartDate());
    }
}

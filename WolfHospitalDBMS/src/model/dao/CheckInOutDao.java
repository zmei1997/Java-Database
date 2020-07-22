package model.dao;

import model.entity.Patient.CheckInOutEntity;

import java.util.Date;

/**
 * Contains the operations to manage the check in/out
 */
public interface CheckInOutDao {

    /**
     * query check in/out entity with the primary keys
     * @param patientId of the patient
     * @param hospitalId of the hospital
     * @param startDate from the check-in/out
     * @return entity with the keys
     */
    CheckInOutEntity query(int patientId, int hospitalId, Date startDate);

    /**
     * first call getAvailableBedsInHospitalGivenSpecialty to check if available,
     * if return number = 0, return false
     * else, we have available beds,
     * find a available bed, get its number,
     * add new check in tuple with given number
     * MODIFY PATIENT STATUS HERE
     * @param patientId given patient
     * @param hospitalId given hospital
     * @param doctorId given doctor id
     * @param specialty given specialty
     * @param startDate given startDate
     * @return false if unavailable, true if available
     */
    boolean assignPatientIfAvailable(int patientId, int hospitalId, int doctorId, String specialty, Date startDate);

    /**
     * first, find the old check in record with patientId, oldHospitalId, oldStartDate
     * update its end date with new start date
     * second, insert new check in record with patientId, newHospitalId, newStartDate
     * @param patientId given patient
     * @param oldHospitalId hospital from
     * @param newHospitalId hospital to
     * @param newDoctorId new doctor
     * @param newBedNumber given bed number
     * @param oldStartDate old start date
     * @param newStartDate old end date, new start date
     */
    void managePatientTransfer(int patientId, int oldHospitalId, int newHospitalId, int newDoctorId, int newBedNumber, Date oldStartDate, Date newStartDate);

    /**
     * insert a new check in/out entity
     * @param checkInOut to add
     */
    void insert(CheckInOutEntity checkInOut);

    /**
     * update the checkinout with the same primary keys
     * @param checkInOut to replace the old one
     */
    void update(CheckInOutEntity checkInOut);
}

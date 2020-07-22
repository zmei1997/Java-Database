package model.dao;

import java.util.Date;

/**
 * Bed Table Interface
 * @author Huang Yuanmin
 */
public interface BedDao {
    /**
     * scan all the beds in given hospital with given specialty,
     * then use the bed No. to scan in check-in-out records,
     * return the number of beds - the count of those beds with NULL end date
     *
     * @param hospitalId given hospital id
     * @param specialty  given specialty
     * @return the count of those beds with NOT NULL end date
     */
    int getAvailableBedsInHospitalGivenSpecialty(int hospitalId, String specialty);

    /**
     * first call getAvailableBedsInHospitalGivenSpecialty to check if available,
     * if return number = 0, return
     * else, we have available beds,
     * find a available bed, get its number,
     * add new check in tuple with given number
     * MODIFY PATIENT STATUS HERE
     * @param patientId given patient
     * @param hospitalId given hospital
     * @param doctorId assigned doctor
     * @param specialty given specialty
     * @param startDate start date
     * @return false if can't be reserved, true if successfully reserved
     */
    boolean reserveBed(int patientId, int hospitalId, int doctorId, String specialty, Date startDate);

    /**
     * use given patient, hospital, bed to find a check in record with NULL end date,
     * update its end date with given end date
     * MODIFY PATIENT STATUS HERE
     * @param patientId given patient
     * @param hospitalId given hospital
     * @param bedNumber given bed number
     * @param endDate given end date
     */
    void releaseBed(int patientId, int hospitalId, int bedNumber, Date endDate);
}

package model.dao.impl;

/**
 * PatientDao methods and functions are implemented here
 *
 * @author Dunzhi Zhou
 */

import model.dao.PatientDao;
import model.entity.Patient.PatientEntity;
import utils.DBUtils;

/**
 * Implements the functions of PatientDao
 */
public class PatientDaoImpl implements PatientDao {
    /** patient status */
    static final String IN_TREATMENT = "In treatment";
    static final String TREATMENT_COMPLETE = "Treatment complete";
    static final String RESERVERED = "Reserved";

    /**
     * query and return a patient entity with the patient id
     * @param patientId of the patient to query
     * @return patient entity
     */
    @Override
    public PatientEntity query(int patientId) {
        String sql = "SELECT * FROM Patient WHERE PatientId = ?";
        return DBUtils.get(PatientEntity.class, sql, patientId);
    }

    /**
     * Insert Patient
     *
     * @param patient Patient
     */
    @Override
    public void insert(PatientEntity patient) {
        /*9 KEYS*/
        String sql = "INSERT INTO Patient (PatientID, SSN, Name, DateOfBirth, Gender, Age, PhoneNumber, Address, Status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DBUtils.update(sql, patient.getPatientId(), patient.getSSN(), patient.getName(), patient.getDateOfBirth(),
                patient.getGender(), patient.getAge(), patient.getPhoneNumber(), patient.getAddress(), patient.getStatus());
    }

    /**
     * Delete Patient via ID
     *
     * @param patientId ID
     */
    @Override
    public void delete(int patientId) {
        String sql = "DELETE FROM Patient WHERE PatientID = ?";
        DBUtils.update(sql, patientId);
    }

    /**
     * Update Patient information via ID
     *
     * @param patient patient
     */
    @Override
    public void update(PatientEntity patient) {
        String sql = "UPDATE Patient SET SSN = ?, Name = ?, Gender = ?, Age = ?, PhoneNumber = ?, Status = ? " +
                "WHERE PatientId = ?";
        DBUtils.update(sql, patient.getSSN(), patient.getName(), patient.getGender(), patient.getAge(),
                patient.getPhoneNumber(), patient.getStatus(), patient.getPatientId());
    }
}

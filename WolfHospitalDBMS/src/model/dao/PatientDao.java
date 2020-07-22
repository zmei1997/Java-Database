package model.dao;

import model.entity.Patient.PatientEntity;

/**
 * contains operations to manage patient
 */
public interface PatientDao {

    /**
     * query patient entity with the id
     * @param patientId
     * @return patient
     */
    PatientEntity query(int patientId);

    /**
     * add a new patient
     * @param patient
     */
    void insert(PatientEntity patient);

    /**
     * delete a patient with the id
     * @param patientId
     */
    void delete(int patientId);

    /**
     * update the patient with the same id
     * @param patient
     */
    void update(PatientEntity patient);
}

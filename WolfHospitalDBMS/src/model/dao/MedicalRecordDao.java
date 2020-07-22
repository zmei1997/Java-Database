package model.dao;

import model.entity.Hospital.MedicalRecordEntity;

/**
 * Contains operations to manage medical records
 */
public interface MedicalRecordDao {

    /**
     * query a medical record with its id
     * @param medicalRecordId
     * @return medical record entity
     */
    MedicalRecordEntity query(int medicalRecordId);

    /**
     * insert a new medical record
     * @param medicalRecord
     */
    void insert(MedicalRecordEntity medicalRecord);

    /**
     * update an existing medical record with the same id
     * @param medicalRecord
     */
    void update(MedicalRecordEntity medicalRecord);


}

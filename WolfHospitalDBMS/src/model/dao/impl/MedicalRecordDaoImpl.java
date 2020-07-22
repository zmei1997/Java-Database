package model.dao.impl;

import model.dao.MedicalRecordDao;
import model.entity.Hospital.MedicalRecordEntity;
import utils.DBUtils;

/**
 * Implements methods and functions in the MedicalRecordDao
 */
public class MedicalRecordDaoImpl implements MedicalRecordDao {

    /**
     * query the medical record with the given medical record id
     * @param medicalRecordId of the entity to return
     * @return entity with the id
     */
    @Override
    public MedicalRecordEntity query(int medicalRecordId) {
        String sql = "SELECT * FROM MedicalRecord WHERE MedicalRecordId = ?";
        return DBUtils.get(MedicalRecordEntity.class, sql, medicalRecordId);
    }

    /**
     * insert a new medical record entity the database
     * @param medicalRecord to insert
     */
    @Override
    public void insert(MedicalRecordEntity medicalRecord) {
        String sql = "INSERT INTO MedicalRecord " +
                "(MedicalRecordId, PatientId, HospitalId, StartDate, EndDate, DoctorId, " +
                "Prescription, Diagnosic, Test, Result, Treatment, ConsultationFee, TestFee, TreatmentFee) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        DBUtils.update(sql, medicalRecord.getMedicalRecordId(), medicalRecord.getPatientId(), medicalRecord.getHospitalId(), medicalRecord.getStartDate(),
                medicalRecord.getEndDate(), medicalRecord.getDoctorId(), medicalRecord.getPrescription(), medicalRecord.getDiagnosis(), medicalRecord.getTest(),
                medicalRecord.getResult(), medicalRecord.getTreatment(), medicalRecord.getConsultationFee(),
                medicalRecord.getTestFee(), medicalRecord.getTreatmentFee());
    }

    /**
     * update medical record with an existing one with the same id
     * @param medicalRecord to update the old one
     */
    @Override
    public void update(MedicalRecordEntity medicalRecord) {
        String sql = "UPDATE MedicalRecord SET PatientId = ?, HospitalId = ?, StartDate = ?, EndDate = ?, " +
                "DoctorId = ?, Prescription = ?, Diagnosic = ?, Test = ?," +
                "Result = ?, Treatment = ?, ConsultationFee = ?, TestFee = ?, TreatmentFee = ? " +
                "WHERE MedicalRecordId = ?";
        DBUtils.update(sql, medicalRecord.getPatientId(), medicalRecord.getHospitalId(),
                medicalRecord.getStartDate(), medicalRecord.getEndDate(), medicalRecord.getDoctorId(),
                medicalRecord.getPrescription(), medicalRecord.getDiagnosis(), medicalRecord.getTest(),
                medicalRecord.getResult(), medicalRecord.getTreatment(), medicalRecord.getConsultationFee(),
                medicalRecord.getTestFee(), medicalRecord.getTreatmentFee(), medicalRecord.getMedicalRecordId());
    }
}

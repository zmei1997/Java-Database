package model.dao.impl;

import model.dao.BillingAccountDao;
import model.entity.Hospital.BedEntity;
import model.entity.Hospital.MedicalRecordEntity;
import model.entity.Patient.BillingAccountEntity;
import model.entity.Patient.CheckInOutEntity;
import utils.DBUtils;

import java.util.*;

/**
 * Implementation of funcions in BillingAccountDao
 */
public class BillingAccountDaoImpl implements BillingAccountDao {
    /**
     * query and return the billing account entity based on the billing account id
     * @param billingAccountId of the account to query
     * @return entity with the id
     */
    @Override
    public BillingAccountEntity query(int billingAccountId) {
        String sql = "SELECT * FROM BillingAccount WHERE BillingAccountId = ?";
        return DBUtils.get(BillingAccountEntity.class, sql, billingAccountId);
    }

    /**
     * CHECK AVAILABILITY BEFORE INSERT
     * insert a new billing account entity to the database
     * @param billingAccount generated billing account
     */
    @Override
    public void insert(BillingAccountEntity billingAccount) {
        // 6 attributes
        String sql = "INSERT INTO BillingAccount (BillingAccountId, MedicalRecordId, PatientId, BillingAddress, Method, CardNumber) " +
                "VALUES (?,?,?,?,?,?)";
        DBUtils.update(sql,
                billingAccount.getBillingAccountId(), billingAccount.getMedicalRecordId(), billingAccount.getPatientId(),
                billingAccount.getBillingAddress(), billingAccount.getMethod(), billingAccount.getCardNumber());
    }

    /**
     * CHECK AVAILABILITY BEFORE INSERT
     * update a existing entity with a new one with the same id
     * @param billingAccount generated billing account
     */
    @Override
    public void update(BillingAccountEntity billingAccount) {
        String sql = "UPDATE BillingAccount SET MedicalRecordId = ?, PatientId = ?, BillingAddress = ?, " +
                "Method = ?, CardNumber = ? WHERE BillingAccountId = ?";
        DBUtils.update(sql,
                billingAccount.getMedicalRecordId(), billingAccount.getPatientId(), billingAccount.getBillingAddress(),
                billingAccount.getMethod(), billingAccount.getCardNumber(), billingAccount.getBillingAccountId());
    }

    /**
     * use patient id and start date and end date to scan in:
     * 1. check in out, to find registration fee
     * 2. medical record, to find:
     * a. consultation fee
     * b. test fee
     * c. treatment fee
     * d. charges per day * days (computed from the length of stay of a patient in the record)
     *
     * @param patientId given patient
     * @param startDate given start date
     * @param endDate   given end date
     * @return map of fee name and fee value
     */
    @Override
    public List<Map<String, Integer>> getBillingHistory(int patientId, Date startDate, Date endDate) {
        List<Map<String, Integer>> result = new ArrayList<>();

        List<CheckInOutEntity> check = DBUtils.getList(CheckInOutEntity.class, "SELECT * FROM CheckInOut WHERE PatientId = ? AND StartDate >= ? AND (EndDate <= ? OR EndDate IS NULL)", patientId, startDate, endDate);
        List<MedicalRecordEntity> medicalRecord = DBUtils.getList(MedicalRecordEntity.class, "SELECT * FROM MedicalRecord WHERE PatientId = ? AND StartDate >= ? AND (EndDate <= ? OR EndDate IS NULL)", patientId, startDate, endDate);

        if (medicalRecord.size() == 0) {
            return result;
        }

        for (int i = 0; i < medicalRecord.size(); i++) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("Registration fee", (int) check.get(i).getRegistrationFee());
            map.put("Consultation fee", medicalRecord.get(i).getConsultationFee());
            map.put("Test fee", medicalRecord.get(i).getTestFee());
            map.put("Treatment fee", medicalRecord.get(i).getTreatmentFee());
            BedEntity bed = DBUtils.get(BedEntity.class, "SELECT * FROM Bed WHERE BedNumber = " + check.get(i).getBedNumber() + ";");
            assert bed != null;
            map.put("Hospital expense", (int) (bed.getChargesPerDay() * (((medicalRecord.get(i).getEndDate().getTime() - medicalRecord.get(i).getStartDate().getTime()) / 86400000) + 1)));
            result.add(map);
        }

        return result;
    }

}

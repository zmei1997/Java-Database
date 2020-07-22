package model.dao;

import model.entity.Patient.BillingAccountEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Contains all operations to manage Billing Account
 */
public interface BillingAccountDao {

    /**
     * quert the entity with the given billing account id
     * @param billingAccountId to query
     * @return entity with the id
     */
    BillingAccountEntity query(int billingAccountId);

    /**
     * CHECK AVAILABILITY BEFORE INSERT
     * @param billingAccount generated billing account
     */
    void insert(BillingAccountEntity billingAccount);

    /**
     * CHECK AVAILABILITY BEFORE INSERT
     * @param billingAccount generated billing account
     */
    void update(BillingAccountEntity billingAccount);

    /**
     * use patient id and start date and end date to scan in:
     * 1. check in out, to find registration fee
     * 2. medical record, to find:
     *      a. consultation fee
     *      b. test fee
     *      c. treatment fee
     *      d. charges per day * days (computed from the length of stay of a patient in the record)
     * @param patientId given patient
     * @param startDate given start date
     * @param endDate given end date
     * @return map of fee name and fee value
     */
    List<Map<String, Integer>> getBillingHistory(int patientId, Date startDate, Date endDate);
}

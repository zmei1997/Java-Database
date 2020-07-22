package model.dao.impl;

import model.dao.HospitalDao;
import model.entity.Hospital.HospitalEntity;
import model.entity.Hospital.MedicalRecordEntity;
import utils.DBUtils;

import java.util.*;

/**
 * Implements HospitalDao methods and functions
 * @author Dunzhi Zhou Huang Yuanmin
 */
public class HospitalDaoImpl implements HospitalDao {
    /**
     * query and return a hospital entity with the hospital id
     * @param hospitalId of the entity
     * @return entity with the hospital id
     */
    @Override
    public HospitalEntity query ( final int hospitalId ) {
        String sql = "SELECT * FROM Hospital WHERE HospitalId = ?";
        return DBUtils.get( HospitalEntity.class, sql, hospitalId );
    }

    /**
     * scan the medical record table to count the number of patients, whose
     * record's: 1. start date is <= given start date AND (end date is >= given
     * start date OR end date IS NULL) 2. start date is > given start date AND
     * start date is <= given end date , in the given hospital
     *
     * @param hospitalId
     *            given hospital id
     * @param startDate
     *            the start day of a month
     * @param endDate
     *            the end day of a month
     * @return the total number of patients in all hospitals within the given
     *         month
     */
    @Override
    public int getNumberOfPatientsPerMonth(int hospitalId, Date startDate, Date endDate) {
        String sql = "SELECT * FROM MedicalRecord WHERE HospitalId = ? AND (" +
                "(StartDate <= ? AND (EndDate >= ? OR EndDate IS NULL)) " +
                "OR " +
                "(StartDate >= ? AND StartDate <= ?)" +
                ")";
        List<MedicalRecordEntity> result = DBUtils.getList(MedicalRecordEntity.class, sql,
                        hospitalId, startDate, endDate, startDate, startDate);

        return result.size();
    }

    /**
     * Insert into table
     *
     * @param hospital to insert
     */
    @Override
    public void insert(HospitalEntity hospital) {
        String sql = "INSERT INTO Hospital (HospitalId, AdministratorId, Address, Phone, Specialization1,"
                + "ChargesPerDay1, Specialization2, ChargesPerDay2, Capacity) " +
                "Values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DBUtils.update( sql, hospital.getHospitalId(), hospital.getAdministratorId(),hospital.getAddress(), hospital.getPhone(),
                hospital.getSpecialization1(), hospital.getChargesPerDay1(), hospital.getSpecialization2(),
                hospital.getChargesPerDay2(), hospital.getCapacity() );
    }

    /**
     * Delete row from the table
     *
     * @param hospitalId to delete
     */
    @Override
    public void delete(int hospitalId) {
        String sql = "DELETE FROM Hospital WHERE HospitalId = ?";
        DBUtils.update(sql, hospitalId);
    }

    /**
     * Update row of the table
     *
     * @param hospital to update
     */
    @Override
    public void update(HospitalEntity hospital) {
        String sql = "UPDATE Hospital SET AdministratorId = ?, Address = ?, Phone = ?, Specialization1 = ?,"
                + "ChargesPerDay1 = ?, Specialization2 = ?, ChargesPerDay2 = ?, Capacity = ? WHERE HospitalId = ?";
        DBUtils.update( sql, hospital.getAdministratorId(), hospital.getAddress(),
                hospital.getPhone(), hospital.getSpecialization1(), hospital.getChargesPerDay1(),
                hospital.getSpecialization2(), hospital.getChargesPerDay2(), hospital.getCapacity(),
                hospital.getHospitalId());
    }

    /**
     * first scan the check in/out table to count the number of check in
     * records, of which the end date IS NULL(, which means that the patient is
     * currently in a hospital) then group the numbers by hospital id
     *
     * @return a map of the hospital id and the number of patients currently in
     *         the hospital
     */
    @Override
    public Map<Integer, Integer> getAllHospitalUsageStatus() {
        Map<Integer, Integer> map = new HashMap<>();

        // initialize all entries for all hospitals
        String sqlHospital = "SELECT * FROM Hospital";
        List<HospitalEntity> hospitals = DBUtils.getList(HospitalEntity.class, sqlHospital);
        for (HospitalEntity hospital : hospitals) {
            map.put(hospital.getHospitalId(), 0);
        }

        // get queried map
        Map<Integer, Integer> queried = DBUtils.getAllHospitalUsageStatus();
        // update entries
        for (Map.Entry<Integer, Integer> entry : queried.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }

    /**
     * first scan the check in/out table to count the number of check in
     * records, of which the end date IS NULL(, which means that the patient is
     * currently in a hospital), in a given hospital then scan the hospital
     * table to query the capacity of the hospital finally return the first
     * value by second
     *
     * @param hospitalId given hospital
     * @return the number of patients currently / capacity
     */
    @Override
    public float getHospitalUsagePercentage(int hospitalId) {
        int numOfPatients = getAllHospitalUsageStatus().get(hospitalId) != null ? getAllHospitalUsageStatus().get(hospitalId) : 0;

        String sql = "SELECT * FROM Hospital WHERE HospitalId = ?";
        HospitalEntity hospital = DBUtils.get(HospitalEntity.class, sql, hospitalId);

        assert hospital != null;
        return (float)numOfPatients / hospital.getCapacity();
    }

    /**
     * get all hospitals
     * insert into map
     * return a map of specialty to list of hospitals
     * @return list of hospitals order by specialty 1 and 2
     */
    @Override
    public Map<String, List<HospitalEntity>> getHospitalsGroupBySpecialty() {
        Map<String, List<HospitalEntity>> map = new HashMap<>();

        String sql = "SELECT * FROM Hospital";
        List<HospitalEntity> hospitals = DBUtils.getList(HospitalEntity.class, sql);

        for (HospitalEntity hospital : hospitals) {
            if (map.get(hospital.getSpecialization1()) == null) {// new spec
                List<HospitalEntity> list = new ArrayList<>();
                list.add(hospital);
                map.put(hospital.getSpecialization1(), list);
            } else {// old spec
                map.get(hospital.getSpecialization1()).add(hospital);
            }
            if (map.get(hospital.getSpecialization2()) == null) {// new spec
                List<HospitalEntity> list = new ArrayList<>();
                list.add(hospital);
                map.put(hospital.getSpecialization2(), list);
            } else {// old spec
                map.get(hospital.getSpecialization2()).add(hospital);
            }
        }

        return map;
    }
}

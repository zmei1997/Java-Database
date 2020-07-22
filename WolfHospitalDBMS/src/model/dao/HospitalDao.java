package model.dao;

import model.entity.Hospital.HospitalEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HospitalDao {
    HospitalEntity query(int hospitalId);

    void insert(HospitalEntity hospital);

    void delete(int hospitalId);

    void update(HospitalEntity hospital);

    /**
     * first scan the check in/out table to count the number of check in records,
     * of which the end date IS NULL(, which means that the patient is currently
     * in a hospital)
     * then group the numbers by hospital id
     * @return a map of the hospital id and the number of patients currently in the hospital
     */
    Map<Integer, Integer> getAllHospitalUsageStatus();

    /**
     * scan the medical record table to count the number of patients, whose record's:
     * 1. start date is <= given start date
     *      AND (end date is >= given start date OR end date IS NULL)
     * 2. start date is > given start date
     *      AND start date is <= given end date
     * , in the given hospital
     * @param hospitalId given hospital id
     * @param startDate the start day of a month
     * @param endDate the end day of a month
     * @return the total number of patients in all hospitals within the given month
     */
    int getNumberOfPatientsPerMonth(int hospitalId, Date startDate, Date endDate);

    /**
     * first scan the check in/out table to count the number of check in records,
     * of which the end date IS NULL(, which means that the patient is currently
     * in a hospital), in a given hospital
     * then scan the hospital table to query the capacity of the hospital
     * finally return the first value by second
     * @param hospitalId given hospital
     * @return the number of patients currently / capacity
     */
    float getHospitalUsagePercentage(int hospitalId);

    /**
     * get all hospitals
     * insert into map
     * return a map of specialty to list of hospitals
     * @return list of hospitals order by specialty 1 and 2
     */
    Map<String, List<HospitalEntity>> getHospitalsGroupBySpecialty();
}

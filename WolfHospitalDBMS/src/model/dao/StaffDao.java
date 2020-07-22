package model.dao;

import model.entity.Hospital.StaffEntity;

import java.util.List;

/**
 * StaffDao contains operations to manage staff in the DB
 */
public interface StaffDao {
    StaffEntity query(int staffId);

    void insert(StaffEntity staff);

    void delete(int staffId);

    void update(StaffEntity staff);

    /**
     * first scan check in/out table to find all the check in records whose:
     * 1. patient id = given patient id
     * 2. end date IS NULL
     * , which means that the patient is currently seeing the doctor
     * use those doctorIds returned by last query to scan in staff table,
     * to get all the tuples of doctors
     * @param patientId given patient
     * @return list of doctor entities
     */
    List<StaffEntity> getAllDoctorsAPatientIsSeeing(int patientId);
}

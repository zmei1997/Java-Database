package model.dao.impl;

import model.dao.StaffDao;
import model.entity.Hospital.StaffEntity;
import model.entity.Patient.CheckInOutEntity;
import utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements functions in StaffDao
 */
public class StaffDaoImpl implements StaffDao {

    /**
     * query a staff entity with the staff id
     * @param staffId of the entity to return
     * @return staff entity
     */
    @Override
    public StaffEntity query(int staffId) {
        String sql = "SELECT * FROM Staff WHERE StaffId = ?";
        return DBUtils.get(StaffEntity.class, sql, staffId);
    }

    /**
     * insert a new staff entity
     * @param staff to enter
     */
    @Override
    public void insert(StaffEntity staff) {/*11 KEY*/
        String sql = "INSERT INTO Staff (StaffId, HospitalId, Name, Age, Gender, JobTitle, ProfessionalTitle, " +
                "Department, Phone, EmailAddress, Address) Values (?,?,?,?,?,?,?,?,?,?,?)";
        DBUtils.update(sql, staff.getStaffId(), staff.getHospitalId(),staff.getName(),staff.getAge(),
                staff.getGender(),staff.getJobTitle(),staff.getProfessionalTitle(), staff.getDepartment(), staff.getPhone(),
                staff.getEmailAddress(), staff.getAddress());
    }

    /**
     * delete an existing staff entity
     * @param staffId of the entity to remove
     */
    @Override
    public void delete(int staffId) {
        String sql = "DELETE FROM Staff WHERE StaffId = ?";
        DBUtils.update(sql, staffId);
    }

    /**
     * update the staff entity with the same staff id to replace the old one
     * @param staff to update
     */
    @Override
    public void update(StaffEntity staff) {
        String sql = "UPDATE Staff SET HospitalId = ?, Name = ?, Age = ?, Gender = ?, JobTitle = ?, " +
                "ProfessionalTitle = ?, Department = ?, Phone = ?, EmailAddress = ?, Address = ? WHERE StaffId = ?";
        DBUtils.update(sql, staff.getHospitalId(), staff.getName(), staff.getAge(), staff.getGender(),
                staff.getJobTitle(), staff.getProfessionalTitle(), staff.getDepartment(), staff.getPhone(), staff.getEmailAddress(),
                staff.getAddress(), staff.getStaffId());
    }

    /**
     * first scan check in/out table to find all the check in records whose:
     * 1. patient id = given patient id
     * 2. end date IS NULL
     * , which means that the patient is currently seeing the doctor
     * use those doctorIds returned by last query to scan in staff table,
     * to get all the tuples of doctors
     *
     * @param patientId given patient
     * @return list of doctor entities
     */
    @Override
    public List<StaffEntity> getAllDoctorsAPatientIsSeeing(int patientId) {
        List<StaffEntity> doctors = new ArrayList<>();

        // get list of check in records
        String sqlCheck = "SELECT * FROM CheckInOut WHERE PatientId = ? AND EndDate IS NULL";
        List<CheckInOutEntity> checkInOuts = DBUtils.getList(CheckInOutEntity.class, sqlCheck, patientId);

        // get all doctor entities
        for (CheckInOutEntity checkInOut : checkInOuts) {
            doctors.add(query(checkInOut.getDoctorId()));
        }

        return doctors;
    }
}

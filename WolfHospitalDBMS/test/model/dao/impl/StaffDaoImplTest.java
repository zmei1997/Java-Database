package test.model.dao.impl;

import model.dao.StaffDao;
import model.dao.impl.StaffDaoImpl;
import model.entity.Hospital.StaffEntity;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test implementation of StaffDap
 */
public class StaffDaoImplTest {

    //construct a new staff dao object
    private final StaffDao sd = new StaffDaoImpl();

    /**
     * test insert, query, delete, update and get all doctors a given patient is seeing
     */
    @Test
    public void test() {
        final StaffEntity s = new StaffEntity(1, 111, "Alex", 55, "M", "Doctor", "Senior Surgeon", "pediatrics", "333", "newbee@ncsu.edu", "Drive Drive");
        sd.insert(s);
        assertEquals(1, sd.query(1).getStaffId());
        assertEquals(111, sd.query(1).getHospitalId());
        assertEquals("Alex", sd.query(1).getName());
        assertEquals("M", sd.query(1).getGender());
        assertEquals("Doctor", sd.query(1).getJobTitle());
        final StaffEntity s1 = new StaffEntity(1, 111, "George", 55, "M", "Doctor", "Senior Surgeon", "pediatrics", "333", "newbee@ncsu.edu", "Drive Drive");
        sd.update(s1);
        assertEquals("George", sd.query(1).getName());
        sd.delete(1);
        assertNull(sd.query(1));

        List<StaffEntity> staffList = sd.getAllDoctorsAPatientIsSeeing(3002);
        StaffEntity doctor = staffList.get(0);
        assertEquals(1003, doctor.getStaffId());
        assertEquals(111, doctor.getHospitalId());
        assertEquals("Lucy", doctor.getName());
        assertEquals(40, doctor.getAge());
        assertEquals("F", doctor.getGender());

    }

}

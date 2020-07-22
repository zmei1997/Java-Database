package test.model.dao.impl;

import model.dao.BedDao;
import model.dao.CheckInOutDao;
import model.dao.PatientDao;
import model.dao.impl.BedDaoImpl;
import model.dao.impl.CheckInOutDaoImpl;
import model.dao.impl.PatientDaoImpl;
import model.entity.Patient.CheckInOutEntity;
import model.entity.Patient.PatientEntity;
import org.junit.Test;
import utils.DBUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * test the implementation of bed dao
 */
public class BedDaoImplTest {
    /**
     * convert the date from string to date object
     * @param s
     * @return
     */
    private static Date convertDate(final String s) {
        Date date = null;
        try {
            final DateFormat fmt = new SimpleDateFormat( "yyyy-MM-dd" );
            date = fmt.parse( s );
        }
        catch ( final ParseException ignored) {
        }
        return date;
    }

    /**
     * test bed available
     */
    @Test
    public void testBedAvailable() {
        BedDao bd = new BedDaoImpl();
        assertEquals(0, bd.getAvailableBedsInHospitalGivenSpecialty(111, "neurology"));
    }

    /**
     * test reserving bed
     */
    @Test
    public void testReserveBed() {
        BedDao bd = new BedDaoImpl();
        assertFalse(bd.reserveBed(3001, 111, 1003, "neurology", convertDate("2019-08-10")));
        assertTrue(bd.reserveBed(3001, 111, 1003, "oncology", convertDate("2019-08-10")));
        PatientDao pDao = new PatientDaoImpl();
        PatientEntity p = pDao.query(3001);
        p.setStatus("Treatment complete");
        pDao.update(p);
        // delete added CheckInOut
        String sql = "DELETE FROM CheckInOut WHERE PatientId = ? AND HospitalId = ? AND DoctorId = ? AND StartDate = ?";
        DBUtils.update(sql, 3001, 111, 1003, convertDate("2019-08-10"));
    }

    /**
     * test releasing bed
     */
    @Test
    public void testReleaseBed() {
        BedDao bd = new BedDaoImpl();
        bd.releaseBed(3002, 111, 5002, convertDate("2019-11-01"));
        CheckInOutEntity ce = DBUtils.get(CheckInOutEntity.class, "SELECT * FROM CheckInOut WHERE patientId = ? AND hospitalId = ? AND bedNumber = ? AND startDate = ?", 3002, 111, 5002, convertDate("2019-10-15"));
        assert ce != null;
        assertEquals(convertDate("2019-11-01"), ce.getEndDate());
        // set endDate back to null
        CheckInOutDao chkD = new CheckInOutDaoImpl();
        ce.setEndDate(null);
        chkD.update(ce);
        assertNull(ce.getEndDate());
        // set patient 3002's status back to In treatment
        PatientDao pDao = new PatientDaoImpl();
        PatientEntity p = pDao.query(3002);
        p.setStatus("In treatment");
        pDao.update(p);
        assertEquals("In treatment", p.getStatus());
    }
}

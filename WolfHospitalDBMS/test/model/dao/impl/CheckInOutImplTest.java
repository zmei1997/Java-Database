package test.model.dao.impl;

import model.dao.CheckInOutDao;
import model.dao.impl.CheckInOutDaoImpl;
import model.entity.Patient.CheckInOutEntity;
import org.junit.Test;
import utils.DBUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

/**
 * test implementation of check in out dao
 */
public class CheckInOutImplTest {

    //create a new check in out dao object
    CheckInOutDao c = new CheckInOutDaoImpl();

    /**
     * convert string to Date
     *
     * @param s
     * @return
     */
    public static Date convertDate (final String s ) {
        Date date = null;
        try {
            final DateFormat fmt = new SimpleDateFormat( "yyyy-MM-dd" );
            date = fmt.parse( s );
        }
        catch ( final ParseException e ) {
        }
        return date;
    }

    /**
     * test check-in/out insert, query, update, manage patient transfer, and assign patient if available
     *
     * */
    @Test
    public void test() {
        CheckInOutEntity ck = new CheckInOutEntity(3001, 111, 1010, 5002, convertDate("2019-08-06"), convertDate("2019-09-05"), 100, "Detail");
        c.insert(ck);
        assertEquals(3001, c.query(3001, 111, convertDate("2019-08-06")).getPatientId());
        assertEquals(111, c.query(3001, 111, convertDate("2019-08-06")).getHospitalId());
        assertEquals(1010, c.query(3001, 111, convertDate("2019-08-06")).getDoctorId());
        assertEquals(5002, c.query(3001, 111, convertDate("2019-08-06")).getBedNumber().intValue());
        assertEquals(convertDate("2019-08-06"), c.query(3001, 111, convertDate("2019-08-06")).getStartDate());
        assertEquals(convertDate("2019-09-05"), c.query(3001, 111, convertDate("2019-08-06")).getEndDate());
        assertEquals(100.0, c.query(3001, 111, convertDate("2019-08-06")).getRegistrationFee());
        assertEquals("Detail", c.query(3001, 111, convertDate("2019-08-06")).getDiagnosisDetail());
        CheckInOutEntity ck1 = new CheckInOutEntity(3001, 111, 1010, 5002, convertDate("2019-08-06"), convertDate("2019-09-05"), 100, "WOW");
        c.update(ck1);
        assertEquals("WOW", c.query(3001, 111, convertDate("2019-08-06")).getDiagnosisDetail());
        assertFalse(c.assignPatientIfAvailable(3001, 111, 1004, "neurology", convertDate("2020-01-01")));
        //manage patient transfer
        c.managePatientTransfer(3002, 111, 222, 1010, 5004, convertDate("2019-08-05"), convertDate("2025-01-01"));
        assertEquals(3002, c.query(3002, 222, convertDate("2025-01-01")).getPatientId());
        assertEquals(1010, c.query(3002, 222, convertDate("2025-01-01")).getDoctorId());
        assertEquals(5004, c.query(3002, 222, convertDate("2025-01-01")).getBedNumber().intValue());
        assertEquals(convertDate("2025-01-01"), c.query(3002, 222, convertDate("2025-01-01")).getStartDate());
        //recover data
        CheckInOutEntity ck2 = new CheckInOutEntity(3002, 111, 1003, 5002, convertDate("2019-10-15"), null, 20, "def");
        c.update(ck2);
        DBUtils.update("DELETE FROM CheckInOut WHERE PatientId =  " + 3002 + " AND BedNumber = " + 5004 + " ;");

        DBUtils.update("DELETE FROM CheckInOut WHERE PatientId =  " + 3001 + " AND BedNumber = " + 5002 + ";");
        assertEquals(null, c.query(3001, 111, convertDate("2019-08-06")));

    }








}


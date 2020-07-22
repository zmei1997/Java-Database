package test.model.dao.impl;

import model.dao.HospitalDao;
import model.dao.impl.HospitalDaoImpl;
import model.entity.Hospital.HospitalEntity;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * test the implementation of hospital dao
 */
public class HospitalDaoImplTest {

    // create a new hospital dao object
    private HospitalDao hd = new HospitalDaoImpl();

    /**
     * convert date from string to data object
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
     * test hospiatl entity query, insert, update and delete
     */
    @Test
    public void test() {
        final HospitalEntity h = new HospitalEntity(333, 33301, "333 St", "303", "cardiology", 10, "neurology", 20, 500);
        hd.insert(h);
        assertEquals(333, hd.query(333).getHospitalId());
        assertEquals(33301, hd.query(333).getAdministratorId());
        assertEquals("333 St", hd.query(333).getAddress());
        assertEquals("303", hd.query(333).getPhone());
        assertEquals("cardiology", hd.query(333).getSpecialization1());
        assertEquals(10, hd.query(333).getChargesPerDay1());
        assertEquals("neurology", hd.query(333).getSpecialization2());
        assertEquals(20, hd.query(333).getChargesPerDay2());
        assertEquals(500, hd.query(333).getCapacity());
        final HospitalEntity h1 = new HospitalEntity(333, 33301, "333 St", "303", "cardiology", 10, "neurology", 20, 10);
        hd.update(h1);
        assertEquals(10, hd.query(333).getCapacity());
        hd.delete(333);
        assertEquals(null, hd.query(333));
    }

    /**
     * test get number of patients per month
     */
    @Test
    public void testGetNumberOfPatientsPerMonth() {
        assertEquals(1, hd.getNumberOfPatientsPerMonth(111, convertDate("2019-09-01"), convertDate("2019-09-30")));
    }

    /**
     * test get all hospital usage status
     */
    @Test
    public void testGetAllHospitalUsageStatus() {
        Map<Integer, Integer> all = hd.getAllHospitalUsageStatus();
        assertEquals(2, all.get(111).intValue());
    }

    /**
     * test get hospiatl usage percentage
     */
    @Test
    public void testGetHospitalUsagePercentage() {
        float expected = (float) 0.02;
        float actual = hd.getHospitalUsagePercentage(111);
        assertTrue(Math.abs(actual - expected) < 0.001);
    }

    /**
     * test getting hospiatls grouped by specialty
     */
    @Test
    public void testGetHospitalsGroupBySpecialty() {
        Map<String, List<HospitalEntity>> map = hd.getHospitalsGroupBySpecialty();
        List<HospitalEntity> hospitalListPediatrics = map.get("pediatrics");
        assertEquals(111, hospitalListPediatrics.get(0).getHospitalId());
        List<HospitalEntity> hospiatlListCardiology = map.get("cardiology");
        assertEquals(222, hospiatlListCardiology.get(0).getHospitalId());
    }

}

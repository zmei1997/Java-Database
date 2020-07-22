package test.model.dao.impl;

import model.dao.PatientDao;
import model.dao.impl.PatientDaoImpl;
import model.entity.Patient.PatientEntity;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/**
 * test the implementation of patient dao
 */
public class PatientDaoImplTest {

    private final PatientDao pd = new PatientDaoImpl();

    /**
     * convert string to Date
     *
     * @param s
     * @return
     */
    public static Date convertDate ( final String s ) {
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
     * test patient insert, query, update and delete
     */
    @Test
    public void test() {
        final PatientEntity p = new PatientEntity(1, null, "Weixiong", convertDate("1997-12-04"), "M", 21, "123", "Gorman St", "Treatment complete");
        pd.insert(p);
        assertEquals(1, pd.query(1).getPatientId());
        assertNull(pd.query(1).getSSN());
        assertEquals("Weixiong", pd.query(1).getName());
        assertEquals(convertDate("1997-12-04"), pd.query(1).getDateOfBirth());
        assertEquals("M", pd.query(1).getGender());
        assertEquals(21, pd.query(1).getAge());
        assertEquals("123", pd.query(1).getPhoneNumber());
        assertEquals("Gorman St", pd.query(1).getAddress());
        assertEquals("Treatment complete", pd.query(1).getStatus());
        final PatientEntity pg1 = new PatientEntity(1, null, "Alex", convertDate("1997-12-04"), "M", 21, "123", "Gorman St", "Treatment complete");
        pd.update(pg1);
        assertEquals("Alex", pd.query(1).getName());
        pd.delete(1);
        assertNull(pd.query(1));
    }




}

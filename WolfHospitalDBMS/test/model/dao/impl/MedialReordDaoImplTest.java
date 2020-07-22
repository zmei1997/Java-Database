package test.model.dao.impl;

import model.dao.MedicalRecordDao;
import model.dao.impl.MedicalRecordDaoImpl;
import model.entity.Hospital.MedicalRecordEntity;
import org.junit.Test;
import utils.DBUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Test implementations for medical record
 */
public class MedialReordDaoImplTest {

    /**
     * convert string to Date
     *
     * @param s
     * @return
     */
    public static Date convertDate(final String s) {
        Date date = null;
        try {
            final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            date = fmt.parse(s);
        } catch (final ParseException e) {
        }
        return date;
    }

    /**
     * test medical record operations including insert, query, update
     */
    @Test
    public void test() {
        MedicalRecordDao m = new MedicalRecordDaoImpl();
        MedicalRecordEntity m1 = new MedicalRecordEntity(2003, 3002, 222, convertDate("2030-01-01"), convertDate("2030-02-01"), 1004, "still alive", "PTSD", "PTSD Test", "Positive", "Music", 8000, 8000, 8000);
        m.insert(m1);

        assertEquals(3002, m.query(2003).getPatientId());
        assertEquals(222, m.query(2003).getHospitalId());
        assertEquals(convertDate("2030-01-01"), m.query(2003).getStartDate());
        assertEquals(convertDate("2030-02-01"), m.query(2003).getEndDate());
        assertEquals("still alive", m.query(2003).getPrescription());
        assertEquals("PTSD", m.query(2003).getDiagnosis());
        assertEquals("PTSD Test", m.query(2003).getTest());
        assertEquals("Positive", m.query(2003).getResult());
        assertEquals("Music", m.query(2003).getTreatment());
        assertEquals(8000, m.query(2003).getConsultationFee());
        assertEquals(8000, m.query(2003).getTestFee());
        assertEquals(8000, m.query(2003).getTreatmentFee());

        MedicalRecordEntity m2 = new MedicalRecordEntity(2003, 3002, 222, convertDate("2030-01-01"), convertDate("2030-02-01"), 1004, "still alive", "PTSD", "PTSD Test", "Negative", "Music", 8000, 8000, 8000);
        m.update(m2);
        assertEquals("Negative", m.query(2003).getResult());
        DBUtils.update("DELETE FROM MedicalRecord WHERE MedicalRecordId = 2003");
        assertEquals(null, m.query(2003));
    }

}

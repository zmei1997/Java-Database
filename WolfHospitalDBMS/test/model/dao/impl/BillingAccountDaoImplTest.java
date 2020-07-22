package test.model.dao.impl;

import model.dao.BillingAccountDao;
import model.dao.impl.BillingAccountDaoImpl;
import model.entity.Patient.BillingAccountEntity;
import org.junit.Test;
import utils.DBUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * test implementation of billing account dao
 */
public class BillingAccountDaoImplTest {

    // create a BillingAccountDao
    private final BillingAccountDao bill = new BillingAccountDaoImpl();

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
     * test billing account insert, query, update
     */
    @Test
    public void test () {
        // creat a BillingAccountEntity
        final BillingAccountEntity ba = new BillingAccountEntity( 1, 2001, 3001, "111 drive", "CreditCard",
                "1234567890123456" );
        // insert the BillingAccountEntity and saved into database
        bill.insert( ba );
        // compare the saved data
        assertEquals( 1, bill.query( 1 ).getBillingAccountId() );
        assertEquals( 2001, bill.query( 1 ).getMedicalRecordId() );
        assertEquals( 3001, bill.query( 1 ).getPatientId() );
        assertEquals( "111 drive", bill.query( 1 ).getBillingAddress() );
        assertEquals( "CreditCard", bill.query( 1 ).getMethod() );
        assertEquals( "1234567890123456", bill.query( 1 ).getCardNumber() );

        // create a new BillingAccountEntity by patientId
        final BillingAccountEntity baNew = new BillingAccountEntity( 1, 2001, 3001, "2000 drive", "Debit",
                "1234567890123456" );
        // update and save
        bill.update( baNew );
        // check updated
        assertEquals( "2000 drive", bill.query( 1 ).getBillingAddress() );
        assertEquals( "Debit", bill.query( 1 ).getMethod() );

        //test delete billing account
        DBUtils.update("DELETE FROM BillingAccount WHERE BillingAccountId =  " + 1 + ";");
        assertNull(bill.query(1));
    }

    /**
     * test getting billing history
     */
    @Test
    public void testGetBillingHistory() {
        List<Map<String, Integer>> history = bill.getBillingHistory(3001, convertDate("2019-08-04"), convertDate("2019-09-16"));
        assertEquals(20, history.get(0).get("Registration fee").intValue());
        assertEquals(50, history.get(0).get("Consultation fee").intValue());
        assertEquals(75, history.get(0).get("Test fee").intValue());
        assertEquals(200, history.get(0).get("Treatment fee").intValue());
        assertEquals(405, history.get(0).get("Hospital expense").intValue()); // bedNumber: 5001, ChargesPerDay of 5001: 15, startDate: 2019-08-05, endDate: 2019-08-31 // Days: 27
    }

}

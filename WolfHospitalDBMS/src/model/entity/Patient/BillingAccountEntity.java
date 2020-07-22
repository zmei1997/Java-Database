package model.entity.Patient;

import javax.persistence.*;
import java.util.Objects;

/**
 * object class of BillingAccountEntity
 */
@Entity
@Table ( name = "billingaccount", schema = "health" )
public class BillingAccountEntity {

    private int BillingAccountId;
    private int MedicalRecordId;
    private int PatientId;
    private String BillingAddress;
    private String Method;
    private String CardNumber;

    /**
     * constructor of a billing account entity
     * @param billingAccountId
     * @param medicalRecordId
     * @param patientId
     * @param billingAddress
     * @param method
     * @param cardNumber
     */
    public BillingAccountEntity ( final int billingAccountId, final int medicalRecordId, final int patientId,
            final String billingAddress, final String method, final String cardNumber ) {
        this.BillingAccountId = billingAccountId;
        this.MedicalRecordId = medicalRecordId;
        this.PatientId = patientId;
        this.BillingAddress = billingAddress;
        this.Method = method;
        this.CardNumber = cardNumber;
    }

    public BillingAccountEntity () {

    }

    /**
     * get the id of the billing account
     * @return
     */
    @Id
    @Column ( name = "BillingAccountId" )
    public int getBillingAccountId() {
        return BillingAccountId;
    }

    /**
     * set billing account id
     * @param billingAccountId
     */
    public void setBillingAccountId(final int billingAccountId ) {
        this.BillingAccountId = billingAccountId;
    }

    /**
     * get medical record id
     * @return
     */
    @Basic
    @Column ( name = "MedicalRecordId" )
    public int getMedicalRecordId() {
        return MedicalRecordId;
    }

    /**
     * set medical record id
     * @param medicalRecordId
     */
    public void setMedicalRecordId(final int medicalRecordId ) {
        this.MedicalRecordId = medicalRecordId;
    }

    /**
     * get patient id
     * @return
     */
    @Basic
    @Column ( name = "PatientId" )
    public int getPatientId() {
        return PatientId;
    }

    /**
     * set patient id
     * @param patientId
     */
    public void setPatientId(final int patientId ) {
        this.PatientId = patientId;
    }

    /**
     * get billing address
     * @return
     */
    @Basic
    @Column ( name = "BillingAddress" )
    public String getBillingAddress() {
        return BillingAddress;
    }

    /**
     * set billing address
     * @param billingAddress
     */
    public void setBillingAddress(final String billingAddress ) {
        this.BillingAddress = billingAddress;
    }

    /**
     * get payment method
     * @return
     */
    @Basic
    @Column ( name = "Method" )
    public String getMethod() {
        return Method;
    }

    /**
     * set payment method
     * @param method
     */
    public void setMethod(final String method ) {
        this.Method = method;
    }

    /**
     * get card number
     * @return
     */
    @Basic
    @Column ( name = "CardNumber" )
    public String getCardNumber() {
        return CardNumber;
    }

    /**
     * set card number
     * @param cardNumber
     */
    public void setCardNumber(final String cardNumber ) {
        this.CardNumber = cardNumber;
    }

    /**
     * method to compare two objects
     * @param o
     * @return
     */
    @Override
    public boolean equals ( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        final BillingAccountEntity that = (BillingAccountEntity) o;
        return BillingAccountId == that.BillingAccountId && MedicalRecordId == that.MedicalRecordId
                && PatientId == that.PatientId && Objects.equals(BillingAddress, that.BillingAddress)
                && Objects.equals(Method, that.Method) && Objects.equals(CardNumber, that.CardNumber);
    }

    @Override
    public int hashCode () {
        return Objects.hash(BillingAccountId, MedicalRecordId, PatientId, BillingAddress, Method, CardNumber);
    }
}

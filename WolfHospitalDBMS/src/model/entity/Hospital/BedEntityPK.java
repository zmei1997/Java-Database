package model.entity.Hospital;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class BedEntityPK implements Serializable {
    private int bedNumber;
    private int hospitalId;

    @Column(name = "BedNumber")
    @Id
    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    @Column(name = "HospitalId")
    @Id
    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BedEntityPK that = (BedEntityPK) o;
        return bedNumber == that.bedNumber &&
                hospitalId == that.hospitalId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bedNumber, hospitalId);
    }
}

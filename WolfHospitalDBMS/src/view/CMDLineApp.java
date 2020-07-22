package view;

import model.dao.*;
import model.dao.impl.*;
import model.entity.Hospital.HospitalEntity;
import model.entity.Hospital.MedicalRecordEntity;
import model.entity.Hospital.StaffEntity;
import model.entity.Patient.BillingAccountEntity;
import model.entity.Patient.CheckInOutEntity;
import model.entity.Patient.PatientEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * User Interface
 *
 * @author Zhang Weixiong
 * @author Zhongxiao Mei
 *
 */
public class CMDLineApp {

    /**
     * those dao objects are created to perform database operations implemented in daoimpl classes
     */
    private static PatientDao        patientDao        = new PatientDaoImpl();
    private static StaffDao          staffDao          = new StaffDaoImpl();
    private static HospitalDao       hospitalDao       = new HospitalDaoImpl();
    private static BedDao            bedDao            = new BedDaoImpl();
    private static BillingAccountDao billingAccountDao = new BillingAccountDaoImpl();
    private static CheckInOutDao     checkInOutDao     = new CheckInOutDaoImpl();
    private static MedicalRecordDao  medicalRecordDao  = new MedicalRecordDaoImpl();

    //create the scanner to read all user input through the processes
    private static Scanner           reader    = new Scanner( System.in );

    /**
     * main method
     *
     * @param args
     */
    public static void main ( final String[] args ) {
        //print welcome message marking the start of running
        printWelcomeMsg();
        //forever runs to prompt user for options until they choose to exit the system
        while (true) {
            //create a list of operations to display
            final String[] operationList = createOperationList();
            //print the operation list
            printOperationList(operationList);
            //prompt user for input, choosing operation options
            final int choiceInt = promptUser(operationList, reader);
            //print input instruction to guide users entering data
            printDataEntryInstruction();

            //operation 1
            if (choiceInt == 1) {
                final Object data1 = getDataOperation1(reader);
                assert data1 != null;
                final String objectTypeName = data1.getClass().getSimpleName();
                // seperate cases when user wants to enter different object entities
                switch (objectTypeName) {
                    //patient entity
                    case "PatientEntity":
                        //insert the patient into DB
                        patientDao.insert((PatientEntity) data1);
                        System.out.println("Patient entered.");
                        break;
                    case "StaffEntity":
                        //insert the staff into DB
                        staffDao.insert((StaffEntity) data1);
                        System.out.println("Staff entered.");
                        break;
                    case "HospitalEntity":
                        //insert the hospital into DB
                        hospitalDao.insert((HospitalEntity) data1);
                        System.out.println("Hospital entered.");
                        break;
                    case "MedicalRecordEntity":
                        //insert the medical record into DB
                        medicalRecordDao.insert((MedicalRecordEntity) data1);
                        System.out.println("Medical Record entered.");
                        break;
                    case "BillingAccountEntity":
                        //insert billing account into DB
                        billingAccountDao.insert((BillingAccountEntity) data1);
                        System.out.println("Billing Account Entered");
                        break;
                    case "CheckInOutEntity":
                        //insert check in/out entity
                        checkInOutDao.insert((CheckInOutEntity) data1);
                        System.out.println("Check in or out entered.");
                        break;
                }
            } else if (choiceInt == 2) {
                //when user choose to perform operation 2 to update patient/hospital/staff
                final String[] dataArray = getTypeAndId2(reader);
                //if user chooses patient
                if (dataArray[0].equalsIgnoreCase("patient")) {
                    //get the old patient with the primary key
                    final PatientEntity patient = patientDao.query(Integer.parseInt(dataArray[1]));
                    //print attribute values of the entity
                    System.out.println("patientId/ssn/name/dataOfBirth/gender/age/phoneNumber/address/status");
                    System.out.println(patient.getPatientId() + "/" + patient.getSSN() + "/" + patient.getName() + "/"
                            + patient.getDateOfBirth() + "/" + patient.getGender() + "/" + patient.getAge() + "/"
                            + patient.getPhoneNumber() + "/" + patient.getAddress() + "/" + patient.getStatus());
                    //scan the input from user
                    final String[] inputArray = getDataOperation2(reader, "Patient");
                    //deal with cases of null attributes
                    Integer ssn;
                    if (inputArray[1].equalsIgnoreCase("null")) {
                        ssn = null;
                    } else {
                        ssn = Integer.parseInt(inputArray[1]);
                    }
                    //create a new entity with new input from user
                    final PatientEntity p = new PatientEntity(Integer.parseInt(inputArray[0]),
                            ssn, inputArray[2], convertDate(inputArray[3]), inputArray[4],
                            Integer.parseInt(inputArray[5]), inputArray[6], inputArray[7], inputArray[8]);
                    //update, replace the old one with new one
                    patientDao.update(p);
                    System.out.println("Patient updated.");
                    PatientEntity pe = patientDao.query(Integer.parseInt(inputArray[0]));
                    System.out.println("Updated Patient: ");
                    System.out.println("patientId/ssn/name/dataOfBirth/gender/age/phoneNumber/address/status");
                    System.out.println(pe.getPatientId() + "/" + pe.getSSN() + "/" + pe.getName() + "/" + pe.getDateOfBirth() + "/" + pe.getGender() + "/" + pe.getAge() + "/" + pe.getPhoneNumber() + "/" + pe.getAddress() + "/" + pe.getStatus());
                } else if (dataArray[0].equalsIgnoreCase("staff")) {
                    //get the old staff entity the user searches with primary key
                    final StaffEntity staff = staffDao.query(Integer.parseInt(dataArray[1]));
                    //display the old data to user
                    System.out.println(
                            "staffId/hospitalId/name/age/gender/jobTitle/professionalTitle/department/phone/emailAddress/address");
                    System.out.println(staff.getStaffId() + "/" + staff.getHospitalId() + "/" + staff.getName() + "/"
                            + staff.getAge() + "/" + staff.getGender() + "/" + staff.getJobTitle() + "/"
                            + staff.getProfessionalTitle() + "/" + staff.getDepartment() + "/" + staff.getPhone() + "/"
                            + staff.getEmailAddress() + "/" + staff.getAddress());
                    //ask for new input and create a new staff
                    final String[] inputArray = getDataOperation2(reader, "Staff");
                    final StaffEntity s = new StaffEntity(Integer.parseInt(inputArray[0]),
                            Integer.parseInt(inputArray[1]), inputArray[2], Integer.parseInt(inputArray[3]),
                            inputArray[4], inputArray[5], inputArray[6], inputArray[7], inputArray[8], inputArray[9],
                            inputArray[10]);
                    //update the old staff with the new staff
                    staffDao.update(s);
                    System.out.println("Staff updated.");
                    //return the newest values of the staff to users
                    StaffEntity se = staffDao.query(Integer.parseInt(inputArray[0]));
                    System.out.println("Updated Staff: ");
                    System.out.println("staffId/hospitalId/name/age/gender/jobTitle/professionalTitle/department/phone/emailAddress/address");
                    System.out.println(se.getStaffId() + "/" + se.getHospitalId() + "/" + se.getName() + "/"
                            + se.getAge() + "/" + se.getGender() + "/" + se.getJobTitle() + "/"
                            + se.getProfessionalTitle() + "/" + se.getDepartment() + "/" + se.getPhone() + "/"
                            + se.getEmailAddress() + "/" + se.getAddress());
                //user choosing hospital
                } else if (dataArray[0].equalsIgnoreCase("hospital")) {
                    //get the old hospital with the entered primary key
                    final HospitalEntity hospital = hospitalDao.query(Integer.parseInt(dataArray[1]));
                    //show old attribut values of the hospital to the user
                    System.out.println(
                            "hospitalId/administratorId/address/phone/specialization1/chargesPerDay1/specialization2/chargesPerDay2/capacity");
                    System.out.println(hospital.getHospitalId() + "/" + hospital.getAdministratorId() + "/"
                            + hospital.getAddress() + "/" + hospital.getPhone() + "/" + hospital.getSpecialization1()
                            + "/" + hospital.getChargesPerDay1() + "/" + hospital.getSpecialization2() + "/"
                            + hospital.getChargesPerDay2() + "/" + hospital.getCapacity());
                    //get input from user and to create a new entity
                    final String[] inputArray = getDataOperation2(reader, "Hospital");
                    final HospitalEntity h = new HospitalEntity(Integer.parseInt(inputArray[0]),
                            Integer.parseInt(inputArray[1]), inputArray[2], inputArray[3], inputArray[4],
                            Integer.parseInt(inputArray[5]), inputArray[6], Integer.parseInt(inputArray[7]),
                            Integer.parseInt(inputArray[8]));
                    //update the hospital entity in DB
                    hospitalDao.update(h);
                    System.out.println("Hospital updated.");
                    //return the newest attribute values to the user
                    HospitalEntity he = hospitalDao.query(Integer.parseInt(inputArray[0]));
                    System.out.println("Updated Hospital: ");
                    System.out.println("hospitalId/administratorId/address/phone/specialization1/chargesPerDay1/specialization2/chargesPerDay2/capacity");
                    System.out.println(he.getHospitalId() + "/" + he.getAdministratorId() + "/"
                            + he.getAddress() + "/" + he.getPhone() + "/" + he.getSpecialization1()
                            + "/" + he.getChargesPerDay1() + "/" + he.getSpecialization2() + "/"
                            + he.getChargesPerDay2() + "/" + he.getCapacity());
                }
            //Choosing to perform operation 3
            } else if (choiceInt == 3) {
                final String[] dataArray = getDataOperation3(reader);
                assert dataArray != null;
                final String type = dataArray[1];
                //get primary key of the entities to delete
                switch (type) {
                    case "Patient":
                        patientDao.delete(Integer.parseInt(dataArray[0]));
                        break;
                    case "Staff":
                        staffDao.delete(Integer.parseInt(dataArray[0]));
                        break;
                    case "Hospital":
                        hospitalDao.delete(Integer.parseInt(dataArray[0]));
                        break;
                }
                System.out.println(type + " deleted.");
                // choose to perform operation 4
            } else if (choiceInt == 4) {
                final String[] dataArray = getDataOperation4(reader);
                //get the number of available beds in hospital given specialty
                final int numBeds = bedDao.getAvailableBedsInHospitalGivenSpecialty(Integer.parseInt(dataArray[0]),
                        dataArray[1]);
                System.out.println("The number of available beds with the given specialty: " + numBeds);
                //choose to perform operation 5
            } else if (choiceInt == 5) {
                final String[] dataArray = getDataOperation5(reader);
                //return if patient is successfully assigned
                final boolean ifAssigned = checkInOutDao.assignPatientIfAvailable(Integer.parseInt(dataArray[0]),
                        Integer.parseInt(dataArray[1]), Integer.parseInt(dataArray[2]), dataArray[3], convertDate(dataArray[4]));
                //print success or failure msg to user
                if (ifAssigned) {
                    System.out.println("Successfully assigned patient");
                } else {
                    System.out.println("Failure assigning patient");
                }
                //perform operation 6
            } else if (choiceInt == 6) {
                final String[] dataArray = getDataOperation6(reader);
                final int patientId6 = Integer.parseInt(dataArray[0]);
                final int hospitalId6 = Integer.parseInt(dataArray[1]);
                final int doctorId6 = Integer.parseInt(dataArray[2]);
                final String specialty6 = dataArray[3];
                final Date startDate6 = convertDate(dataArray[4]);
                //reserve bed according to user input
                final boolean reserved6 = bedDao.reserveBed(patientId6, hospitalId6, doctorId6, specialty6, startDate6);
                //return success or failure msg
                if (reserved6) {
                    System.out.println("Bed reserved successfully");
                } else {
                    System.out.println("Failure reserving bed");
                }
                //perform operation 7
            } else if (choiceInt == 7) {
                final String[] dataArray = getDataOperation7(reader);
                final int patientId7 = Integer.parseInt(dataArray[0]);
                final int hospitalId7 = Integer.parseInt(dataArray[1]);
                final int bedNumber7 = Integer.parseInt(dataArray[2]);
                final Date endDate7 = convertDate(dataArray[3]);
                //based on user input, release bed
                bedDao.releaseBed(patientId7, hospitalId7, bedNumber7, endDate7);
                System.out.println("Bed released.");
                //perform operation 8
            } else if (choiceInt == 8) {
                final String[] dataArray = getDataOperation8(reader);
                //manage patient transfer with given input data
                checkInOutDao.managePatientTransfer(Integer.parseInt(dataArray[0]), Integer.parseInt(dataArray[1]),
                        Integer.parseInt(dataArray[2]), Integer.parseInt(dataArray[3]), Integer.parseInt(dataArray[4]),
                        convertDate(dataArray[5]), convertDate(dataArray[6]));
                System.out.println("Patient Transfer complete.");
                //perform operation 9
            } else if (choiceInt == 9) {
                final MedicalRecordEntity data9 = getDataOperation9(reader);
                //enter a new medical record entity
                medicalRecordDao.insert(data9);
                System.out.println("Medical Record Entered");
                //perform operation 10
            } else if (choiceInt == 10) {
                final String id = getId10(reader);
                //create a updated new medical record entity
                final MedicalRecordEntity mr = medicalRecordDao.query(Integer.parseInt(id));
                //print the old attribute values to user
                System.out.println(
                        "MedicalRecordId/PatientId/HospitalId/StartDate/EndDate/DoctorId/Prescription/Diagnosic/Test/Result/Treatment/ConsulationFee/TestFee/TreatmentFee");
                System.out.println(mr.getMedicalRecordId() + "/" + mr.getPatientId() + "/" + mr.getHospitalId() + "/"
                        + mr.getStartDate() + "/" + mr.getEndDate() + "/" + mr.getDoctorId() + "/" + mr.getPrescription()
                        + "/" + mr.getDiagnosis() + "/" + mr.getTest() + "/" + mr.getResult() + "/" + mr.getTreatment()
                        + "/" + mr.getConsultationFee() + "/" + mr.getTestFee() + "/" + mr.getTreatmentFee());
                final String[] input = getDataOperation10(reader);
                //deal with legal null attribute input
                Date endDate;
                if (input[4].equalsIgnoreCase("null")) {
                    endDate = null;
                } else {
                    endDate = convertDate(input[4]);
                }
                String prescription;
                if (input[6].equalsIgnoreCase("null")) {
                    prescription = null;
                } else {
                    prescription = input[6];
                }
                String diagnosic;
                if (input[7].equalsIgnoreCase("null")) {
                    diagnosic = null;
                } else {
                    diagnosic = input[7];
                }
                String test;
                if (input[8].equalsIgnoreCase("null")) {
                    test = null;
                } else {
                    test = input[8];
                }
                String result;
                if (input[9].equalsIgnoreCase("null")) {
                    result = null;
                } else {
                    result = input[9];
                }
                //create a new updated entity and update
                final MedicalRecordEntity m = new MedicalRecordEntity(Integer.parseInt(input[0]),
                        Integer.parseInt(input[1]), Integer.parseInt(input[2]), convertDate(input[3]),
                        endDate, Integer.parseInt(input[5]), prescription, diagnosic, test, result,
                        input[10], Integer.parseInt(input[11]), Integer.parseInt(input[12]),
                        Integer.parseInt(input[13]));
                //replace the old one with the new one
                medicalRecordDao.update(m);
                System.out.println("Medical Record Updated.");
                //return new values of the entity after the update to user
                MedicalRecordEntity me = medicalRecordDao.query(Integer.parseInt(input[0]));
                System.out.println("Updated Medical Record: ");
                System.out.println("MedicalRecordId/PatientId/HospitalId/StartDate/EndDate/DoctorId/Prescription/Diagnosic/Test/Result/Treatment/ConsulationFee/TestFee/TreatmentFee");
                System.out.println(me.getMedicalRecordId() + "/" + me.getPatientId() + "/" + me.getHospitalId() + "/"
                        + me.getStartDate() + "/" + me.getEndDate() + "/" + me.getDoctorId() + "/" + me.getPrescription()
                        + "/" + me.getDiagnosis() + "/" + me.getTest() + "/" + me.getResult() + "/" + me.getTreatment()
                        + "/" + me.getConsultationFee() + "/" + me.getTestFee() + "/" + me.getTreatmentFee());
            //perform operation 11
            } else if (choiceInt == 11) {
                final CheckInOutEntity data11 = getDataOperation11(reader);
                //enter a new check in out entity
                checkInOutDao.insert(data11);
                System.out.println("Check In/Out Entered");
                //perform operation 12
            } else if (choiceInt == 12) {
                final String[] ids12 = getId12(reader);
                //get the old entity of check in out from the given primary keys
                final CheckInOutEntity ck = checkInOutDao.query(Integer.parseInt(ids12[0]), Integer.parseInt(ids12[1]),
                        convertDate(ids12[2]));
                //print the old attribute values to user
                System.out.println(
                        "PatientId/HospitalId/DoctorId/BedNumber/StartDate/EndDate/RegistrationFee/DiagnosisDetail");
                System.out.println(ck.getPatientId() + "/" + ck.getHospitalId() + "/" + ck.getDoctorId() + "/"
                        + ck.getBedNumber() + "/" + ck.getStartDate() + "/" + ck.getEndDate() + "/"
                        + ck.getRegistrationFee() + "/" + ck.getDiagnosisDetail());
                //get the data input from user
                final String[] dataArray = getDataOperation12(reader);
                //deal with possible legal null attribute input
                Integer bedNumber;
                if (dataArray[3].equalsIgnoreCase("null")) {
                    bedNumber = null;
                } else {
                    bedNumber = Integer.parseInt(dataArray[3]);
                }
                Date endDate;
                if (dataArray[5].equalsIgnoreCase("null")) {
                    endDate = null;
                } else {
                    endDate = convertDate(dataArray[5]);
                }
                String diagnosisDetail;
                if (dataArray[7].equalsIgnoreCase("null")) {
                    diagnosisDetail = null;
                } else {
                    diagnosisDetail = dataArray[7];
                }
                //create a new updated entity
                final CheckInOutEntity chkInOut = new CheckInOutEntity(Integer.parseInt(dataArray[0]),
                        Integer.parseInt(dataArray[1]), Integer.parseInt(dataArray[2]),
                        bedNumber, convertDate(dataArray[4]), endDate,
                        Integer.parseInt(dataArray[6]), diagnosisDetail);
                //update it in the db
                checkInOutDao.update(chkInOut);
                System.out.println("Check In/Out Updated.");
                //display the updated new values to user
                CheckInOutEntity ce = checkInOutDao.query(Integer.parseInt(dataArray[0]), Integer.parseInt(dataArray[1]), convertDate(dataArray[4]));
                System.out.println("Updated Check In/Out: ");
                System.out.println("PatientId/HospitalId/DoctorId/BedNumber/StartDate/EndDate/RegistrationFee/DiagnosisDetail");
                System.out.println(ce.getPatientId() + "/" + ce.getHospitalId() + "/" + ce.getDoctorId() + "/"
                        + ce.getBedNumber() + "/" + ce.getStartDate() + "/" + ce.getEndDate() + "/"
                        + ce.getRegistrationFee() + "/" + ce.getDiagnosisDetail());

            }
            // else if ( choiceInt == 13 ) {
            // final String[] dataArray = getDataOperation13( reader );
            // }
            // else if ( choiceInt == 14 ) {
            // final String[] dataArray = getDataOperation14( reader );
            // }
            //perform operation 15
            else if (choiceInt == 15) {
                final BillingAccountEntity data15 = getDataOperation15(reader);
                //insert a new billing account entity to DB
                billingAccountDao.insert(data15);
                System.out.println("Billing Account Created.");
                //perform operation 16
            } else if (choiceInt == 16) {
                final int id = Integer.parseInt(getId16(reader));
                final BillingAccountEntity ba = billingAccountDao.query(id);
                //query and get the old billing account entity and display the old values to user
                System.out.println("billingAccountId/medicalRecordId/patientId/billingAddress/method/cardNumber");
                System.out.println(ba.getBillingAccountId() + "/" + ba.getMedicalRecordId() + "/" + ba.getPatientId()
                        + "/" + ba.getBillingAddress() + "/" + ba.getMethod() + "/" + ba.getCardNumber());
                //prompt for input of new data
                final String[] dataArray = getDataOperation16(reader);
                //build a new entity
                final BillingAccountEntity billingA = new BillingAccountEntity(Integer.parseInt(dataArray[0]),
                        Integer.parseInt(dataArray[1]), Integer.parseInt(dataArray[2]), dataArray[3], dataArray[4],
                        dataArray[5]);
                billingAccountDao.update(billingA);
                //update the entity in the DB
                System.out.println("Billing Account Updated.");
                //perform operation 17
            } else if (choiceInt == 17) {
                //get input from user
                final String[] dataArray = getDataOperation17(reader);
                //get billing history
                final List<Map<String, Integer>> list = billingAccountDao.getBillingHistory(
                        Integer.parseInt(dataArray[0]), convertDate(dataArray[1]), convertDate(dataArray[2]));
                System.out.println("All Billing History");
                System.out.println("*******************");
                //return ino from the map
                for (final Map<String, Integer> map : list) {
                    System.out.println("No./Type of Fee/Fee Amount");
                    for (final Map.Entry<String, Integer> entry : map.entrySet()) {
                        final int no = list.indexOf(map) + 1;
                        // entry.getKey();
                        // entry.getValue();
                        System.out.println(no + "/" + entry.getKey() + "/" + entry.getValue());
                    }
                    System.out.println("*******************");
                }
                if (list.size() == 0) {
                    System.out.println("No Billing History at this period.");
                }
                System.out.println("Billing History Generated as Above.");
                //perform operation 18
            } else if (choiceInt == 18) {
                reader.nextLine();
                //reader.close();
                System.out.println("You selected (18) Return Current Usage Status for All Hospitals");
                final Map<Integer, Integer> map = hospitalDao.getAllHospitalUsageStatus();
                System.out.println("***************************************************************");
                System.out.println("HospitalId/Number of Patients");
                for (final Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    System.out.println(entry.getKey() + "/" + entry.getValue());
                }
                System.out.println("***************************************************************");
                System.out.println("Above are Current Usage Status for All Hospitals");
                //perform operation 19
            } else if (choiceInt == 19) {
                final String[] dataArray = getDataOperation19(reader);
                //get number of patient per month
                final int num = hospitalDao.getNumberOfPatientsPerMonth(Integer.parseInt(dataArray[0]),
                        convertDate(dataArray[1]), convertDate(dataArray[2]));
                System.out.println("The Number of Patients Per Month of this Hospital: " + num);
                //perform operation 20
            } else if (choiceInt == 20) {
                final String hId = getDataOperation20(reader);
                //get hospital usage percentage
                final float ratio = hospitalDao.getHospitalUsagePercentage(Integer.parseInt(hId));
                final float percentage = ratio * 100;
                System.out.println("Usage Percentage of the Hospital: " + percentage + " %");
                //perform operation 21
            } else if (choiceInt == 21) {
                final String[] dataArray = getDataOperation21(reader);
                final int patientId21 = Integer.parseInt(dataArray[0]);
                //get all doctors a patient is currently seeing
                final List<StaffEntity> list = staffDao.getAllDoctorsAPatientIsSeeing(patientId21);
                System.out.println(
                        "ID/HospitalID/Name/Age/Gender/JobTitle/ProfessionalTitle/Department/Phone/Email/Address");
                for (final StaffEntity d : list) {
                    System.out.println(d.getStaffId() + "/" + d.getHospitalId() + "/" + d.getName() + "/" + d.getAge()
                            + "/" + d.getGender() + "/" + d.getJobTitle() + "/" + d.getProfessionalTitle() + "/"
                            + d.getDepartment() + "/" + d.getPhone() + "/" + d.getEmailAddress() + "/"
                            + d.getAddress());
                }
                System.out.println("----------Doctors Information Above----------");
                //perform operation 22
            } else if (choiceInt == 22) {
                System.out.println("You selected (22) Return Information on Hospitals Grouped by Specialty");
                //get the map of hospitals group by specialty
                final Map<String, List<HospitalEntity>> map = hospitalDao.getHospitalsGroupBySpecialty();
                System.out.println(
                        "hospitalId/administratorId/address/phone/specialization1/chargesPerDay1/specialization2/chargesPerDay2/capacity");
                for (final Map.Entry<String, List<HospitalEntity>> entry : map.entrySet()) {
                    for (final HospitalEntity hospitalInfo : entry.getValue()) {
                        System.out.println(entry.getKey() + "/" + hospitalInfo.getHospitalId() + "/"
                                + hospitalInfo.getAdministratorId() + "/" + hospitalInfo.getAddress() + "/"
                                + hospitalInfo.getPhone() + "/" + hospitalInfo.getSpecialization1() + "/"
                                + hospitalInfo.getChargesPerDay1() + "/" + hospitalInfo.getSpecialization2() + "/"
                                + hospitalInfo.getChargesPerDay2() + "/" + hospitalInfo.getCapacity());
                    }
                }
                System.out.println(
                        "--------------------------Information on Hospitals Grouped by Specialty Displayed--------------------------------------");
                //perform operation 23, close the system
            } else if (choiceInt == 23) {
                System.exit(0);
            }
            System.out.println("-----------------One Operation Completed-----------------");
        }
    }

    /**
     * get the data array of responses from user in "Enter Information"
     *
     * @param reader reader
     * @return relational entities
     */
    public static Object getDataOperation1 ( final Scanner reader ) {
        String[] dataArray = {};
        System.out.println( "You selected (1) Enter Information" );
        System.out.println(
                "Please enter: Relation name you want to enter (e.g. patient, staff, hospital, billingaccount, checkinout, medicalrecord)" );
        reader.nextLine();
        final String relation = reader.nextLine();
        switch ( relation ) {
            case "patient":
                System.out.println(
                        "Enter: PatientId/SSN/Name/DateOfBirth/Gender/Age/PhoneNumber/Address/Status (hint: SSN can be NULL)" );
                final String dataEntered = reader.nextLine();
                dataArray = dataEntered.split( "/" );
                if (dataArray[1].equalsIgnoreCase("null")) {
                    // create a new patient
                    final PatientEntity p = new PatientEntity( Integer.parseInt( dataArray[0] ),
                            null, dataArray[2], convertDate( dataArray[3] ), dataArray[4],
                            Integer.parseInt( dataArray[5] ), dataArray[6], dataArray[7], dataArray[8] );
                    //reader.close();
                    return p;
                } else {
                    final PatientEntity p = new PatientEntity(Integer.parseInt(dataArray[0]),
                            Integer.parseInt(dataArray[1]), dataArray[2], convertDate(dataArray[3]), dataArray[4],
                            Integer.parseInt(dataArray[5]), dataArray[6], dataArray[7], dataArray[8]);
                    //reader.close();
                    return p;
                }
            case "staff":
                System.out.println(
                        "Enter: StaffId/HospitalId/Name/Age/Gender/JobTitle/ProfessionalTitle/Department/Phone/EmailAddress/Address (hint: all attributes are needed)" );
                final String dataEntered2 = reader.nextLine();
                dataArray = dataEntered2.split( "/" );
                // create a new staff
                final StaffEntity s = new StaffEntity( Integer.parseInt( dataArray[0] ),
                        Integer.parseInt( dataArray[1] ), dataArray[2], Integer.parseInt( dataArray[3] ), dataArray[4],
                        dataArray[5], dataArray[6], dataArray[7], dataArray[8], dataArray[9], dataArray[10] );
                //reader.close();
                return s;
            case "hospital":
                System.out.println(
                        "Enter: HospitalId/AdministratorId/Address/Phone/Specialization1/ChargesPerDay1/Specialization2/ChargesPerDay2/Capacity (hint: all attributes are needed)" );
                final String dataEntered3 = reader.nextLine();
                dataArray = dataEntered3.split( "/" );
                // create a new hospital
                final HospitalEntity h = new HospitalEntity( Integer.parseInt( dataArray[0] ),
                        Integer.parseInt( dataArray[1] ), dataArray[2], dataArray[3], dataArray[4],
                        Integer.parseInt( dataArray[5] ), dataArray[6], Integer.parseInt( dataArray[7] ),
                        Integer.parseInt( dataArray[8] ) );
                //reader.close();
                return h;
            // case "bed":
            // System.out.println(
            // "Enter:
            // BedNumber/HospitalId/PatientSSN/Specialization/ChargesPerDay/NurseId
            // (hint: all attributes are needed)" );
            // final String dataEntered4 = reader.nextLine();
            // dataArray = dataEntered4.split( "/" );
            // final BedEntity b = new BedEntity( Integer.parseInt( dataArray[0]
            // ), Integer.parseInt( dataArray[1] ),
            // Integer.parseInt( dataArray[2] ), dataArray[3], Integer.parseInt(
            // dataArray[4] ),
            // Integer.parseInt( dataArray[5] ) );
            // reader.close();
            // return b;
            case "billingaccount":
                System.out.println(
                        "Enter: BillingAccountId/MedicalRecordId/PatientId/BillingAddress/Method/CardNumber (hint: all attributes are needed)" );
                final String dataEntered5 = reader.nextLine();
                dataArray = dataEntered5.split( "/" );
                // create a new billingAccount
                final BillingAccountEntity billing = new BillingAccountEntity( Integer.parseInt( dataArray[0] ),
                        Integer.parseInt( dataArray[1] ), Integer.parseInt( dataArray[2] ), dataArray[3], dataArray[4],
                        dataArray[5] );
                //reader.close();
                return billing;
            case "checkinout":
                System.out.println(
                        "Enter: PatientId/HospitalId/DoctorId/BedNumber/StartDate/EndDate/RegistrationFee/DiagnosisDetail (hint: BedNumber, EndDate and DiagnosisDetail can be NULL)" );
                final String dataEntered6 = reader.nextLine();
                dataArray = dataEntered6.split( "/" );
                // check if bed number is null
                Integer bedNumber;
                if (dataArray[3].equalsIgnoreCase("null")) {
                    bedNumber = null;
                } else {
                    bedNumber = Integer.parseInt(dataArray[3]);
                }
                // check if end date is null
                Date endDate;
                if (dataArray[5].equalsIgnoreCase("null")) {
                    endDate = null;
                } else {
                    endDate = convertDate(dataArray[5]);
                }
                // if diagnosisDetail is null
                String diagnosisDetail;
                if (dataArray[7].equalsIgnoreCase("null")) {
                    diagnosisDetail = null;
                } else {
                    diagnosisDetail = dataArray[7];
                }
                // create a new checkInOut
                final CheckInOutEntity chkInOut = new CheckInOutEntity( Integer.parseInt( dataArray[0] ),
                        Integer.parseInt( dataArray[1] ), Integer.parseInt( dataArray[2] ),
                        bedNumber, convertDate( dataArray[4] ), endDate,
                        Integer.parseInt( dataArray[6] ), diagnosisDetail);
                //reader.close();
                return chkInOut;
            case "medicalrecord":
                System.out.println(
                        "Enter: MedicalRecordId/PatientId/HospitalId/StartDate/EndDate/DoctorId/Prescription/Diagnosic/Test/Result/Treatment/ConsultationFee/TestFee/TreatmentFee (hint: EndDate, Prescription, Diagnosic, Test, Result can be NULL)" );
                final String dataEntered7 = reader.nextLine();
                dataArray = dataEntered7.split( "/" );
                final MedicalRecordEntity medicR = new MedicalRecordEntity( Integer.parseInt( dataArray[0] ),
                        Integer.parseInt( dataArray[1] ), Integer.parseInt( dataArray[2] ), convertDate( dataArray[3] ),
                        convertDate( dataArray[4] ), Integer.parseInt( dataArray[5] ), dataArray[6], dataArray[7],
                        dataArray[8], dataArray[9], dataArray[10], Integer.parseInt( dataArray[11] ),
                        Integer.parseInt( dataArray[12] ), Integer.parseInt( dataArray[13] ) );
                //reader.close();
                return medicR;
            default:
                System.out.println( "The Relation Name You Entered Does Not Exist" );
                return null;
        }
    }

    /**
     * method to get entity Type and id
     * @param reader reader
     * @return a list of user input
     */
    public static String[] getTypeAndId2 ( final Scanner reader ) {
        System.out.println( "You selected (2) Update Information" );
        reader.nextLine();
        System.out.println( "Enter: InfoType(patient, staff, or hospital)/ID(patientId, staffId or hospitalId)" );
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        return dataArray;
    }

    /**
     * (2) Update Information
     *
     * @param reader reader
     * @return a list of Information
     */
    public static String[] getDataOperation2 ( final Scanner reader, final String type ) {
        System.out.println( "Update " + type );
        if ( type.equals( "Patient" ) ) {
            System.out.println(
                    "Enter: PatientId/SSN/Name/DateOfBirth/Gender/Age/PhoneNumber/Address/Status (hint: SSN can be NULL)" );
        }
        else if ( type.equals( "Staff" ) ) {
            System.out.println(
                    "Enter: StaffId/HospitalId/Name/Age/Gender/JobTitle/ProfessionalTitle/Department/Phone/EmailAddress/Address (hint: all attributes are needed)" );
        }
        else if ( type.equals( "Hospital" ) ) {
            System.out.println(
                    "Enter: HospitalId/AdministratorId/Address/Phone/Specialization1/ChargesPerDay1/Specialization2/ChargesPerDay2/Capacity (hint: all attributes are needed)" );
        }
        final String dataEntered = reader.nextLine();
        final String[] dataArray = dataEntered.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * (3) Delete Information
     *
     * @param reader reader
     * @return a list of id Information
     */
    public static String[] getDataOperation3 ( final Scanner reader ) {

        System.out.println( "You selected (3) Delete Information" );
        System.out.println( "Select the relation name you want to delete. (patient, staff, or hospital)" );
        reader.nextLine();
        final String relation = reader.nextLine();
        switch ( relation ) {
            case "patient":
                System.out.println( "Enter: PatientId" );
                final String dataEntered = reader.nextLine();
                String[] dataArray = new String[2];
                dataArray[0] = dataEntered;
                dataArray[1] = "Patient";
                //reader.close();
                return dataArray;
            case "staff":
                System.out.println( "Enter: StaffId" );
                final String dataEntereds = reader.nextLine();
                String[] dataArrays = new String[2];
                dataArrays[0] = dataEntereds;
                dataArrays[1] = "Staff";
                //reader.close();
                return dataArrays;
            case "hospital":
                System.out.println( "Enter: HospitalId" );
                final String dataEnteredh = reader.nextLine();
                String[] dataArrayh = new String[2];
                dataArrayh[0] = dataEnteredh;
                dataArrayh[1] = "Hospital";
                //reader.close();
                return dataArrayh;
        }
        return null;
    }

    /**
     * Get Data Array of responses from user in "Check Capacity of Hospital -
     * Check available beds" Need user to enter specialty and hospital id to
     * count for available bed number
     *
     * @param reader reader
     * @return a list has HospitalId and Specialty of beds
     */
    public static String[] getDataOperation4 ( final Scanner reader ) {
        System.out.println(
                "You selected (4) Check Number of Available Beds of a Certain Specialty in a Given Hospital" );
        System.out.println( "Enter: HospitalId/Specialty of beds" );
        reader.nextLine();
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * Get Data Array of responses from user in "Assign Patients" Need
     * PatientId, HospitalId and Specialty
     *
     * @param reader reader
     * @return a list of user input
     */
    public static String[] getDataOperation5 ( final Scanner reader ) {
        System.out.println( "You selected (5) Assign Patients" );
        System.out.println( "Enter: PatientId/HospitalId/DoctorId/Specialty/startDate" );
        reader.nextLine();
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * (6) Reserve Beds in Hospital
     *
     * @param reader reader
     * @return a list of user input
     */
    public static String[] getDataOperation6 ( final Scanner reader ) {
        System.out.println( "You selected (6) Reserve Beds in Hospital" );
        System.out.println( "Enter: PatientId/HospitalId/DoctorId/Specialty/startDate" );
        reader.nextLine();
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * (7) Release Beds in Hospital
     *
     * @param reader reader
     * @return a list of user input
     */
    public static String[] getDataOperation7 ( final Scanner reader ) {
        System.out.println( "You selected (7) Release Beds in Hospital" );
        System.out.println( "Enter: PatientId/HospitalId/BedNumber/EndDate" );
        reader.nextLine();
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * Get data array of user response of managing patient transfer between
     * hospitals
     *
     * @param reader reader
     * @return a list of user input
     */
    public static String[] getDataOperation8 ( final Scanner reader ) {
        System.out.println( "You selected (8) Manage Patient Transfer" );
        System.out.println( "Enter: PatientId/OldHospitalId/NewHospitalId/NewDoctorId/NewBedNumber/OldStartDate/NewStartDate" );
        reader.nextLine();
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * get data array of user response of entering new medical record
     *
     * @param reader reader
     * @return a list of user input
     */
    public static MedicalRecordEntity getDataOperation9 ( final Scanner reader ) {
        System.out.println( "You selected (9) Enter Medical Record" );
        System.out.println(
                "Enter: MedicalRecordId/PatientId/HospitalId/StartDate/EndDate/DoctorId/Prescription/Diagnosic/Test/Result/Treatment/ConsultationFee/TestFee/TreatmentFee (hint: EndDate, Prescription, Diagnosic, Test, Result can be NULL)" );
        reader.nextLine();
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        // handle null endDate
        Date endDate;
        if (dataArray[4].equalsIgnoreCase("null")) {
            endDate = null;
        } else {
            endDate = convertDate(dataArray[4]);
        }
        // handle null prescription
        String prescription;
        if (dataArray[6].equalsIgnoreCase("null")) {
            prescription = null;
        } else {
            prescription = dataArray[6];
        }
        // handle null diagnosic
        String diagnosic;
        if (dataArray[7].equalsIgnoreCase("null")) {
            diagnosic = null;
        } else {
            diagnosic = dataArray[7];
        }
        // handle null test
        String test;
        if (dataArray[8].equalsIgnoreCase("null")) {
            test = null;
        } else {
            test = dataArray[8];
        }
        // handle null result
        String result;
        if (dataArray[9].equalsIgnoreCase("null")) {
            result = null;
        } else {
            result = dataArray[9];
        }
        // create a new MedicalRecord
        final MedicalRecordEntity medicR = new MedicalRecordEntity( Integer.parseInt( dataArray[0] ),
                Integer.parseInt( dataArray[1] ), Integer.parseInt( dataArray[2] ), convertDate(dataArray[3]),
                endDate, Integer.parseInt( dataArray[5] ), prescription, diagnosic, test,
                result, dataArray[10], Integer.parseInt( dataArray[11] ), Integer.parseInt( dataArray[12] ),
                Integer.parseInt( dataArray[13] ) );
        return medicR;
    }

    /**
     * mehtod to get Medical Record id
     * @param reader reader
     * @return Medical Record id
     */
    public static String getId10 ( final Scanner reader ) {
        System.out.println( "You selected (10) Update Medical Record" );
        reader.nextLine();
        System.out.println( "Enter: MedicalRecordId" );
        final String id = reader.nextLine();
        return id;
    }

    /**
     * (10) Update Medical Record
     *
     * @param reader reader
     * @return a list of user input
     */
    public static String[] getDataOperation10 ( final Scanner reader ) {
        System.out.println( "Update Medical Record" );
        System.out.println(
                "Enter: MedicalRecordId/PatientId/HospitalId/StartDate/EndDate/DoctorId/Prescription/Diagnosic/Test/Result/Treatment/ConsultationFee/TestFee/TreatmentFee (hint: EndDate, Prescription, Diagnosic, Test, Result can be NULL)" );
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * (11) Enter CheckInOrOut
     *
     * @param reader reader
     * @return a list of user input
     */
    public static CheckInOutEntity getDataOperation11 ( final Scanner reader ) {
        System.out.println( "You selected (11) Enter CheckInOrOut" );
        System.out.println(
                "Enter: PatientId/HospitalId/DoctorId/BedNumber/StartDate/EndDate/RegistrationFee/DiagnosisDetail" );
        reader.nextLine();
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        // handle null bedNumber
        Integer bedNumber;
        if (dataArray[3].equalsIgnoreCase("null")) {
            bedNumber = null;
        } else {
            bedNumber = Integer.parseInt(dataArray[3]);
        }
        // handle null endDate
        Date endDate;
        if (dataArray[5].equalsIgnoreCase("null")) {
            endDate = null;
        } else {
            endDate = convertDate(dataArray[5]);
        }
        // handle null diagnosisDetail
        String diagnosisDetail;
        if (dataArray[7].equalsIgnoreCase("null")) {
            diagnosisDetail = null;
        } else {
            diagnosisDetail = dataArray[7];
        }
        // new CheckInOut
        final CheckInOutEntity chkInOut = new CheckInOutEntity( Integer.parseInt( dataArray[0] ),
                Integer.parseInt( dataArray[1] ), Integer.parseInt( dataArray[2] ), bedNumber,
                convertDate( dataArray[4] ), endDate, Integer.parseInt( dataArray[6] ),
                diagnosisDetail );
        return chkInOut;
    }

    /**
     * method to get PatientId/HospitalId/StartDate
     * @param reader reader
     * @return a list has PatientId/HospitalId/StartDate
     */
    public static String[] getId12 ( final Scanner reader ) {
        System.out.println( "You selected (12) Update CheckInOrOut" );
        reader.nextLine();
        System.out.println( "Enter: PatientId/HospitalId/StartDate" );
        final String[] ids = reader.nextLine().split( "/" );
        return ids;
    }

    /**
     * (12) Update CheckInOrOut
     *
     * @param reader reader
     * @return a list of user input
     */
    public static String[] getDataOperation12 ( final Scanner reader ) {
        System.out.println( "Update CheckInOut Detail" );
        System.out.println(
                "Enter: PatientId/HospitalId/DoctorId/BedNumber/StartDate/EndDate/RegistrationFee/DiagnosisDetail" );
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * (15) Create Billing Account
     *
     * @param reader reader
     * @return a list of user inputs
     */
    public static BillingAccountEntity getDataOperation15 ( final Scanner reader ) {
        System.out.println( "You selected (15) Create Billing Account" );
        System.out.println(
                "Enter: BillingAccountId/MedicalRecordId/PatientId/BillingAddress/Method/CardNumber (hint: all attributes are needed)" );
        reader.nextLine();
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        final BillingAccountEntity billing = new BillingAccountEntity( Integer.parseInt( dataArray[0] ),
                Integer.parseInt( dataArray[1] ), Integer.parseInt( dataArray[2] ), dataArray[3], dataArray[4],
                dataArray[5] );
        return billing;
    }

    /**
     * method to get BillingAccountId
     * @param reader reader
     * @return BillingAccountId
     */
    public static String getId16 ( final Scanner reader ) {
        System.out.println( "You selected (16) Maintain Billing Account" );
        reader.nextLine();
        System.out.println( "Enter: BillingAccountId" );
        final String ids = reader.nextLine();
        return ids;
    }

    /**
     * (16) Maintain Billing Account
     *
     * @param reader reader
     * @return a list of user inputs
     */
    public static String[] getDataOperation16 ( final Scanner reader ) {
        System.out.println( "Update billing Account" );
        System.out.println( "Enter: billingAccountId/medicalRecordId/patientId/billingAddress/method/cardNumber" );
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * (17) Generate Billing History
     *
     * @param reader reader
     * @return a list of user inputs
     */
    public static String[] getDataOperation17 ( final Scanner reader ) {
        System.out.println( "You selected (17) Generate Billing History" );
        reader.nextLine();
        System.out.println( "Enter: PatientId/startVisitDate/endVisitDate" );
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * (18) Return Current Usage Status for all Hospitals
     *
     * @param reader
     * @return
     */
    // public static String getDataOperation18 ( final Scanner reader ) {
    // System.out.println( "You selected (18) Return Current Usage Status for
    // all Hospitals" );
    // .out.println( "Enter: Status" );
    // reader.nextLine();
    // final String dataLine = reader.nextLine();
    // final String data = dataLine;
    // reader.close();
    // return data;
    // }

    /**
     * (19) Return the Number of Patients per Month
     *
     * @param reader reader
     * @return a list of user inputs
     */
    public static String[] getDataOperation19 ( final Scanner reader ) {
        System.out.println( "You selected (19) Return the Number of Patients per Month" );
        System.out.println( "Enter: HospitalId/StartDate/EndDate" );
        reader.nextLine();
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * (20) Return the Hospital Usage Percentage
     *
     * @param reader reader
     * @return a list of user inputs
     */
    public static String getDataOperation20 ( final Scanner reader ) {
        System.out.println( "You selected (20) Return the Hospital Usage Percentage" );
        System.out.println( "Enter: HospitalId" );
        reader.nextLine();
        final String data = reader.nextLine();
        //reader.close();
        return data;
    }

    /**
     * (21) Return Information on all Doctors a Given Patient is Currently
     * Seeing
     *
     * @param reader reader
     * @return a list of user inputs
     */
    public static String[] getDataOperation21 ( final Scanner reader ) {
        System.out.println( "You selected (21) Return Information on all Doctors a Given Patient is Currently Seeing" );
        System.out.println( "Enter: PatientId" );
        reader.nextLine();
        final String dataLine = reader.nextLine();
        final String[] dataArray = dataLine.split( "/" );
        //reader.close();
        return dataArray;
    }

    /**
     * (22) Return Information on Hospitals Grouped by Specialty
     *
     * @param reader reader
     * @return a list of user inputs
     */
    public static void getDataOperation22 ( final Scanner reader ) {

        // retrive data from the database
    }

    /**
     * print the instructions for entering data
     */
    private static void printDataEntryInstruction() {
        System.out.println( "***********************" );
        System.out.println( "Instructions for Entering Data" );
        System.out.println( "Data will be prompted in format of Data1/Data2/Data3" );
        System.out.println( "Data should be entered in format of Value1/Value2/Value3, no space between slashes" );
        System.out.println( "If a data value is NULL, replace the block with a null" );
        System.out.println( "For example, Value2 is NULL, user should input: Value1/null/Value3" );
        System.out.println( "***********************" );
    }

    /**
     * construct operation list as string array containing operations and IDs
     *
     * @return string array of operation list
     */
    public static String[] createOperationList () {

        // create string array containing all operations
        final String[] operationList = new String[23];

        operationList[0] = "(1) Enter Information";
        operationList[1] = "(2) Update Information";
        operationList[2] = "(3) Delete Information";
        operationList[3] = "(4) Check Number of Available Beds of a Certain Specialty in a Given Hospital";
        operationList[4] = "(5) Assign Patients";
        operationList[5] = "(6) Reserve Beds in Hospital";
        operationList[6] = "(7) Release Beds in Hospital";
        operationList[7] = "(8) Manage Patient Transfers";

        operationList[8] = "(9) Enter Medical Record";
        operationList[9] = "(10) Update Medical Record";
        operationList[10] = "(11) Enter CheckInOrOut";
        operationList[11] = "(12) Update CheckInOrOut";
        operationList[12] = "(13) Enter Test Record (Removed Operation)";
        operationList[13] = "(14) Update Test Record (Removed Operation)";

        operationList[14] = "(15) Create Billing Account";
        operationList[15] = "(16) Maintain Billing Account";

        operationList[16] = "(17) Generate Billing History";
        operationList[17] = "(18) Return Current Usage Status for all Hospitals";
        operationList[18] = "(19) Return the Number of Patients per Month";
        operationList[19] = "(20) Return the Hospital Usage Percentage";
        operationList[20] = "(21) Return Information on all Doctors a Given Patient is Currently Seeing";
        operationList[21] = "(22) Return Information on Hospitals Grouped by Specialty";
        operationList[22] = "(23) Exit the System";
        return operationList;
    }

    /**
     * print welcome message to user of the system
     */
    public static void printWelcomeMsg () {
        System.out.println( "Welcome to WolfHospital" );
        System.out.println( "***********************" );
    }

    /**
     * print operation list from given string array
     *
     * @param operationList
     *            given containing operations
     */
    public static void printOperationList ( final String[] operationList ) {
        for ( int i = 0; i < operationList.length; i++ ) {
            System.out.println( operationList[i] );
        }
        System.out.println( "***********************" );
    }

    /**
     * prompt user for operation selection
     *
     * @param operationList
     *            given operation list as string array
     * @return choice number in front of the operation selected
     */
    public static int promptUser ( final String[] operationList, final Scanner reader ) {
        System.out.print( "Enter Operation Number: " );
        // Scanner reader = new Scanner(System.in);
        while ( !reader.hasNextInt() ) { // if the input is not integer
            reader.next();
            System.out.println( "Input value must be interger" );
            System.out.print( "Enter Operation Number: " );
        }

        int choiceNum = reader.nextInt();

        while ( choiceNum > 23 || choiceNum < 1 ) { // if the int value is out
                                                    // of range
            System.out.println( "Input value must be from 1 to 23" );
            System.out.print( "Enter Operation Number: " );
            while ( !reader.hasNextInt() ) {
                reader.next();
                System.out.println( "Input value must be interger" );
                System.out.print( "Enter Operation Number: " );
            }
            choiceNum = reader.nextInt();
        }
        System.out.println( "Your choice is " + operationList[choiceNum - 1] ); // display
                                                                                // selection
                                                                                // to
                                                                                // the
                                                                                // user
        // reader.close(); //close the scanner
        return choiceNum;
    }

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
        catch ( final ParseException e ) {

        }
        return date;

    }

}

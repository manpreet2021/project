package project.repository;

import org.springframework.data.jpa.repository.Query;
import project.entity.PatientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, Long> {
    PatientEntity findByPhoneNumber(String phoneNumber);

    PatientEntity findByPatientId(String patientId);

    PatientEntity findByEmail(String email);
    @Query(value="select * from patient ORDER BY appointmentdate",nativeQuery = true)
    List<PatientEntity> getAllPatientsByDate();
    @Query(value="select MAX(appointmentdate) from patient",nativeQuery = true)
    Date getLatestAppointmentdate();
    @Query(value="SELECT DATE_ADD(:date, INTERVAL 10 DAY)",nativeQuery = true)
    Date updateDate(Date date);
}
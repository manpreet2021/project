package project.service;

import project.dto.PatientDto;
import project.response.PatientRest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PatientService extends UserDetailsService {
    PatientDto createPatient(PatientDto userDto);

    PatientDto findPatient(String phoneNumber);

    PatientDto findPatientByPhone(String phone);

    List<PatientRest> findAll();

    PatientDto findPatientByPatientID(String patientId);

    PatientDto updatePatient(PatientDto patientDto);

    List<PatientDto> getAllPatientsInOrder();

    PatientDto scheduleAppointment(String patientId);

}

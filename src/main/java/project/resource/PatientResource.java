package project.resource;

import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.dto.PatientDto;
import project.dto.Utils;
import project.entity.PatientEntity;
import project.repository.PatientRepository;
import project.request.PatientRequestModel;
import project.response.PatientRest;
import project.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("patient")
public class PatientResource {
    @Autowired
    Utils utils;
    @Autowired
    PatientService patientService;
    @Autowired
    PatientRepository patientRepository;
    @PostMapping
    public PatientRest createPatient(@RequestBody  PatientRequestModel patient){
        PatientDto patientDto=utils.getMapper().map(patient,PatientDto.class);
        return utils.getMapper()
                .map(patientService.createPatient(patientDto),PatientRest.class);
    }
    @DeleteMapping
    public void delete(@RequestParam("patientId") String patientId){
        if(patientId==null){
            throw new RuntimeException("PatientId Cant be null");
        }
        PatientEntity patientEntity=patientRepository.findByPatientId(patientId);
        if(patientEntity==null){
            throw new RuntimeException("No such Patient Exists.");
        }
        patientRepository.delete(patientEntity);
    }
    @GetMapping
    public List<PatientRest> getAllPatientsAsc(){
        List<PatientDto> patients=patientService.getAllPatientsInOrder();
      return   utils.getMapper()
              .map(patients,new TypeToken<List<PatientRest>>(){}.getType());
    }
    @PutMapping
    public PatientRest updatePatient(@RequestBody  PatientDto patientDto){
        if(patientDto.getPatientId()==null){
            throw new RuntimeException("PatientId Cant be null");
        }
        return utils.getMapper().map(patientService
                .updatePatient(patientDto),PatientRest.class);
    }
    @PutMapping("/appointment")
    public PatientRest scheduleAppointment(@RequestParam("patientId")String patientId){
       return utils.getMapper().map(patientService
               .scheduleAppointment(patientId),PatientRest.class);
    }

}

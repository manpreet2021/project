package project.service.impl;


import project.dto.PatientDto;
import project.dto.Utils;
import project.entity.PatientEntity;
import project.repository.PatientRepository;
import project.response.PatientRest;
import project.service.PatientService;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        userValidation(patientDto);

        if (patientRepository.findByPhoneNumber(patientDto.getPhoneNumber()) != null) {
            throw new RuntimeException("Patient already exists");
        }
        PatientEntity patientEntity = utils.getMapper().map(patientDto, PatientEntity.class);

        patientEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(patientDto.getPassword()));
        patientEntity.setPatientId(utils.generateLargeLengthId());
        patientEntity = patientRepository.save(patientEntity);
        return utils.getMapper().map(patientEntity, PatientDto.class);
    }

    @Override
    public PatientDto findPatient(String patientId) {
        PatientEntity patientEntity = patientRepository.findByPatientId(patientId);
        if (patientEntity == null) {
            throw new RuntimeException("Patient not found");
        }
        return utils.getMapper().map(patientEntity, PatientDto.class);
    }

    public PatientDto findPatientByPhone(String phone) {
        PatientEntity patientEntity = patientRepository.findByPhoneNumber(phone);
        if (patientEntity == null) throw new UsernameNotFoundException(phone);
        return utils.getMapper().map(patientEntity, PatientDto.class);
    }

    @Override
    public List<PatientRest> findAll() {
        List<PatientEntity> patients = (List<PatientEntity>) patientRepository.findAll();
        return utils.getMapper().map(patients, new TypeToken<List<PatientDto>>() {
        }.getType());
    }

    @Override
    public PatientDto findPatientByPatientID(String patientId) {
        if (patientId == null) throw new RuntimeException("PatientId cannot be null");
        PatientEntity patientEntity = patientRepository.findByPatientId(patientId);
        if (patientEntity == null) throw new RuntimeException("No patientId exist with such name");
        return utils.getMapper().map(patientEntity, PatientDto.class);
    }

    @Override
    public PatientDto updatePatient(PatientDto patientDto) {
        userValidation(patientDto);
        PatientEntity patientEntity=patientRepository.
                findByPatientId(patientDto.getPatientId());
        if(patientEntity==null){
            throw new RuntimeException("Invalid PatientId");
        }

        PatientEntity patientEntity1=utils.getMapper()
                .map(patientDto,PatientEntity.class);
        patientEntity1.setId(patientEntity.getId());
        patientEntity1.setEncryptedPassword(bCryptPasswordEncoder
                .encode(patientDto.getPassword()));
        return utils.getMapper().map(patientRepository
                .save(patientEntity1),PatientDto.class);
    }

    @Override
    public List<PatientDto> getAllPatientsInOrder() {
        List<PatientEntity> patientEntity=patientRepository.getAllPatientsByDate();
        return utils.getMapper().map(patientEntity,new TypeToken<List<PatientDto>>()
        {}.getType());
    }

    @Override
    public PatientDto scheduleAppointment(String patientId) {
        if(patientId==null){
            throw new RuntimeException("PatientId Cant be null");
        }
        PatientEntity patientEntity=patientRepository.findByPatientId(patientId);
        if(patientEntity==null){
            throw new RuntimeException("No such Patient Exists.");
        }

        //PatientEntity patientEntity1=patientRepository.getLatestAppointmentdate();
        Date date1=patientRepository.getLatestAppointmentdate();
        if(date1==null){
            patientEntity.setAppointmentdate(new Date());
        }
        else{

            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            c.add(Calendar.DATE, 1);
            Date currentDatePlusOne = c.getTime();

            patientEntity.setAppointmentdate(currentDatePlusOne);
        }
        return utils.getMapper().map(patientRepository.save(patientEntity)
                ,PatientDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {

         PatientEntity patientEntity=patientRepository.findByPhoneNumber(phone);
             if(patientEntity==null){
                 throw new RuntimeException("No User Exist With such name");
             }

        return new User(patientEntity.getPhoneNumber(), patientEntity.getEncryptedPassword(), new ArrayList<>());
    }
    private boolean userValidation(PatientDto patientDto){
        int flag=0;
        if(patientDto.getName().length()<3){
            throw new RuntimeException("Name must be atLeast 3 characters Long");
        }
        if(patientDto.getAddress().length()<10){
            throw new RuntimeException("Address must be atLeast 10 characters Long");
        }
        if(patientDto.getPhoneNumber().length()!=10){
            throw new RuntimeException("Invalid PhoneNumber");
        }
        char ch[]=patientDto.getPhoneNumber().toCharArray();
        for(int i=0;i<10;i++){
            if(ch[i]!='0'&&ch[i]!='1'&&ch[i]!='2' &&ch[i]!='3' &&ch[i]!='4'
                    &&ch[i]!='5' &&ch[i]!='6' &&ch[i]!='7'&&ch[i]!='8'&&ch[i]!='9'){
                throw new RuntimeException("Invalid PhoneNumber");
            }
        }
        if(patientDto.getPassword().length()<7||patientDto.getPassword().length()>15){
            throw new RuntimeException(" Password  length must be greater than equal to 7 and less than equal to 15.");
        }
        char ch1[]=patientDto.getPassword().toCharArray();
        for(int i=0;i<ch.length;i++) {

            if (ch[i] == 'A' || ch[i] == 'B' || ch[i] == 'B' || ch[i] == 'C' || ch[i] == 'D' || ch[i] == 'E'
                    || ch[i] == 'F' || ch[i] == 'G' || ch[i] == 'H' || ch[i] == 'I' || ch[i] == 'J'
                    || ch[i] == 'K' || ch[i] == 'L' || ch[i] == 'M' || ch[i] == 'N' || ch[i] == 'O'
                    || ch[i] == 'P' || ch[i] == 'Q' || ch[i] == 'R' || ch[i] == 'S' || ch[i] == 'T'
                    || ch[i] == 'U' || ch[i] == 'V' || ch[i] == 'W' || ch[i] == 'X'
                    || ch[i] == 'Y' || ch[i] == 'Z') {
                flag++;
            }

            if (ch[i] == 'a' || ch[i] == 'b' || ch[i] == 'c' || ch[i] == 'd' || ch[i] == 'e'
                    || ch[i] == 'f'
                    || ch[i] == 'g' || ch[i] == 'h' || ch[i] == 'i' || ch[i] == 'j' || ch[i] == 'k'
                    || ch[i] == 'l' || ch[i] == 'm' || ch[i] == 'n' || ch[i] == 'o' || ch[i] == 'p'
                    || ch[i] == 'p' || ch[i] == 'q' || ch[i] == 'r' || ch[i] == 's' || ch[i] == 't'
                    || ch[i] == 'u' || ch[i] == 'v' || ch[i] == 'w' || ch[i] == 'x'
                    || ch[i] == 'y' || ch[i] == 'z') {
                flag++;
            }
            if(ch[i]=='0'||ch[i]=='1'||ch[i]=='2'||ch[i]=='3' ||ch[i]=='4'
                    ||ch[i]=='5'||ch[i]=='6'||ch[i]=='7'||ch[i]=='8'||ch[i]=='9'){
                flag++;
            }

        }
        if(flag<3){
            throw new RuntimeException("Password must Contain atleast One uppercase character and one small character and atleast One Number");
        }
        return true;
    }
}

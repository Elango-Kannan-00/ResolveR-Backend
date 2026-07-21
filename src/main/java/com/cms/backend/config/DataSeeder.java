package com.cms.backend.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cms.backend.entity.AcademicDepartment;
import com.cms.backend.entity.ComplaintDepartment;
import com.cms.backend.enums.DepartmentType;
import com.cms.backend.repository.AcademicDepartmentRepository;
import com.cms.backend.repository.ComplaintDepartmentRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDepartments(
            AcademicDepartmentRepository academicDepartmentRepository,
            ComplaintDepartmentRepository complaintDepartmentRepository) {
        return args -> {
            seedAcademicDepartments(academicDepartmentRepository);
            seedComplaintDepartments(academicDepartmentRepository, complaintDepartmentRepository);
        };
    }

    private void seedAcademicDepartments(AcademicDepartmentRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        repository.saveAll(List.of(
                department("CSE"),
                department("ECE"),
                department("IT"),
                department("AIDS"),
                department("CSBS"),
                department("EEE"),
                department("MECH"),
                department("CIVIL"),
                department("ECX")));
    }

    private void seedComplaintDepartments(
            AcademicDepartmentRepository academicDepartmentRepository,
            ComplaintDepartmentRepository complaintDepartmentRepository) {
        if (complaintDepartmentRepository.count() > 0) {
            return;
        }

        List<ComplaintDepartment> departments = List.of(
                complaintDepartment("Hostel", DepartmentType.COMMON, null),
                complaintDepartment("Infrastructure", DepartmentType.COMMON, null),
                complaintDepartment("Library", DepartmentType.COMMON, null),
                complaintDepartment("Transport", DepartmentType.COMMON, null),
                complaintDepartment("Physical Education", DepartmentType.COMMON, null),
                complaintDepartment("CSE", DepartmentType.ACADEMIC,
                        academicDepartmentRepository.findById(lookupAcademicDepartmentId(academicDepartmentRepository, "CSE")).orElse(null)),
                complaintDepartment("ECE", DepartmentType.ACADEMIC,
                        academicDepartmentRepository.findById(lookupAcademicDepartmentId(academicDepartmentRepository, "ECE")).orElse(null)),
                complaintDepartment("IT", DepartmentType.ACADEMIC,
                        academicDepartmentRepository.findById(lookupAcademicDepartmentId(academicDepartmentRepository, "IT")).orElse(null)),
                complaintDepartment("AIDS", DepartmentType.ACADEMIC,
                        academicDepartmentRepository.findById(lookupAcademicDepartmentId(academicDepartmentRepository, "AIDS")).orElse(null)),
                complaintDepartment("CSBS", DepartmentType.ACADEMIC,
                        academicDepartmentRepository.findById(lookupAcademicDepartmentId(academicDepartmentRepository, "CSBS")).orElse(null)),
                complaintDepartment("EEE", DepartmentType.ACADEMIC,
                        academicDepartmentRepository.findById(lookupAcademicDepartmentId(academicDepartmentRepository, "EEE")).orElse(null)),
                complaintDepartment("MECH", DepartmentType.ACADEMIC,
                        academicDepartmentRepository.findById(lookupAcademicDepartmentId(academicDepartmentRepository, "MECH")).orElse(null)),
                complaintDepartment("CIVIL", DepartmentType.ACADEMIC,
                        academicDepartmentRepository.findById(lookupAcademicDepartmentId(academicDepartmentRepository, "CIVIL")).orElse(null)),
                complaintDepartment("ECX", DepartmentType.ACADEMIC,
                        academicDepartmentRepository.findById(lookupAcademicDepartmentId(academicDepartmentRepository, "ECX")).orElse(null)));

        complaintDepartmentRepository.saveAll(departments);
    }

    private AcademicDepartment department(String name) {
        AcademicDepartment department = new AcademicDepartment();
        department.setDepartmentName(name);
        return department;
    }

    private ComplaintDepartment complaintDepartment(
            String name,
            DepartmentType type,
            AcademicDepartment academicDepartment) {
        ComplaintDepartment department = new ComplaintDepartment();
        department.setDepartmentName(name);
        department.setDepartmentType(type);
        department.setAcademicDepartment(academicDepartment);
        return department;
    }

    private Long lookupAcademicDepartmentId(AcademicDepartmentRepository repository, String departmentName) {
        return repository.findAll()
                .stream()
                .filter(department -> departmentName.equals(department.getDepartmentName()))
                .findFirst()
                .map(AcademicDepartment::getAcademicDepartmentId)
                .orElseThrow(() -> new IllegalStateException("Missing academic department: " + departmentName));
    }
}

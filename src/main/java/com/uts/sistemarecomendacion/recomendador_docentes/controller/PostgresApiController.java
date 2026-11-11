package com.uts.sistemarecomendacion.recomendador_docentes.controller;

import com.uts.sistemarecomendacion.recomendador_docentes.repository.postgres.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/postgres")
public class PostgresApiController {

    @Autowired private CampusRepository campusRepo;
    @Autowired private ConfigurationRepository configurationRepo;
    @Autowired private CoordinatorRepository coordinatorRepo;
    @Autowired private DeanRepository deanRepo;
    @Autowired private FormatRepository formatRepo;
    @Autowired private TeacherRepository teacherRepo;
    @Autowired private ActivityRepository activityRepo;
    @Autowired private ProgramRepository programRepo;
    @Autowired private ScheduleRepository scheduleRepo;

    @GetMapping("/campus") public ResponseEntity<?> getCampus() { return ResponseEntity.ok(campusRepo.findAll()); }
    @GetMapping("/configurations") public ResponseEntity<?> getConfigurations() { return ResponseEntity.ok(configurationRepo.findAll()); }
    @GetMapping("/coordinators") public ResponseEntity<?> getCoordinators() { return ResponseEntity.ok(coordinatorRepo.findAll()); }
    @GetMapping("/deans") public ResponseEntity<?> getDeans() { return ResponseEntity.ok(deanRepo.findAll()); }
    @GetMapping("/formats") public ResponseEntity<?> getFormats() { return ResponseEntity.ok(formatRepo.findAll()); }
    @GetMapping("/teachers") public ResponseEntity<?> getTeachers() { return ResponseEntity.ok(teacherRepo.findAll()); }
    @GetMapping("/activities") public ResponseEntity<?> getActivities() { return ResponseEntity.ok(activityRepo.findAll()); }
    @GetMapping("/programs") public ResponseEntity<?> getPrograms() { return ResponseEntity.ok(programRepo.findAll()); }
    @GetMapping("/schedules") public ResponseEntity<?> getSchedules() { return ResponseEntity.ok(scheduleRepo.findAll()); }
}
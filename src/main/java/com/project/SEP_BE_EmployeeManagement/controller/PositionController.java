package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.PositionDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.position.CreatePositionRequest;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import com.project.SEP_BE_EmployeeManagement.service.PositionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/position")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PositionController {
    @Autowired
    PositionService positionService;
    @GetMapping("/get-all")
    public ResponseEntity<?> GetAll(){
        return ResponseEntity.ok(positionService.GetAll());
    }

    @GetMapping("/data")
    public ResponseEntity<Page<PositionDto>> getPosition(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                                                      @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
                                                      @RequestParam(name = "search", required = false, defaultValue = "") String search){
        Page<PositionDto> response = positionService.getData(search, pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDto> getPositionById( @PathVariable long id) throws NotFoundException {
        PositionDto response = positionService.getPositionById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Position> createPosition(@RequestBody CreatePositionRequest request) throws NotFoundException {
        Position response = positionService.createPosititon(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Position> updatePosititon(@RequestBody CreatePositionRequest request, @PathVariable long id) throws NotFoundException {
        Position response = positionService.updatePosition(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Integer> deletePosition(@PathVariable Integer id) throws NotFoundException {
        int result = positionService.deletePosition(id);
        if (result == 1) {
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        return new  ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
    }

}

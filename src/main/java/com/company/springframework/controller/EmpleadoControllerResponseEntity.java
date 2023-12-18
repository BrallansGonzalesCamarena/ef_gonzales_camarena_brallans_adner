package com.company.springframework.controller;

import com.company.springframework.model.Empleado;
import com.company.springframework.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleador-response-entity")
public class EmpleadoControllerResponseEntity {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<Empleado>> listarTodosLosEmpleados() {
        return ResponseEntity.ok(empleadoService.listarTodosLosEmpleados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleado(@PathVariable("id") Long id) {
        Empleado empleado = empleadoService.obtenerEmpleado(id);
        return empleado != null ? ResponseEntity.ok(empleado) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Empleado> guardarEmpleado(@RequestBody Empleado empleado) {
        Empleado nuevoEmpleado = empleadoService.guardarEmpleado(empleado);
        return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable("id") Long id, @RequestBody Empleado empleado) {
        empleado.setId(id);
        Empleado empleadoActualizado = empleadoService.actualizarEmpleado(empleado);
        return empleadoActualizado != null ? ResponseEntity.ok(empleadoActualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable("id") Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}

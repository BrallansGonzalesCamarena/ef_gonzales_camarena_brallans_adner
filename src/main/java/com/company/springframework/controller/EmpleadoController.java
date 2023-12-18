package com.company.springframework.controller;

import com.company.springframework.model.Empleado;
import com.company.springframework.service.EmpleadoService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> listarTodosLosEmpleados() {
        return empleadoService.listarTodosLosEmpleados();
    }

    @GetMapping("/{id}")
    public Empleado obtenerEmpleado(@PathVariable("id") Long id) {
        return empleadoService.obtenerEmpleado(id);
    }

    @PostMapping
    public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.guardarEmpleado(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable("id") Long id, @RequestBody Empleado empleado) {
        empleado.setId(id);
        return empleadoService.actualizarEmpleado(empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable("id") Long id) {
        empleadoService.eliminarEmpleado(id);
    }


    @GetMapping("/reporteEmpleado1")
    public ResponseEntity<byte[]>reporteEmpleado1() throws JRException {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleadoService.listarTodosLosEmpleados());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("titulo", "Reporte Empleados");
        JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/reportes/Reportes.jasper", parameters, dataSource);
        byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("EmpleadoReporte.pdf").build());
        return ResponseEntity.ok()
                .headers(headers)
                .body(reporte);
    }
    @GetMapping("/reporteEmpleado2")
    public ResponseEntity<byte[]> reporteEmpleado2() throws JRException {
        JasperReport report = JasperCompileManager.compileReport("src/main/resources/reportes/Reportes.jrxml");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleadoService.listarTodosLosEmpleados());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("titulo", "Reporte Empleado");
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
        byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("EmpleadoReporte.pdf").build());
        return ResponseEntity.ok()
                .headers(headers)
                .body(reporte);
    }
}

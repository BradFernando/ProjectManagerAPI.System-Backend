package com.system.pma.service;

import com.system.pma.dto.EmployeeDto;
import com.system.pma.dto.ReportRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReportService {

    private final EmployeeService employeeService;
    private final NotificationService notificationService;

    @Autowired
    public ReportService(EmployeeService employeeService, NotificationService notificationService) {
        this.employeeService = employeeService;
        this.notificationService = notificationService;
    }

    public boolean processAndSendReport(ReportRequestDto reportRequest) {
        try {
            // Obtener datos del empleado usando employeeId
            Long employeeId = reportRequest.getEmployeeId();
            EmployeeDto employee = employeeService.getEmployeeById(employeeId);

            if (employee == null) {
                System.out.println("Empleado no encontrado con ID: " + employeeId);
                return false;
            }

            // Aquí puedes agregar lógica para guardar el PDF en el sistema de archivos o en la base de datos
            byte[] pdfData = reportRequest.getPdf().getBytes();
            String pdfFilename = "Reporte_" + employee.getFirstName() + "_" + employee.getLastName() + ".pdf";

            // Simulación de almacenamiento del archivo (puedes implementarlo según tus necesidades)
            System.out.println("PDF recibido para: " + employee.getFirstName() + " " + employee.getLastName());
            System.out.println("Archivo PDF: " + pdfFilename);

            // Enviar notificación al empleado utilizando algún servicio (como FCM)
            boolean notificationSent = notificationService.sendNotificationToEmployee(employeeId, "Has recibido un nuevo reporte.");

            return notificationSent;
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo PDF: " + e.getMessage());
            return false;
        }
    }
}

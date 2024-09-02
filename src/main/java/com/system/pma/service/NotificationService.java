package com.system.pma.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public boolean sendNotificationToEmployee(Long employeeId, String message) {
        // Implementación de lógica para enviar notificaciones (por ejemplo, usando FCM)
        // Esto es solo un ejemplo simple para mostrar cómo podrías estructurar la lógica

        // Supongamos que la notificación fue enviada con éxito
        System.out.println("Enviando notificación al empleado ID: " + employeeId + " con el mensaje: " + message);
        return true; // Simulación de éxito
    }
}

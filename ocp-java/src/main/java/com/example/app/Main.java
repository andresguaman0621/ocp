// class NotificationService {
//     public void sendNotification(String type, String message) {
//         if (type.equals("Email")) {
//             System.out.println("Sending Email: " + message);
//         } else if (type.equals("SMS")) {
//             System.out.println("Sending SMS: " + message);
//         } else if (type.equals("Push")) {
//             System.out.println("Sending Push Notification: " + message);
//         } else {
//             System.out.println("Invalid notification type!");
//         }
//     }
// }

// public class Main {
//     public static void main(String[] args) {
//         NotificationService service = new NotificationService();
//         service.sendNotification("Email", "Hello via Email!");
//         service.sendNotification("SMS", "Hello via SMS!");
//         service.sendNotification("Push", "Hello via Push Notification!");
//         service.sendNotification("Fax", "Hello via Fax!");
//     }
// }

// 
// REFACTORIZACION
//

// Interfaz base para todas las notificaciones
interface Notification {
    void send(String message);
}

// Implementación para notificaciones por Email
class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

// Implementación para notificaciones por SMS
class SMSNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

// Implementación para notificaciones Push
class PushNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending Push Notification: " + message);
    }
}

// Nueva implementación para notificaciones por Fax (extensión sin modificar código existente)
class FaxNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending Fax: " + message);
    }
}

// Implementación adicional para demostrar extensibilidad
class SlackNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending Slack Message: " + message);
    }
}

// Factory para crear instancias de notificaciones
class NotificationFactory {
    public static Notification createNotification(String type) {
        switch (type.toLowerCase()) {
            case "email":
                return new EmailNotification();
            case "sms":
                return new SMSNotification();
            case "push":
                return new PushNotification();
            case "fax":
                return new FaxNotification();
            case "slack":
                return new SlackNotification();
            default:
                return null;
        }
    }
}

// Servicio refactorizado que cumple con OCP
class NotificationService {
    public void sendNotification(String type, String message) {
        Notification notification = NotificationFactory.createNotification(type);
        if (notification != null) {
            notification.send(message);
        } else {
            System.out.println("Invalid notification type: " + type);
        }
    }
    
    // Método alternativo que recibe directamente la implementación
    public void sendNotification(Notification notification, String message) {
        notification.send(message);
    }
}

// Clase principal para probar el sistema
public class Main {
    public static void main(String[] args) {
        NotificationService service = new NotificationService();
        
        System.out.println("=== Prueba usando Factory Pattern ===");
        service.sendNotification("Email", "Hello via Email!");
        service.sendNotification("SMS", "Hello via SMS!");
        service.sendNotification("Push", "Hello via Push Notification!");
        service.sendNotification("Fax", "Hello via Fax!");
        service.sendNotification("Slack", "Hello via Slack!");
        service.sendNotification("Invalid", "This should show error message");
        
        System.out.println("\n=== Prueba usando inyección directa de dependencias ===");
        service.sendNotification(new EmailNotification(), "Direct Email injection!");
        service.sendNotification(new SMSNotification(), "Direct SMS injection!");
        service.sendNotification(new PushNotification(), "Direct Push injection!");
        service.sendNotification(new FaxNotification(), "Direct Fax injection!");
        service.sendNotification(new SlackNotification(), "Direct Slack injection!");
        
        System.out.println("\n=== Demostración de extensibilidad ===");
        // Se puede agregar fácilmente un nuevo tipo sin modificar código existente
        Notification telegramNotification = new Notification() {
            @Override
            public void send(String message) {
                System.out.println("Sending Telegram Message: " + message);
            }
        };
        service.sendNotification(telegramNotification, "Anonymous Telegram implementation!");
    }
}
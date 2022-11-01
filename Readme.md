# Para correr los modulos:
## Admin y reporte en REST:
>Se ejecuta con **vscode**, en **launch.json** se encuentra la configuración que levanta los proyectos

### Servicios de docente y estudiante en SOAP:
Para levantar el servicio wcf es necesario tener instalado **iisexpress**. Levantar en un simbolo del sistema para cada una de las siguientes instancias. Posicionandonos **en la ruta donde se encuentre instalado iisexpress**, por ejemplo: 
>cd C:\Program Files\IIS Express

Y luego ejecutamos el siguiente comando para levantar el **servicio soap del estudiante**:

>iisexpress /path:"..\guarani-web-service-soap-rest\estudiante\Estudiante-Service\Estudiante-Service" /port:63599 

 
En otra consola diferente seguir los mismos pasos y ejecutar para levantar el **servicio soap del docente**: 
>iisexpress /path:"..\guarani-web-service-soap-rest\docente\Docente-Service\Docente-Service" /port:63598

### Clientes consumidores de servicios web SOAP
Seguidamente ejecutamos los **clientes consumidores de estos servicios web soap**, posicionandonos sobre la ruta del proyecto:
>cd ..\guarani-web-service-soap-rest\docente\Docente-Service\DocenteSOAPClient

Ejecutamos:
>dotnet run DocenteSOAPClient

Y lo mismo para el cliente del estudiante, posicionandonos en la ruta del proyecto:
>cd ..\guarani-web-service-soap-rest\estudiante\Estudiante-Service\EstudianteSOAPClient

ejecutamos:
>dotnet run EstudianteSOAPClient

### Para correr el frontend en react.js
Posicionarse en: 
>cd ..\guarani-web-service-soap-rest\views\guarani-views

Y ejecutar en el siguiente orden para instalar las dependencias, compilar y correr la aplicacion
>npm install
npm start
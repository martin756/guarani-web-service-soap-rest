
Una vez corriendo el proyecto, si es la primera vez y se necesitan generar datos.. en esta ruta se van a generar algunos datos 
para poblar a las tablas de algunas carreras, materias, usuarios, cuatrimestres, turnos y catedras.

http://127.0.0.1:8080/generacionDatos

Por el momento solo implementado Create, Read y update.

La key de una catedra es compuesta por idturno, idprofesor, idmateria, idcuatrimestre

entonces de querer hacer update o leer una catedra especifica la rura sera

http://127.0.0.1:8080/catedra/idTurno/idProfesor/idMateria/idCuatrimestre

Recursos:

get Carreras:
GET http://127.0.0.1:8080/carrera

Carrera especifica:
GET http://127.0.0.1:8080/carrera/id

Crear Carrera:
POST http://127.0.0.1:8080/carrera

Update Carrera:
POST http://127.0.0.1:8080/carrera/id


get Materias:
GET http://127.0.0.1:8080/materia

Materia especifica:
GET http://127.0.0.1:8080/materia/id

Crear Materia:
POST http://127.0.0.1:8080/materia

Update Materia
POST http://127.0.0.1:8080/materia/id

etc..

Otras recursos:
cuatrimestre
turno(solo lectura)
usuario
catedra







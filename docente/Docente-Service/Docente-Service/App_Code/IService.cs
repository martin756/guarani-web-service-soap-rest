using System.Collections.Generic;
using System.ServiceModel;


// NOTA: puede usar el comando "Rename" del menú "Refactorizar" para cambiar el nombre de interfaz "IService1" en el código y en el archivo de configuración a la vez.
[ServiceContract]
public interface IService
{

    [OperationContract]
    IEnumerable<Service.MateriaDocente> GetMateriasDocente(int idDocente);

    [OperationContract]
    IEnumerable<Service.AlumnoMateria> GetAlumnosMateria(int idMateria);

    [OperationContract]
    IEnumerable<Service.MateriaDocente> GetMaterias();

    [OperationContract]
    string InsertNotasFinal(int idCatedra, List<Alumnos> alumnos);

    [OperationContract]
    string InsertNotasCursada(int idCatedra, List<Alumnos> alumnos);

}

public class Alumnos
{
    public int idAlumno { get; set; }
    public float notaParcial { get; set; }
}
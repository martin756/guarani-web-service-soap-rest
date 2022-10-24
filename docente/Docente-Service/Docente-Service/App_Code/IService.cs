using System.Collections.Generic;
using System.ServiceModel;

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
    string InsertNotasFinal(int idCatedra, List<Service.Alumnos> alumnos);

    [OperationContract]
    string InsertNotasCursada(int idCatedra, List<Service.Alumnos> alumnos);
}
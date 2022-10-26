using System.Collections.Generic;
using System.ServiceModel;

[ServiceContract]
public interface IService
{
    [OperationContract]
    IEnumerable<MateriaDocente> GetMateriasDocente(int idDocente);

    [OperationContract]
    IEnumerable<AlumnoMateria> GetAlumnosMateria(int idMateria, int idDocente, bool esFinal);

    [OperationContract]
    IEnumerable<MateriaDocente> GetMaterias();

    [OperationContract]
    string UpsertNotasCursada(int idCatedra, int nroParcial, IEnumerable<AlumnoMateriaNotaRequest> listadoAlumnos);
}

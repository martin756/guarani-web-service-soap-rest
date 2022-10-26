using System;
using System.Activities.Expressions;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

// NOTA: puede usar el comando "Rename" del menú "Refactorizar" para cambiar el nombre de interfaz "IService1" en el código y en el archivo de configuración a la vez.
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
    //string InsertNotasCursada(int idUsuario, int idCatedra, int nroParcial, int nota);
    string UpsertNotasCursada(int idCatedra, int nroParcial, IEnumerable<AlumnoMateriaNotaRequest> listadoAlumnos);
}

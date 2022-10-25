using System.Collections.Generic;
using System.ServiceModel;

[ServiceContract]
public interface IService
{
    [OperationContract]
    string UpdateModificacionDatos(int idAlumno, string password);

    [OperationContract]
    string DeleteInscripcionAlumno(int id, int idCatedra);

    [OperationContract]
    IEnumerable<Service.Materias> SelectInformeAnalitico(int idusuario);

}
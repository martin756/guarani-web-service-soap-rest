using System.Collections.Generic;
using System.ServiceModel;

[ServiceContract]
public interface IService
{
    [OperationContract]
    string UpdateModificacionDatos(int idusuario, string password);

    [OperationContract]
    string DeleteInscripcionAlumno(int idusuario, int idcatedra);

    [OperationContract]
    List<Service.Analitico> SelectInformeAnalitico(int idusuario);

}
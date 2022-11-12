using System.Collections.Generic;
using System.ServiceModel;

[ServiceContract]
public interface IService
{
    [OperationContract]
    string UpdateModificacionDatos(Estudiante datos);

    [OperationContract]
    string InscripcionAlumno(int idusuario, int idcatedra);

    [OperationContract]
    string DeleteInscripcionAlumno(int idusuario, int idcatedra);

    [OperationContract]
    Analitico SelectInformeAnalitico(int idusuario);

    [OperationContract]
    List<Catedra> TraerInscripcionesDisponibles(int idUsuario);

    [OperationContract]
    string CambioCatedra(int idUsuarioMateriaCuatrimestre, int idCatedraNueva);

}
using Dapper;
using MySql.Data.MySqlClient;
using MySqlX.XDevAPI.Common;
using System;
using System.Activities.Expressions;
using System.Activities.Statements;
using System.CodeDom;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

// NOTA: puede usar el comando "Rename" del menú "Refactorizar" para cambiar el nombre de clase "Service1" en el código, en svc y en el archivo de configuración.
public class Service : IService
{

    //-------------------------------------
    //-----b.dar de baja inscripcion------
    //-------------------------------------
    public string DeleteInscripcionAlumno(int id, int idCatedra)
    {
        //string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        //using (var db = new MySqlConnection(connection))
        //{
        //    var sql = "DELETE usuarios SET password = @password WHERE id = @id";
        //    var result = db.Execute(sql, new { password, id });

        //    if (result == 1)
        //    {
        //        return "Los cambios se ejecuaron correctamente";

        //    }
        //    return "Los cambios no se ejecuaron correctamente";
        //}
        return "Los cambios no se ejecuaron correctamente";

    }

    //-------------------------------------
    //-----c. Consultar informe analitico------
    //-------------------------------------
    IEnumerable<Materias> IService.SelectInformeAnalitico(int idusuario)
    {
        var MateriasUsuario = GetIdsMaterias(idusuario);

        foreach (var materia in MateriasUsuario)
        {
            var FinalAprobado = GetFinalAprobadoFromUsuario(materia.id);
            if (FinalAprobado)
            {
                string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
                using (var db = new MySqlConnection(connection))
                {
                    var sql = "SELECT * FROM catedra as ca INNER JOIN materia ON ca.materia_id = materia.id INNER JOIN usuario_materia_cuatrimestre ON ca.id = usuario_materia_cuatrimestre.idcatedra WHERE usuario_materia_cuatrimestre.idusuario = @idusuario AND usuario_materia_cuatrimestre.nota_promedio >= 4";
                    var result = db.Query<Materias>(sql, new { idusuario });

                    return result;
                }
            }
        }
        return null;
    }

    //-------------------------------------
    //-------d. Modificar contraseña-------
    //-------------------------------------
    public string UpdateModificacionDatos(int id, string password)
    {
        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "UPDATE usuarios SET password = @password WHERE id = @id";
            var result = db.Execute(sql, new { password, id });

            if (result == 1)
            {
                return "Los cambios se ejecuaron correctamente";

            }
            return "Los cambios no se ejecuaron correctamente";
        }
    }

    //-------------------------------------
    //----Funcion Privada de Busqueda------
    //-------------------------------------
    public int GetIdFromUsuarioMateriaCuatrimestre(int idusuario, int idcatedra)
    {
        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "SELECT id FROM usuario_materia_cuatrimestre WHERE idusuario = @idusuario AND idcatedra = @idcatedra";
            var result = db.QueryFirst(sql, new { idusuario, idcatedra });

            return result;
        }
    }

    //------------------------------------------------------------------
    //----Funcion Privada para ver si tiene final y si esta aprobado----
    //------------------------------------------------------------------
    public bool GetFinalAprobadoFromUsuario(int idusuario_materia_cuatrimestre)
    {
        try
        {
            var nro_parcial = 5;
            string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
            using (var db = new MySqlConnection(connection))
            {
                var sql = "SELECT nota FROM nota_parciales WHERE idusuario_materia_cuatrimestre = @idusuario_materia_cuatrimestre AND nro_parcial = @nro_parcial";
                var result = db.QueryFirst<int>(sql, new { idusuario_materia_cuatrimestre, nro_parcial });

                if (result >= 4)
                {
                    return true;
                }
                return false;
            }
        }
        catch (Exception)
        {
            return false;
            throw;
        }
    }

    //------------------------------------------------------------------
    //----Funcion Privada para ver las catedras de un usuario-----------
    //------------------------------------------------------------------
    public IEnumerable<Materias> GetIdsMaterias(int idusuario)
    {
        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "select id FROM usuario_materia_cuatrimestre WHERE idusuario = @idusuario";
            var result = db.Query<Materias>(sql, new { idusuario });
            return result;
        }
    }

    public class Materias
    {
        public int id { get; set; }
        public float nota_promedio { get; set; }
        public int anio { get; set; }
        public string nombre { get; set; }
        public int idcatedra { get; set; }
        public int cuatrimestre_id { get; set; }
        public int materia_id { get; set; }
    }
}
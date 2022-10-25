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
using System.Web.UI.WebControls.WebParts;
using System.Web;

// NOTA: puede usar el comando "Rename" del menú "Refactorizar" para cambiar el nombre de clase "Service1" en el código, en svc y en el archivo de configuración.
public class Service : IService
{
    //Consulta de materias/exámenes disponibles para inscripción, los listados deben mostrar los
    //días, horarios y docentes asignados.El sistema debe impedir la inscripción de materias con
    //horarios superpuestos
    //-------------------------------------------------------
    //-----a. Consulta de Materias/Examenes Inscripcion------
    //-------------------------------------------------------




    //-------------------------------------
    //-----b. dar de baja inscripcion------
    //-------------------------------------
    public string DeleteInscripcionAlumno(int idusuario, int idcatedra)
    {
        try
        {
            string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
            using (var db = new MySqlConnection(connection))
            {
                var sql = "DELETE FROM usuario_materia_cuatrimestre WHERE idusuario = @idusuario AND idcatedra = @idcatedra";
                var result = db.Execute(sql, new { idusuario, idcatedra });

                if (result == 1)
                {
                    return "Se elimino el usuario de la catedra";
                }
                else
                {
                    return "No se elimino el usuario de la catedra";
                }
            }
        }
        catch (Exception)
        {
            return "No se elimino el usuario de la catedra";
            throw;
        }
    }

    //-----------------------------------------
    //-----c. Consultar informe analitico------
    //-----------------------------------------
    public List<Analitico> SelectInformeAnalitico(int idusuario)
    {
        var MateriasUsuario = GetIdsUsuariomateriaCuatrimestre(idusuario);
        var listaMateriasAprobadas = new List<Analitico>();

        try
        {
            foreach (var materia in MateriasUsuario)
            {
                var FinalAprobado = GetFinalAprobadoFromUsuario(materia.id);
                if (FinalAprobado)
                {
                    string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
                    using (var db = new MySqlConnection(connection))
                    {
                        var sql = "SELECT nombre, nota_promedio FROM catedra as ca INNER JOIN materia ON ca.materia_id = materia.id INNER JOIN usuario_materia_cuatrimestre ON ca.id = usuario_materia_cuatrimestre.idcatedra WHERE usuario_materia_cuatrimestre.idusuario = @idusuario AND usuario_materia_cuatrimestre.nota_promedio >= 4";
                        var materiasAprobada = db.QueryMultiple(sql, new { idusuario });

                        List<Analitico> listaMateriasAprobadasUsuario = materiasAprobada.Read<Analitico>().AsList();

                        foreach (var list in listaMateriasAprobadasUsuario)
                        {
                            list.final_cursada = GetNotaFinalAlumno(materia.id);
                            list.nota_promedio = GetNotaPromedioAlumno(materia.id);
                            list.nota_final = (list.final_cursada + list.nota_promedio) / 2;

                            listaMateriasAprobadas.Add(list);
                        }
                        continue;
                    }
                }
            }
            return listaMateriasAprobadas;
        }
        catch (Exception)
        {
            return listaMateriasAprobadas;
            throw;
        }
    }

    //-------------------------------------
    //-------d. Modificar contraseña-------
    //-------------------------------------
    public string UpdateModificacionDatos(int idusuario, string password)
    {
        try
        {
            string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
            using (var db = new MySqlConnection(connection))
            {
                var sql = "UPDATE usuarios SET password = @password WHERE id = @id";
                var result = db.Execute(sql, new { password, idusuario });

                if (result == 1)
                {
                    return "Los cambios se ejecuaron correctamente";

                }
                return "Los cambios no se ejecuaron correctamente";
            }
        }
        catch (Exception)
        {
            return "Los cambios no se ejecuaron correctamente";
            throw;
        }
    }

    //-------------------------------------
    //----Funcion Privada de Busqueda------
    //-------------------------------------
    private float GetNotaFinalAlumno(int idusuario_materia_cuatrimestre)
    {
        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "SELECT nota FROM nota_parciales WHERE idusuario_materia_cuatrimestre = @idusuario_materia_cuatrimestre AND nro_parcial = 5";
            var result = db.QueryFirst<float>(sql, new { idusuario_materia_cuatrimestre });

            return result;
        }
    }

    //-------------------------------------
    //----Funcion Privada de Busqueda------
    //-------------------------------------
    private float GetNotaPromedioAlumno(int id)
    {
        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "SELECT nota_promedio FROM usuario_materia_cuatrimestre WHERE id = @id";
            var result = db.QueryFirst<float>(sql, new { id });

            return result;
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
            var result = 0;
            var nro_parcial = 5;
            string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
            using (var db = new MySqlConnection(connection))
            {
                var sql = "SELECT nota FROM nota_parciales WHERE idusuario_materia_cuatrimestre = @idusuario_materia_cuatrimestre AND nro_parcial = @nro_parcial";
                result = db.QueryFirst<int>(sql, new { idusuario_materia_cuatrimestre, nro_parcial });

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
    public IEnumerable<Materias> GetIdsUsuariomateriaCuatrimestre(int idusuario)
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
        public float final_cursada { get; set; }
        public float promedio_cursada { get; set; }
        public float promedio_general { get; set; }
    }

    public class Analitico
    {
        public string nombre { get; set; }
        public float nota_promedio { get; set; }
        public float final_cursada { get; set; }
        public float nota_final { get; set; }
        public float promedio_general { get; set; }

    }
}
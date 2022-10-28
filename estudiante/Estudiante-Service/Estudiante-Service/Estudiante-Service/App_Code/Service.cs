using Dapper;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;

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
                            continue;
                        }
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
    public string UpdateModificacionDatos(int id, string password, string email, string direccion)
    {
        try
        {
            var datos = GetUsuarioDatos(id);
            if (password == "" || password == null)
            {
                password = datos.password;
            }
            if (email == "" || email == null)
            {
                email = datos.email;
            }
            if (direccion == "" || direccion == null)
            {
                direccion = datos.direccion;
            }

            string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
            using (var db = new MySqlConnection(connection))
            {
                var sql = "UPDATE usuarios SET password = @password, email = @email, direccion = @direccion WHERE id = @id";
                var result = db.Execute(sql, new { password, email, direccion, id });

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

    //-------------------------------------------------
    //-------Inscripcion Estudiante a la catedra-------
    //-------------------------------------------------
    public string InscripcionEstudianteCatedra(int idusuario, int idcatedra)
    {
        try
        {
            var usuarioExiste = GetUsuario(idusuario);
            var catedraExiste = GetCatedra(idcatedra);
            var inscripcionAlumno = GetInscripcionAlumnoCatedra(idusuario, idcatedra);
            if (usuarioExiste && catedraExiste)
            {
                if (!inscripcionAlumno)
                {
                    string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
                    using (var db = new MySqlConnection(connection))
                    {
                        var sql = "INSERT INTO usuario_materia_cuatrimestre (idusuario, idcatedra) VALUES (@idusuario, @idcatedra)";
                        var result = db.Execute(sql, new { idusuario, idcatedra });

                        if (result == 1)
                        {
                            return "El alumno se inscripto correctamente";
                        }
                        return "No se pudo inscribir al alumno";
                    }
                }
            }
            return "No se puede inscribir";
        }
        catch (Exception)
        {
            return "No se pudo inscribir al alumno";
            throw;
        }
    }

    //-------------------------------------
    //----Funcion Privada de Busqueda------
    //-------------------------------------
    private bool GetInscripcionAlumnoCatedra(int idusuario, int idcatedra)
    {
        try
        {
            string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
            using (var db = new MySqlConnection(connection))
            {
                var sql = "SELECT id FROM usuario_materia_cuatrimestre WHERE idusuario = @idusuario AND idcatedra = @idcatedra";
                var result = db.QuerySingle<int>(sql, new { idusuario, idcatedra });

                if (result == 1)
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

    //-------------------------------------
    //----Funcion Privada de Busqueda------
    //-------------------------------------
    private DatosEstudiante GetUsuarioDatos(int id)
    {
        var vacio = new DatosEstudiante();
        try
        {
            string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
            using (var db = new MySqlConnection(connection))
            {
                var sql = "SELECT * FROM usuarios WHERE id = @id";
                var result = db.QuerySingle<DatosEstudiante>(sql, new { id });

                return result;
            }
        }
        catch (Exception)
        {
            return vacio;
            throw;
        }
    }

    //-------------------------------------
    //----Funcion Privada de Busqueda------
    //-------------------------------------
    private bool GetCatedra(int id)
    {
        try
        {
            string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
            using (var db = new MySqlConnection(connection))
            {
                var sql = "SELECT COUNT(*) id FROM catedra WHERE id = @id";
                var result = db.QuerySingle<int>(sql, new { id });

                if (result == 1)
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

    //-------------------------------------
    //----Funcion Privada de Busqueda------
    //-------------------------------------
    private bool GetUsuario(int id)
    {
        try
        {
            string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
            using (var db = new MySqlConnection(connection))
            {
                var sql = "SELECT COUNT(*) id FROM usuarios WHERE id = @id";
                var result = db.QuerySingle<int>(sql, new { id });

                if (result == 1)
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
        try
        {
            string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
            using (var db = new MySqlConnection(connection))
            {
                var sql = "SELECT nota_promedio FROM usuario_materia_cuatrimestre WHERE id = @id";
                var result = db.QueryFirst<float>(sql, new { id });

                return result;
            }
        }
        catch (Exception)
        {
            return 0;
            throw;
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

    public class DatosEstudiante
    {
        public string password { get; set; }
        public string direccion { get; set; }
        public string email { get; set; }

    }
}
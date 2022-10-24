using System;
using System.Collections.Generic;
using Dapper;
using MySql.Data.MySqlClient;
using System.Reflection;
using System.Web.UI.WebControls.WebParts;
using System.Threading.Tasks;
using System.Activities.Expressions;

public class Service : IService
{
    //---------------------------------------
    //---a. Consulta de materias asignadas---
    //---------------------------------------
    public IEnumerable<MateriaDocente> GetMateriasDocente(int idDocente)
    {
        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "SELECT * FROM catedra as ca INNER JOIN materia ON ca.materia_id = materia.id WHERE ca.profesor_id = @idDocente";
            var result = db.Query<MateriaDocente>(sql, new { idDocente });

            return result;
        }
    }

    //-------------------------------------------------------
    //---b. Consulta de listado de alumnos de cada materia---
    //-------------------------------------------------------
    public IEnumerable<AlumnoMateria> GetAlumnosMateria(int idMateria)
    {
        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "SELECT * from catedra as ca INNER JOIN usuarios ON ca.profesor_id = usuarios.id INNER JOIN materia ON ca.materia_id = materia.id WHERE ca.materia_id = @idMateria";
            var result = db.Query<AlumnoMateria>(sql, new { idMateria });

            return result;
        }
    }

    //---------------------------------------
    //-----c. Carga de notas de Cursada------
    //---------------------------------------
    public string InsertNotasCursada(int idCatedra, List<Alumnos> alumnos)
    {
        foreach (var alumno in alumnos)
        {
            {
                var idAlumno = alumno.idAlumno;
                var nota = alumno.notaParcial;
                var idusuario_materia_cuatrimestre = GetUsuarioMateriaCuatrimestreID(idAlumno, idCatedra);
                var nro_parcial = GetNroParciales(idusuario_materia_cuatrimestre) + 1;
                var fecha_carga = DateTime.Now;

                string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
                using (var db = new MySqlConnection(connection))
                {
                    var sql = "INSERT INTO nota_parciales (nota, fecha_carga, idusuario_materia_cuatrimestre, nro_parcial) VALUES (@nota, @fecha_carga, @idusuario_materia_cuatrimestre, @nro_parcial)";
                    var result = db.Execute(sql, new { nota, fecha_carga, idusuario_materia_cuatrimestre, nro_parcial });
                }
                UpdateUsuarioMateriaCuatrimestrePromedio(idAlumno, idCatedra, idusuario_materia_cuatrimestre);
            }
        }
    return "Se Insertaron las notas de la cursada";
    }

    //---------------------------------------
    //-----d. Carga de notas de finales------
    //---------------------------------------
    public string InsertNotasFinal(int idCatedra, List<Alumnos> alumnos)
    {
        foreach (var alumno in alumnos)
        {
            {
                var idAlumno = alumno.idAlumno;
                var nota = alumno.notaParcial;
                var idUsuarioMateriaCuatrimestre = GetUsuarioMateriaCuatrimestreID(idAlumno, idCatedra);
                var fecha_carga = DateTime.Now;

                string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
                using (var db = new MySqlConnection(connection))
                {
                    var sql = "INSERT INTO nota_parciales (nota, fecha_carga, idusuario_materia_cuatrimestre, nro_parcial) VALUES (@nota, @fecha_carga, @idusuario_materia_cuatrimestre, @nro_parcial)";
                    var result = db.Execute(sql, new { nota, fecha_carga, idUsuarioMateriaCuatrimestre, nro_parcial = 5 });
                }
            }
        }
        return "Se Insertaron las notas de los finales";
    }

    //---------------------------------------
    //-----Funcion privada de busqueda-------
    //---------------------------------------

    public IEnumerable<MateriaDocente> GetMaterias()
    {
        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "SELECT * from materia";
            var result = db.Query<MateriaDocente>(sql);

            return result;
        }
    }

    //----------------------------------------------------
    //-----Funcion privada de Calculo de parciales--------
    //----------------------------------------------------
    public int GetNroParciales(int idusuario_materia_cuatrimestre)
    {
        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "SELECT COUNT(*) nota FROM nota_parciales WHERE idusuario_materia_cuatrimestre = @idusuario_materia_cuatrimestre";
            var result = db.QueryFirst<int>(sql, new { idusuario_materia_cuatrimestre });

            return result;
        }
    }

    //---------------------------------------
    //------Funcion privada de busqueda------
    //---------------------------------------
    private int GetUsuarioMateriaCuatrimestreID(int idUsuario, int idCatedra)
    {
        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "SELECT id FROM usuario_materia_cuatrimestre WHERE idusuario = @idusuario AND idcatedra = @idcatedra";
            var result = db.QuerySingle<UsuarioMateriaCuatrimestre>(sql, new { idUsuario, idCatedra });

            return result.id;
        }
    }

    //---------------------------------------
    //---Funcion privada de Actualizacion----
    //---------------------------------------
    private bool UpdateUsuarioMateriaCuatrimestrePromedio(int idusuario, int idcatedra, int idusuario_materia_cuatrimestre)
    {
        var sumaNotas = GetSumaNotasUsuario(idusuario_materia_cuatrimestre);
        var cantidadNotas = GetNroParciales(idusuario_materia_cuatrimestre);
        var nota_promedio = sumaNotas / cantidadNotas;

        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "UPDATE usuario_materia_cuatrimestre SET nota_promedio = @nota_promedio WHERE idusuario = @idusuario AND idcatedra = @idcatedra";
            var result = db.Execute(sql, new { nota_promedio, idusuario, idcatedra });

            if (result <= 1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    //--------------------------------------------
    //---Funcion privada Suma de notas usuario----
    //--------------------------------------------
    private float GetSumaNotasUsuario(int idusuario_materia_cuatrimestre)
    {
        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
        using (var db = new MySqlConnection(connection))
        {
            var sql = "SELECT SUM(nota) FROM nota_parciales WHERE idusuario_materia_cuatrimestre = @idusuario_materia_cuatrimestre";
            var result = db.QueryFirst<int>(sql, new { idusuario_materia_cuatrimestre });
            
            return result;
        }
    }

    //public string InsertNotasCursadas(int idUsuario, int idCatedra, int[] notas)
    //{
    //    float totalNotas = 0;
    //    float nota = 0;
    //    var usuarioMateriaCuatrimestreID = GetUsuarioMateriaCuatrimestreID(idUsuario, idCatedra);

    //    for (int i = 0; i < notas.Length; i++)
    //    {
    //        totalNotas = +notas[i];
    //        nota = notas[i];
    //        string connection = @"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root";
    //        using (var db = new MySqlConnection(connection))
    //        {
    //            var sql = "INSERT INTO nota_parciales (nota, fecha_carga, idusuario_materia_cuatrimestre) VALUES (@nota,@fecha_carga,@idusuario_materia_cuatrimestre)";
    //            var result = db.Query(sql, new { nota, fecha_carga = new DateTime(), usuarioMateriaCuatrimestreID });
    //        }
    //    }
    //    float promedio = totalNotas / notas.Length;
    //    UpdateUsuarioMateriaCuatrimestrePromedio(idUsuario, idCatedra, promedio);

    //    return "Se guardaron las notas";
    //}

    public class UsuarioMateriaCuatrimestre
    {
        public int id { get; set; }
    }

    public class MateriaDocente
    {
        public int id { get; set; }
        public string nombre { get; set; }
        public int anio { get; set; }
        public int id_carrera { get; set; }   
    }

    public class AlumnoMateria
    {
        public int id { get; set; }
        public string es_final { get; set; }
        public int turno_id { get; set; }
        public int profesor_id { get; set; }
        public int cuatrimestr_id { get; set; }
        public int materia_id { get; set; }
        public int fecha_final { get; set; }
        public int dia_semana_id { get; set; }
        public string apellido { get; set; }
        public int dni { get; set; }
        public string nombre { get; set; }
        public int tipo_usuario { get; set; }
    }
}

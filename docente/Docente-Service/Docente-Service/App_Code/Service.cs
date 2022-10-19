using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using Dapper;
using MySql.Data.MySqlClient;
using MySqlX.XDevAPI.Common;

// NOTA: puede usar el comando "Rename" del menú "Refactorizar" para cambiar el nombre de clase "Service1" en el código, en svc y en el archivo de configuración.
public class Service : IService
{
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

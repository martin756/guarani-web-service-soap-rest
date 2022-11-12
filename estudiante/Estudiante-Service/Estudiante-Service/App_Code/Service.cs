using Dapper;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.ServiceModel;
using System.Text;
using System.Net;
using Newtonsoft.Json;

// NOTA: puede usar el comando "Rename" del menú "Refactorizar" para cambiar el nombre de clase "Service1" en el código, en svc y en el archivo de configuración.
public class Service : IService
{
    readonly MySqlConnection db = new MySqlConnection(@"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root");
    //-------------------------------------------------------
    //-----a. Consulta de Materias/Examenes Inscripcion------
    //-------------------------------------------------------
    public List<Catedra> TraerInscripcionesDisponibles(int idUsuario)
    {
        Fechas_Inscripciones fechas = db.QueryFirst<Fechas_Inscripciones>("SELECT * FROM fechas_inscripciones");
        string mensaje = InscripcionesDisponibles(fechas);
        if( mensaje != "OK") 
        {
            throw HttpException(HttpStatusCode.Forbidden, mensaje);
        }
        string sql = "SELECT ca.id as idcatedra, m.nombre as materia, concat_ws(' ', u.nombre, u.apellido) as profesor, " +
            "t.descripcion as turno, ca.es_final, ca.fecha_final as fecha, ds.descripcion as dia, " +
            "concat_ws('° ', cu.periodo, cu.anio) as cuatrimestre from catedra as ca " +
            "INNER JOIN turno t ON t.id = ca.turno_id " +
            "INNER JOIN usuarios u ON ca.profesor_id = u.id " +
            "INNER JOIN cuatrimestre cu ON cu.id = ca.cuatrimestre_id " +
            "INNER JOIN materia m ON ca.materia_id = m.id " +
            "INNER JOIN dia_semana ds ON ds.id = ca.dia_semana_id " +
            "WHERE ca.es_final = @esFinal";
        IEnumerable<Catedra> catedras = db.Query<Catedra>(sql, new { esFinal = fechas.es_final });

        Analitico materiasVigentes = SelectInformeAnalitico(idUsuario);
        List<Catedra> catedrasFiltradas = new List<Catedra>();
        List<Materia> materiasAprobadas = new List<Materia>();
        foreach (Materia materia in materiasVigentes.listadoNotas)
        {
            if (materia.nota_definitiva >= 4 )
            {
                materiasAprobadas.Add(materia);
            }
        }
        foreach (Catedra catedra in catedras)
        {
            if (materiasAprobadas.Count > 0)
            {
                foreach (var materia in materiasAprobadas)
                {
                    if (catedra.materia != materia.nombre)
                    {
                        catedrasFiltradas.Add(catedra);
                    }
                }
            }
            else
            {
                catedrasFiltradas.Add(catedra);
            }
            catedra.inscripto = EstaInscripto(idUsuario, catedra.idcatedra) ? "Sí" : "No";
        }
        return catedrasFiltradas;
    }

    //Valido si el usuario está inscripto a la materia
    private bool EstaInscripto(int idUsuario, int idCatedra)
    {
        string sql = "SELECT umc.id FROM usuario_materia_cuatrimestre umc " +
            "INNER JOIN catedra c ON c.id = umc.idcatedra " +
            "INNER JOIN materia m ON m.id = c.materia_id " +
            "WHERE idusuario = @idUsuario AND idcatedra = @idCatedra";
        return db.QueryFirstOrDefault<string>(sql, new { idUsuario, idCatedra }) != null;
    }

    //Valido si las inscripciones estan abiertas para anotarse
    private string InscripcionesDisponibles(Fechas_Inscripciones fi)
    {
        if (fi.inscripcion_desde < DateTime.Now && fi.inscripcion_hasta > DateTime.Now)
        {
            return "OK";
        }
        return fi.inscripcion_desde > DateTime.Now ? "Las inscripciones " + (fi.es_final ? "a finales " : "a cuatrimestres ") + "estarán disponibles a partir del " + fi.inscripcion_desde.AddHours(3).ToLocalTime() :
            "El periodo de inscripciones " + (fi.es_final ? "a finales " : "a cuatrimestres ") + "ha finalizado";
    }

    private FaultException HttpException(HttpStatusCode codigoError, string mensaje)
    {
        ErrorDetails details = new ErrorDetails
        {
            Message = mensaje,
            ErrorCode = codigoError.ToString()
        };
        throw new FaultException(JsonConvert.SerializeObject(details), new FaultCode(((int)codigoError).ToString()));
    }
    //-------------------------------------
    //-------dar de alta inscripcion-------
    //-------------------------------------
    public string InscripcionAlumno(int idusuario, int idcatedra)
    {
        //Compruebo existencia de usuario
        bool existente = db.QueryFirstOrDefault<bool>("SELECT COUNT(*) FROM usuarios WHERE id = @idusuario", new { idusuario });
        if (!existente)
        {
            throw HttpException(HttpStatusCode.NotFound, "El id usuario especificado no se encuentra existente");
        }
        //Compruebo existencia de catedra
        existente = db.QueryFirstOrDefault<bool>("SELECT COUNT(*) FROM catedra WHERE id = @idcatedra", new { idcatedra });
        if (!existente)
        {
            throw HttpException(HttpStatusCode.NotFound, "El id catedra especificado no se encuentra existente");
        }
        if (SePuedeInscribir(idusuario, idcatedra))
        {
            string sql = "INSERT INTO usuario_materia_cuatrimestre VALUES (null,null,@idusuario,@idcatedra,0,0)";
            db.Execute(sql, new { idusuario, idcatedra});
            return "Ok, Inscripción exitosa";
        }
        else
        {
            throw HttpException(HttpStatusCode.Forbidden, "El horario de inscripcion se superpone con otra catedra");
        }
    }

    //Metodo para validar la superposicion de horarios
    private bool SePuedeInscribir(int idusuario, int idcatedra)
    {
        string sql = "SELECT ca.id as idcatedra, m.nombre as materia, t.descripcion as turno, ds.descripcion as dia, ca.es_final, " +
            "concat_ws('° ', cu.periodo, cu.anio) as cuatrimestre from catedra as ca " +
            "INNER JOIN turno t ON t.id = ca.turno_id " +
            "INNER JOIN usuarios u ON ca.profesor_id = u.id " +
            "INNER JOIN cuatrimestre cu ON cu.id = ca.cuatrimestre_id " +
            "INNER JOIN materia m ON ca.materia_id = m.id " +
            "INNER JOIN dia_semana ds ON ds.id = ca.dia_semana_id WHERE ca.id = @idcatedra";
        Catedra catedra = db.QuerySingleOrDefault<Catedra>(sql, new { idcatedra });

        sql = "SELECT c.id as idcatedra, m.nombre as materia, t.descripcion as turno, ds.descripcion as dia, c.es_final, " +
            "concat_ws('° ', cu.periodo, cu.anio) as cuatrimestre FROM usuario_materia_cuatrimestre umc " +
            "INNER JOIN catedra c ON umc.idcatedra = c.id " +
            "INNER JOIN turno t ON t.id = c.turno_id " +
            "INNER JOIN cuatrimestre cu ON cu.id = c.cuatrimestre_id " +
            "INNER JOIN dia_semana ds ON ds.id = c.dia_semana_id " +
            "INNER JOIN materia m ON c.materia_id = m.id WHERE umc.idusuario = @idusuario AND c.es_final = @es_final";
        IEnumerable<Catedra> inscripciones = db.Query<Catedra>(sql, new { idusuario, catedra.es_final });
        foreach (var inscripcion in inscripciones)
        {
            if (inscripcion.cuatrimestre == catedra.cuatrimestre && 
                inscripcion.dia == catedra.dia && 
                inscripcion.turno == catedra.turno)
            {
                return false;
            }
        }
        return true;
    }

    //-------------------------------------
    //-----b. dar de baja inscripcion------
    //-------------------------------------
    public string DeleteInscripcionAlumno(int idusuario, int idcatedra)
    {
        bool existente = db.QueryFirstOrDefault<bool>("SELECT COUNT(*) FROM usuarios WHERE id = @idusuario", new { idusuario });
        if (!existente)
        {
            throw HttpException(HttpStatusCode.NotFound, "El id usuario especificado no se encuentra existente");
        }
        var sql = "DELETE FROM usuario_materia_cuatrimestre WHERE idusuario = @idusuario AND idcatedra = @idcatedra";
        var result = db.Execute(sql, new { idusuario, idcatedra });

        if (result == 0)
        {
            throw HttpException(HttpStatusCode.NotFound, "El alumno no se encuentra inscripto a la catedra con el id especificado");
        }
        return "Se eliminó el usuario de la catedra";
    }

    //-----------------------------------------
    //-----c. Consultar informe analitico------
    //-----------------------------------------
    public Analitico SelectInformeAnalitico(int idusuario)
    {
        var inscripciones = GetIdsUsuariomateriaCuatrimestre(idusuario);
        var listaNotasMaterias = new List<Materia>();
        float total = 0, cantidad = 0;

        foreach (var inscripcion in inscripciones)
        {
            Materia nota = new Materia
            {
                nombre = inscripcion.materia,
                examen_final = GetNotaFinalAlumno(inscripcion.idEstudiante, inscripcion.idmateria), //Nota de examen final
                nota_cursada = GetNotaPromedioAlumno(inscripcion.idInscripcion) //Nota de cursada
            };
            nota.nota_definitiva = nota.examen_final == 0 ? 0 : nota.nota_cursada == 0 ? nota.examen_final : (nota.examen_final + nota.nota_cursada) / 2; //Nota definitiva

            if (!ComprobarMateriaRepetida(inscripcion.materia, listaNotasMaterias))
            {
                listaNotasMaterias.Add(nota);
                if (nota.nota_definitiva != 0)
                {
                    total += nota.nota_definitiva;
                    cantidad++;
                }
            }
        }

        return new Analitico { listadoNotas = listaNotasMaterias, promedio_general = total / (cantidad == 0 ? 1: cantidad) };
    }

    //-------------------------------------
    //----d. Modificar datos de usuario----
    //-------------------------------------
    public string UpdateModificacionDatos(Estudiante datos)
    {
        Estudiante estudiante = db.QueryFirstOrDefault<Estudiante>("SELECT password, email, direccion FROM usuarios WHERE id = @id", new { datos.id });
        if (estudiante == null)
        {
            throw HttpException(HttpStatusCode.NotFound, "El id de usuario especificado no se encuentra existente");
        }
        var sql = "UPDATE usuarios SET ";
        sql += "password = " + (datos.password != null ? "@password, " : (("'" + estudiante.password + "', ") == "'', " ? "null" : ("'" + estudiante.password + "', ")));
        sql += "direccion = " + (datos.direccion != null ? "@direccion, " : (("'" + estudiante.direccion + "', ") == "'', " ? "null" : ("'" + estudiante.direccion + "', ")));
        sql += "email = " + (datos.email != null ? "@email " : (("'" + estudiante.email + "' ") == "'' " ? "null" : ("'" + estudiante.email + "' ")));

        sql += " WHERE id = @id";
        var result = db.Execute(sql, new Estudiante{ id = datos.id, password = (datos.password != null ? Sha1(datos.password) : estudiante.password), direccion = datos.direccion, email = datos.email });

        return "Los cambios se ejecutaron correctamente";
    }


    //-------------------------------------
    //----RECUPERATORIO. Modificar catedra----
    //-------------------------------------
    public string UpdateCambioCatedra(int idusuario, int idcatedra, int id)
    {
        Estudiante estudiante = db.QueryFirstOrDefault<Estudiante>("SELECT nota_promedio, idusuario, idcatedra, activo FROM usuario_materia_cuatrimestre WHERE id = @id", new { id });
        if (estudiante == null)
        {
            throw HttpException(HttpStatusCode.NotFound, "El id especificado no se encuentra existente");
        }

        string sql = "UPDATE usuario_materia_cuatrimestre SET idcatedra = @idcatedra , activo = false WHERE id = @id";
        db.Execute(sql, new { idcatedra, id });
        return "Los cambios se ejecutaron correctamente";
    }
    //Para codificar la password
    private static string Sha1(string value)
    {
        var shaSHA1 = System.Security.Cryptography.SHA1.Create();
        var inputEncodingBytes = Encoding.ASCII.GetBytes(value);
        var hashString = shaSHA1.ComputeHash(inputEncodingBytes);

        var stringBuilder = new StringBuilder();
        for (var x = 0; x < hashString.Length; x++)
        {
            stringBuilder.Append(hashString[x].ToString("x2"));
        }
        return stringBuilder.ToString();
    }

    //Funcion para comprobar si una materia ya fue agregada a la lista de materias del analitico
    private bool ComprobarMateriaRepetida(string nombreMateria, List<Materia> listadoNotasMaterias)
    {
        foreach (var item in listadoNotasMaterias)
        {
            if (item.nombre == nombreMateria)
            {
                return true;
            }
        }
        return false;
    }

    //-----------------------------------------------
    //---Funciones Privadas de Obtencion de Notas----
    //-----------------------------------------------
    private float GetNotaFinalAlumno(int idUsuario, int idMateria)
    {
        var sql = "SELECT np.nota as examen_final, c.materia_id FROM nota_parciales np " +
            "INNER JOIN usuario_materia_cuatrimestre umc ON umc.id = np.idusuario_materia_cuatrimestre " +
            "INNER JOIN catedra c ON c.id = umc.idcatedra " +
            "WHERE umc.idusuario = @idUsuario AND c.materia_id = @idMateria AND c.es_final = true";
        Materia result = db.QueryFirstOrDefault<Materia>(sql, new { idUsuario, idMateria });
        return result != null ? result.examen_final : 0;
    }

    public float GetNotaPromedioAlumno(int idusuario_materia_cuatrimestre)
    {
        string sql = "SELECT nota FROM nota_parciales WHERE idusuario_materia_cuatrimestre = @idusuario_materia_cuatrimestre";
        var notasParciales = db.QueryMultiple(sql, new { idusuario_materia_cuatrimestre });
        float total = 0, cantidad = 0;
        foreach (var nota in notasParciales.Read<float>())
        {
            if(nota != 0)
            {
                total += nota;
                cantidad++;
            }
        }
        return total / (cantidad == 0 ? 1 : cantidad);
    }

    //------------------------------------------------------------------
    //----Funcion Privada para ver las catedras de un usuario-----------
    //------------------------------------------------------------------
    private IEnumerable<Catedra> GetIdsUsuariomateriaCuatrimestre(int idusuario)
    {
        var sql = "SELECT umc.id as idInscripcion, m.nombre as materia, m.id as idmateria, umc.idusuario as idestudiante FROM usuario_materia_cuatrimestre umc " +
            "INNER JOIN catedra c ON c.id = umc.idcatedra "+
            "INNER JOIN materia m ON c.materia_id = m.id WHERE umc.idusuario = @idusuario";
        return db.Query<Catedra>(sql, new { idusuario });
    }

    public class ErrorDetails
    {
        public string ErrorCode { get; set; }
        public string Message { get; set; }
    }
}
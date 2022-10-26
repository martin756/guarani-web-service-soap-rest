using System;
using System.Collections.Generic;
using Dapper;
using MySql.Data.MySqlClient;

public class Service : IService
{
    readonly MySqlConnection db = new MySqlConnection(@"Server=localhost; Database=db_gestionacademica; Uid=root; Pwd=root");
    //---------------------------------------
    //---a. Consulta de materias asignadas---
    //---------------------------------------
    public IEnumerable<MateriaDocente> GetMateriasDocente(int idDocente)
    {
        var sql = "SELECT m.id, m.nombre, m.anio, c.nombre as carrera, t.descripcion as turno, ds.descripcion as dia, IF(ca.es_final = 0, 'Cuatrimestre', 'Final') as instancia FROM catedra as ca " +
                    "INNER JOIN materia m ON ca.materia_id = m.id " +
                    "INNER JOIN carrera c ON m.id_carrera = c.id " +
                    "INNER JOIN turno t ON ca.turno_id = t.id " +
                    "INNER JOIN dia_semana ds ON ca.dia_semana_id = ds.id WHERE ca.profesor_id = @idDocente";

        var result = db.Query<MateriaDocente>(sql, new { idDocente });

        return result;
    }

    //-------------------------------------------------------
    //---b. Consulta de listado de alumnos de cada materia---
    //-------------------------------------------------------
    public IEnumerable<AlumnoMateria> GetAlumnosMateria(int idMateria, int idDocente, bool esFinal)
    {
        var sql = "SELECT u.id, u.nombre, u.apellido, u.dni, umc.idcatedra, umc.id as id_inscripcion FROM db_gestionacademica.usuario_materia_cuatrimestre umc " +
                    "INNER JOIN catedra c ON c.id = umc.idcatedra " +
                    "INNER JOIN usuarios u ON umc.idusuario = u.id " +
                    "INNER JOIN materia m ON c.materia_id = m.id WHERE c.materia_id = @idMateria AND c.profesor_id = @idDocente AND c.es_final = @esFinal";
        var result = db.Query<AlumnoMateria>(sql, new { idMateria, idDocente, esFinal });
        foreach (var item in result)
        {
            item.notas = GetNotasParciales(item.id_inscripcion);
            if(item.notas.ToList().Count > 0)
            {
                int cantidad = 0;
                float total = 0, nota_final = 0;
                foreach (var notas in item.notas)
                {
                    if(notas.nro_parcial != 0)
                    {
                        cantidad++;
                        total += notas.nota;
                    }
                    else
                    {
                        nota_final = notas.nota;
                    }
                    DateTime fechaCargaConHorasEdicion = TraerFechaCargaConHorasEdicion(notas.idusuario_materia_cuatrimestre, notas.nro_parcial);
                    notas.Tiempo_limite = fechaCargaConHorasEdicion < DateTime.Now ? new TimeSpan(0,0,0) : fechaCargaConHorasEdicion - DateTime.Now ;
                }
                item.Nota_Cursada = total / (cantidad == 0 ? 1 : cantidad);
                item.Nota_Definitiva = nota_final != 0 ? (nota_final + item.Nota_Cursada) / 2 : 0;
                if (esFinal) { item.Nota_Definitiva = nota_final; }
                //else { item.Nota_Cursada = total / cantidad; }
            }
            
        }
        return result;
    }

    //------------------------------------------------------
    //-----c. & d. Carga de notas de Cursada y Finales------
    //------------------------------------------------------
    public string UpsertNotasCursada(int idCatedra, int nroParcial, IEnumerable<AlumnoMateriaNotaRequest> listadoAlumnos)
    {
        string result = "Se guardaron las notas";
        var sql = "INSERT INTO nota_parciales (nota, nro_parcial, fecha_carga, idusuario_materia_cuatrimestre) " +
                    "VALUES (@nota, @nro_parcial, @fecha_carga, @idusuario_materia_cuatrimestre)";
        foreach (var alumno in listadoAlumnos)
        {
            var usuarioMateriaCuatrimestreID = GetUsuarioMateriaCuatrimestreID(alumno.idEstudiante, idCatedra);
            bool NotaCargada = ComprobarNotaCargada(nroParcial, usuarioMateriaCuatrimestreID.id);
            sql = NotaCargada ?
                "UPDATE nota_parciales SET nota = @nota " +
                "WHERE idusuario_materia_cuatrimestre = @idusuario_materia_cuatrimestre AND nro_parcial = @nro_parcial" : sql;
            if (TraerFechaCargaConHorasEdicion(usuarioMateriaCuatrimestreID.id, nroParcial) > DateTime.Now || !NotaCargada) {
                db.Execute(sql,
                    new NotasParciales
                    {
                        nota = alumno.Nota,
                        nro_parcial = nroParcial,
                        fecha_carga = DateTime.Now,
                        idusuario_materia_cuatrimestre = usuarioMateriaCuatrimestreID.id
                    }
                ); 
            }
            else
            {
                result = "Todas o algunas notas no se pudieron modificar ya que su tiempo de edicion ha sido superado";
            }
        }

        return result;
    }

    public IEnumerable<MateriaDocente> GetMaterias()
    {
        var sql = "SELECT * from materia";
        var result = db.Query<MateriaDocente>(sql);

        return result;
    }


    //---------------------------------------
    //----Funciones privadas de busqueda-----
    //---------------------------------------

    //Retorna true si encuentra nota cargada
    private bool ComprobarNotaCargada(int nroParcial, int idusuario_materia_cuatrimestre)
    {
        var sql = "SELECT * FROM nota_parciales WHERE idusuario_materia_cuatrimestre = @idusuario_materia_cuatrimestre AND nro_parcial = @nroParcial";
        var result = db.QuerySingleOrDefault<NotasParciales>(sql, new { idusuario_materia_cuatrimestre, nroParcial });

        return result != null;
    }

    private UsuarioMateriaCuatrimestre GetUsuarioMateriaCuatrimestreID(int idUsuario, int idCatedra)
    {
        var sql = "SELECT id FROM usuario_materia_cuatrimestre WHERE idusuario = @idUsuario AND idcatedra = @idCatedra";
        var result = db.QueryFirst<UsuarioMateriaCuatrimestre>(sql, new { idUsuario, idCatedra });

        return result;
    }

    private IEnumerable<NotasParciales> GetNotasParciales(int idUsuarioMateriaCuatrimestre)
    {
        var sql = "SELECT umc.id as idusuario_materia_cuatrimestre, np.nota, np.nro_parcial, np.fecha_carga FROM db_gestionacademica.nota_parciales np " +
                    "INNER JOIN usuario_materia_cuatrimestre umc ON umc.id = np.idusuario_materia_cuatrimestre " +
                    "WHERE np.idusuario_materia_cuatrimestre = @idUsuarioMateriaCuatrimestre";
        var result = db.Query<NotasParciales>(sql, new { idUsuarioMateriaCuatrimestre });

        return result;
    }

    private DateTime TraerFechaCargaConHorasEdicion(int idUsuarioMateriaCuatrimestre, int nroParcial)
    {
        var sql = "SELECT horas_edicion_notas FROM fechas_inscripciones";
        int horas = db.QuerySingleOrDefault<int>(sql);
        sql = "SELECT fecha_carga FROM nota_parciales WHERE nro_parcial = @nroParcial AND idusuario_materia_cuatrimestre = @idUsuarioMateriaCuatrimestre";
        DateTime fecha_carga = db.QuerySingleOrDefault<DateTime>(sql, new { nroParcial, idUsuarioMateriaCuatrimestre });
        return fecha_carga.AddHours(horas);
    }

    private class UsuarioMateriaCuatrimestre
    {
        public int id { get; set; }
    }
}

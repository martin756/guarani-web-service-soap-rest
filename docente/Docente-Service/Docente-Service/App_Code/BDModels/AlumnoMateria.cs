using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Summary description for AlumnoMateria
/// </summary>
public class AlumnoMateria
{
    public int id { get; set; }
    //public string es_final { get; set; }
    //public int turno_id { get; set; }
    //public int profesor_id { get; set; }
    //public int cuatrimestr_id { get; set; }
    //public int materia_id { get; set; }
    //public int fecha_final { get; set; }
    //public int dia_semana_id { get; set; }
    public string apellido { get; set; }
    public int dni { get; set; }
    public string nombre { get; set; }
    //public int tipo_usuario { get; set; }
    public int idcatedra { get; set; }
    public int id_inscripcion { get; set; }
    public IEnumerable<NotasParciales> notas { get; set; }
    public float Nota_Cursada { get; set; }
    public float Nota_Definitiva { get; set; }
}

public class AlumnoMateriaNotaRequest
{
    public int idEstudiante { get; set; }
    public float Nota { get; set; }
}
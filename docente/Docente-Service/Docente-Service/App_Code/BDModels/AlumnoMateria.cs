using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

public class AlumnoMateria
{
    public int id { get; set; }
    public string apellido { get; set; }
    public int dni { get; set; }
    public string nombre { get; set; }
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
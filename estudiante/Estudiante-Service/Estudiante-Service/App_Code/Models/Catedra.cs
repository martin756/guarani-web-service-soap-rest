using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

public class Catedra
{
    public int idcatedra { get; set; }
    public string materia { get; set; }
    public string profesor { get; set; }
    public string turno { get; set; }
    public string dia { get; set; }
    public DateTime fecha { get; set; }
    public string cuatrimestre { get; set; }
    public string inscripto { get; set; }
    public bool es_final { get; set; }
    [IgnoreDataMember]
    public int idInscripcion { get; set; }
    [IgnoreDataMember]
    public int idmateria { get; set; }
    [IgnoreDataMember]
    public int idEstudiante { get; set; }
}
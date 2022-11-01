using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

public class Materia
{
    [IgnoreDataMember]
    public int materia_id { get; set; }
    public string nombre { get; set; }
    public float nota_cursada { get; set; }
    public float examen_final { get; set; }
    public float nota_definitiva { get; set; }
}
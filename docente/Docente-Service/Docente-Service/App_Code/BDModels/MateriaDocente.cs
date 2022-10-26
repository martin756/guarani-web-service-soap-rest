using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Summary description for MateriaDocente
/// </summary>
public class MateriaDocente
{
    public int id { get; set; }
    public string nombre { get; set; }
    public int anio { get; set; }
    //public int id_carrera { get; set; }   
    public string carrera { get; set; }
    public string turno { get; set; }
    public string dia { get; set; }
    public string instancia { get; set; }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Summary description for NotasParciales
/// </summary>
public class NotasParciales
{
    public float nota { get; set; }
    public int nro_parcial { get; set; }
    public DateTime fecha_carga { get; set; }
    public int idusuario_materia_cuatrimestre { get; set; }
    public TimeSpan Tiempo_limite { get; set; }
}
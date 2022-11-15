using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using EstudianteService;
using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;
using System.ServiceModel;

namespace EstudianteSOAPClient.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class EstudianteServiceController : ControllerBase
    {
        readonly ServiceClient soapClient = new();

        [HttpGet("ListadoInscripciones")]
        public async Task<IActionResult> TraerInscripciones([Required] int idUsuario)
        {
            try
            {
                TraerInscripcionesDisponiblesRequest request = new() { idUsuario = idUsuario };
                TraerInscripcionesDisponiblesResponse response = await soapClient.TraerInscripcionesDisponiblesAsync(request);
                return Ok(response.TraerInscripcionesDisponiblesResult.ToList());
            }
            catch(FaultException e)
            {
                return StatusCode(int.Parse(e.Code.Name), JsonConvert.DeserializeObject<ErrorDetails>(e.Message));
            }
        }

        [HttpPost("Inscripcion")]
        public async Task<IActionResult> InscribirAlumno([Required] int idUsuario, [Required] int idCatedra)
        {
            try
            {
                InscripcionAlumnoRequest request = new() { idusuario = idUsuario, idcatedra = idCatedra };
                InscripcionAlumnoResponse response = await soapClient.InscripcionAlumnoAsync(request);
                return Ok(response.InscripcionAlumnoResult);
            }
            catch(FaultException e)
            {
                return StatusCode(int.Parse(e.Code.Name), JsonConvert.DeserializeObject<ErrorDetails>(e.Message));
            }
        }
     
        [HttpDelete("Desinscripcion")]
        public async Task<IActionResult> DesinscribirAlumno([Required] int idUsuario, [Required] int idCatedra)
        {
            try
            {
                DeleteInscripcionAlumnoRequest request = new() { idusuario = idUsuario, idcatedra = idCatedra };
                DeleteInscripcionAlumnoResponse response = await soapClient.DeleteInscripcionAlumnoAsync(request);
                return Ok(response.DeleteInscripcionAlumnoResult);
            }
            catch(FaultException e)
            {
                return StatusCode(int.Parse(e.Code.Name), JsonConvert.DeserializeObject<ErrorDetails>(e.Message));
            }
        }

        [HttpGet("Analitico")]
        public async Task<string> TraerAnalitico([Required] int idUsuario)
        {
            SelectInformeAnaliticoRequest request = new() { idusuario = idUsuario };
            SelectInformeAnaliticoResponse response = await soapClient.SelectInformeAnaliticoAsync(request);
            return JsonConvert.SerializeObject(response.SelectInformeAnaliticoResult);
        }

        [HttpPut("ModificacionDatos")]
        public async Task<IActionResult> ModificarDatos([Required] Estudiante datos)
        {
            try
            {
                UpdateModificacionDatosRequest request = new() { datos = datos };
                UpdateModificacionDatosResponse response = await soapClient.UpdateModificacionDatosAsync(request);
                return Ok(response.UpdateModificacionDatosResult);
            }
            catch(FaultException e)
            {
                return StatusCode(int.Parse(e.Code.Name), JsonConvert.DeserializeObject<ErrorDetails>(e.Message));
            }
        }

        [HttpPost("CambioCatedra")]
        public async Task<IActionResult> CambioCatedra([Required] CambioCatedra cambioCatedra)
        {
            try
            {
                CambioCatedraRequest request = new() { datos =cambioCatedra };
                CambioCatedraResponse response = await soapClient.CambioCatedraAsync(request);
                return Ok(response.CambioCatedraResult);
            }
            catch (FaultException e)
            {
                return StatusCode(int.Parse(e.Code.Name), JsonConvert.DeserializeObject<ErrorDetails>(e.Message));
            }
        }

        public class ErrorDetails
        {
            public string ErrorCode { get; set; }
            public string Message { get; set; }
        }
    }
}

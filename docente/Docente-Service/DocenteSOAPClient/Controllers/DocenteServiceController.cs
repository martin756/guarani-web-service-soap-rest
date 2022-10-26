using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ServicioDocente;
using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace DocenteSOAPClient.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DocenteServiceController : ControllerBase
    {
        readonly ServiceClient soapClient = new();

        [HttpGet("MateriasAsignadas/{IdDocente}")]
        public async Task<string> GetMateriasAsignadas(int IdDocente)
        {
            GetMateriasDocenteRequest request = new() { idDocente = IdDocente };
            GetMateriasDocenteResponse response = await soapClient.GetMateriasDocenteAsync(request);
            return JsonConvert.SerializeObject(response.GetMateriasDocenteResult.ToList());
        }

        [HttpGet("ListadoAlumnos")]
        public async Task<string> GetListadoAlumnosInscriptos([Required]int IdMateria, [Required]int IdDocente, [Required]bool EsFinal)
        {
            GetAlumnosMateriaRequest request = new() { idMateria = IdMateria, idDocente = IdDocente, esFinal = EsFinal };
            GetAlumnosMateriaResponse response = await soapClient.GetAlumnosMateriaAsync(request);
            return JsonConvert.SerializeObject(response.GetAlumnosMateriaResult.ToList());
        }

        [HttpPut("CargarNotas")]
        public async Task<string> PutCargarNotas([FromBody] CargarNotasRequest requestBody )
        {
            UpsertNotasCursadaRequest request = new() { idCatedra = requestBody.IdCatedra, nroParcial = requestBody.NroParcial, listadoAlumnos = requestBody.ListadoAlumnos.ToArray() };
            UpsertNotasCursadaResponse response = await soapClient.UpsertNotasCursadaAsync(request);
            return response.UpsertNotasCursadaResult;
        }

        public class CargarNotasRequest
        {
            public int IdCatedra { get; set; }
            public int NroParcial { get; set; }
            public IEnumerable<AlumnoMateriaNotaRequest> ListadoAlumnos { get; set; }
        }

        // POST api/<DocenteServiceController>
        /*[HttpPost]
        public void Post([FromBody] string value)
        {
        }

        // PUT api/<DocenteServiceController>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/<DocenteServiceController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }*/
    }
}

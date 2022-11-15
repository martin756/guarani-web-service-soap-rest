import axios from 'axios'
import { saveAs } from 'file-saver'
export const diasLaborales = ["Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"]
export const turnos = ["Mañana","Tarde","Noche"]
export const tipoUsuarios = ["Estudiante","Docente","Administrador"]

export const estudiantePaths = [
  {path: '/consultamateriasestudiante',label: "Consulta de Materias/Exámenes"},
  {path: '/consultaanalitico',label: "Consultar informe analítico"},
  {path: '/contacto',label: "Modificación de datos de contacto"},
  {path: '/cambiocatedraEstudiante',label: "Cambio de comisión"}
], docentePaths = [
  {path: '/consultamateriasdocente',label: "Consulta de materias asignadas"}
], adminPaths = [
  {path: '/abmusuarios',label: "ABM de estudiantes y docentes"},
  {path: '/cargacuatrimestres',label: "Carga de cuatrimestres"},
  {path: '/cargaexamenes',label: "Carga de mesas de examen"},
  {path: '/inscripciones',label: "Habilitación de inscripciones"},
  {path: '/cambioCatedra',label: "Peticiones pendientes"}
]

export const traerDatos = async(fullUrl)=>{
    return await (await axios.get(fullUrl)).data
}

const urltoFile = (url, filename, mimeType) => {
    return (fetch(url)
        .then(function (res) { return res.arrayBuffer(); })
        .then(function (buf) { return new File([buf], filename, { type: mimeType }); })
    );
}

export const descargarArchivo = async(url, filename, mimeType) => {
    await axios.get(url).then(response=>{
      const data = response.data
      const mimetype = mimeType
      const base64String = mimetype+';base64,'+data.bytes
      urltoFile(base64String, filename, mimetype)
      .then(file => {
         saveAs(file)
        })
    }).catch(error=>{
      alert(error)
    })
}
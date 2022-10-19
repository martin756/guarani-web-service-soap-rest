import axios from 'axios'
import { saveAs } from 'file-saver'
export const diasLaborales = ["Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"]
export const turnos = ["Mañana","Tarde","Noche"]
export const tipoUsuarios = ["Estudiante","Docente","Administrador"]

export const traerDatos = async(fullUrl)=>{
    return await (await axios.get(fullUrl)).data
}

const urltoFile = (url, filename, mimeType) => {
    return (fetch(url)
        .then(function (res) { return res.arrayBuffer(); })
        .then(function (buf) { return new File([buf], filename, { type: mimeType }); })
    );
}

export const descargarExcel = async(url, filename) => {
    await axios.get("http://localhost:8081/inscripcionesMesas/1").then(response=>{
      const data = response.data
      const mimetype = 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      const base64String = mimetype+';base64,'+data.bytes
      urltoFile(base64String, 'test.xls', mimetype)
      .then(file => {
         saveAs(file)
        })
    }).catch(error=>{
      alert(error)
    })
  }
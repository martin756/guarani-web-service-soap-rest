import axios from 'axios'
export const diasLaborales = ["Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"]
export const turnos = ["Mañana","Tarde","Noche"]
export const tipoUsuarios = ["Estudiante","Docente","Administrador"]

export const traerDatos = async(fullUrl)=>{
    return await (await axios.get(fullUrl)).data
}
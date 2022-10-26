import React, { useEffect, useMemo, useRef, useState } from 'react'
import { descargarArchivo, traerDatos } from '../../components/Data'
import { Upload, Download, JournalArrowUp } from 'react-bootstrap-icons'
import { useLocation } from 'react-router-dom'
import * as xlsx from 'xlsx'
import HtmlTableToJson from 'html-table-to-json'
import TableMarks from '../../components/TableMarks'
import axios from 'axios'
import Cookies from 'universal-cookie'

function ListadoAlumnos() {
    const materiaUrl = "http://localhost:8080/materia/",
    docenteServiceUrl = "https://localhost:5001/api/DocenteService/",
    reporteUrl = "http://localhost:8081/inscripcionesCatedra/"
    const fileInput = useRef(null)
    const [materia, setMateria] = useState([])
    const [alumnos, setAlumnos] = useState([])
    const cookies = new Cookies()
    const { search } = useLocation();
    let query = useMemo(() => new URLSearchParams(search), [search]);

    const idMateria = query.get('id')
    const [idCatedra, setIdCatedra] = useState(0)
    const esFinal = query.get('esFinal')
  
    useEffect(() => {
      const fetchData = async() =>{
        try { 
          debugger
            setMateria(await traerDatos(materiaUrl+idMateria))
            const listadoAlumnos = await traerDatos(docenteServiceUrl+"ListadoAlumnos?IdMateria="+idMateria+"&IdDocente="+cookies.get('Idusuario')+"&EsFinal="+esFinal)
            console.log(listadoAlumnos)
            let nota_final = 0, tiempo_limite = null
            listadoAlumnos.forEach(alumno=>{
              const tiemposLimites = []
              const tiempoCarga = {}
              alumno.notas.forEach(value=>{
                if(value.nro_parcial !== 0){
                  Object.defineProperty(alumno, "Nota "+value.nro_parcial+"° Parcial", {value: value.nota, enumerable: true})
                  //tiempoCarga["Fecha limite de carga "+value.nro_parcial+"° Parcial"] = value.Tiempo_limite
                  Object.defineProperty(tiempoCarga, value.nro_parcial+"° Parcial: ", {value: value.Tiempo_limite, enumerable: true})
                }else{
                  //tiempoCarga["Fecha limite de carga Nota Final"] = value.Tiempo_limite
                  nota_final = value.nota
                  tiempo_limite = value.Tiempo_limite
                }
              })
              tiempo_limite !== null && Object.defineProperty(tiempoCarga, "Nota Final: ", {value: tiempo_limite, enumerable: true})
              Object.keys(tiempoCarga).length > 0 && tiemposLimites.push(tiempoCarga)
              alumno.Nota_Cursada !== 0 && Object.defineProperty(alumno, "Nota Cursada", {value: alumno.Nota_Cursada, enumerable: true})
              nota_final !== 0 && Object.defineProperty(alumno, "Nota Final", {value: nota_final, enumerable: true})
              alumno.Nota_Definitiva !== 0 && Object.defineProperty(alumno, "Nota Definitiva", {value: alumno.Nota_Definitiva, enumerable: true})
              tiemposLimites.length > 0 && Object.defineProperty(alumno, "tiempos_limites", {value: tiemposLimites, enumerable: true})
              /*!alumno.hasOwnProperty('Nota Final') && */
              delete alumno.notas
              delete alumno.Nota_Cursada
              delete alumno.Nota_Definitiva
              delete alumno.id_inscripcion;
              setIdCatedra(alumno.idcatedra); 
              delete alumno.idcatedra;
            })
            setAlumnos(listadoAlumnos)
          }
        catch (error) { alert(error) }
      }
      fetchData()
    }, [])
    const importarExcel = event =>{
        debugger
        if (event.target.files) {
            const reader = new FileReader()
            reader.onload = e => {
                const data = e.target.result
                const workbook = xlsx.read(data, { type: "array" });
                const sheetName = workbook.SheetNames[0];
                const worksheet = workbook.Sheets[sheetName];
                const json = xlsx.utils.sheet_to_json(worksheet);
                //console.log(json);
                const jsonData = {listadoAlumnos: []}
                json.forEach(value=>{
                  jsonData.idCatedra = parseInt(sheetName.slice(8))
                  jsonData.nroParcial = value.NroParcial === undefined ? 0 : value.NroParcial
                  jsonData.listadoAlumnos.push({
                    idEstudiante: value.idEstudiante,
                    nota: value.Nota === undefined ? value["Nota Final"] : value.Nota
                  })
                })
                procesarNotas(jsonData)
                /*json.forEach(obj => {
                    let listaNotas = []
                    for (var propName in obj) {
                        if (!propName.toLowerCase().indexOf('nota')) {
                            const nota = {}
                            Object.defineProperty(nota, propName, {value: obj[propName], enumerable: true})
                            listaNotas.push(nota)
                            delete obj[propName]
                        }
                    }
                    obj.notas = listaNotas
                });*/
                
            }
            reader.readAsArrayBuffer(event.target.files[0])
            fileInput.current.value = ''
        }
    }
    const procesarNotas = async(jsonData) => {
      await axios.put(docenteServiceUrl+"CargarNotas", jsonData)
      .then((response)=>{
        alert(response.data)
      }).catch(error=>{
        alert(error)
      })
    }
    const procesarDatos = () => {
      debugger
      const table = document.getElementById('table-marks')
      const jsonTables = HtmlTableToJson.parse(table.outerHTML)._results[0]
      console.log(jsonTables)
      const jsonNotas = {listadoAlumnos: []}
      jsonTables.forEach(value=>{
        jsonNotas.idCatedra = idCatedra
        jsonNotas.nroParcial = parseInt(value.NroParcial === undefined ? 0 : value.NroParcial)
        jsonNotas.listadoAlumnos.push({
          idEstudiante: parseInt(value.id),
          nota: parseFloat(value.Nota === undefined ? value["Nota Final"] : value.Nota)
        })
      })
      procesarNotas(jsonNotas)
    }
  return (
    <>
      <div className="container pt-5">
        <div className="row">
          <h4 className="mb-3 text-center">{materia.nombre}</h4>
          <div className='d-flex justify-content-end mb-3'>
            <button onClick={()=>{procesarDatos()}} className='me-2 btn btn-success'><JournalArrowUp/> Cargar Notas</button>
            <button onClick={()=>{descargarArchivo(reporteUrl+idCatedra,"Listado de alumnos.xls","data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")}} className='me-2 btn btn-info'><Download/> Exportar planilla</button>
            <button onClick={()=>{fileInput.current.click()}} className='me-2 btn btn-primary'><Upload/> Importar planilla</button>
            <input type="file" ref={fileInput} onChange={importarExcel} style={{display: 'none'}}/>
          </div>
          <TableMarks data={alumnos} linkPage='listadoalumnos' />
        </div>
      </div>
    </>
  )
}

export default ListadoAlumnos
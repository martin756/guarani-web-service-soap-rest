import React, { useEffect, useMemo, useRef, useState } from 'react'
import Table from '../../components/Table'
import { descargarExcel, traerDatos } from '../../components/Data'
import { Upload, Download } from 'react-bootstrap-icons'
import { useLocation } from 'react-router-dom'
import * as xlsx from 'xlsx'

function ListadoAlumnos() {
    const materiaUrl = "http://localhost:8080/materia/",
    docenteServiceUrl = "https://localhost:5001/api/DocenteService/ListadoAlumnos/",
    reporteUrl = "http://localhost:8081/inscripcionesCatedra/"
    const fileInput = useRef(null)
    const [materia, setMateria] = useState([])
    const [alumnos, setAlumnos] = useState([])
    const { search } = useLocation();
    let query = useMemo(() => new URLSearchParams(search), [search]);

    const idMateria = query.get('id')
  
    useEffect(() => {
      const fetchData = async() =>{
        try { 
            setMateria(await traerDatos(materiaUrl+idMateria))
            setAlumnos(await traerDatos(docenteServiceUrl+idMateria)) }
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
                console.log(json);
                json.forEach(obj => {
                    let listaNotas = []
                    for (var propName in obj) {
                        if (!propName.toLowerCase().indexOf('nota')) {
                            const nota = {}
                            Object.defineProperty(nota, propName, {value: obj[propName]})
                            listaNotas.push(nota)
                            delete obj[propName]
                        }
                    }
                    obj.notas = listaNotas
                });
                
            }
            reader.readAsArrayBuffer(event.target.files[0])
            fileInput.current.value = ''
        }
    }
  return (
    <>
      <div className="container pt-5">
        <div className="row">
          <h4 className="mb-3 text-center">{materia.nombre}</h4>
          <div className='d-flex justify-content-end mb-3'>
            <button onClick={()=>{descargarExcel(reporteUrl+(alumnos.length > 0 && alumnos[0].idcatedra),"Listado de alumnos.xls")}} className='me-2 btn btn-info'><Download/> Exportar planilla</button>
            <button onClick={()=>{fileInput.current.click()}} className='me-2 btn btn-primary'><Upload/> Importar planilla</button>
            <input type="file" ref={fileInput} onChange={importarExcel} style={{display: 'none'}}/>
          </div>
          <Table data={alumnos} linkPage='listadoalumnos' />
        </div>
      </div>
    </>
  )
}

export default ListadoAlumnos
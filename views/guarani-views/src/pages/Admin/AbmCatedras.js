import React, { useEffect, useRef, useState } from 'react'
import Table from '../../components/Table'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { Download, PencilSquare } from 'react-bootstrap-icons'
import { descargarArchivo, turnos } from '../../components/Data'

function AbmCatedras(props) {
    const turno = useRef(null)
    const adminUrl = "http://localhost:8080"
    const reporteUrl = "http://localhost:8081"
    const [catedras, setCatedras] = useState([])
    const [todasLasCatedras, setTodasLasCatedras] = useState([])
    const navigate = useNavigate()

    const traerCatedras = async () => {
      await axios.get(adminUrl+(props.esFinal ? "/mesa/" : "/catedra/")).then(response=>{
        const mappedData = []
        response.data.forEach(element => {
          const rawData = {
            "id": element.id,
            "cuatrimestre": element.cuatrimestre.periodo+"° "+element.cuatrimestre.anio,
            "materia": element.materia.nombre,
            "año": element.materia.anio+"°",
            "carrera": element.materia.carrera.nombre,
            "docente": element.profesor.nombre+" "+element.profesor.apellido,
            "turno": element.turno.horario === "MANIANA" ? "MAÑANA" : element.turno.horario
          }
          rawData[props.esFinal ? "fecha de final" : "dia"] = props.esFinal ? new Date(element.fecha_final).toLocaleString() : element.dia.dia
          mappedData.push(rawData)
        });
        setCatedras(mappedData)
        setTodasLasCatedras(mappedData)
      }).catch(error=>{
        alert(error)
      })
    }
  
    useEffect(() => {const fetchData = async()=>{
        await traerCatedras()
      }
      fetchData()
    }, [props.esFinal])

    const filtrarPorTurno = e => {
      const idTurno = e.target.selectedIndex+1
      let arr = [...todasLasCatedras]
      arr = arr.filter(catedra => idTurno === (catedra.turno === "MAÑANA" ? 1 : catedra.turno === "TARDE" ? 2 : catedra.turno === "NOCHE" ? 3 : 0))
      setCatedras(arr)
    }
  return (
    <>
        <div className="container pt-5">
            <div className="row">
                <h4 className="text-center">{!props.esFinal ? "Carga de cuatrimestres" : "Carga de mesas de exámen"}</h4>
                <div className="row g-3 d-flex justify-content-center mb-3">
                    <div className="col-3 me-3">
                        <label className="form-label">Turno</label>
                        <select ref={turno} className="form-select" required onChange={filtrarPorTurno}>
                          {turnos.map((value, index)=>(
                            <option key={index+1} value={index+1}>{value}</option>
                          ))}
                        </select>
                    </div>
                </div>
                <div className='d-flex justify-content-end mb-3'>
                    <button onClick={()=>{descargarArchivo(reporteUrl+(props.esFinal ? "/pdfFinales" : "/pdfMateriasCuatrimestre?idTurno="+(turno.current === null ? "1" : turno.current.options.selectedIndex+1)),props.esFinal ? "Planilla de finales.pdf" : "Planilla de materias del cuatrimestre.pdf","data:application/pdf")}} className='me-2 btn btn-info'><Download/> Exportar planilla</button>
                    <button onClick={()=>navigate('/Materia')} className='btn btn-primary'>+ Agregar materia</button>
                </div>
                <Table data={catedras} linkPage='Materia' actions={{edit: <PencilSquare/>}}/>
            </div>
        </div>
    </>
  )
}

export default AbmCatedras
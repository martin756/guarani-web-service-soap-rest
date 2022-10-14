import React, { useEffect, useRef, useState } from 'react'
import Table from '../../components/Table'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { Download, PencilSquare, Trash } from 'react-bootstrap-icons'
import { traerDatos, turnos} from '../../components/Data'

function AbmCatedras(props) {
    const baseUrl = "http://localhost:8080"
    const [catedras, setCatedras] = useState([])
    const [cuatrimestres, setCuatrimestres] = useState([])
    const navigate = useNavigate()
    const turno = useRef(null)
    const cuatrimestre = useRef(null)
  
    const traerCatedras = async () => {
      await axios.get(baseUrl+(props.esFinal ? "/mesa/" : "/catedra/")).then(response=>{
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
      }).catch(error=>{
        alert(error)
      })
    }
  
    useEffect(() => {const fetchData = async()=>{
        await traerCatedras()
        setCuatrimestres(await traerDatos(baseUrl+(props.esFinal ? "/mesa" : "/cuatrimestre")))
      }
      fetchData()
    }, [props.esFinal])

    
  return (
    <>
        <div className="container pt-5">
            <div className="row">
                <h4 className="text-center">{!props.esFinal ? "Carga de cuatrimestres" : "Carga de mesas de exámen"}</h4>
                <div className="row g-3 d-flex justify-content-center mb-3">
                    {!props.esFinal ? <><div className="col-3 me-3">
                        <label className="form-label">Turno</label>
                        <select ref={turno} className="form-select" required>
                          <option key={0} value="">Seleccione turno</option>
                          {turnos.map((value, index)=>(
                            <option key={index+1} value={index+1}>{value}</option>
                          ))}
                        </select>
                        <div className="invalid-feedback">Seleccione un turno.</div>
                    </div>
                    <div className="col-3 ms-3">
                        <label className="form-label">Cuatrimestre</label>
                        <select ref={cuatrimestre} className="form-select" required>
                            <option value="">Seleccione cuatrimestre</option>
                            {cuatrimestres.map((value,index)=>(
                              <option key={index} value={value.id}>{value.periodo}° {value.anio}</option>
                            ))}
                        </select>
                        <div className="invalid-feedback">Seleccione cuatrimestre.</div>
                    </div></> : 
                    <div className="col-3">
                        <label className="form-label">Mesa</label>
                        <select ref={cuatrimestre} className="form-select" required>
                            <option value="">Seleccione mesa de exámen</option>
                            {cuatrimestres.map((value,index)=>(
                              <option key={index} value={value.id}>Id: {value.id}, Materia: {value.materia.nombre}</option>
                            ))}
                        </select>
                        <div className="invalid-feedback">Seleccione cuatrimestre.</div>
                    </div>}
                </div>
                <div className='d-flex justify-content-end mb-3'>
                    <button  className='me-2 btn btn-info'><Download/> Exportar planilla</button>
                    <button onClick={()=>navigate('/Materia')} className='btn btn-primary'>+ Agregar materia</button>
                </div>
                <Table data={catedras} linkPage='Materia' actions={{edit: <PencilSquare/>, delete: <Trash/>}}/>
            </div>
        </div>
    </>
  )
}

export default AbmCatedras
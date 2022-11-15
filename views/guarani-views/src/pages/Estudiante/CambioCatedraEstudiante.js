
import axios from 'axios'
import React, { useEffect, useRef, useState } from 'react'
import Cookies from 'universal-cookie'
import { traerDatos } from '../../components/Data'
import TableInscripciones from '../../components/TablaInscripciones'

function CambioCatedraEstudiante() {

  const [inscripciones, setInscripciones] = useState([])

    const inscripcion = useRef(null)
    const catedraNueva = useRef(null)
    const cookies = new Cookies()
    const [usuario, setUsuario] = useState([])



    useEffect(() => {
        const fetchData = async() =>{
          try { setInscripciones(await traerDatos("http://localhost:8080/inscripcion/{idUsuario}?idUsuario="+cookies.get('Idusuario'))) }
          catch (error) { alert(error) }
        }
        fetchData()
      }, [])

    const publicar = async(event) => {
        event.preventDefault()
        const jsonBody = 
        {
            "solicitud": "Pendiente"
        }
        inscripcion.current.value !== "" && Object.defineProperty(jsonBody, "idusuario_materia_cuatrimestre", {value: inscripcion.current.value, enumerable: true})
        catedraNueva.current.value !== "" && Object.defineProperty(jsonBody, "idcatedra_nueva", {value: catedraNueva.current.value, enumerable: true})
        await axios.post("https://localhost:5003/api/EstudianteService/CambioCatedra", jsonBody)
        .then(response=>{
            alert(response.data)
        }).catch(error=>{
            alert(error)
        })
    };


  return (
    <>
        <div className="container">
            <div className="row g-5 mt-4" style={{justifyContent: 'center'}}>
                <div className="col-md-6 col-lg-7">
                    <h4 className="mb-3 text-center">Cambio de comisión</h4>
                    <form onSubmit={event=>publicar(event)} className="needs-validation" noValidate>
                        <div className="row g-3">
                            <div className="col-12">
                                <label className="form-label">Inscripcion (id)</label>
                                <input ref={inscripcion} type="text" className='form-control' defaultValue={usuario.inscripcion}/>
                            </div>
                            <div className="col-12">
                                <label className="form-label">Catedra solicitada (idCatedra)</label>
                                <input ref={catedraNueva} type="text" className='form-control' defaultValue={usuario.catedraNueva}/>
                            </div>
                        </div>
                        <hr className="my-4" />
                        <button className="w-100 btn btn-primary btn-lg" type="submit">Enviar Solicitud</button>
                    </form>
                </div>

            </div>
            <div className="container pt-5">
              <div className="row">
                <h4 className="mb-3 text-center">Inscripciones</h4>
                <TableInscripciones data={inscripciones} linkPage='CambioCatedra'/>
              </div>
            </div>
        </div>
    </>
  )
}



export default CambioCatedraEstudiante
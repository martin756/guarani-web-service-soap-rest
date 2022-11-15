
import axios from 'axios'
import React, { useEffect, useState } from 'react'
import Cookies from 'universal-cookie'
import { traerDatos } from '../../components/Data'
import TableInscripciones from '../../components/TablaInscripciones'

function CambioCatedraEstudiante() {

  const [inscripciones, setInscripciones] = useState([])
  const cookies = new Cookies()
  const [formData, setFormData] = useState({ inscripcionId: "", catedraId: "" })
  const [publicado, setPublicado] = useState(false)
  const [fechaInscripcion, setFechaInscripcion] = useState([])



  useEffect(() => {
    const fetchData = async () => {
      try {
        setInscripciones(await traerDatos("http://localhost:8080/inscripcion/{idUsuario}?idUsuario=" + cookies.get('Idusuario')))
        setFechaInscripcion(await traerDatos("http://localhost:8080/fechas_inscripcion"))
      }
      catch (error) { alert(error) }
    }
    fetchData()
  }, [])

  const handleChange = e => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value

    })
  }

  const publicar = async (event) => {
    event.preventDefault()
    const { inscripcionId, catedraId } = formData;
    setPublicado(true)
    if (inscripcionId && catedraId) {
       const jsonBody = 
       {
        "idcatedra_nueva": catedraId,
        "idusuario_materia_cuatrimestre": inscripcionId,
        "solicitud": "Pendiente"
      }
      await axios.post("https://localhost:5003/api/EstudianteService/CambioCatedra",jsonBody)
        .then(response => {
          setFormData({ inscripcionId: "", catedraId: "" })
          alert(response.data)
          setPublicado(false)
        }).catch(error => {
          alert(error)
        })
    }
  };
  return (   
    <>
      {(new Date(new Date(fechaInscripcion.inscripcion_desde).toString()+" GMT-03").toISOString() < new Date().toISOString() 
      && new Date(new Date(fechaInscripcion.inscripcion_hasta).toString()+" GMT-03").toISOString() > new Date().toISOString()) ?
        <div className="container">
          <div className="row g-5 mt-4" style={{ justifyContent: 'center' }}>
            <div className="col-md-6 col-lg-7">
              <h4 className="mb-3 text-center">Cambio de comisión</h4>
              <form onSubmit={event => publicar(event)} className="needs-validation" noValidate>
                <div className="row g-3">
                  <div className="col-12">
                    <label className="form-label">Inscripcion (id)</label>
                    <input onChange={handleChange} name="inscripcionId" type="number" pattern="[0-9]*" inputmode="numeric" className='form-control' required value={formData.inscripcionId} />
                    {(!formData.inscripcionId && publicado) && <span className='text-danger'>Campo requerido</span>}
                  </div>
                  <div className="col-12">
                    <label className="form-label">Catedra solicitada (idCatedra)</label>
                    <input onChange={handleChange} name="catedraId" type="number" pattern="[0-9]*" inputmode="numeric" className='form-control' required value={formData.catedraId} />
                    {(!formData.catedraId && publicado) ? <span className='text-danger'>Campo requerido</span> : <span></span>}
                  </div>
                </div>
                <hr className="my-4" />
                <button className="w-100 btn btn-primary btn-lg" type="submit">Enviar Solicitud</button>
              </form>
            </div>
          </div>
          <div className="container pt-5">
            <div className="row">
              <h4 className="mb-3 text-center">Inscripciones actuales</h4>
              <TableInscripciones data={inscripciones} linkPage='CambioCatedra' />
            </div>
          </div>
        </div>
        : <h4 className="mb-3 text-center">Fecha de inscripcion cerrada</h4>
      }
    </>
  )
}



export default CambioCatedraEstudiante
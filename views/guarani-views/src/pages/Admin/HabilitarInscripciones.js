import axios from 'axios'
import React, { useEffect, useRef, useState } from 'react'
import { traerDatos } from '../../components/Data'

function HabilitarInscripciones() {
    const fechaDesde = useRef(null)
    const fechaHasta = useRef(null)
    const instanciaCuatri = useRef(null)
    const instanciaFinales = useRef(null)

    const [fechas, setFechas] = useState([])
    const baseUrl = "http://localhost:8080/fechas_inscripcion"

    const establecer = async event =>{
        event.preventDefault()
        debugger
        const jsonData = {
            "es_final": instanciaFinales.current.checked,
            "inscripcion_desde": new Date(new Date(fechaDesde.current.value).toString()+" GMT-03").toISOString(),
            "inscripcion_hasta": new Date(new Date(fechaHasta.current.value).toString()+" GMT-03").toISOString()
        }
        await axios.put(baseUrl, jsonData).then(response=>{
            alert("Inscripciones establecidas")
        }).catch(error=>{
            alert(error)
        })
    }

    useEffect(() => {
        const fetchData = async()=>{
            const datosFechas = await traerDatos(baseUrl)
            datosFechas.inscripcion_desde = new Date(new Date(datosFechas.inscripcion_desde).toString()+" GMT").toISOString().slice(0, -8)
            datosFechas.inscripcion_hasta = new Date(new Date(datosFechas.inscripcion_hasta).toString()+" GMT").toISOString().slice(0, -8)
            setFechas(datosFechas)
        }
        fetchData()
        const script = document.createElement('script');
        script.src = "https://getbootstrap.com/docs/5.2/examples/checkout/form-validation.js";
        script.async = true;
        document.body.appendChild(script);
        return () => {
            document.body.removeChild(script);
        }
    }, []);
  return (
    <>
        <div className="container">
            <div className="row g-5 mt-4" style={{justifyContent: 'center'}}>
                <div className="col-md-6 col-lg-7">
                <h4 className="mb-3 text-center">Habilitación de Inscripciones</h4>
                <form onSubmit={event=>establecer(event)} className="needs-validation" noValidate>
                    <div className="row g-3">
                        <div className="col-12">
                            <label className="form-label">Fecha Comienzo</label>
                            <input ref={fechaDesde} type="datetime-local" className='form-control' min={new Date().toISOString().slice(0, -8)} required defaultValue={fechas.inscripcion_desde}/>
                            <div className="invalid-feedback">Ingrese la fecha de comienzo de inscripciones!</div>
                        </div>
                        <div className="col-12">
                            <label className="form-label">Fecha Finalización</label>
                            <input ref={fechaHasta} type="datetime-local" className='form-control' min={new Date().toISOString().slice(0, -8)} required defaultValue={fechas.inscripcion_hasta}/>
                            <div className="invalid-feedback">Ingrese la fecha de finalización de inscripciones!</div>
                        </div>
                        <div className='col-12'>
                            <label className="form-label">Instancia</label>
                            <div className="form-check">
                                <input ref={instanciaCuatri} className="form-check-input" type="radio" name="flexRadioDefault" defaultChecked={!fechas.es_final} />
                                <label className="form-check-label">Cuatrimestres</label>
                            </div>
                            <div className="form-check">
                                <input ref={instanciaFinales} className="form-check-input" type="radio" name="flexRadioDefault" defaultChecked={fechas.es_final}/>
                                <label className="form-check-label">Finales</label>
                            </div>
                        </div>
                    </div>
                    <hr className="my-4" />
                    <button className="w-100 btn btn-primary btn-lg" type="submit">Establecer</button>
                </form>
                </div>
            </div>
        </div>
    </>
  )
}

export default HabilitarInscripciones
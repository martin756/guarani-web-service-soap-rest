import React, { useRef } from 'react'

function HabilitarInscripciones() {
    const fechaDesde = useRef(null)
    const fechaHasta = useRef(null)
    const instanciaCuatri = useRef(null)
    const instanciaFinales = useRef(null)

    const establecer = event =>{
        event.preventDefault()
        const jsonData = {

        }
    }
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
                    <input ref={fechaDesde} type="datetime-local" className='form-control' name="text" min={new Date().toISOString().slice(0, -8)} required/>
                    <div className="invalid-feedback">Ingrese la fecha de comienzo de inscripciones!</div>
                </div>
                <div className="col-12">
                    <label className="form-label">Fecha Finalización</label>
                    <input ref={fechaHasta} type="datetime-local" className='form-control' name="text" min={new Date().toISOString().slice(0, -8)} required/>
                    <div className="invalid-feedback">Ingrese la fecha de finalización de inscripciones!</div>
                </div>
                <div className='col-12'>
                    <label className="form-label">Instancia</label>
                    <div className="form-check">
                        <input ref={instanciaCuatri} className="form-check-input" type="radio" name="flexRadioDefault"checked />
                        <label className="form-check-label">Cuatrimestres</label>
                    </div>
                    <div className="form-check">
                        <input ref={instanciaFinales} className="form-check-input" type="radio" name="flexRadioDefault"/>
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
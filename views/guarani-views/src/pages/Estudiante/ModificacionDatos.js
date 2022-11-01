import axios from 'axios'
import React, { useEffect, useRef, useState } from 'react'
import Cookies from 'universal-cookie'
import { traerDatos } from '../../components/Data'

function ModificacionDatos() {
    const baseUrl = "http://localhost:8080/usuario"
    const email = useRef(null)
    const direccion = useRef(null)
    const clave = useRef(null)
    const cookies = new Cookies()
    const [usuario, setUsuario] = useState([])

    const publicar = async(event) => {
        event.preventDefault()
        const jsonBody = 
        {
            "id": cookies.get('Idusuario')
        }
        email.current.value !== "" && Object.defineProperty(jsonBody, "email", {value: email.current.value, enumerable: true})
        direccion.current.value !== "" && Object.defineProperty(jsonBody, "direccion", {value: direccion.current.value, enumerable: true})
        clave.current.value !== "" && Object.defineProperty(jsonBody, "password", {value: clave.current.value, enumerable: true})
        await axios.put("https://localhost:5003/api/EstudianteService/ModificacionDatos", jsonBody)
        .then(response=>{
            alert(response.data)
        }).catch(error=>{
            alert(error)
        })
    }

    useEffect(() => {
        const fetchData = async()=>{
            try { setUsuario(await traerDatos(baseUrl+"/"+cookies.get('Idusuario'))) } 
            catch (error) { alert(error) }
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
                    <h4 className="mb-3 text-center">{cookies.get('Nombre')+" "+cookies.get('Apellido')}</h4>
                    <form onSubmit={event=>publicar(event)} className="needs-validation" noValidate>
                        <div className="row g-3">
                            <div className="col-12">
                                <label className="form-label">Email</label>
                                <input ref={email} type="text" className='form-control' defaultValue={usuario.email}/>
                            </div>
                            <div className="col-12">
                                <label className="form-label">Dirección</label>
                                <input ref={direccion} type="text" className='form-control' defaultValue={usuario.direccion}/>
                            </div>
                            <div className="col-12">
                                <label className="form-label">Contraseña</label>
                                <input ref={clave} type="password" className='form-control' minLength={6}/>
                                <div className="invalid-feedback">La contraseña debe tener al menos 6 caracteres.</div>
                            </div>
                        </div>
                        <hr className="my-4" />
                        <button className="w-100 btn btn-primary btn-lg" type="submit">Guardar</button>
                    </form>
                </div>
            </div>
        </div>
    </>
  )
}

export default ModificacionDatos
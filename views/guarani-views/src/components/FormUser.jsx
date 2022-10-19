import axios from 'axios'
import React, { useEffect, useRef, useState } from 'react'
import { tipoUsuarios, traerDatos } from '../components/Data'

function FormUser(props) {
    const baseUrl = "http://localhost:8080/usuario"
    const nombre = useRef(null)
    const apellido = useRef(null)
    const dni = useRef(null)
    const tipoUsuario = useRef(null)

    const [usuario, setUsuario] = useState([])

    const publicar = async(event) => {
        event.preventDefault()
        const jsonBody = 
        {
            "nombre": nombre.current.value,
            "apellido": apellido.current.value,
            "dni": parseInt(dni.current.value),
            "tipo": tipoUsuario.current.options.selectedIndex-1
        }
        handlePeticion(props.idUsuario == undefined ? "post" : "put", jsonBody)
    }

    const handlePeticion = async(method, data) => {
        await axios({
            "method": method,
            "url": baseUrl+"/"+(props.idUsuario == undefined ? "" : props.idUsuario),
            "data": data})
        .then(response=>{
            method == "post" ? 
                alert("Usuario creado con id "+response.data) :
                alert("Usuario con id "+response.data+" modificado")
        }).catch(error=>{
            alert(error)
        })
    }

    useEffect(() => {
        const fetchData = async()=>{
            try {
                props.idUsuario !== undefined && setUsuario(await traerDatos(baseUrl+"/"+props.idUsuario)) 
            } 
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
        <h4 className="mb-3 text-center">{props.name}</h4>
        <form onSubmit={event=>publicar(event)} className="needs-validation" noValidate>
            <div className="row g-3">
                <div className="col-12">
                    <label className="form-label">Nombre</label>
                    <input ref={nombre} type="text" className='form-control' required defaultValue={usuario.nombre}/>
                    <div className="invalid-feedback">El nombre es requerido.</div>
                </div>
                <div className="col-12">
                    <label className="form-label">Apellido</label>
                    <input ref={apellido} type="text" className='form-control' required defaultValue={usuario.apellido}/>
                    <div className="invalid-feedback">El apellido es requerido.</div>
                </div>
                <div className="col-12">
                    <label className="form-label">DNI</label>
                    <input ref={dni} type="number" className='form-control' required min={1} max={99999999} defaultValue={usuario.dni}/>
                    <div className="invalid-feedback">El dni no es válido.</div>
                </div>
                <div className="col-12">
                    <label className="form-label">Tipo de usuario</label>
                    <select ref={tipoUsuario} className="form-select" required>
                        <option key={0} value="">Seleccione tipo</option>
                        {tipoUsuarios.map((value, index)=>(<>
                            {usuario !== undefined && usuario.tipoUsuario === value.toUpperCase() ? 
                                <option key={index+1} value={index+1} selected>{value}</option> :
                                <option key={index+1} value={index+1}>{value}</option>}</>
                        ))}
                    </select>
                    <div className="invalid-feedback">Seleccione una tipo de usuario.</div>
                </div>
            </div>
            <hr className="my-4" />
            <button className="w-100 btn btn-primary btn-lg" type="submit">Publicar</button>
        </form>
    </>
  )
}

export default FormUser
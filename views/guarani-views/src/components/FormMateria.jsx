import axios from 'axios'
import React, { useEffect, useRef, useState } from 'react'
import { traerDatos, diasLaborales, turnos } from '../components/Data'

function FormMateria(props) {
    const baseUrl = "http://localhost:8080"
    const cuatrimestre = useRef(null)
    const materia = useRef(null)
    const docente = useRef(null)
    const dia = useRef(null)
    const turno = useRef(null)
    const fechaFinal = useRef(null)

    const [resp, setResponse] = useState([])
    const [catedra, setCatedra] = useState([])
    const [carreras, setCarreras] = useState([])
    const [cuatrimestres, setCuatrimestres] = useState([])
    const [docentes, setDocentes] = useState([])
    const [materias, setMaterias] = useState([])
    const [subjectsFiltered, setSubjectsFiltered] = useState([])
    const [flagCarrera, setFlagCarrera] = useState(props.idMateria === undefined ? true : false)
    const [flagFinal, setFlagFinal] = useState(props.idMateria === undefined ? false : true)

    const cargar = async(event) => {
        event.preventDefault()
        debugger
        const jsonBody = 
        {
            "idCuatrimestre": cuatrimestre.current.options.item(cuatrimestre.current.options.selectedIndex).value,
            "idMateria": materia.current.options.item(materia.current.options.selectedIndex).value,
            "idProfesor": docente.current.options.item(docente.current.options.selectedIndex).value,
            "idTurno": turno.current.options.selectedIndex
        }
        if(flagFinal){
            jsonBody.fecha = fechaFinal.current.value
        }else{
            jsonBody.diaSemana = dia.current.options.selectedIndex
        }
        handlePeticion(props.idMateria === undefined ? "post" : "put", jsonBody, jsonBody.hasOwnProperty("fecha") ? "/mesa/" : "/catedra/")
    }

    const handlePeticion = async(method, data, partialUrl) => {
        await axios({
            "method": method,
            "url": baseUrl+partialUrl+(props.idMateria === undefined ? "" : props.idMateria),
            "data": data})
        .then(response=>{
            method == "post" ? 
                alert("Materia creada con id "+response.data) :
                alert("Materia con id "+response.data+" modificado")
        }).catch(error=>{
            alert(error)
        })
    }

    const traerCatedra = async() => {
        if(props.idMateria == null) return
        debugger
        await axios.get(baseUrl+"/catedra/"+props.idMateria).then(response=>{
            definirDatos(response.data)
        }).catch(()=>{
            traerMesa()
        })
    }

    const traerMesa = async() => {
        await axios.get(baseUrl+"/mesa/"+props.idMateria).then(response=>{
            definirDatos(response.data)
        }).catch(error=>{
            alert(error)
        })
    }

    const definirDatos = (data) =>{
        const dataCatedra = {
            "idTurno": data.turno.id,
            "idProfesor": data.profesor.id,
            "profesor": data.profesor.nombre+" "+data.profesor.apellido,
            "idCuatrimestre": data.cuatrimestre.id,
            "cuatrimestre": data.cuatrimestre.periodo+"° "+data.cuatrimestre.anio,
            "idMateria": data.materia.id,
            "materia": data.materia.nombre,
            "idCarrera": data.materia.carrera.id_carrera,
            "carrera": data.materia.carrera.nombre,
            "idDia": data.dia.id,
            "dia": data.dia.dia,
            "esFinal": data.es_final,
            "fechaFinal": new Date(new Date(data.fecha_final).toString()+" GMT").toISOString().slice(0, -8)
        }
        setCatedra(dataCatedra)
        setFlagFinal(dataCatedra.esFinal)
    }

    useEffect(() => {
        const fetchData = async()=>{
            try {
                await traerCatedra()
                let profesores = await traerDatos(baseUrl+"/usuarios")
                profesores = profesores.filter(element => element.tipoUsuario === "DOCENTE")
                setDocentes(profesores)
                setCuatrimestres(await traerDatos(baseUrl+"/cuatrimestre"))
                setCarreras(await traerDatos(baseUrl+"/carrera"))
                const subjects = await traerDatos(baseUrl+"/materia")
                setMaterias(subjects)
                setSubjectsFiltered(subjects)
            } catch (error) {
                alert(error)
            }
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

    const handleCarrera = event => {
        event.target.selectedIndex === 0 ? setFlagCarrera(true) : setFlagCarrera(false)
        let subjects = [...subjectsFiltered]
        subjects = subjects.filter(element => element.carrera.id_carrera === event.target.selectedIndex)
        setMaterias(subjects)
    };

    const handleFinal = () => {
        setFlagFinal(!flagFinal)
    }
  return (
    <>
        <h4 className="mb-3 text-center">{props.name}</h4>
        <form onSubmit={event=>cargar(event)} className="needs-validation" noValidate>
            <div className="row g-3">
                <div className="col-12">
                    <label className="form-label">Cuatrimestre</label>
                    <select ref={cuatrimestre} className="form-select" required>
                        <option key={0} value="">Seleccione cuatrimestre</option>
                        {cuatrimestres.length > 0 && cuatrimestres.map((value, index)=>(<>
                            {catedra !== undefined && catedra.idCuatrimestre === value.id ? 
                                <option key={index} value={value.id} selected>{value.periodo}° {value.anio}</option> :
                                <option key={index} value={value.id}>{value.periodo}° {value.anio}</option>}</>
                        ))}
                    </select>
                    <div className="invalid-feedback">Seleccione un cuatrimestre.</div>
                </div>
                <div className="col-12">
                    <label className="form-label">Carrera</label>
                    <select className="form-select" required onChange={handleCarrera}>
                        <option key={0} value="">Seleccione carrera</option>
                        {carreras.length > 0 && carreras.map((value, index)=>(<>
                            {catedra !== undefined && catedra.idCarrera === value.id_carrera ? 
                                <option key={index} value={value.id_carrera} selected>{value.nombre}</option> :
                                <option key={index} value={value.id_carrera}>{value.nombre}</option>}</>
                        ))}
                    </select>
                    <div className="invalid-feedback">Seleccione una carrera.</div>
                </div>
                <div className="col-12">
                    <label className="form-label">Materia</label>
                    <select ref={materia} className="form-select" required={!flagCarrera} disabled={flagCarrera}>
                        <option key={0} value="">Seleccione materia</option>
                        {materias.length > 0 && materias.map((value, index)=>(<>
                            {catedra !== undefined && catedra.idMateria === value.id ? 
                                <option key={index} value={value.id} selected>{value.nombre}</option> :
                                <option key={index} value={value.id}>{value.nombre}</option>}</>
                        ))}
                    </select>
                    <div className="invalid-feedback">Seleccione una materia.</div>
                </div>
                <div className="col-12">
                    <label className="form-label">Docente</label>
                    <select ref={docente} className="form-select" required>
                        <option key={0} value="">Seleccione docente</option>
                        {docentes.length > 0 && docentes.map((value, index)=>(<>
                            {catedra !== undefined && catedra.idProfesor === value.id ? 
                                <option key={index} value={value.id} selected>{value.nombre} {value.apellido}</option> :
                                <option key={index} value={value.id}>{value.nombre} {value.apellido}</option>}</>
                        ))}
                    </select>
                    <div className="invalid-feedback">Seleccione un docente.</div>
                </div>
                {!flagFinal &&
                <div className="col-12">
                    <label className="form-label">Dia de la semana</label>
                    <select ref={dia} className="form-select" required>
                        <option key={0} value="">Seleccione dia</option>
                        {diasLaborales.map((value, index)=>(<>
                            {catedra !== undefined && catedra.idDia === index+1 ? 
                                <option key={index+1} value={index+1} selected>{value}</option> :
                                <option key={index+1} value={index+1}>{value}</option>}</>
                        ))}
                    </select>
                    <div className="invalid-feedback">Seleccione un dia.</div>
                </div>
                }
                <div className="col-12">
                    <label className="form-label">Turno</label>
                    <select ref={turno} className="form-select" required>
                        <option key={0} value="">Seleccione turno</option>
                        {turnos.map((value, index)=>(<>
                            {catedra !== undefined && catedra.idTurno === index+1 ? 
                                <option key={index+1} value={index+1} selected>{value}</option> :
                                <option key={index+1} value={index+1}>{value}</option>}</>
                        ))}
                    </select>
                    <div className="invalid-feedback">Seleccione un turno.</div>
                </div>
                <div>
                <div className="form-check mb-3">
                    <input type="checkbox" className="form-check-input" onChange={handleFinal} defaultChecked={catedra.esFinal}/> 
                    <label className="form-check-label" >¿Es Final?</label>
                </div>
                {flagFinal &&
                <div className="col-12">
                    <label className="form-label">Fecha de exámen</label>
                    <input ref={fechaFinal} type="datetime-local" className='form-control' name="text" min={new Date().toISOString().slice(0, -8)} required value={catedra.fechaFinal}/>
                    <div className="invalid-feedback">Ingrese la fecha de exámen!</div>
                </div>
                }
                </div>
            </div>
            <hr className="my-4" />
            <button className="w-100 btn btn-primary btn-lg" type="submit">Cargar</button>
        </form>
    </>
  )
}

export default FormMateria
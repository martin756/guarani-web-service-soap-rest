import React, { useRef, useEffect, useState }  from 'react'
import {useNavigate} from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css'
import Cookies from 'universal-cookie'
import axios from 'axios'
import sha1 from 'sha1'
import '../css/Home.css'

function Login() {
    const baseUrl="http://localhost:8080/usuario"
    const cookies = new Cookies()
    const username = useRef(null)
    const password = useRef(null)
    const navigate = useNavigate()
    const [changePassword, setChangePassword] = useState(false)
    const [validarPasswords, setValidarPasswords] = useState(false)

    const iniciarSesion=async(event)=>{
        debugger
        event.preventDefault()
        await axios.get(baseUrl+`?username=${username.current.value}&password=${sha1(password.current.value)}`)
        .then(response=>{
            return response.data
        }).then(response=>{
            cookies.set('Idusuario',response.id)
            cookies.set('Nombre',response.nombre)
            cookies.set('Apellido',response.apellido)
            cookies.set('Dni',response.dni)
            cookies.set('tipoUsuario',response.tipoUsuario)
            alert("Bienvenido "+response.nombre)
            debugger
            !validateChangePassword(response.dni, password.current.value) &&
                derivarUsuario(response.tipoUsuario)
        })
        .catch(error=>{
            if(error.response.data.status === 404){
                alert("El usuario o la contraseña no son correctos")
            }else{
                alert(error)
            }
        })
        //navigate('/mainmenu')
    }

    const cambiarPassword=async(event)=>{
        event.preventDefault()
        if(username.current.value !== password.current.value){
            //alert("Las contraseñas no coinciden")
            setValidarPasswords(true)
            return
        }
        await axios.put(baseUrl+"/"+cookies.get("Idusuario"),{"password":password.current.value})
        .then(response=>{
            console.log(response)
            alert("Contraseña cambiada con éxito")
            derivarUsuario(cookies.get('tipoUsuario'))
        }).catch(error=>{
            alert(error)
        })
    }

    const derivarUsuario = (tipoUsuario) => {
        switch (tipoUsuario) {
            case "ESTUDIANTE":
                navigate('/consultamateriasestudiante')
                break;
            case "DOCENTE":
                navigate('/consultamateriasdocente')
                break;
            case "ADMINISTRADOR":
                navigate('/abmusuarios');
                break;
        }
    }

    const validateChangePassword = (dni, password)=>{
        setChangePassword(dni == password)
        return dni == password
    }

    /*const Registrarse=()=>{
        navigate('/signup')
    }*/

    useEffect(()=>{
        derivarUsuario(cookies.get('tipoUsuario'))
        /*if(cookies.get('tipoUsuario') === "ESTUDIANTE"){
            navigate('/consultamateriasestudiante')
        }else if(cookies.get('tipoUsuario') === "DOCENTE"){
            navigate('/consultamateriasdocente')
        }else if(cookies.get('tipoUsuario') === "ADMIN"){
            navigate('/abmusuarios')
        }*/
    },[])

    return (
        <div className='containerPrincipal'>
            <div className='containerTitulo'>
                SiuGuarani</div>
            <div className='containerHome'>
                {!changePassword ?
                <form onSubmit={event => iniciarSesion(event)} className='form-group'>
                    <label>Usuario: </label>
                    <br />
                    <input ref={username} type="text" className='form-control' name='usuario' required />
                    <br />
                    <label>Contraseña: </label>
                    <br />
                    <input ref={password} type='password' className='form-control' name='password' required />
                    <br />
                    <button type="submit" className='btn btn-primary'>Iniciar Sesión</button>
                </form>
                :
                <form onSubmit={event => cambiarPassword(event)} className='form-group'>
                    <div class="alert alert-danger" role="alert">
                    Necesita cambiar su contraseña, dado que es su primer ingreso al sistema!</div>
                    <br />
                    <label>Nueva Contraseña: </label>
                    <br />
                    <input ref={username} type="password" className='form-control' name='newPassword' required />
                    <br />
                    <label>Confirme Contraseña: </label>
                    <br />
                    <input ref={password} type='password' className='form-control' name='repeatPassword' required />
                    {validarPasswords && <span><br/><span className='text-danger'>Las contraseñas no coinciden</span><br/></span>}
                    <br />
                    <button type="submit" className='btn btn-primary'>Cambiar Contraseña</button>
                </form>}
            </div>
        </div>
    )
}

export default Login;
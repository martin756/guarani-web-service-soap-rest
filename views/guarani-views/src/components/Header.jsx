import React, { useEffect }  from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { useNavigate } from 'react-router-dom';
import Cookies from 'universal-cookie';
// import { Cart } from 'react-bootstrap-icons';

function Header() {
  const cookies = new Cookies()
  const navigate = useNavigate()

  const cerrarSesion = () => {
    cookies.remove('Nombre')
    cookies.remove('Apellido')
    cookies.remove('Dni')
    cookies.remove('Email')
    cookies.remove('User')
    cookies.remove('Password')
    cookies.remove('EsAdministrador')
    navigate('/')
  }
  
  useEffect(() => {
    if (!cookies.get('User')) {
      navigate('/')
    }
  }, [])

  const estudiantePaths = [
    {path: '/consultamateriasestudiante',label: "Consulta de Materias/Exámenes"},
    {path: '/inscripcion',label: "Inscripción"},
    {path: '/consultaanalitico',label: "Consultar informe analítico"},
    {path: '/contacto',label: "Modificación de datos de contacto"}
  ], docentePaths = [
    {path: '/consultamateriasdocente',label: "Consulta de materias asignadas"},
    {path: '/consultaalumnos',label: "Consulta de materias asignadas"},
    {path: '/carganotas',label: "Carga de notas de cursada"},
    {path: '/cargafinales',label: "Carga de notas de final"}
  ], adminPaths = [
    {path: '/abmusuarios',label: "ABM de estudiantes y docentes"},
    {path: '/cargacuatrimestre',label: "Carga de cuatrimestres"},
    {path: '/planillacuatrimestre',label: "Planilla de cuatrimestre"},
    {path: '/cargaexamenes',label: "Carga/planilla de mesas de examen"},
    {path: '/inscripciones',label: "Ventana de inscripciones"}
  ]

  return (
    <div>
        {/* //CAMBIAR EL TERNARIO POR UN IF DEPENDIENDO EL USAURIO QUE */}
        {
          cookies.get('tipoUsuario') === "ESTUDIANTE" ?
          <Usuario cookies={cookies} navigator={navigate} closeSession={cerrarSesion} paths={estudiantePaths}/>
        : cookies.get('tipoUsuario') === "DOCENTE" ? 
          <Usuario cookies={cookies} navigator={navigate} closeSession={cerrarSesion} paths={docentePaths}/>
        :
          <Usuario cookies={cookies} navigator={navigate} closeSession={cerrarSesion} paths={adminPaths}/>
        }
    </div>
  );
}

function Usuario(props){
  return(
    <div>
      <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" sticky='top'>
        <Container>
          <Navbar.Brand href="/">SiuGuarani</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="justify-content-end flex-grow-1 pe-3">
              {props.cookies.get('Nombre') &&
              <NavDropdown title={props.cookies.get('Nombre')} id="collasible-nav-dropdown" align="end">
                {props.paths.map(value=>(
                  <NavDropdown.Item onClick={()=>{props.navigator(value.path)}}>{value.label}</NavDropdown.Item>
                ))}
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={()=>props.closeSession()}>Cerrar Sesión</NavDropdown.Item>
              </NavDropdown>
              }
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </div>
  );
}

export default Header;
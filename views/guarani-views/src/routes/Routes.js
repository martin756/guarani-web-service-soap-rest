import React from 'react'
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import Login from '../pages/Login'
import MateriasEstudiante from '../pages/Estudiante/MateriasEstudiante';
import Analitico from '../pages/Estudiante/Analitico';
import ModificacionDatos from '../pages/Estudiante/ModificacionDatos';
import AbmUsuarios from '../pages/Admin/AbmUsuarios';
import Header from '../components/Header';
import MateriasDocente from '../pages/Docente/MateriasDocente';
import Usuario from '../pages/Admin/Usuario';
import AbmCatedras from '../pages/Admin/AbmCatedras';
import Materia from '../pages/Admin/Materia';
import HabilitarInscripciones from '../pages/Admin/HabilitarInscripciones';
import ListadoAlumnos from '../pages/Docente/ListadoAlumnos';

function App() {
  return (
    <BrowserRouter>
      <Header/>
      <Routes>
        <Route path='/' element={<Login />}/>
        {/* Paths de ADMINISTRADORES */}
        <Route path='/abmusuarios' element={<AbmUsuarios />}/>
        <Route path='/Usuario' element={<Usuario />}/>
        <Route path='/cargacuatrimestres' element={<AbmCatedras esFinal={false}/>}/>
        <Route path='/cargaexamenes' element={<AbmCatedras esFinal={true}/>}/>
        <Route path='/Materia' element={<Materia />}/>
        <Route path='/inscripciones' element={<HabilitarInscripciones />}/>

        {/* Paths de ESTUDIANTES */}
        <Route path='/consultamateriasestudiante' element={<MateriasEstudiante />}/>
        <Route path='/consultaanalitico' element={<Analitico />}/>
        <Route path='/contacto' element={<ModificacionDatos />}/>

        {/* Paths de DOCENTES */}
        <Route path='/consultamateriasdocente' element={<MateriasDocente />}/>
        <Route path='/listadoalumnos' element={<ListadoAlumnos />}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;

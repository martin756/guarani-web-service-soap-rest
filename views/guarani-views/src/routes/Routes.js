import React from 'react'
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import Login from '../pages/Login'
import MenuAdmin from '../pages/MateriasDocente';
import MateriasEstudiante from '../pages/MateriasEstudiante';
import AbmUsuarios from '../pages/Admin/AbmUsuarios';
import Header from '../components/Header';
import MateriasDocente from '../pages/MateriasDocente';
import Usuario from '../pages/Admin/Usuario';
import AbmCatedras from '../pages/Admin/AbmCatedras';
import Materia from '../pages/Admin/Materia';
import HabilitarInscripciones from '../pages/Admin/HabilitarInscripciones';

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

        {/* Paths de DOCENTES */}
        <Route path='/consultamateriasdocente' element={<MateriasDocente />}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;

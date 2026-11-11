import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { LandingPage } from './pages/LandingPage';
import { LoginPage } from './pages/LoginPage';
import { RegisterPage } from './pages/RegisterPage'; 
import { MainLayout } from './components/layout/MainLayout';
import { DashboardPage } from './pages/DashboardPage';
import { PanelComitePage } from './pages/PanelComitePage';
import { GestionDocentesPage } from './pages/GestionDocentesPage';
import { isAuthenticated } from './utils/auth';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Rutas Públicas */}
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} /> {/* <-- Nueva ruta */}
        
        {/* Rutas Protegidas */}
        <Route path="/dashboard" element={ isAuthenticated() ? <MainLayout><DashboardPage /></MainLayout> : <Navigate to="/login" replace /> } />
        <Route path="/buscar" element={ isAuthenticated() ? <MainLayout><PanelComitePage /></MainLayout> : <Navigate to="/login" replace /> } />
        <Route path="/docentes" element={ isAuthenticated() ? <MainLayout><GestionDocentesPage /></MainLayout> : <Navigate to="/login" replace /> } />
        
        {/* Redirección por defecto */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
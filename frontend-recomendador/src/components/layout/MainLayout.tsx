import { type ReactNode, useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { removeToken } from '../../utils/auth';

interface MainLayoutProps {
  children: ReactNode;
}

export const MainLayout = ({ children }: MainLayoutProps) => {
  const navigate = useNavigate();
  const location = useLocation();
  
  const [isMenuOpen, setIsMenuOpen] = useState(true);

  const handleLogout = () => {
    removeToken();
    navigate('/login');
  };

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const menuItems = [
    { path: '/dashboard', label: 'Inicio', icon: '🏠' },
    { path: '/buscar', label: 'Buscar Trabajo', icon: '🔍' },
    { path: '/docentes', label: 'Gestión Docentes', icon: '👨‍🏫' },
  ];

  return (
    <div className="min-h-screen bg-gray-50 flex">
      
      {/*  MENÚ LATERAL  */}
      {isMenuOpen ? (
        // MENÚ ABIERTO (Una sola tarjeta grande)
        <aside className="m-4 w-64 bg-white rounded-xl shadow-sm flex flex-col transition-all duration-300">
          <div className="h-[70px] border-b flex items-center justify-between p-4">
            <img src="/logo-uts.png" alt="UTS" className="h-10 w-auto" />
            <button 
              onClick={toggleMenu} 
              className="text-2xl text-gray-600 hover:text-[#9BC31C] focus:outline-none border border-gray-300 rounded-lg p-1"
            >
              ☰
            </button>
          </div>

          <nav className="flex-1 p-4 space-y-2">
            {menuItems.map((item) => (
              <Link
                key={item.path}
                to={item.path}
                className={`flex items-center gap-3 px-4 py-3 rounded-lg transition ${
                  location.pathname === item.path
                    ? 'bg-[#9BC31C] text-white font-semibold'
                    : 'text-gray-600 hover:bg-gray-100'
                }`}
              >
                <span className="text-lg">{item.icon}</span>
                <span>{item.label}</span>
              </Link>
            ))}
          </nav>
        </aside>
      ) : (
        // MENÚ CERRADO (Dos tarjetas separadas: Botón arriba, Íconos abajo)
        <div className="m-4 w-20 flex flex-col gap-4 transition-all duration-300">
          {/*  Botón Hamburguesa */}
          <div className="bg-white rounded-xl shadow-sm p-4 flex justify-center">
            <button 
              onClick={toggleMenu} 
              className="text-2xl text-gray-600 hover:text-[#9BC31C] focus:outline-none border border-gray-300 rounded-lg p-2"
            >
              ☰
            </button>
          </div>

          {/* Tarjeta de los Íconos */}
          <div className="bg-white rounded-xl shadow-sm p-2 flex-1 flex flex-col items-center space-y-4 pt-4">
            {menuItems.map((item) => (
              <Link
                key={item.path}
                to={item.path}
                className={`flex items-center justify-center w-12 h-12 rounded-lg transition ${
                  location.pathname === item.path
                    ? 'bg-[#9BC31C] text-white shadow-md'
                    : 'text-gray-400 hover:bg-gray-100 hover:text-gray-600'
                }`}
                title={item.label} 
              >
                <span className="text-2xl">{item.icon}</span>
              </Link>
            ))}
          </div>
        </div>
      )}

      {/*  CONTENIDO PRINCIPAL */}
      <div className="flex-1 flex flex-col w-full overflow-hidden">
        {/* Topbar */}
        <header className="bg-white shadow-sm h-[70px] px-6 flex justify-between items-center m-4 ml-0 rounded-xl">
          <h1 className="text-xl font-semibold text-gray-800 hidden sm:block">Bienvenido</h1>
          
          <div className="flex items-center gap-4 ml-auto">
            <div className="flex items-center gap-2">
              <div className="w-9 h-9 bg-gray-200 rounded-full flex items-center justify-center text-gray-500">
                👤
              </div>
              <span className="text-sm text-gray-600 font-medium hidden md:block">
                Comité de Trabajos de Grado
              </span>
            </div>
            <button
              onClick={handleLogout}
              className="text-sm text-red-500 hover:text-red-700 font-medium"
            >
              Cerrar Sesión
            </button>
          </div>
        </header>

        <main className="p-6 pt-0 flex-1 overflow-auto">
          {children}
        </main>
      </div>
    </div>
  );
};
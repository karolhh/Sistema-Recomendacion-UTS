import { Link } from 'react-router-dom';

export const DashboardPage = () => {
  return (
    <div className="p-6 flex flex-col items-center justify-center min-h-[80vh]">
      {/* Título de la página */}
      <div className="text-center mb-12">
        <h1 className="text-3xl md:text-4xl font-bold text-gray-700">
          SISTEMA - TRABAJOS DE <span className="bg-[#9BC31C] text-white px-3 py-1 rounded">GRADO</span>
        </h1>
        <p className="text-gray-500 mt-3 text-lg">
          Bienvenido al portal de trabajos de grado. Selecciona un módulo para comenzar:
        </p>
      </div>

      {/* Contenedor de Tarjetas (Centrado y más grande) */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8 max-w-4xl mx-auto w-full">
        
        {/* Tarjeta 1: Buscar Trabajo */}
        <Link 
          to="/buscar" 
          className="bg-white p-10 rounded-2xl shadow-md hover:shadow-xl transition-all duration-300 border border-gray-100 flex flex-col items-center text-center group cursor-pointer hover:-translate-y-1"
        >
          <div className="text-6xl mb-6 text-gray-400 group-hover:text-[#9BC31C] transition-colors">
            🔍
          </div>
          <h2 className="text-2xl font-bold text-gray-700 mb-3 group-hover:text-[#9BC31C] transition-colors">
            Buscar Trabajo
          </h2>
          <p className="text-sm text-gray-500">
            Accede al banco de trabajos de grado, revisa los pendientes y asigna docentes de manera equitativa.
          </p>
        </Link>

        {/* Tarjeta 2: Gestión Docentes */}
        <Link 
          to="/docentes" 
          className="bg-white p-10 rounded-2xl shadow-md hover:shadow-xl transition-all duration-300 border border-gray-100 flex flex-col items-center text-center group cursor-pointer hover:-translate-y-1"
        >
          <div className="text-6xl mb-6 text-gray-400 group-hover:text-[#9BC31C] transition-colors">
            👨‍🏫
          </div>
          <h2 className="text-2xl font-bold text-gray-700 mb-3 group-hover:text-[#9BC31C] transition-colors">
            Gestión Docentes
          </h2>
          <p className="text-sm text-gray-500">
            Consulta la carga académica real, los proyectos asignados y los créditos disponibles de cada profesor.
          </p>
        </Link>
        
      </div>
    </div>
  );
};
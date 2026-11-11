import { useNavigate } from 'react-router-dom';

export const LandingPage = () => {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gray-50 font-sans">
      
      {/* ================= HEADER ================= */}
      <header className="bg-white py-4 px-8 flex justify-between items-center shadow-sm border-b-4 border-[#9BC31C] sticky top-0 z-50">
        <div className="flex items-center gap-3">
          {/* Aquí puedes poner el logo real de la UTS */}
          <div className="bg-[#9BC31C] text-white p-2 rounded-lg font-bold text-xl shadow-sm">
            UTS
          </div>
          <div>
            <h1 className="text-xl font-bold text-gray-800 leading-tight">Recomendador Docente</h1>
            <p className="text-xs text-gray-500 font-medium">Gestión de Trabajos de Grado</p>
          </div>
        </div>

        <div className="flex gap-3">
          <button 
            onClick={() => navigate('/login')}
            className="flex items-center gap-2 bg-[#9BC31C] text-white px-6 py-2.5 rounded-lg font-semibold hover:bg-[#7A9E14] transition shadow-md hover:shadow-lg"
          >
            <span>🔐</span> Acceso al Sistema
          </button>
        </div>
      </header>

      {/* ================= HERO SECTION (Bienvenida) ================= */}
      <section className="bg-gradient-to-br from-green-50 to-cyan-50 py-20 px-4 text-center border-b border-gray-200">
        <div className="max-w-4xl mx-auto">
          <span className="bg-[#9BC31C] text-white px-4 py-1.5 rounded-full text-sm font-bold tracking-wide uppercase mb-6 inline-block shadow-sm">
            Ingeniería de Sistemas
          </span>
          <h1 className="text-4xl md:text-6xl font-extrabold text-gray-800 mb-6 leading-tight">
            Sistema de Recomendación de <br/>
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-[#7A9E14] to-green-500">
              Docentes Evaluadores
            </span>
          </h1>
          <p className="text-lg md:text-xl text-gray-600 mb-10 max-w-2xl mx-auto leading-relaxed">
            Optimizando la asignación de directores y evaluadores para trabajos de grado. Un sistema basado en el perfil académico, carga laboral y equidad institucional.
          </p>
          <button 
            onClick={() => navigate('/login')}
            className="bg-gray-800 text-white px-8 py-3 rounded-xl font-bold text-lg hover:bg-gray-900 transition shadow-xl hover:-translate-y-1 transform duration-200"
          >
            Ingresar al Panel del Comité
          </button>
        </div>
      </section>

      {/* ================= CARACTERÍSTICAS / BENEFICIOS ================= */}
      <section className="py-16 px-4 max-w-6xl mx-auto">
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold text-gray-800">¿Qué hace nuestro sistema?</h2>
          <p className="text-gray-500 mt-2">Diseñado para facilitar el trabajo del Comité de Trabajos de Grado</p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {/* Tarjeta 1 */}
          <div className="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition">
            <div className="bg-green-100 text-[#7A9E14] w-14 h-14 rounded-xl flex items-center justify-center text-2xl mb-6">
              🎯
            </div>
            <h3 className="text-xl font-bold text-gray-800 mb-3">Recomendación Inteligente</h3>
            <p className="text-gray-600 text-sm leading-relaxed">
              Algoritmo que cruza las palabras clave del título del trabajo con el perfil académico (maestrías, doctorados) de los docentes.
            </p>
          </div>

          {/* Tarjeta 2 */}
          <div className="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition">
            <div className="bg-blue-100 text-blue-600 w-14 h-14 rounded-xl flex items-center justify-center text-2xl mb-6">
              ⚖️
            </div>
            <h3 className="text-xl font-bold text-gray-800 mb-3">Equidad de Carga</h3>
            <p className="text-gray-600 text-sm leading-relaxed">
              Control de créditos dinámico según la vinculación (Tiempo completo, Medio tiempo, Cátedra) para evitar sobrecargar a un solo docente.
            </p>
          </div>

          {/* Tarjeta 3 */}
          <div className="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition">
            <div className="bg-purple-100 text-purple-600 w-14 h-14 rounded-xl flex items-center justify-center text-2xl mb-6">
              📊
            </div>
            <h3 className="text-xl font-bold text-gray-800 mb-3">Panel de Control</h3>
            <p className="text-gray-600 text-sm leading-relaxed">
              Visualización en tiempo real de la carga académica, historial de proyectos asignados y disponibilidad de cada profesor.
            </p>
          </div>
        </div>
      </section>

      {/* ================= FOOTER ================= */}
      <footer className="bg-gray-800 text-gray-300 py-8 text-center mt-10">
        <p className="text-sm">© 2026 - Unidades Tecnológicas de Santander</p>
        <p className="text-xs text-gray-500 mt-1">Proyecto de Grado - Tecnología en Desarrollo de Sistemas Informáticos</p>
      </footer>

    </div>
  );
};
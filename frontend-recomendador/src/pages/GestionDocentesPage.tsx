import { useState, useEffect } from 'react';
import { gestionService } from '../services/gestionService';
import type { DocenteDetalle } from '../types';

export const GestionDocentesPage = () => {
  const [docentes, setDocentes] = useState<DocenteDetalle[]>([]);
  const [filtroDocente, setFiltroDocente] = useState('');
  const [docenteDetalle, setDocenteDetalle] = useState<DocenteDetalle | null>(null);

  useEffect(() => {
    cargarDocentes();
  }, []);

  const cargarDocentes = async () => {
    try {
      const data = await gestionService.getAllDocentes();
      setDocentes(data);
    } catch (error) {
      console.error("Error cargando docentes", error);
    }
  };

  const docentesFiltrados = docentes.filter(d => 
    d.nombre.toLowerCase().includes(filtroDocente.toLowerCase()) || 
    d.email.toLowerCase().includes(filtroDocente.toLowerCase())
  );

  // Función auxiliar para limpiar el texto de la vinculación
  const obtenerVinculacion = (vinculacion: string | undefined) => {
    if (!vinculacion || vinculacion.trim() === '' || vinculacion.toUpperCase() === 'N/A') {
      return 'Cátedra';
    }
    return vinculacion;
  };

  return (
    <div className="flex flex-col h-full bg-gray-50">
      
      <div className="bg-white px-6 py-4 rounded-t-xl border-b border-gray-200">
        <h1 className="text-lg font-bold text-[#9BC31C] border-b-2 border-[#9BC31C] inline-block pb-2">
          Directorio y Carga Docente
        </h1>
      </div>

      <div className="flex-1 p-6 overflow-hidden">
        <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6 h-full flex flex-col">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-xl font-bold text-gray-700">Carga Académica</h2>
            <span className="text-sm text-gray-500">Total docentes: {docentesFiltrados.length}</span>
          </div>
          
          <input 
            type="text" 
            placeholder="Buscar docente..." 
            value={filtroDocente} 
            onChange={(e) => setFiltroDocente(e.target.value)} 
            className="w-full max-w-md px-3 py-2 border rounded-lg mb-4 text-sm focus:outline-none focus:ring-2 focus:ring-[#9BC31C]" 
          />
          
          <div className="flex-1 overflow-auto">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="bg-gray-100 text-gray-600 text-sm">
                  <th className="p-3 border-b">Docente</th>
                  <th className="p-3 border-b">Vinculación</th>
                  <th className="p-3 border-b text-center">Total Proyectos</th>
                  <th className="p-3 border-b text-center">Como Director</th>
                  <th className="p-3 border-b text-center">Como Evaluador</th>
                  <th className="p-3 border-b text-center">Créditos</th>
                  <th className="p-3 border-b text-center">Estado</th>
                  <th className="p-3 border-b text-center">Acciones</th>
                </tr>
              </thead>
              <tbody>
                {docentesFiltrados.length === 0 ? (
                  <tr><td colSpan={8} className="text-center p-4 text-gray-500">No se encontraron docentes con ese filtro.</td></tr>
                ) : (
                  docentesFiltrados.map((docente) => (
                    <tr key={docente.id} className="border-b hover:bg-gray-50 text-sm">
                      <td className="p-3 font-medium text-gray-800">
                        {docente.nombre}
                        <div className="text-xs text-gray-500">{docente.email}</div>
                      </td>
                      {/* APLICAMOS LA FUNCIÓN PARA LIMPIAR EL N/A */}
                      <td className="p-3 text-xs text-gray-600">
                        {obtenerVinculacion(docente.vinculacion)}
                      </td>
                      <td className="p-3 text-center font-bold">{docente.totalProyectos}</td>
                      <td className="p-3 text-center text-blue-600 font-bold">{docente.totalComoDirector}</td>
                      <td className="p-3 text-center text-purple-600 font-bold">{docente.totalComoEvaluador}</td>
                      <td className={`p-3 text-center font-bold ${docente.creditosDisponibles > 0 ? 'text-green-600' : 'text-red-600'}`}>
                        {docente.creditosDisponibles}
                      </td>
                      <td className="p-3 text-center">
                        {docente.creditosDisponibles > 0 ? (
                          <span className="bg-green-100 text-green-700 px-2 py-1 rounded-full text-xs font-bold">Disponible</span>
                        ) : (
                          <span className="bg-red-100 text-red-700 px-2 py-1 rounded-full text-xs font-bold">No Disponible</span>
                        )}
                      </td>
                      <td className="p-3 text-center">
                        <button onClick={() => setDocenteDetalle(docente)} className="text-[#9BC31C] font-semibold hover:underline text-xs bg-green-50 px-3 py-1 rounded">
                          Ver Detalles
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>

      {/* Modal para Ver Detalles del Docente */}
      {docenteDetalle && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
          <div className="bg-white p-6 rounded-xl shadow-lg w-full max-w-3xl max-h-[85vh] overflow-y-auto">
            <div className="flex justify-between items-start mb-4 border-b pb-4">
              <div>
                <h3 className="text-xl font-bold text-gray-800">{docenteDetalle.nombre}</h3>
                <p className="text-sm text-gray-600">{docenteDetalle.email}</p>
              </div>
              <button onClick={() => setDocenteDetalle(null)} className="text-gray-400 hover:text-red-500 text-2xl font-bold">&times;</button>
            </div>
            
            <div className="grid grid-cols-2 gap-4 mb-6 bg-gray-50 p-4 rounded-lg">
              <div>
                <p className="text-xs text-gray-500 uppercase">Vinculación</p>
                {/* TAMBIÉN LO APLICAMOS EN EL MODAL */}
                <p className="text-sm font-semibold">{obtenerVinculacion(docenteDetalle.vinculacion)}</p>
              </div>
              <div>
                <p className="text-xs text-gray-500 uppercase">Créditos Disponibles</p>
                <p className={`text-sm font-bold ${docenteDetalle.creditosDisponibles > 0 ? 'text-green-600' : 'text-red-600'}`}>
                  {docenteDetalle.creditosDisponibles}
                </p>
              </div>
            </div>

            <h4 className="font-bold text-gray-700 mb-2">Historial de Proyectos Asignados:</h4>
            {docenteDetalle.proyectos && docenteDetalle.proyectos.length > 0 ? (
              <table className="w-full text-left border-collapse mb-4 border border-gray-200">
                <thead>
                  <tr className="bg-gray-100 text-gray-600 text-sm">
                    <th className="p-2 border-b">Título del Trabajo</th>
                    <th className="p-2 border-b text-center">Rol</th>
                    <th className="p-2 border-b text-center">Etapa</th>
                  </tr>
                </thead>
                <tbody>
                  {docenteDetalle.proyectos.map((proy, index) => (
                    <tr key={index} className="border-b text-sm hover:bg-gray-50">
                      <td className="p-2">{proy.titulo || 'Sin título'}</td>
                      <td className="p-2 text-center">
                        <span className={`px-2 py-1 rounded text-xs font-bold ${proy.rol === 'DIRECTOR' ? 'bg-blue-100 text-blue-700' : 'bg-purple-100 text-purple-700'}`}>
                          {proy.rol}
                        </span>
                      </td>
                      <td className="p-2 text-center">{proy.etapa || 'Sin etapa'}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            ) : (
              <div className="text-center p-6 bg-gray-50 rounded-lg border border-dashed border-gray-300">
                <p className="text-sm text-gray-500">Este docente no tiene proyectos asignados actualmente.</p>
              </div>
            )}

            <div className="mt-6 flex justify-end">
              <button onClick={() => setDocenteDetalle(null)} className="bg-gray-200 hover:bg-gray-300 text-gray-800 font-bold py-2 px-6 rounded-lg transition">
                Cerrar
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
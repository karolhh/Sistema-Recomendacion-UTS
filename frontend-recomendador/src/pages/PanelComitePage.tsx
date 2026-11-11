import { useState, useEffect } from 'react';
import { recomendacionService } from '../services/recomendacionService';
import { asignacionService } from '../services/asignacionService';
import type { Recomendacion, DocenteDetalle } from '../types';
import api from '../services/api';

export const PanelComitePage = () => {
  // Solo dos pestañas ahora
  const [activeTab, setActiveTab] = useState<'pendientes' | 'asignados'>('pendientes');
  
  const [trabajos, setTrabajos] = useState<any[]>([]); 
  const [trabajoSeleccionado, setTrabajoSeleccionado] = useState<any | null>(null);
  const [recomendacion, setRecomendacion] = useState<Recomendacion | null>(null);
  const [buscando, setBuscando] = useState(false);
  const [filtroTrabajo, setFiltroTrabajo] = useState('');
  
  const [directorSeleccionado, setDirectorSeleccionado] = useState<string>('');
  const [evaluadorSeleccionado, setEvaluadorSeleccionado] = useState<string>('');
  const [mensaje, setMensaje] = useState<string>('');

  // Docentes solo para los selectores
  const [docentes, setDocentes] = useState<DocenteDetalle[]>([]);

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      const response = await api.get('/trabajos/con-asignaciones');
      const trabajosOrdenados = response.data.sort((a: any, b: any) => 
        (a.titulo || "Sin título").localeCompare(b.titulo || "Sin título")
      );
      setTrabajos(trabajosOrdenados);

      const resDocentes = await api.get('/gestion/docentes');
      setDocentes(resDocentes.data);
    } catch (error) {
      console.error("Error cargando datos", error);
    }
  };

  const handleSeleccionarTrabajo = async (trabajo: any) => {
    setTrabajoSeleccionado(trabajo);
    setRecomendacion(null);
    setDirectorSeleccionado('');
    setEvaluadorSeleccionado('');
    setMensaje('');
    setBuscando(true);
    try {
      const rec = await recomendacionService.getRecomendacion(trabajo.id);
      setRecomendacion(rec);
      if (rec.director) setDirectorSeleccionado(rec.director.id.toString());
      if (rec.evaluador) setEvaluadorSeleccionado(rec.evaluador.id.toString());
    } catch (error) {
      console.error("Error recomendación", error);
    } finally {
      setBuscando(false);
    }
  };

  const handleConfirmarAsignacion = async () => {
    if (!trabajoSeleccionado) return;
    if (!directorSeleccionado || !evaluadorSeleccionado) {
      setMensaje("Por favor, selecciona un director y un evaluador.");
      return;
    }
    if (directorSeleccionado === evaluadorSeleccionado) {
      setMensaje("El director y el evaluador no pueden ser la misma persona.");
      return;
    }

    try {
      await asignacionService.asignar(trabajoSeleccionado.id, Number(directorSeleccionado), "DIRECTOR");
      await asignacionService.asignar(trabajoSeleccionado.id, Number(evaluadorSeleccionado), "EVALUADOR");
      
      setMensaje("¡Asignación realizada con éxito!");
      cargarDatos(); 
    } catch (error) {
      console.error("Error asignando", error);
      setMensaje("Error al realizar la asignación.");
    }
  };

  const trabajosPendientes = trabajos.filter(t => 
    (!t.estaAsignado) && (t.titulo ? t.titulo.toLowerCase().includes(filtroTrabajo.toLowerCase()) : true)
  );
  
  const trabajosAsignados = trabajos.filter(t => 
    (t.estaAsignado) && (t.titulo ? t.titulo.toLowerCase().includes(filtroTrabajo.toLowerCase()) : true)
  );

  return (
    <div className="flex flex-col h-full bg-gray-50">
      
      {/* Pestañas (Solo las dos de la primera imagen) */}
      <div className="flex border-b border-gray-200 bg-white px-6 rounded-t-xl">
        <button onClick={() => setActiveTab('pendientes')} className={`py-4 px-6 font-semibold text-sm border-b-2 transition ${activeTab === 'pendientes' ? 'border-[#9BC31C] text-[#9BC31C]' : 'border-transparent text-gray-500'}`}>
          Pendientes por Asignar
        </button>
        <button onClick={() => setActiveTab('asignados')} className={`py-4 px-6 font-semibold text-sm border-b-2 transition ${activeTab === 'asignados' ? 'border-[#9BC31C] text-[#9BC31C]' : 'border-transparent text-gray-500'}`}>
          Trabajos Asignados
        </button>
      </div>

      <div className="flex-1 p-6 overflow-hidden">
        
        {/* Pestaña: Pendientes por Asignar */}
        {activeTab === 'pendientes' && (
          <div className="flex flex-col lg:flex-row gap-6 h-full">
            <div className="lg:w-1/3 flex flex-col bg-white rounded-xl shadow-sm border border-gray-100 p-4 h-full">
              <h2 className="text-lg font-bold text-gray-700 mb-4">Trabajos Pendientes</h2>
              <input type="text" placeholder="Filtrar..." value={filtroTrabajo} onChange={(e) => setFiltroTrabajo(e.target.value)} className="w-full px-3 py-2 border border-gray-300 rounded-lg mb-4 text-sm focus:outline-none focus:ring-2 focus:ring-[#9BC31C]" />
              <div className="flex-1 overflow-y-auto space-y-2 pr-2">
                {trabajosPendientes.length === 0 ? (
                   <p className="text-sm text-gray-500 text-center mt-4">No hay trabajos pendientes.</p>
                ) : (
                  trabajosPendientes.map(trabajo => (
                    <div key={trabajo.id} onClick={() => handleSeleccionarTrabajo(trabajo)} className={`p-3 rounded-lg border cursor-pointer transition ${trabajoSeleccionado?.id === trabajo.id ? 'border-[#9BC31C] bg-green-50' : 'border-gray-200 hover:border-[#9BC31C]'}`}>
                      <h3 className="font-semibold text-gray-800 text-xs">{trabajo.titulo || 'Sin título'}</h3>
                      <div className="flex justify-between text-xs text-gray-500 mt-2">
                        <span>ID: {trabajo.id}</span>
                        <span>{trabajo.etapa || 'Sin etapa'}</span>
                      </div>
                    </div>
                  ))
                )}
              </div>
            </div>

            <div className="lg:w-2/3 bg-white rounded-xl shadow-sm border border-gray-100 p-6 overflow-y-auto">
              {!trabajoSeleccionado ? (
                <div className="flex flex-col items-center justify-center h-full text-gray-400">
                  <span className="text-6xl mb-4">👈</span>
                  <p>Selecciona un trabajo pendiente.</p>
                </div>
              ) : (
                <div>
                  <div className="mb-6 border-b pb-4">
                    <h2 className="text-xl font-bold">{trabajoSeleccionado.titulo || 'Sin título'}</h2>
                    <p className="text-sm text-gray-500">ID: {trabajoSeleccionado.id} | Estudiante: {trabajoSeleccionado.estudiante || 'N/A'}</p>
                  </div>
                  
                  {buscando ? (
                    <div className="text-center py-10 text-[#9BC31C]">Buscando...</div>
                  ) : (
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                      <div className="bg-gray-50 p-4 rounded-lg border border-gray-200">
                        <h3 className="font-bold text-gray-700 mb-3">Director</h3>
                        {recomendacion && recomendacion.director && (
                          <div className="mb-4 p-3 bg-white border border-[#9BC31C] rounded">
                            <p className="text-xs font-bold text-[#9BC31C]">Sugerencia:</p>
                            <p className="font-semibold">{recomendacion.director.nombre}</p>
                          </div>
                        )}
                        <select value={directorSeleccionado} onChange={(e) => setDirectorSeleccionado(e.target.value)} className="w-full p-2 border rounded text-sm">
                          <option value="">Seleccione un director...</option>
                          {docentes.map(d => <option key={d.id} value={d.id}>{d.nombre}</option>)}
                        </select>
                      </div>
                      
                      <div className="bg-gray-50 p-4 rounded-lg border border-gray-200">
                        <h3 className="font-bold text-gray-700 mb-3">Evaluador</h3>
                        {recomendacion && recomendacion.evaluador && (
                          <div className="mb-4 p-3 bg-white border border-gray-300 rounded">
                            <p className="text-xs font-bold text-gray-500">Sugerencia:</p>
                            <p className="font-semibold">{recomendacion.evaluador.nombre}</p>
                          </div>
                        )}
                        <select value={evaluadorSeleccionado} onChange={(e) => setEvaluadorSeleccionado(e.target.value)} className="w-full p-2 border rounded text-sm">
                          <option value="">Seleccione un evaluador...</option>
                          {docentes.map(d => <option key={d.id} value={d.id}>{d.nombre}</option>)}
                        </select>
                      </div>
                    </div>
                  )}
                  
                  {mensaje && (
                    <div className={`mt-4 p-3 rounded text-sm text-center ${mensaje.includes('éxito') ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}`}>
                      {mensaje}
                    </div>
                  )}

                  <div className="mt-8 flex justify-end">
                    <button onClick={handleConfirmarAsignacion} className="bg-[#9BC31C] text-white font-bold py-2 px-6 rounded-lg hover:bg-[#7A9E14] transition">
                      Confirmar Asignación
                    </button>
                  </div>
                </div>
              )}
            </div>
          </div>
        )}

        {/* Pestaña: Trabajos Asignados */}
        {activeTab === 'asignados' && (
          <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6 h-full flex flex-col">
            <h2 className="text-xl font-bold text-gray-700 mb-4">Trabajos con Asignación Completa</h2>
            <input type="text" placeholder="Filtrar..." value={filtroTrabajo} onChange={(e) => setFiltroTrabajo(e.target.value)} className="w-full max-w-md px-3 py-2 border rounded-lg mb-4 text-sm focus:outline-none focus:ring-2 focus:ring-[#9BC31C]" />
            
            <div className="flex-1 overflow-auto">
              <table className="w-full text-left border-collapse">
                <thead>
                  <tr className="bg-gray-100 text-gray-600 text-sm">
                    <th className="p-3 border-b">Trabajo de Grado</th>
                    <th className="p-3 border-b">Director Asignado</th>
                    <th className="p-3 border-b">Evaluador Asignado</th>
                    <th className="p-3 border-b text-center">Etapa</th>
                  </tr>
                </thead>
                <tbody>
                  {trabajosAsignados.length === 0 ? (
                    <tr><td colSpan={4} className="text-center p-4 text-gray-500">No hay trabajos asignados aún.</td></tr>
                  ) : (
                    trabajosAsignados.map((trabajo) => (
                      <tr key={trabajo.id} className="border-b hover:bg-gray-50 text-sm">
                        <td className="p-3 font-medium text-gray-800">
                          {trabajo.titulo || 'Sin título'}
                          <div className="text-xs text-gray-500">ID: {trabajo.id}</div>
                        </td>
                        <td className="p-3 text-blue-600 font-semibold">{trabajo.directorAsignado || 'N/A'}</td>
                        <td className="p-3 text-purple-600 font-semibold">{trabajo.evaluadorAsignado || 'N/A'}</td>
                        <td className="p-3 text-center">
                          <span className="bg-gray-200 text-gray-700 px-2 py-1 rounded-full text-xs font-bold">{trabajo.etapa || 'Sin etapa'}</span>
                        </td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};
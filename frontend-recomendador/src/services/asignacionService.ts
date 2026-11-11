import api from './api';

export const asignacionService = {
  asignar: async (trabajoId: number, docenteId: number, rol: string): Promise<any> => {
    const response = await api.post('/asignacion/manual', {
      trabajoId,
      docenteId,
      rol
    });
    return response.data;
  }
};
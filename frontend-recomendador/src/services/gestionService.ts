import api from './api';
import type { DocenteDetalle } from '../types';

export const gestionService = {
  getAllDocentes: async (): Promise<DocenteDetalle[]> => {
    const response = await api.get('/gestion/docentes');
    return response.data;
  },
};
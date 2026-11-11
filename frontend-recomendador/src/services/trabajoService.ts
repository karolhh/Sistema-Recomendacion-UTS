import api from './api';
import type { TrabajoGrado } from '../types';

export const trabajoService = {
  getAll: async (): Promise<TrabajoGrado[]> => {
    const response = await api.get('/mysql/trabajos');
    return response.data;
  },
};
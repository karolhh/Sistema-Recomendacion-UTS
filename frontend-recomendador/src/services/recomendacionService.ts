import api from './api';
import type { Recomendacion } from '../types';

export const recomendacionService = {
  getRecomendacion: async (trabajoId: number): Promise<Recomendacion> => {
    const response = await api.get(`/recomendacion/trabajo/${trabajoId}`);
    return response.data[0];
  },
};
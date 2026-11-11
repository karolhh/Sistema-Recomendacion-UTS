import api from './api';

export interface LoginRequest { username: string; password: string; }

export const authService = {
  login: async (data: LoginRequest): Promise<string> => {
      const response = await api.post('/auth/login', data);
    return response.data;
  },
};
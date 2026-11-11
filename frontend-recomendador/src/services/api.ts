import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' },
});

// Interceptor de Peticiones (Inyecta el token)
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => Promise.reject(error));

// Interceptor de Respuestas (Maneja errores de sesión)
api.interceptors.response.use(
  (response) => response, 
  (error) => {
    // Solo redirigir si el token falló y NO estamos intentando hacer login
    if ((error.response?.status === 401 || error.response?.status === 403) && !error.config.url.includes('/auth/login')) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
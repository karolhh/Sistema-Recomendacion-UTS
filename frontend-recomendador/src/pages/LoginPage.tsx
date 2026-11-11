import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';
import { saveToken } from '../utils/auth';

export const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const token = await authService.login({ username, password });
      
      // Verificamos que el token no venga vacío o con error
      if (token && token.length > 10) {
        saveToken(token);
        // Usamos window.location.href para forzar una recarga completa y que Spring Security actualice el estado
        window.location.href = '/dashboard'; 
      } else {
        setError('El servidor no devolvió un token válido.');
      }
    } catch (err: any) {
      console.error("Error en login:", err);
      setError('Credenciales incorrectas o error de conexión.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 px-4">
      <div className="w-full max-w-md">
        
        <div className="text-center mb-8 flex flex-col sm:flex-row justify-center items-center gap-4">
          <img src="/logo-uts.png" alt="UTS" className="h-20 sm:h-24" />
        </div>

        <div className="bg-white p-6 sm:p-8 rounded-xl shadow-lg">
          <h2 className="text-xl sm:text-2xl font-bold text-gray-700 text-center mb-8">
            Iniciar <span className="bg-[#9BC31C] text-white px-3 py-1 rounded">SESIÓN</span>
          </h2>

          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <label className="block text-sm font-medium text-gray-600 mb-1">✉️ Correo:</label>
              <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#9BC31C] bg-blue-50"
                placeholder="usuario@uts.edu.co"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-600 mb-1">🔒 Contraseña:</label>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#9BC31C] bg-blue-50"
                placeholder="********"
                required
              />
            </div>

            {error && (
              <div className="p-3 bg-red-100 text-red-700 rounded-lg text-sm text-center">
                {error}
              </div>
            )}

            <div className="pt-4">
              <button
                type="submit"
                disabled={loading}
                className="w-full bg-[#9BC31C] text-white font-bold py-3 rounded-lg hover:bg-[#7A9E14] transition shadow-md"
                style={{ padding: '12px 32px', fontSize: '1.125rem' }}
              >
                {loading ? 'Ingresando...' : 'Ingresar'}
              </button>
            </div>
          </form>

          <div className="flex flex-col sm:flex-row justify-between mt-8 text-sm gap-2">
            <Link to="/register" className="text-gray-500 hover:text-[#9BC31C] underline text-center sm:text-left">
              Crear cuenta
            </Link>
            <a href="#" className="text-gray-500 hover:text-[#9BC31C] underline text-center sm:text-right">
              ¿Olvidó su contraseña?
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};
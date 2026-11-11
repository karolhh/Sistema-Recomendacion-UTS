export interface Docente {
  id: number;
  nombre: string;
  email: string;
  programa: string;
  vinculacion: string;
  master?: string;
  puntaje?: number;
  creditosDisponibles?: number;
}

export interface TrabajoGrado {
  id: number;
  titulo: string;
  codigoTg: string;
  estudiante: string;
  modalidad: string;
  nivel: string;
  etapa: string;
  director?: string;
  evaluador?: string;
}

export interface Recomendacion {
  director: Docente;
  evaluador: Docente;
}

export interface ProyectoAsignado {
  trabajoId: number;
  titulo: string;
  etapa: string;
  rol: string;
}

export interface DocenteDetalle {
  id: number;
  nombre: string;
  email: string;
  programa: string;
  vinculacion: string;
  totalProyectos: number;
  totalComoDirector: number;
  totalComoEvaluador: number;
  creditosDisponibles: number;
  proyectos: ProyectoAsignado[];
}
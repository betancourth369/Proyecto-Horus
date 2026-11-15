export interface ClimaDTO {
  ciudad: string;
  temperatura: number;
  descripcion: string;
  humedad: number;
  sensacionTermica: number;
  region?: string;
  pais?: string;
  horaLocal?: string;
}
export interface ShoppingItemDTO {
  id?: number;
  nombre: string;
  cantidad: number;
  unidad: string;
  comprado: boolean;
  taskId?: number;
  taskNombre?: string;
}
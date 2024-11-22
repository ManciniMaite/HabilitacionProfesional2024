import { Animal } from "../Animal";

export const DataAnimal: Animal = {
    esActivo: true,
    nombre: 'Agatha',
    fechaNac: '05/05/12',
    peso: 4.5,
    foto: 'Foto del animal',
    raza: {
        id:1,
        nombre: 'Persa',
        especie: {
            id:1,
            nombre: 'Gato',
            tipoEspecie: {
                nombre: 'Chico',
                descripcion: 'Animales pequeños'
            }
        },
    }
}
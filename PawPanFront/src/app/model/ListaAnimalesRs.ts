import { Animal } from "./Animal";
import { Response } from "./Response";

export class ListaAnimalesRs extends Response{
    animales: Animal[];
}
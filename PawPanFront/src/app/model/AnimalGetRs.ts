import { Animal } from "./Animal";
import { Response } from "./Response";

export class AnimalGetRs extends Response{
    animales: Animal[];
}
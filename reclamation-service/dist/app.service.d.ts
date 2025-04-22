import { Model } from 'mongoose';
import { Reclamation, ReclamationDocument } from './reclamation.schema';
export declare class AppService {
    private readonly reclamationModel;
    constructor(reclamationModel: Model<ReclamationDocument>);
    createReclamation(reclamation: Reclamation): Promise<Reclamation>;
    findAllReclamations(): Promise<Reclamation[]>;
    deleteReclamation(id: string): Promise<import("mongoose").Document<unknown, {}, ReclamationDocument> & Reclamation & import("mongoose").Document<unknown, any, any> & Required<{
        _id: unknown;
    }> & {
        __v: number;
    }>;
}

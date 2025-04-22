import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { Reclamation, ReclamationDocument } from './reclamation.schema';

@Injectable()
export class AppService {
  constructor(
    @InjectModel(Reclamation.name) private readonly reclamationModel: Model<ReclamationDocument>,
  ) {}

  async createReclamation(reclamation: Reclamation): Promise<Reclamation> {
    const newReclamation = new this.reclamationModel(reclamation);
    return newReclamation.save();
  }

  async findAllReclamations(): Promise<Reclamation[]> {
    return this.reclamationModel.find().exec();
  }
  async deleteReclamation(id: string) {
    return this.reclamationModel.findByIdAndDelete(id);
  }
}

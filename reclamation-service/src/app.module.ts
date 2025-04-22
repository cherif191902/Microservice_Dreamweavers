import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { AppConfigModule } from './config.module';  // Import Config Module
import { AppConfigService } from './config.service';
import { EurekaService } from './eureka.service';
import { Reclamation, ReclamationSchema } from './reclamation.schema';

@Module({
  imports: [
    AppConfigModule, // Ensure AppConfigService is available
    
    MongooseModule.forRootAsync({
      imports: [AppConfigModule], // Import it here
      useFactory: async (appConfigService: AppConfigService) => {
        await appConfigService.loadConfig();

        return {
          uri: `mongodb://${appConfigService.get('spring.data.mongodb.username')}:${appConfigService.get('spring.data.mongodb.password')}@${appConfigService.get('spring.data.mongodb.host')}:${appConfigService.get('spring.data.mongodb.port')}/${appConfigService.get('spring.data.mongodb.database')}?authSource=${appConfigService.get('spring.data.mongodb.authentication-database')}`,
        };
      },
      inject: [AppConfigService], // Ensure AppConfigService is injected
    }),
    MongooseModule.forFeature([{ name: Reclamation.name, schema: ReclamationSchema }]),
  ],
  controllers: [AppController],
  providers: [AppService,EurekaService],
})
export class AppModule {
  
}

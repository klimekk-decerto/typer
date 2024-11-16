import {mergeApplicationConfig, ApplicationConfig, importProvidersFrom} from '@angular/core';
import { provideServerRendering } from '@angular/platform-server';
import { appConfig } from './app.config';
import {provideHttpClient, withFetch, withInterceptorsFromDi} from "@angular/common/http";
import {provideAnimations, provideNoopAnimations} from "@angular/platform-browser/animations";
import {CommonModule} from "@angular/common";
import {provideAnimationsAsync} from "@angular/platform-browser/animations/async";

const serverConfig: ApplicationConfig = {
  providers: [
    provideServerRendering(), provideHttpClient(withInterceptorsFromDi(), withFetch()), CommonModule, provideNoopAnimations()
    // , provideClientHydration()
  ]
};

export const config = mergeApplicationConfig(appConfig, serverConfig);

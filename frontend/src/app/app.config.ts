import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {CommonModule} from "@angular/common";
import {provideAnimations} from "@angular/platform-browser/animations";
import {
  HttpClientModule,
  provideHttpClient,
  withFetch,
  withInterceptors,
  withInterceptorsFromDi
} from "@angular/common/http";
import {tokenInterceptor} from "./core/interceptor/auth.interceptor";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    // provideClientHydration(),
    provideHttpClient(withInterceptorsFromDi(), withFetch(), withInterceptors([tokenInterceptor])),
    CommonModule,
    provideAnimations(),
    HttpClientModule
  ]
};

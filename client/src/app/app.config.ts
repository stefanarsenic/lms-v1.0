import {ApplicationConfig, importProvidersFrom, provideZoneChangeDetection} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {AuthInterceptor} from "./interceptors/auth.interceptor";
import {provideAnimations} from "@angular/platform-browser/animations";
import {ConfirmationService, MessageService} from "primeng/api";
export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),provideHttpClient(withInterceptorsFromDi()),
    {provide:HTTP_INTERCEPTORS,useClass:AuthInterceptor,multi:true},
    provideAnimations(),
    {provide:MessageService },
    {provide:ConfirmationService}
  ]
};

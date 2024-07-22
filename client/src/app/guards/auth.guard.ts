import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {LoginService} from "../services/login.service";

export const authGuard: CanActivateFn = (route, state) => {
  let loginService: LoginService = inject(LoginService);
  let router: Router = inject(Router);

  if (loginService.isAuthenticated() && loginService.proveraUloga(route.data["allowedRoles"])){
    return true;
  }
  router.navigate(['/login']);
  return false;
};

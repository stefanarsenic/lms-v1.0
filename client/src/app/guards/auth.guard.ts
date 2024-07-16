import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {LoginService} from "../services/login.service";

export const authGuard: CanActivateFn = (route, state) => {
  let loginService=inject(LoginService)
  let router=inject(Router)
  if (loginService.proveraUloga(route.data["allowedRoles"])){
    return true
  }

  return router.createUrlTree(["/login"]);
};

import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from "rxjs";
import {LoginService} from "../services/login.service";
import {Injectable} from "@angular/core";


@Injectable()
export class AuthInterceptor implements HttpInterceptor{

  constructor(private loginService: LoginService) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.loginService.getToken();
    if (token) {
      const newReq = req.clone({ headers: req.headers.set("Authorization", token) });
      return next.handle(newReq);
    }
    return next.handle(req);
  }
}

import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor{

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const accessToken = localStorage.getItem('access_token');

    if(accessToken){
      const authReq = req.clone({
        headers: new HttpHeaders({
          'Authorization': `Bearer ${accessToken}`
        })
      });
      return next.handle(authReq);
    }
    return next.handle(req);
  }
}

import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AccessGuardService implements CanActivate {

  constructor(
    private router: Router
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const accessToken = localStorage.getItem('access_token');

    if(accessToken){
      const jwtHelper = new JwtHelperService();
      const isTokenNonExpired = !jwtHelper.isTokenExpired(accessToken);

      if(isTokenNonExpired){
        return true;
      }
    }

    this.router.navigate(['login']);
    return false;
  }
}

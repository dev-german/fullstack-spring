import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class UserInfoService {

  constructor(
    private jwtHelper: JwtHelperService
  ) { }

  getUsername(): string {
    const jwtToken = localStorage.getItem('access_token');

    if(jwtToken){
      const decodedToken = this.jwtHelper.decodeToken(jwtToken);
      return decodedToken.sub;
    }

    return '--'
  }

  getUserRole(): string {
    const jwtToken = localStorage.getItem('access_token');

    if(jwtToken){
      const decodedToken = this.jwtHelper.decodeToken(jwtToken);
      return decodedToken.scopes;
    }

    return '--'
  }
}

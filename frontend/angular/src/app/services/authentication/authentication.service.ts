import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthenticationRequest } from 'src/app/models/authentication-request';
import { AuthenticationResponse } from 'src/app/models/authentication-response';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly authUrl = `${environment.api.baseUrl}/${environment.api.authUrl}`

  constructor(
    private http: HttpClient
  ) { }

  login(authRequest: AuthenticationRequest): Observable<any> {
    return this.http.post<any>(this.authUrl, authRequest, {
      observe: 'response'
    });
  }

}

import { HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationRequest } from 'src/app/models/authentication-request';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  authenticationRequest: AuthenticationRequest = {};
  errorMsg = ''

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) { }

  login(){
    this.errorMsg = '';
    this.authenticationService.login(this.authenticationRequest)
    .subscribe({
      next: (res: HttpResponse<any>) =>{
        console.log(res.headers.get('Authorization'));
        localStorage.setItem('access_token', res.headers.get('Authorization')!);
        this.router.navigate(['/customers']);
        
      },
      error: (err) =>{
        if(err.error.statusCode === 401){
          this.errorMsg = 'Login and/or password is incorrect'
        }
        
      }
    })
  }

  register(){
    this.router.navigate(['/register']);
  }

}

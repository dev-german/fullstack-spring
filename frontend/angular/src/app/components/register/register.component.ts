import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationRequest } from 'src/app/models/authentication-request';
import { CustomerRegistrationRequest } from 'src/app/models/customer-registration-request';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { CustomerService } from 'src/app/services/customer/customer.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  errorMsg = '';

  customer: CustomerRegistrationRequest = {}

  constructor(
    private router: Router,
    private customerService: CustomerService,
    private authenticationService: AuthenticationService
  ) { }

  login(){
    this.router.navigate(['/login']);
  }

  createAccount(){
    this.customerService.save(this.customer).subscribe({
      next: () => {
        const authReq : AuthenticationRequest = {
          username: this.customer.email,
          password: this.customer.password
        }
        this.authenticationService.login(authReq).subscribe({
          next: (res) => {
            localStorage.setItem('access_token', res.headers.get('Authorization')!);
            this.router.navigate(['customers']);
          },
          error: (err) => {
            if(err.error.statusCode === 401){
              this.errorMsg = 'Login and/or password is incorrect'
            }
          }
        })
      }
    })
  }

}

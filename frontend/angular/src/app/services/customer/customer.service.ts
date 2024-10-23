import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomerDTO } from 'src/app/models/customer-dto';
import { CustomerRegistrationRequest } from 'src/app/models/customer-registration-request';
import { CustomerUpdateRequest } from 'src/app/models/customer-update-request';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private readonly customerUrl = `${environment.api.baseUrl}/${environment.api.customerUrl}`

  constructor(
    private http: HttpClient
  ) { }

  findAll(): Observable<CustomerDTO[]> {
    return this.http.get<CustomerDTO[]>(this.customerUrl)
  }

  save(customer: CustomerRegistrationRequest): Observable<void>{
    return this.http.post<void>(this.customerUrl, customer)
  }

  delete(id: number | undefined): Observable<void> {
    return this.http.delete<void>(`${this.customerUrl}/${id}`)
  }

  update(id: number | undefined, customer: CustomerUpdateRequest): Observable<void> {
    return this.http.put<void>(`${this.customerUrl}/${id}`, customer)
  }
}

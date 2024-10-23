import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CustomerDTO } from 'src/app/models/customer-dto';
import { CustomerRegistrationRequest } from 'src/app/models/customer-registration-request';
import { CustomerService } from 'src/app/services/customer/customer.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss'],
})
export class CustomerComponent implements OnInit {
  display = false;
  operation: 'create' | 'update' = 'create';
  customers: CustomerDTO[] = [];
  customer: CustomerRegistrationRequest = {};

  constructor(
    private customerService: CustomerService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.findAllCustomers();
  }

  private findAllCustomers() {
    this.customerService.findAll().subscribe({
      next: (res) => {
        this.customers = res;
      },
    });
  }

  save(customer: CustomerRegistrationRequest) {
    if (customer) {
      if(this.operation === 'create'){
        this.customerService.save(customer).subscribe({
          next: () => {
            this.findAllCustomers();
            this.display = false;
            this.customer = {};
            this.messageService.add({
              severity: 'success',
              summary: 'Customer saved',
              detail: `Customer ${customer.name} saved successfully`,
            });
          },
        });
      } else if(this.operation === 'update'){
        this.customerService.update(customer.id, customer).subscribe({
          next: () => {
            this.findAllCustomers();
            this.display = false;
            this.customer = {};
            this.messageService.add({
              severity: 'success',
              summary: 'Customer updated',
              detail: `Customer ${customer.name} was successfully updated`,
            });
          },
        });
      }
      
    }
  }

  delete(customer: CustomerDTO) {
    this.confirmationService.confirm({
      header: 'Delete customer',
      message: `Are you sure you want to delete ${customer.name}? You can't undo this action afterwards.`,
      accept: () => {
        this.customerService.delete(customer.id).subscribe({
          next: () => {
            this.findAllCustomers();
            this.messageService.add({
              severity: 'error',
              summary: 'Customer deleted',
              detail: `Customer ${customer.name} was successfully deleted`,
            });
          },
        });
      }
    });
  }

  update(customer: CustomerDTO) {
    this.display = true;
    this.operation = 'update';
    this.customer = customer;
  }

  create(){
    this.display = true;
    this.customer = {};
    this.operation = 'create';
  }

  cancel(){
    this.display = false;
    this.customer = {};
    this.operation = 'create';
  }
}
